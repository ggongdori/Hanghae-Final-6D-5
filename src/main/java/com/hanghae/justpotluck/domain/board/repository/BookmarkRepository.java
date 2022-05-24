package com.hanghae.justpotluck.domain.board.repository;


import com.hanghae.justpotluck.domain.board.entity.Board;
import com.hanghae.justpotluck.domain.board.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    Optional<Bookmark> findByBoard(Board board);
//    @Modifying
//    @Query(value = "INSERT INTO bookmark(board_id) VALUES(:boardId)", nativeQuery = true)
//    void addBookmark(Long boardId);
//
//    @Modifying
//    @Query(value = "DELETE FROM bookmark where board_id = :boardId", nativeQuery = true)
//    void removeBookmark(Long boardId);
}
