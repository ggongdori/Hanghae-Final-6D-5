package com.hanghae.justpotluck.domain.board.dto.response.board;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardUpdateResponse {
    private Long boardId;
    private List<String> saveImages = new ArrayList<>();
}
