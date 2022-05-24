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
    private ArrayList<String> processList;
    //    private List<Ingredient> ingredientList;
//    private FoodCategory category;
    private String category;
//    private User user;
//    private String nickname;
    private String cookingTime;
    private int viewCount;
    private boolean bookmark;
    private List<MultipartFile> images = new ArrayList<>();
}
