package com.hanghae.justpotluck.domain.user.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hanghae.justpotluck.domain.user.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("유저 조회 정보")
public class UserResponse {

    @ApiModelProperty(value = "유저 식별자", example = "1")
    private Long id;

    @ApiModelProperty(value = "유저 닉네임", example = "모아모아")
    private String name;

    @ApiModelProperty(value = "유저 이메일", example = "kmw106933@naver.com")
    private String email;

    @ApiModelProperty(value = "생성 시각", example = "")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdDate;

    @ApiModelProperty(value = "수정 시각", example = "")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime updatedDate;

    public static UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .createdDate(user.getTimeEntity().getCreatedDate())
                .updatedDate(user.getTimeEntity().getUpdatedDate())
                .build();
    }

}