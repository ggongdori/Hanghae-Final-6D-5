package com.hanghae.justpotluck.domain.review.service;


import com.hanghae.justpotluck.global.aws.S3Uploader;
import com.hanghae.justpotluck.domain.review.dto.request.ReviewSaveRequestDto;
import com.hanghae.justpotluck.domain.review.dto.response.ReviewSaveResponse;
import com.hanghae.justpotluck.domain.review.dto.request.ReviewUpdateRequestDto;
import com.hanghae.justpotluck.domain.review.dto.response.ReviewUpdateResponse;
import com.hanghae.justpotluck.domain.board.entity.Board;
import com.hanghae.justpotluck.domain.review.entity.Review;
import com.hanghae.justpotluck.domain.review.entity.ReviewImage;
import com.hanghae.justpotluck.domain.board.repository.BoardRepository;
import com.hanghae.justpotluck.domain.review.repository.ReviewImageRepository;
import com.hanghae.justpotluck.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewImageRepository reviewImageRepository;
    private final BoardRepository boardRepository;
    private final S3Uploader s3Uploader;

    @Transactional
    public ReviewSaveResponse saveReview(ReviewSaveRequestDto requestDto) {
        Board board = boardRepository.findById(requestDto.getBoardId()).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다.")
        );
        Review review = reviewRepository.save(Review.createReview(requestDto.getContents(), requestDto.getNickname(), board));
        List<String> reviewImages = uploadReviewImages(requestDto, review);
        return new ReviewSaveResponse(requestDto.getBoardId(), review.getId(), reviewImages);
    }

    private List<String> uploadReviewImages(ReviewSaveRequestDto requestDto, Review review) {
        return requestDto.getImages().stream()
                .map(image -> s3Uploader.upload(image, "review"))
                .map(reviewUrl -> createReviewImage(review, reviewUrl))
                .map(reviewImage -> reviewImage.getImageUrl())
                .collect(Collectors.toList());
    }

    private ReviewImage createReviewImage(Review review, String reviewUrl) {
        return reviewImageRepository.save(ReviewImage.builder()
                .imageUrl(reviewUrl)
                .storeFileName(StringUtils.getFilename(reviewUrl))
                .review(review)
                .build());
    }

    @Transactional
    public ReviewUpdateResponse updateReview(ReviewUpdateRequestDto requestDto) {
        Review review = reviewRepository.findById(requestDto.getReviewId()).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 없습니다.")
        );
        validateDeletedImages(requestDto);
        uploadReviewImages(requestDto, review);
        List<String> saveImages = getSaveImages(requestDto);
        review.updateReview(requestDto.getContents());
        return new ReviewUpdateResponse(review.getBoard().getId(), review.getId(), saveImages);
    }

    private void validateDeletedImages(ReviewUpdateRequestDto requestDto) {
        reviewImageRepository.findBySavedImageUrl(requestDto.getReviewId()).stream()
                .filter(reviewImage -> !requestDto.getSaveImageUrl().stream().anyMatch(Predicate.isEqual(reviewImage.getImageUrl())))
                .forEach(url -> {
                    reviewImageRepository.delete(url);
                    s3Uploader.deleteImage(url.getImageUrl());
                });
    }
    private void uploadReviewImages(ReviewUpdateRequestDto request, Review review) {
        request.getImages()
                .stream()
                .forEach(file -> {
                    String url = s3Uploader.upload(file, "review");
                    createReviewImage(review, url);
                });
    }

    /**
     * PostImage 테이블에 저장 되어있는 이미지 경로를 추출
     */
    private List<String> getSaveImages(ReviewUpdateRequestDto request) {
        return reviewImageRepository.findBySavedImageUrl(request.getReviewId())
                .stream()
                .map(image -> image.getImageUrl())
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 없습니다.")
        );

        reviewRepository.delete(review);
    }

}
