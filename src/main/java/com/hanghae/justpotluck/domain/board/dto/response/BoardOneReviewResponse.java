package com.hanghae.justpotluck.domain.board.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class BoardOneReviewResponse {
    private Long reviewId;
    private String contents;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime updateDate;

    public BoardOneReviewResponse(Long reviewId, String contents, LocalDateTime createDate, LocalDateTime updateDate) {
        this.reviewId = reviewId;
        this.contents = contents;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }
}
