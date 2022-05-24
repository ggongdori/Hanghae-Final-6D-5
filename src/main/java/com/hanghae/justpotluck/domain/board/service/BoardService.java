package com.hanghae.justpotluck.domain.board.service;

import com.hanghae.justpotluck.domain.board.dto.request.BoardSaveRequestDto;
import com.hanghae.justpotluck.domain.board.dto.request.BoardUpdateRequestDto;
import com.hanghae.justpotluck.domain.board.dto.response.board.BoardOneResponse;
import com.hanghae.justpotluck.domain.board.dto.response.board.BoardSaveResponse;
import com.hanghae.justpotluck.domain.board.dto.response.board.BoardUpdateResponse;
import com.hanghae.justpotluck.domain.board.entity.Board;
import com.hanghae.justpotluck.domain.board.entity.Bookmark;
import com.hanghae.justpotluck.domain.board.entity.Image;
import com.hanghae.justpotluck.domain.board.repository.BoardImageRepository;
import com.hanghae.justpotluck.domain.board.repository.BoardRepository;
import com.hanghae.justpotluck.domain.board.repository.BookmarkRepository;
import com.hanghae.justpotluck.domain.user.repository.UserRepository;
import com.hanghae.justpotluck.global.aws.S3Uploader;
import com.hanghae.justpotluck.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardImageRepository boardImageRepository;
    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;
    private final S3Uploader s3Uploader;
    private final UserUtil userUtil;

//    private final FileHandler fileHandler;


    @Transactional
    public BoardSaveResponse saveBoard(BoardSaveRequestDto requestDto) throws Exception {
//        User user = userUtil.findCurrentUser();
        Board board = boardRepository.save(Board.createBoard(requestDto));
        List<String> boardImages = uploadBoardImages(requestDto, board);

        return new BoardSaveResponse(board.getId(), requestDto.getProcessList(), requestDto.getCategory(), boardImages);
    }

//    private List<String> uploadProcessList(BoardSaveRequestDto requestDto, Board board) {
//        return requestDto.getProcessList().stream()
//                .map(recipeProcess -> recipeProcess.toString())
//                .collect(Collectors.toList());
//    }
    private List<String> uploadBoardImages(BoardSaveRequestDto requestDto, Board board) {
        return requestDto.getImages().stream()
                .map(image -> s3Uploader.upload(image, "board"))
                .map(url -> saveBoardImage(board, url))
                .map(boardImage -> boardImage.getImageUrl())
                .collect(Collectors.toList());
    }

    private Image saveBoardImage(Board board, String url) {
        return boardImageRepository.save(Image.builder()
                .imageUrl(url)
                .storeFileName(StringUtils.getFilename(url))
                .board(board)
                .build());
    }

    @Transactional
    public BoardUpdateResponse updateBoard(Long boardId, BoardUpdateRequestDto requestDto) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다.")
        );
        validateDeletedImages(requestDto);
        uploadBoardImages(requestDto, board);
        List<String> saveImages = getSaveImages(requestDto);
        board.update(requestDto);
        return new BoardUpdateResponse(board.getId(), saveImages);
    }

    private void validateDeletedImages(BoardUpdateRequestDto requestDto) {
        boardImageRepository.findBySavedImageUrl(requestDto.getBoardId()).stream()
                .filter(image -> !requestDto.getSaveImageUrl().stream().anyMatch(Predicate.isEqual(image.getImageUrl())))
                .forEach(url -> {
                    boardImageRepository.delete(url);
                    s3Uploader.deleteImage(url.getImageUrl());
                });
    }
    private void uploadBoardImages(BoardUpdateRequestDto requestDto, Board board) {
        requestDto.getImages()
                .stream()
                .forEach(file -> {
                    String url = s3Uploader.upload(file, "board");
                    saveBoardImage(board, url);
                });
    }
    private List<String> getSaveImages(BoardUpdateRequestDto requestDto) {
        return boardImageRepository.findBySavedImageUrl(requestDto.getBoardId())
                .stream()
                .map(image -> image.getImageUrl())
                .collect(Collectors.toList());
    }


    @Transactional
    public BoardOneResponse getOneBoard(Long boardId) {
        return boardRepository.findOneBoardById(boardId).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다.")
        );
    }

    public Board getBoard(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다.")
        );
        return board;
    }

    @Transactional
    public void deleteBoard(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        boardRepository.delete(board);
    }

    @Transactional
    public boolean bookmarkBoard(Long boardId) {
        Board board = getBoard(boardId);

        Optional<Bookmark> bookmark = bookmarkRepository.findByBoard(board);

        if (bookmark.isEmpty()) {
            bookmarkRepository.save(Bookmark.createBookmark(board));
            return true;
        } else {
            bookmarkRepository.delete(bookmark.get());
            return false;
        }
    }



//    @Transactional
//    public BoardOneResponse getOneBoard(Long boardId) {
//        return boardRepository.findOneBoardById(boardId).orElseThrow(
//                () -> new IllegalArgumentException("해당 게시글이 없습니다.")
//        );
//    }


}
