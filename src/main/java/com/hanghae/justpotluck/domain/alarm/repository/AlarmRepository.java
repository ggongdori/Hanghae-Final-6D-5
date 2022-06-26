package com.hanghae.justpotluck.domain.alarm.repository;

import com.hanghae.justpotluck.domain.alarm.entity.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    void deleteAllByPostId(Long postId);
}