package com.hanghae.justpotluck.domain.board.repository;//package com.example.sparta.hanghaefinal.board.repository.board;
//
//import com.example.sparta.hanghaefinal.board.dto.response.board.BoardOneResponse;
//import com.example.sparta.hanghaefinal.board.dto.response.board.QBoardOneResponse;
//import com.querydsl.jpa.JPAExpressions;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import lombok.RequiredArgsConstructor;
//
//import static com.example.sparta.hanghaefinal.board.entity.QBoard.board;
//import static com.example.sparta.hanghaefinal.board.entity.QImage.image1;
//import static com.example.sparta.hanghaefinal.board.entity.QBookmark.bookmark;
//import static com.example.sparta.hanghaefinal.comment.entity.QComment.comment;
//import java.util.Optional;
//
//@RequiredArgsConstructor
//public class BoardRepositoryImpl implements BoardCustomRepository{
//
//    private final JPAQueryFactory queryFactory;
//    @Override
//    public Optional<BoardOneResponse> findOneBoardById(Long boardId) {
//        queryFactory.update(board)
//                .set(board.view, board.view.add(1))
//                .where(board.id.eq(boardId))
//                .execute();
//
//}
