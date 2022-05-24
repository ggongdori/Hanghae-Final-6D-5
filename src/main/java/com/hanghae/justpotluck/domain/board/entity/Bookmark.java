package com.hanghae.justpotluck.domain.board.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Bookmark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "board_id")
    @ManyToOne
    private Board board;

    @Builder
    public Bookmark(Board board) {
        this.board = board;
    }

    public static Bookmark createBookmark(Board board) {
        return Bookmark.builder()
                .board(board)
                .build();
    }
}
