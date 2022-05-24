package com.hanghae.justpotluck.domain.community.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostSaveReponse {
    private Long postId;
    private List<String> imageUrl = new ArrayList<>();
}
