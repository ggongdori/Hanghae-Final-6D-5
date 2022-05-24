package com.hanghae.justpotluck.domain.community.repository;

import com.hanghae.justpotluck.domain.community.entity.Posts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Posts, Long> {

    Page<Posts> findAll(Pageable pageable);

//    위치 반경 내의 게시글 필터링
    String HAVERSINE_FORMULA = "(6371 * acos(cos(radians(:latitude)) * cos(radians(p.latitude)) *" +
        " cos(radians(p.longitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(p.latitude))))";

//    @Query(value = "SELECT p FROM Posts p WHERE p.category NOT LIKE '%나눔완료%' AND "+ HAVERSINE_FORMULA +" <= 5 ORDER BY "+ HAVERSINE_FORMULA +"DESC")
    @Query(value = "SELECT * FROM Posts p where p.category not like '%나눔완료%' and Round(ST_Distance_Sphere(Point(?1, ?2), Point(p.longitude, p.latitude), 4326), 2) <= 5", nativeQuery = true)
    Page<Posts> findPostsToUser(@Param("longitude") Double longitude, @Param("latitude") Double latitude, Pageable pageable);



    List<Posts> findAllByExpiredAtBefore(LocalDateTime now);

    void deleteByPostId(Long postId);

    Optional<Posts> findByPostId(Long postId);
}
