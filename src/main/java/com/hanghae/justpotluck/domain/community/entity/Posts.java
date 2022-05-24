package com.hanghae.justpotluck.domain.community.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.hanghae.justpotluck.domain.comment.entity.Comments;
import com.hanghae.justpotluck.domain.community.dto.request.PostRequestDto;
import com.hanghae.justpotluck.domain.user.entity.User;
import com.hanghae.justpotluck.global.config.Timestamped;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;


@Entity
@DynamicUpdate
@DynamicInsert
@Data
@NoArgsConstructor
//@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id")
public class Posts extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

//    @Column
//    private String image;

    @JsonBackReference
    @OneToMany(
            mappedBy = "posts",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true
    )
    private List<PostImage> imageList = new ArrayList<>();

    @Column(nullable = false)
    private String category;

//    @OneToMany(mappedBy = "posts")
//    @Column
    ArrayList<String> tags = new ArrayList<>();

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(nullable = false)
    private String location;


    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
//    @Column(nullable = false)
    private LocalDateTime expiredAt;

    @OneToMany(mappedBy = "post", cascade = ALL, orphanRemoval = true)
    private List<Comments> commentList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void addComment(Comments comment) {
        this.commentList.add(comment);
    }


    @Builder
    public Posts(User user, String title, String content,String category, Double longitude, Double latitude, ArrayList<String> tags){
        this.user = user;
        this.title = title;
        this.content = content;
        this.category = category;
        this.longitude = longitude;
        this.latitude = latitude;
//        this.expiredAt = expiredAt;
        this.tags = tags;
    }

    public static Posts createPost(PostRequestDto requestDto) {
        return Posts.builder()
                .title(requestDto.getTitle())
                .category(requestDto.getCategory())
                .content(requestDto.getContent())
//                .user(user)
                .build();
    }

    public void update(PostRequestDto requestDto){
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.category = requestDto.getCategory();
//        this.expiredAt = LocalDateTime.parse(requestDto.getExpiredAt(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
    }

    public void update(String category) {
        this.category = category;
    }
}
