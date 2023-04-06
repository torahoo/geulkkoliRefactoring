package com.geulkkoli.domain.post;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Post findById (Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(()-> new NoSuchElementException("No post found id matches:"+postId));
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public void savePost(Post post) {
        postRepository.save(post);
    }

    public void updatePost (Long postId, Post updateParam) {
        postRepository.update(postId, updateParam);
    }

    public void deletePost (Long postId) {
        postRepository.delete(postId);
    }
}
