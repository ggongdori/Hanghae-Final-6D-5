package com.hanghae.justpotluck.domain.user.service;

import com.hanghae.justpotluck.domain.board.dto.response.board.BoardListResponse;
import com.hanghae.justpotluck.domain.board.entity.Board;
import com.hanghae.justpotluck.domain.board.entity.Bookmark;
import com.hanghae.justpotluck.domain.board.repository.BoardImageRepository;
import com.hanghae.justpotluck.domain.board.repository.BoardRepository;
import com.hanghae.justpotluck.domain.board.repository.BookmarkRepository;
import com.hanghae.justpotluck.domain.comment.entity.Comments;
import com.hanghae.justpotluck.domain.comment.repository.CommentRepository;
import com.hanghae.justpotluck.domain.community.entity.Posts;
import com.hanghae.justpotluck.domain.community.repository.PostRepository;
import com.hanghae.justpotluck.domain.user.dto.request.UserLocationUpdateRequestDto;
import com.hanghae.justpotluck.domain.review.entity.Review;
import com.hanghae.justpotluck.domain.review.repository.ReviewRepository;
import com.hanghae.justpotluck.domain.user.dto.request.UserUpdateRequest;
import com.hanghae.justpotluck.domain.user.dto.response.*;
import com.hanghae.justpotluck.domain.user.entity.User;
import com.hanghae.justpotluck.domain.user.repository.UserRepository;
import com.hanghae.justpotluck.global.aws.S3Uploader;
import com.hanghae.justpotluck.global.exception.CustomException;
import com.hanghae.justpotluck.global.exception.ErrorCode;
import com.hanghae.justpotluck.global.security.TokenAuthenticationFilter;
import com.hanghae.justpotluck.global.security.TokenProvider;
import com.hanghae.justpotluck.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserUtil userUtil;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final ReviewRepository reviewRepository;
    private final BookmarkRepository bookmarkRepository;
    private final TokenProvider tokenProvider;
    private final TokenAuthenticationFilter authenticationFilter;
    private final BoardImageRepository boardImageRepository;
    private final S3Uploader s3Uploader;
    private final RedisTemplate redisTemplate;


    public User getUser() {
        return userUtil.findCurrentUser();
    }

    public User getUser(String email) {
        return findUser(email);
    }

    private User findUser(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_MEMBER)
        );
    }
    @Transactional
    public User locationUpdate(UserLocationUpdateRequestDto updateRequest){
        User user = userUtil.findCurrentUser();
        user.locationupdate(updateRequest);
        return user;
    }
    public Boolean isDuplicateEmail(String userEmail) {
        if (userRepository.existsByEmail(userEmail)) {
            throw new CustomException(ErrorCode.ALREADY_EMAIL_EXISTS);
        } else {
            return false;
        }
    }

    @Transactional
    public User update(UserUpdateRequest userUpdateRequest) {
        User user = userUtil.findCurrentUser();
        // 닉네임 중복 확인
        if (userRepository.existsByName(userUpdateRequest.getName())) {
            throw new CustomException(ErrorCode.ALREADY_NICKNAME_EXISTS);
        }

//        String imageUrl = s3Uploader.upload(userUpdateRequest.getProfileImage(), "profile");

//        // 이메일 중복 확인
//        if (userRepository.existsByEmail(userUpdateRequest.getEmail())) {
//            throw new CustomException(ErrorCode.ALREADY_EMAIL_EXISTS);
//        }
        user.update(userUpdateRequest);
        return user;
    }


    public Page<MyPostResponse> findMyPosts(Pageable pageable) {
        User user = userUtil.findCurrentUser();
        Page<Posts> post = postRepository.findByUserOrderByPostIdDesc(user, pageable);

        List<MyPostResponse> postResponses =
                post.stream()
                        .map(MyPostResponse::toMyPostResponse)
                        .collect(Collectors.toList());

        return new PageImpl<>(postResponses, pageable, post.getTotalElements());
    }
//    @Transactional
//    public List<PostListResponse> findMyPost(Pageable pageable) {
//        User user = userUtil.findCurrentUser();
//        List<BoardListResponse> listMyBoard = new ArrayList<>();
//        Page<Board> boards = boardRepository.findByUserOrderByIdDesc(user, pageable);
//
//        for (Board board : boards) {
//            List<String> boardImages = boardImageRepository.findBySavedImageUrl(board.getId())
//                    .stream()
//                    .map(image ->image.getImageUrl())
//                    .collect(Collectors.toList());
//            listMyBoard.add(new BoardListResponse(board, boardImages));
//        }
//        return listMyBoard;
//    }

    @Transactional
    public List<BoardListResponse> findMyBoard(Pageable pageable) {
        User user = userUtil.findCurrentUser();
        List<BoardListResponse> listMyBoard = new ArrayList<>();
        Page<Board> boards = boardRepository.findByUserOrderByIdDesc(user, pageable);

        for (Board board : boards) {
            List<String> boardImages = boardImageRepository.findBySavedImageUrl(board.getId())
                    .stream()
                    .map(image ->image.getImageUrl())
                    .collect(Collectors.toList());
            listMyBoard.add(new BoardListResponse(board, boardImages));
        }
        return listMyBoard;
    }
//    public Page<MyBoardResponse> findMyBoard(Pageable pageable) {
//        User user = userUtil.findCurrentUser();
//        Page<Board> board = boardRepository.findByUserOrderByIdDesc(user, pageable);
//
//        List<MyBoardResponse> boardResponses =
//                board.stream()
//                        .map(MyBoardResponse::toMyBoardResponse)
//                        .collect(Collectors.toList());
//
//        return new PageImpl<>(boardResponses, pageable, board.getTotalElements());
//    }

    public Page<MyBookmarkResponse> findMyBookmark(Pageable pageable) {
        User user = userUtil.findCurrentUser();
        Page<Bookmark> bookmark = bookmarkRepository.findByUserOrderByIdDesc(user, pageable);

        List<MyBookmarkResponse> bookmarkResponses =
                bookmark.stream()
                        .map(MyBookmarkResponse::toMyBookmarkResponse)
                        .collect(Collectors.toList());

        return new PageImpl<>(bookmarkResponses, pageable, bookmark.getTotalElements());
    }

    public Page<MyCommentResponse> findMyComment(Pageable pageable) {
        User user = userUtil.findCurrentUser();
        Page<Comments> comment = commentRepository.findByUserOrderByCommentIdDesc(user, pageable);

        List<MyCommentResponse> commentResponses =
                comment.stream()
                        .map(MyCommentResponse::toMyCommentResponse)
                        .collect(Collectors.toList());

        return new PageImpl<>(commentResponses, pageable, comment.getTotalElements());
    }
    public Page<MyReviewResponse> findMyReview(Pageable pageable) {
        User user = userUtil.findCurrentUser();
        Page<Review> review = reviewRepository.findByUserOrderByIdDesc(user, pageable);

        List<MyReviewResponse> reviewResponses =
                review.stream()
                        .map(MyReviewResponse::toMyReviewResponse)
                        .collect(Collectors.toList());

        return new PageImpl<>(reviewResponses, pageable, review.getTotalElements());
    }

    public Boolean logout(HttpServletRequest request) {
        String accessToken = authenticationFilter.getAccessToken(request);

        if (!tokenProvider.validateToken(accessToken)) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }

        String email = tokenProvider.parseClaims(accessToken).getSubject();
        log.info("========" + email + "=========");
        if (redisTemplate.opsForValue().get("RT:" + email) != null) {
            redisTemplate.delete("RT:" + email);
        }

        Long expiration = tokenProvider.getExpiration(accessToken);
        log.info("=========" + expiration + "=========");
        redisTemplate.opsForValue().set(accessToken, "logout", expiration, TimeUnit.MILLISECONDS);

        return true;
    }

}
