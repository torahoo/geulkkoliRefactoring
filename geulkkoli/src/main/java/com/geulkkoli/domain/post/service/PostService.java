package com.geulkkoli.domain.post.service;

import com.geulkkoli.domain.post.Post;
import com.geulkkoli.domain.post.PostRepository;
import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import com.geulkkoli.web.post.dto.AddDTO;
import com.geulkkoli.web.post.dto.EditDTO;
import com.geulkkoli.web.post.dto.ListDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public Post findById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("No post found id matches:" + postId));
    }

    //게시글 상세보기만을 담당하는 메서드
    public Post showDetailPost (Long postId) {
        postRepository.updateHits(postId);
        return postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("No post found id matches:" + postId));
    }

    //전체 게시글 리스트 & 조회 기능 포함
    public Page<ListDTO> findAll(Pageable pageable, String searchType, String searchWords) {

        if(searchType==null){
            return postRepository.findAll(pageable)
                    .map(post -> new ListDTO(
                            post.getPostId(),
                            post.getTitle(),
                            post.getNickName(),
                            post.getUpdatedAt(),
                            post.getPostHits()
                    ));
        } else {
            if(searchType.equals("제목")){
                return postRepository.findPostsByTitleContaining(pageable, searchWords)
                        .map(post -> new ListDTO(
                                post.getPostId(),
                                post.getTitle(),
                                post.getNickName(),
                                post.getUpdatedAt(),
                                post.getPostHits()
                        ));
            } else if(searchType.equals("본문")) {
                return postRepository.findPostsByPostBodyContaining(pageable, searchWords)
                        .map(post -> new ListDTO(
                                post.getPostId(),
                                post.getTitle(),
                                post.getNickName(),
                                post.getUpdatedAt(),
                                post.getPostHits()
                        ));
            } else {
                return postRepository.findPostsByNickNameContaining(pageable, searchWords)
                        .map(post -> new ListDTO(
                                post.getPostId(),
                                post.getTitle(),
                                post.getNickName(),
                                post.getUpdatedAt(),
                                post.getPostHits()
                        ));
            }
        }
    }

    public Post savePost(AddDTO post, User user) {
        Post writePost = user.writePost(post);
        return postRepository.save(writePost);
    }

    public void updatePost(Long postId, EditDTO updateParam, User user) {
        Post post = user.editPost(postId, updateParam);
        postRepository.save(post);
    }

    public void deletePost(Long postId) {
        postRepository.delete(postId);
    }

    public void deleteAll() {
        postRepository.deleteAll();
    }
}
