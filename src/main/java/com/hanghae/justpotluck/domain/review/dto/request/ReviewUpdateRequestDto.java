package com.hanghae.justpotluck.domain.review.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ReviewUpdateRequestDto {

    private Long reviewId;
    private String contents;

    private List<String> saveImageUrl = new ArrayList<>();

    private List<MultipartFile> images = new ArrayList<>();

//    @Builder
//    public ReviewUpdateRequestDto(String contents) {
//        this.contents = contents;
//    }
}
