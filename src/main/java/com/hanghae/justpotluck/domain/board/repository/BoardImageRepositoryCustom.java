package com.hanghae.justpotluck.domain.board.repository;

import com.hanghae.justpotluck.domain.board.entity.Image;

import java.util.List;

public interface BoardImageRepositoryCustom {

    List<Image> findBySavedImageUrl(Long boardId);
}
