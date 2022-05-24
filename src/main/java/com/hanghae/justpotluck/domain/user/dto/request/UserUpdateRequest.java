package com.hanghae.justpotluck.domain.user.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value="개인정보 수정 요청 정보")
public class UserUpdateRequest {

    @ApiModelProperty(value = "닉네임", required = true, example = "모아모아")
    @NotBlank(message = "닉네임을 입력해주세요.")
    private String nickname;

    private String imageUrl;

}