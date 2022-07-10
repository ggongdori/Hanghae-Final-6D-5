package com.hanghae.justpotluck.domain.alarm.entity;

import com.hanghae.justpotluck.global.config.Timestamped;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Alarm extends Timestamped {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column
    private String userName; // 알람 받는 대상

    @Column(name="sender_nickname")
    private String senderNickName;

    @Column
    @Enumerated(value = EnumType.STRING)
    private AlarmType type;

    @Column
    private Long pubId; // 채팅방 생성자

    @Column
    private Long postId; // 게시물 번호

    @Column
    private String alarmMessage;

    @Column
    private Boolean isRead;
}