package com.hanghae.justpotluck.domain.review.repository;

import com.hanghae.justpotluck.domain.review.entity.ReviewImage;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.hanghae.justpotluck.domain.review.entity.QReview.review;
import static com.hanghae.justpotluck.domain.review.entity.QReviewImage.reviewImage;


@RequiredArgsConstructor
public class ReviewImageRepositoryImpl implements ReviewImageRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<ReviewImage> findBySavedImageUrl(Long reviewId) {
        return queryFactory
                .selectFrom(reviewImage)
                .innerJoin(reviewImage.review, review)
                .where(review.id.eq(reviewId))
                .fetch();
    }
}
