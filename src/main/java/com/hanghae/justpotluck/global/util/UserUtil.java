package com.hanghae.justpotluck.global.util;

import com.hanghae.justpotluck.global.exception.CustomException;
import com.hanghae.justpotluck.global.exception.ErrorCode;
import com.hanghae.justpotluck.domain.user.entity.User;
import com.hanghae.justpotluck.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserUtil {

    private final UserRepository userRepository;

    public User findCurrentUser() {

        User user = userRepository.findByEmail(SecurityUtil.getCurrentMemberEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_MEMBER));

        return user;
    }

}