package com.hanghae.justpotluck.domain.review.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewSaveResponse {
    private Long boardId;
    private Long reviewId;
    private List<String> imageUrl = new ArrayList<>();
}
