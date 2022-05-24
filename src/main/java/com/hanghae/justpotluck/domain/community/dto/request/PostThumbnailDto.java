package com.hanghae.justpotluck.domain.community.dto.request;

import com.hanghae.justpotluck.domain.community.entity.Posts;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PostThumbnailDto {

    private Long communityId;
    private String category;
    private String imagePath;
    private String title;
    private String location;
    private LocalDateTime createdAt;

    public PostThumbnailDto(Posts post) {
        this.communityId = post.getPostId();
        this.title = post.getTitle();
        this.category = post.getCategory();
        this.location = post.getLocation();
        this.createdAt = post.getCreatedAt();
    }
}


