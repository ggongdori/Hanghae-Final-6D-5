package com.hanghae.justpotluck.domain.comment.dto.response;

import com.hanghae.justpotluck.domain.comment.entity.Comments;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class CommentResponseDto {
    private Long commentId;
    private String content;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private List<CommentResponseDto> children;
    private boolean deleted;
    private String writer;


    public CommentResponseDto(Long id, String content, String writer) {
        this.commentId = id;
        this.writer = writer;
        this.content = content;
    }

    public static CommentResponseDto convertCommentToDto(Comments comment) {
        return comment.isRemoved() == true ?
                new CommentResponseDto(comment.getCommentId(), "삭제된 댓글입니다.", null) :
                new CommentResponseDto(comment.getCommentId(), comment.getContent(), comment.getUser().getNickname());
    }

}
