package com.geulkkoli.domain.post.service;

import com.geulkkoli.domain.post.entity.Post;
import com.geulkkoli.domain.post.repository.PostRepository;
import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import com.geulkkoli.web.post.dto.AddDTO;
import com.geulkkoli.web.post.dto.ListDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public Post findById (Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(()-> new NoSuchElementException("No post found id matches:"+postId));
    }

    public List<ListDTO> findAll() {
        List<Post> allPost = postRepository.findAll();
        List<ListDTO> listDTOs = new ArrayList<>();

        for (Post post : allPost) {
            listDTOs.add(ListDTO.toDTO(post));
        }

        return listDTOs;
    }

    public Long savePost(AddDTO post) {
        User user = userRepository.findById(post.getAuthorId())
                .orElseThrow(() -> new NoSuchElementException("No user found id matches:" + post.getAuthorId()));
        Post entityPost = post.toEntity();
        entityPost.setUser(user);
        Post savePost = postRepository.save(entityPost);
        return savePost.getPostId();
    }

    public void updatePost (Long postId, Post updateParam) {
        postRepository.update(postId, updateParam);
    }

    public void deletePost (Long postId) {
        postRepository.delete(postId);
    }
}
