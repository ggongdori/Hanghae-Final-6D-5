package com.hanghae.justpotluck.domain.community.repository;

import com.hanghae.justpotluck.domain.community.entity.PostImage;

import java.util.List;

public interface PostImageRepositoryCustom {
    List<PostImage> findBySavedImageUrl(Long postId);
}
