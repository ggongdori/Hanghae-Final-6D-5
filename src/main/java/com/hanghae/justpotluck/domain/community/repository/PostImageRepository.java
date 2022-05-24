package com.hanghae.justpotluck.domain.community.repository;

import com.hanghae.justpotluck.domain.community.entity.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostImageRepository extends JpaRepository<PostImage, Long>, PostImageRepositoryCustom {
}
