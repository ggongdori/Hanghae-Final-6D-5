package com.hanghae.justpotluck.domain.review.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ReviewUpdateRequestDto {

    private Long reviewId;
    private String comment;
    private String category;

    private List<String> saveImageUrl = new ArrayList<>();

    private List<MultipartFile> images = new ArrayList<>();

}
