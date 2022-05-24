package com.hanghae.justpotluck.domain.review.entity;

import com.hanghae.justpotluck.global.config.Timestamped;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class ReviewImage extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_image_id")
    private Long id;

//    @JsonBackReference
//    @ManyToOne
//    @JoinColumn(name = "board_id")
//    private Board board;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;

    private String imageUrl;
    private String storeFileName;
    private String image;

//    private String origFileName;
//    private String filePath;
//    private Long fileSize;

    //    @Builder
//    public Image(String origFileName, String filePath, Long fileSize) {
//        this.origFileName = origFileName;
//        this.filePath = filePath;
//        this.fileSize = fileSize;
//    }

    @Builder
    public ReviewImage(String imageUrl, String storeFileName, Review review) {
        this.imageUrl = imageUrl;
        this.storeFileName = storeFileName;
        this.review = review;
    }

    public ReviewImage(Review review, String image) {
        this.review = review;
        this.image = image;
    }

//    private String image;
//
//    public void setBoard(Board board) {
//        this.board = board;
//        if (!board.getImages().contains(this)) {
//            board.getImages().add(this);
//        }
//    }
//
//    public void setReview(Review review) {
//        this.review = review;
//        if (!review.getImages().contains(this)) {
//            review.getImages().add(this);
//        }
//    }

}
