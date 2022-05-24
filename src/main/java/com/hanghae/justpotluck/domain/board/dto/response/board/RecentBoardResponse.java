package com.hanghae.justpotluck.domain.board.dto.response.board;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class RecentBoardResponse {

    private Long boardId;
    private String title;
    private String contents;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime localDateTime;

    private int viewCount;

    @QueryProjection
    public RecentBoardResponse(Long boardId, String title, String contents, LocalDateTime localDateTime, int viewCount) {
        this.boardId = boardId;
        this.title = title;
        this.contents = contents;
        this.localDateTime = localDateTime;
        this.viewCount = viewCount;
    }
}
