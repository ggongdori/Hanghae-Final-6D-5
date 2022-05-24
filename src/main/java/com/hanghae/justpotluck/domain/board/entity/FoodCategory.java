//package com.hanghae.justpotluck.domain.board.entity;
//
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//@Getter
//@NoArgsConstructor
//public class FoodCategory {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "food_category_id")
//    private Long id;
//
//    private String categoryName;
//
//    @OneToMany(mappedBy = "foodCategory", orphanRemoval = true)
//    private List<Board> boards = new ArrayList<>();
//
//    @Builder
//    public FoodCategory(String categoryName, List<Board> boards) {
//        this.categoryName = categoryName;
//        this.boards = boards;
//    }
//
//    public static FoodCategory createCategory(String categoryName) {
//        return FoodCategory.builder()
//                .categoryName(categoryName)
//                .build();
//    }
//}
