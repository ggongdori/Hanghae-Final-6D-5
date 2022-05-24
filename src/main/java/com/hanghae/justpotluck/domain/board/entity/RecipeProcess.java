package com.hanghae.justpotluck.domain.board.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class RecipeProcess {

    @Id
    private Long id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @Column
    private Integer processNumber;

    @Column
    private String process;

    @Column
    private String image;

    @Builder
    public RecipeProcess(Board board, Integer processNumber, String process, String image){
        this.board = board;
        this.processNumber = processNumber;
        this.process = process;
        this.image = image;
    }
}