package com.hanghae.justpotluck.domain.board.dto.response.board;

import com.hanghae.justpotluck.domain.board.dto.response.BoardOneReviewResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BoardOneResponse {
    private Long boardId;
    private String title;
    private String contents;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime updateDate;

    private Integer viewCount;

    private List<String> imageUrl = new ArrayList<>();

    private List<BoardOneReviewResponse> reviews = new ArrayList<>();

    @QueryProjection
    public BoardOneResponse(Long boardId, String title, String contents,
                            Integer viewCount, LocalDateTime createDate, LocalDateTime updateDate) {
        this.boardId = boardId;
        this.title = title;
        this.contents = contents;
        this.viewCount = viewCount;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }


}
