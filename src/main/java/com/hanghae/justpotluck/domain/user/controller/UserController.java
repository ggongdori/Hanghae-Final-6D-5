package com.hanghae.justpotluck.domain.user.controller;

import com.hanghae.justpotluck.domain.user.entity.User;
import com.hanghae.justpotluck.domain.user.repository.UserRepository;
import com.hanghae.justpotluck.domain.user.service.UserService;
import com.hanghae.justpotluck.global.exception.ResourceNotFoundException;
import com.hanghae.justpotluck.global.security.CurrentUser;
import com.hanghae.justpotluck.global.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class UserController {


    private final UserRepository userRepository;
    private final UserService userService;

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }
//
//    @GetMapping("/user/mypage")
//    public ResponseEntity<UserResponse> update(@RequestBody @Valid UserUpdateRequest updateRequest) {
//        return ResponseEntity.ok(UserResponse.toUserResponse(userService.update(updateRequest)));
//    }

}