package com.hanghae.justpotluck.domain.comment.entity;

import com.hanghae.justpotluck.global.config.Timestamped;
import com.hanghae.justpotluck.domain.community.entity.Posts;
import com.hanghae.justpotluck.domain.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id")
public class Comments extends Timestamped {

    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long commentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Posts post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comments parent;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private boolean isRemoved= false;


    //== 부모 댓글을 삭제해도 자식 댓글은 남아있음 ==//
    @OneToMany(mappedBy = "parent")
    private List<Comments> childList = new ArrayList<>();


    //== 연관관계 편의 메서드 ==//
    public void confirmWriter(User writer) {
        this.user = writer;
        writer.addComment(this);
    }

    public void confirmPost(Posts post) {
        this.post = post;
        post.addComment(this);
    }

    public void confirmParent(Comments parent){
        this.parent = parent;
        parent.addChild(this);
    }

    public void addChild(Comments child){
        childList.add(child);
    }




    //== 수정 ==//
    public void updateContent(String content) {
        this.content = content;
    }
    //== 삭제 ==//
    public void remove() {
        this.isRemoved = true;
    }


    @Builder
    public Comments(Posts post, Comments parent, String content) {
        this.post = post;
        this.parent = parent;
        this.content = content;
        this.isRemoved = false;
    }


    //== 비즈니스 로직 ==//
    public List<Comments> findRemovableList() {

        List<Comments> result = new ArrayList<>();

        Optional.ofNullable(this.parent).ifPresentOrElse(

                parentComment ->{//대댓글인 경우 (부모가 존재하는 경우)
                    if( parentComment.isRemoved()&& parentComment.isAllChildRemoved()){
                        result.addAll(parentComment.getChildList());
                        result.add(parentComment);
                    }
                },

                () -> {//댓글인 경우
                    if (isAllChildRemoved()) {
                        result.add(this);
                        result.addAll(this.getChildList());
                    }
                }
        );

        return result;
    }


    //모든 자식 댓글이 삭제되었는지 판단
    private boolean isAllChildRemoved() {
        return getChildList().stream()
                .map(Comments::isRemoved)//지워졌는지 여부로 바꾼다
                .filter(isRemove -> !isRemove)//지워졌으면 true, 안지워졌으면 false이다. 따라서 filter에 걸러지는 것은 false인 녀석들이고, 있다면 false를 없다면 orElse를 통해 true를 반환한다.
                .findAny()//지워지지 않은게 하나라도 있다면 false를 반환
                .orElse(true);//모두 지워졌다면 true를 반환

    }



}
