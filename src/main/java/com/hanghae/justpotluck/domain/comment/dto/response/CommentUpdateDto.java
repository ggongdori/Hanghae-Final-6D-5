package com.hanghae.justpotluck.domain.comment.dto.response;

import com.hanghae.justpotluck.domain.comment.entity.Comments;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentUpdateDto {
    private String content;
    private boolean deleted;


    public CommentUpdateDto(Long id, String content) {
        this.content = content;
    }

    public static CommentUpdateDto convertCommentToDto(Comments comment) {
        return comment.isRemoved() == true ?
                new CommentUpdateDto(comment.getCommentId(), "삭제된 댓글입니다.") :
                new CommentUpdateDto(comment.getCommentId(), comment.getContent());
    }
}
