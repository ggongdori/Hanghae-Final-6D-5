package com.hanghae.justpotluck.domain.board.dto.response.board;

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
public class BoardSaveResponse {
    private Long boardId;
    private List<String> processList = new ArrayList<>();
    private String category;
    private List<String> imageUrl = new ArrayList<>();
}
