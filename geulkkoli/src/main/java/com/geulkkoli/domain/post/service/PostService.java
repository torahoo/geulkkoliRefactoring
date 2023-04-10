package com.geulkkoli.domain.post.service;

import com.geulkkoli.domain.post.Post;
import com.geulkkoli.domain.post.PostRepository;
import com.geulkkoli.web.post.dto.AddDTO;
import com.geulkkoli.web.post.dto.EditDTO;
import com.geulkkoli.web.post.dto.ListDTO;
import com.geulkkoli.web.post.dto.PageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

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

    public Long savePost(Post post) {
        Post savePost = postRepository.save(post);
        return savePost.getPostId();
    }

    public void updatePost (Long postId, Post updateParam) {
        postRepository.update(postId, updateParam);
    }

    public void deletePost (Long postId) {
        postRepository.delete(postId);
    }
}
