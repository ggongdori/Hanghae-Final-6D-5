package com.hanghae.justpotluck.domain.board.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardUpdateRequestDto {
    private Long boardId;
    private String title;
    private String quantity;
    private String category;
    private List<String> saveImageUrl = new ArrayList<>();
    private List<MultipartFile> completeImages = new ArrayList<>();
    private String cookTime;

}
