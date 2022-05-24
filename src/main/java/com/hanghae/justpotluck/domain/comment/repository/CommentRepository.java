package com.hanghae.justpotluck.domain.comment.repository;

import com.hanghae.justpotluck.domain.comment.entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comments, Long> {
    @Query("select c from Comments c left join fetch c.parent where c.commentId = :id")
    Optional<Comments> findCommentByIdWithParent(@Param("id") Long id);

    @Query("select c from Comments c where c.post.postId = :postId")
    Optional<Comments> findByPostId(@Param("postId") Long postId);

    @Query("select c from Comments c where c.post.postId = :postId and c.commentId = :commentId")
    Optional<Comments> findByPostIdAndCommentId(@Param("postId") Long postId, @Param("commentId") Long commentId);

    Optional<Comments> findByCommentId(Long CommentId);

    void delete(Comments comment);
}
