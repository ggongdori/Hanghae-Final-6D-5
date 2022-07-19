package com.hanghae.justpotluck.domain.board.service;

import com.hanghae.justpotluck.domain.board.repository.BookmarkRepository;
import com.hanghae.justpotluck.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookmarkService {
    private final UserRepository userRepository;
    private final BookmarkRepository bookmarkRepository;

//    @Transactional
//    public void bookmark(Long boardId, String email) {
//        User user = userRepository.findByEmail(email).orElseThrow(

}
