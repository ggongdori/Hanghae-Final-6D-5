package com.hanghae.justpotluck.domain.user.controller;

import com.hanghae.justpotluck.domain.board.dto.response.board.BoardListResponse;
import com.hanghae.justpotluck.domain.user.dto.request.UserLocationUpdateRequestDto;
import com.hanghae.justpotluck.domain.user.dto.request.UserUpdateRequest;
import com.hanghae.justpotluck.domain.user.dto.response.*;
import com.hanghae.justpotluck.domain.user.repository.UserRepository;
import com.hanghae.justpotluck.domain.user.service.UserService;
import com.hanghae.justpotluck.global.security.UserPrincipal;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    //test controller
    @GetMapping("/hello")
    public String hello(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return userPrincipal.getUsername();
    }

    @PostMapping("/user/logout")
    public ResponseEntity<Boolean> logout(HttpServletRequest request) {
        return ResponseEntity.ok(userService.logout(request));
    }
    @PostMapping("/user/info")
    public ResponseEntity<UserResponse> saveLocation(@RequestBody @Valid UserLocationUpdateRequestDto userLocationUpdateRequestDto){
        return ResponseEntity.ok(UserResponse.toUserResponse(userService.locationUpdate(userLocationUpdateRequestDto)));
    }

    @PatchMapping("/user/me/edit")
    public ResponseEntity<UserResponse> update(@RequestBody @Valid UserUpdateRequest userUpdateRequest) {
        return ResponseEntity.ok(UserResponse.toUserResponse(userService.update(userUpdateRequest)));
    }

    @GetMapping("/user/me/board")
    @ApiResponse(responseCode = "200", description = "내가 쓴 글이 정상적으로 조회된 경우")
    public ResponseEntity<List<BoardListResponse>> findMyBoard(Pageable pageable) {
        return ResponseEntity.ok(userService.findMyBoard(pageable));
    }

    @GetMapping("/user/me/community")
    public ResponseEntity<Page<MyPostResponse>> findMyPosts(Pageable pageable) {
        return ResponseEntity.ok(userService.findMyPosts(pageable));
    }

    @GetMapping("/user/me/review")
    public ResponseEntity<Page<MyReviewResponse>> findMyReview(Pageable pageable) {
        return ResponseEntity.ok(userService.findMyReview(pageable));
    }

    @GetMapping("/user/me/comment")
    public ResponseEntity<Page<MyCommentResponse>> findMyComment(Pageable pageable) {
        return ResponseEntity.ok(userService.findMyComment(pageable));
    }

    @GetMapping("/user/me/bookmark")
    public ResponseEntity<Page<MyBookmarkResponse>> findMyBookmark(Pageable pageable) {
        return ResponseEntity.ok(userService.findMyBookmark(pageable));
    }

//
//    @GetMapping("/user/mypage")
//    public ResponseEntity<UserResponse> update(@RequestBody @Valid UserUpdateRequest updateRequest) {
//        return ResponseEntity.ok(UserResponse.toUserResponse(userService.update(updateRequest)));
//    }

}