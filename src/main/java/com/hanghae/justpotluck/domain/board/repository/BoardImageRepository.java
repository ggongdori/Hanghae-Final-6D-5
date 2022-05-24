package com.hanghae.justpotluck.domain.board.repository;


import com.hanghae.justpotluck.domain.board.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardImageRepository extends JpaRepository<Image, Long>, BoardImageRepositoryCustom {

    //게시글 전체 이미지 가져오기
//    List<Image> findAllByBoardId(Long boardId);
//
//    List<Image> deleteAllByBoardId(Long boardId);

}
