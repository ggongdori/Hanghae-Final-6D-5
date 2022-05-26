package com.hanghae.justpotluck.domain.board.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardSaveRequestDto {
    private String title;
    private ArrayList<String> process;
    private String quantity;
    //    private List<Ingredient> ingredientList;
    private String category;
//    private User user;
    private String cookTime;
    private int viewCount;
    private boolean bookmark;
    private List<MultipartFile> processImages = new ArrayList<>();
    private List<MultipartFile> completeImages = new ArrayList<>();
}