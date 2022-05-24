package com.hanghae.justpotluck.domain.review.repository;


import com.hanghae.justpotluck.domain.review.entity.ReviewImage;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ReviewImageRepository extends JpaRepository<ReviewImage, Long>, ReviewImageRepositoryCustom {
}
