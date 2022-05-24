package com.hanghae.justpotluck.domain.community.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.hanghae.justpotluck.global.config.Timestamped;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class PostImage extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_image_id")
    private Long id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Posts posts;

    private String imageUrl;
    private String storeFileName;

    @Builder
    public PostImage(String imageUrl, String storeFileName, Posts posts) {
        this.imageUrl = imageUrl;
        this.storeFileName = storeFileName;
        this.posts = posts;
    }

    public PostImage(Posts posts, String imageUrl) {
        this.posts = posts;
        this.imageUrl = imageUrl;
    }

    public void setPosts(Posts posts) {
        this.posts = posts;
        if (!posts.getImageList().contains(this)) {
            posts.getImageList().add(this);
        }
    }
}
