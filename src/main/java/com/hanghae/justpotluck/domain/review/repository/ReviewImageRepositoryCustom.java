package com.hanghae.justpotluck.domain.review.repository;


import com.hanghae.justpotluck.domain.review.entity.ReviewImage;

import java.util.List;

public interface ReviewImageRepositoryCustom {
    List<ReviewImage> findBySavedImageUrl(Long reviewId);
}
