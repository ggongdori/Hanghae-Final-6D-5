package com.hanghae.justpotluck.domain.community.dto.request;

import com.hanghae.justpotluck.domain.community.entity.Posts;
import com.hanghae.justpotluck.domain.community.entity.Tag;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;


@ApiModel(value = "게시글 생성 요청")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDto {

    private Long postId;

    @ApiModelProperty(value = "게시글 이름", notes = "게시글 이름을 입력해주세요", example = "Post title")
    @NotEmpty(message = "게시글 이름을 입력해주세요.")
    private String title;

    @ApiModelProperty(value = "게시글", notes = "게시글을 입력해주세요", required = true, example = "Post content")
    @NotEmpty(message = "게시글을 입력해주세요.")
    @Size(max = 1000)
    private String content;

    @ApiModelProperty(value = "카테고리", notes = "카테고리를 입력해주세요", required = true, example = "나눠먹어요")
    @NotEmpty(message = "카테고리를 입력해주세요.")
    private String category;

    // 이 데이터를 어떻게 받아야하는가? 좀 찾아보자
    @NotNull
    private String expiredAt;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tag> tags;
    //    좌표를 여기서 받아야하는가
    @NotNull
    private String location;

    private List<MultipartFile> images = new ArrayList<>();

    @Builder
    public PostRequestDto(Posts post) {
        this.postId = post.getPostId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.tags = post.getTags();
        this.expiredAt = post.getExpiredAt().toString();
        this.category = post.getCategory();
        this.location = post.getLocation();
    }
}
