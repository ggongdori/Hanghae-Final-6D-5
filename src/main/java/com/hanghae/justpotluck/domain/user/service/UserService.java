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
}
