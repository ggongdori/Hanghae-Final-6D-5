package com.hanghae.justpotluck.global.rediskey;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanghae.justpotluck.domain.alarm.dto.AlarmResponseDto;
import com.hanghae.justpotluck.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisSubscriber {

    private final ObjectMapper objectMapper;
    private final SimpMessageSendingOperations messagingTemplate;
    private final UserRepository userRepository;

    public void sendMessage(String publishMessage) {
        try {
            if (!publishMessage.contains("[알림]")) {
                AlarmResponseDto alarmResponseDto = objectMapper
                        .readValue(publishMessage, AlarmResponseDto.class);

                messagingTemplate.convertAndSend(
                        "/sub/alarm/" + alarmResponseDto.getAlarmTargetId(),
                        alarmResponseDto);
            }
        } catch (Exception e) {
            log.error("Exception {}", e);
        }
    }
}
