package com.example.sparta.hanghaefinal.domain.dto.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberLoginResponseDto {
    private Long id;
    private String token;
    private String refreshToken;
}
