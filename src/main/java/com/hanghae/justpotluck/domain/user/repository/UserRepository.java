package com.hanghae.justpotluck.domain.user.repository;

import com.hanghae.justpotluck.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findByName(String email);
    Boolean existsByEmail(String email);
    //닉네임
    Boolean existsByName(String name);
    Optional<User> findById(Long id);



}