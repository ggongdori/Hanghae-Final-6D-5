package com.hanghae.justpotluck.domain.community.service;

import com.hanghae.justpotluck.domain.community.entity.PostImage;
import com.hanghae.justpotluck.domain.community.dto.request.PostRequestDto;
import com.hanghae.justpotluck.domain.community.dto.request.PostThumbnailDto;
import com.hanghae.justpotluck.domain.community.dto.request.PostUpdateDto;
import com.hanghae.justpotluck.domain.community.dto.response.PostResponseDto;
import com.hanghae.justpotluck.domain.community.dto.response.PostSaveReponse;
import com.hanghae.justpotluck.domain.community.entity.Posts;
import com.hanghae.justpotluck.domain.community.repository.PostImageRepository;
import com.hanghae.justpotluck.domain.community.repository.PostRepository;
import com.hanghae.justpotluck.domain.user.entity.User;
import com.hanghae.justpotluck.domain.user.repository.UserRepository;
import com.hanghae.justpotluck.global.aws.S3Uploader;
import com.hanghae.justpotluck.global.exception.RestException;
import com.hanghae.justpotluck.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostImageRepository postImageRepository;
    private final UserUtil userUtil;
    private final S3Uploader s3Uploader;

    @Scheduled(cron="0 0 00 * * *")
    public void updateExpired(){
        List<Posts> posts = postRepository.findAllByExpiredAtBefore(LocalDateTime.now());
        for (Posts post : posts){
            post.update("나눔완료");
        }
    }

//    반경 쿼리만 작성하면 완료
    @Transactional
    public List<PostThumbnailDto> findPosts(int pagingCnt, Double longitude, Double latitude) {

        Pageable pageRequest = PageRequest.of(pagingCnt, 10, Sort.by("createdAt").descending());
//        위치반경 내 필터링 하여 게시글 목록 불러오기(확실치 않음)--> 쿼리 에러뜨네;;
        Page<Posts> posts = postRepository.findPostsToUser(longitude, latitude, pageRequest);
//        Page<Posts> posts = postRepository.findAll(pageRequest);
        List<PostThumbnailDto> responseDto = new ArrayList<>();
        for (Posts post : posts) {
            PostThumbnailDto postThumbnailDto = new PostThumbnailDto(post);
            responseDto.add(postThumbnailDto);
        }
        return responseDto;
    }


    @Transactional
    public PostResponseDto findOne(Long postId) {
        Posts post = postRepository.findByPostId(postId).orElseThrow(
                () -> new RestException(HttpStatus.NOT_FOUND, "해당 postId가 존재하지 않습니다.")
        );
        return new PostResponseDto(post);
    }

    @Transactional
    public PostSaveReponse savePost(PostRequestDto requestDto) {
//        유저 DB 확인 후 수정
//        User user = userUtil.findCurrentUser();
//        User result = userRepository.findByName(username).orElseThrow(
//                () -> new RestException(HttpStatus.NOT_FOUND, "해당 username이 존재하지 않습니다.")
//        );
//        Posts post = Posts.builder()
//                .content(requestDto.getContent())
//                .category(requestDto.getCategory())
//                .expiredAt(LocalDateTime.parse(requestDto.getExpiredAt(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")))
//                .title(requestDto.getTitle())
//                .build();
        Posts posts = postRepository.save(Posts.createPost(requestDto));
        List<String> postImages = uploadPostImages(requestDto, posts);
        return new PostSaveReponse(requestDto.getPostId(), postImages);
    }

    private List<String> uploadPostImages(PostRequestDto requestDto, Posts posts) {
        return requestDto.getImages().stream()
                .map(image -> s3Uploader.upload(image, "posts"))
                .map(postUrl -> savePostImage(posts, postUrl))
                .map(postImage -> postImage.getImageUrl())
                .collect(Collectors.toList());
    }

    private PostImage savePostImage(Posts posts, String postUrl) {
        return postImageRepository.save(PostImage.builder()
                .imageUrl(postUrl)
                .storeFileName(StringUtils.getFilename(postUrl))
                .posts(posts)
                .build());
//        return postImageRepository.save(PostImage.builder()
//                .imageUrl(url)
//                .storeFileName(StringUtils.getFilename(url))
//                .posts(posts)
//                .build());
    }
    @Transactional
    public void delete(Long postId) {
        postRepository.deleteByPostId(postId);
    }

    @Transactional
    public void modify(Long postId, PostRequestDto requestDto, String username) {

        Posts post = postRepository.findByPostId(postId).orElseThrow(
                () -> new RestException(HttpStatus.NOT_FOUND, "해당 postId가 존재하지 않습니다.")
        );

        if (post.getUser().getNickname().equals(username)) {
            post.update(requestDto);
        } else {
            throw new RestException(HttpStatus.BAD_REQUEST, "username이 일치하지 않습니다.");
        }
    }

    @Transactional
    public void modify(Long postId, PostUpdateDto requestDto, String username) {
        Posts post = postRepository.findByPostId(postId).orElseThrow(
                () -> new RestException(HttpStatus.NOT_FOUND, "해당 postId가 존재하지 않습니다.")
        );

        if (post.getUser().getNickname().equals(username)) {
            post.update(requestDto.getCategory());
        } else {
            throw new RestException(HttpStatus.BAD_REQUEST, "username이 일치하지 않습니다.");
        }
    }
}
