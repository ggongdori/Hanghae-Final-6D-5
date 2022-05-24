package com.hanghae.justpotluck.domain.review.entity;


import com.hanghae.justpotluck.global.config.Timestamped;
import com.hanghae.justpotluck.domain.board.entity.Board;
import com.hanghae.justpotluck.domain.board.entity.Image;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Review extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    private String contents;
    private String nickname;

    @JsonBackReference
    @OneToMany(
            mappedBy = "review",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true
    )
    private List<Image> images = new ArrayList<>();

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @Builder
    public Review(Board board, String contents, String nickname) {
        this.board = board;
        this.contents = contents;
        this.nickname = nickname;
    }

    public static Review createReview(String contents, String nickname, Board board) {
        return Review.builder()
                .contents(contents)
                .nickname(nickname)
                .board(board)
                .build();
    }

    public void updateReview(String contents) {
        this.contents = contents;
    }

    public void addImage(Image image) {
        this.images.add(image);
        if (image.getReview() != this) {
            image.setReview(this);
        }
    }


}
