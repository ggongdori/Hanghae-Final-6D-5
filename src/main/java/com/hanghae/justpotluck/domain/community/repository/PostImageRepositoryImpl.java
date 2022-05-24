package com.hanghae.justpotluck.domain.community.repository;

import com.hanghae.justpotluck.domain.community.entity.PostImage;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.hanghae.justpotluck.domain.community.entity.QPostImage.postImage;
import static com.hanghae.justpotluck.domain.community.entity.QPosts.posts;

@RequiredArgsConstructor
public class PostImageRepositoryImpl implements PostImageRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<PostImage> findBySavedImageUrl(Long postId) {
        return queryFactory
                .selectFrom(postImage)
                .innerJoin(postImage.posts, posts)
                .where(posts.postId.eq(postId))
                .fetch();
    }
}
