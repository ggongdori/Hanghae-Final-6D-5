package com.hanghae.justpotluck.domain.board.repository;

import com.hanghae.justpotluck.domain.board.dto.response.board.BoardOneResponse;

import java.util.Optional;

public interface BoardCustomRepository {
    Optional<BoardOneResponse> findOneBoardById(Long boardId);

//    Page<RecentBoardResponse> findRecentBoards(Pageable pageable);

}
