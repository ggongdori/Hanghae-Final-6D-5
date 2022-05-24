package com.hanghae.justpotluck.domain.review.controller;

import com.hanghae.justpotluck.domain.review.dto.request.ReviewSaveRequestDto;
import com.hanghae.justpotluck.domain.review.dto.response.ReviewSaveResponse;
import com.hanghae.justpotluck.domain.review.dto.request.ReviewUpdateRequestDto;
import com.hanghae.justpotluck.domain.review.dto.response.ReviewUpdateResponse;
import com.hanghae.justpotluck.domain.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/board")
@RequiredArgsConstructor
@RestController
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/{boardId}/review")
    public ResponseEntity<ReviewSaveResponse> saveReview(@PathVariable Long boardId,
                                                         @ModelAttribute ReviewSaveRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.saveReview(requestDto));
    }

    @PatchMapping("/{boardId}/review")
    public ResponseEntity<ReviewUpdateResponse> updateReview(@PathVariable Long boardId,
                                                             @ModelAttribute ReviewUpdateRequestDto requestDto) {
        return ResponseEntity.ok(reviewService.updateReview(requestDto));
    }

    @DeleteMapping("/review/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
