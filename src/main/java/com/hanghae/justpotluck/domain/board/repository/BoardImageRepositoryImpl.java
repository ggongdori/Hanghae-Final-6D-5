package com.hanghae.justpotluck.domain.board.repository;


import com.hanghae.justpotluck.domain.board.entity.Image;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.hanghae.justpotluck.domain.board.entity.QBoard.board;
import static com.hanghae.justpotluck.domain.board.entity.QImage.image;


@RequiredArgsConstructor
public class BoardImageRepositoryImpl implements BoardImageRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Image> findBySavedImageUrl(Long boardId) {
        return queryFactory
                .selectFrom(image)
                .innerJoin(image.board, board)
                .where(board.id.eq(boardId))
                .fetch();
    }


}
