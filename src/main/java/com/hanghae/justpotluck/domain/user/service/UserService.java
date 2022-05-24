package com.hanghae.justpotluck.domain.user.service;

import com.hanghae.justpotluck.domain.user.entity.User;
import com.hanghae.justpotluck.domain.user.repository.UserRepository;
import com.hanghae.justpotluck.global.exception.CustomException;
import com.hanghae.justpotluck.global.exception.ErrorCode;
import com.hanghae.justpotluck.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserUtil userUtil;
    private final UserRepository userRepository;


    public User getUser() {
        return userUtil.findCurrentUser();
    }

    public User getUser(String email) {
        return findUser(email);
    }

    private User findUser(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_MEMBER)
        );
    }

//    @Transactional
//    public User update(UserUpdateRequest userUpdateRequest) {
//        User user = userUtil.findCurrentUser();
//        // 닉네임 중복 확인
//        if (userRepository.existsByNickname(userUpdateRequest.getNickname())) {
//            throw new CustomException(ErrorCode.ALREADY_NICKNAME_EXISTS);
//        }
//
//        user.update(userUpdateRequest);
//        return user;
//    }

}
