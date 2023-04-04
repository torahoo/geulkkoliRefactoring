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

    public Post findById (Long postNo) {
        return postRepository.findById(postNo)
                .orElseThrow(()-> new NoSuchElementException("No post found id matches:"+postNo));
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public void savePost(Post post) {
        postRepository.save(post);
    }

    public void updatePost (Long postNo, Post updateParam) {
        postRepository.update(postNo, updateParam);
    }

    public void deletePost (Long postNo) {
        postRepository.delete(postNo);
    }
}
