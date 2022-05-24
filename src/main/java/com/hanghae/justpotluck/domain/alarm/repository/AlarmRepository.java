package com.hanghae.justpotluck.domain.alarm.repository;

import com.hanghae.justpotluck.domain.alarm.entity.Alarm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {

    Page<Alarm> findAllByUserIdOrderByIdDesc(Long userId, Pageable pageable);

    void deleteAllByPostId(Long postId);
}