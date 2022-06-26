package com.hanghae.justpotluck.domain.alarm.controller;

import com.hanghae.justpotluck.domain.alarm.dto.AlarmResponseDto;
import com.hanghae.justpotluck.domain.alarm.service.AlarmService;
import com.hanghae.justpotluck.global.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AlarmController {
    private final AlarmService alarmService;

    /* 알림 읽음 확인 */
    @PostMapping("/api/alarm/{alarmId}")
    public AlarmResponseDto alarmReadCheck(
            @PathVariable Long alarmId,
            @AuthenticationPrincipal UserPrincipal userDetails) {

        return alarmService.alarmReadCheck(alarmId, userDetails);
    }
}