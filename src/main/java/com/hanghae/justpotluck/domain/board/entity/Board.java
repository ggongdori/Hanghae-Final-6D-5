package com.hanghae.justpotluck.domain.board.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hanghae.justpotluck.domain.board.dto.request.BoardSaveRequestDto;
import com.hanghae.justpotluck.domain.board.dto.request.BoardUpdateRequestDto;
import com.hanghae.justpotluck.domain.review.entity.Review;
import com.hanghae.justpotluck.domain.user.entity.User;
import com.hanghae.justpotluck.global.config.Timestamped;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Board extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;
//    요리 이름
    private String title;
//    조리법
//    private String contents;
    private String quantity;

    //    @ManyToOne
//    private FoodCategory category;
    private String category;
//    private String nickname;
    private int viewCount;
    private boolean bookmark;
    private String cookingTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonBackReference
    @OneToMany(
            mappedBy = "board",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true
    )
    private List<Image> imageList = new ArrayList<>();
//    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private ArrayList<String> processList;
//    private List<Ingredient> ingredientList;

    @JsonIgnoreProperties({"board"})
    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Review> reviewList;

    @JsonIgnoreProperties({"board"})
    @OneToMany(mappedBy = "board", orphanRemoval = true)
    private List<Bookmark> bookmarkList = new ArrayList<>();

    @Builder
    public Board(String title, User user,
                 List<Review> reviewList, String cookingTime,
                 ArrayList<String> processList, int viewCount, List<Bookmark> bookmarkList, String category) {
        this.title = title;
        this.cookingTime = cookingTime;
        this.user = user;
        this.reviewList = reviewList;
        this.processList = processList;
//        this.ingredientList = ingredientList;
        this.category = category;
        this.viewCount = viewCount;
        this.bookmarkList = bookmarkList;
    }

    public static Board createBoard(BoardSaveRequestDto requestDto) {
        return Board.builder()
                .title(requestDto.getTitle())
                .processList(requestDto.getProcessList())
                .category(requestDto.getCategory())
//                .ingredientList(requestDto.getIngredientList())
//                .user(user)
                .cookingTime(requestDto.getCookingTime())
                .build();
    }

    public void update(BoardUpdateRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.quantity = requestDto.getQuantity();
        this.category = requestDto.getCategory();
//        this.ingredientList = requestDto.getIngredient();
        this.processList = requestDto.getProcessList();
    }

    public void addImage(Image image) {
        this.imageList.add(image);
        if (image.getBoard() != this) {
            image.setBoard(this);
        }
    }

}
