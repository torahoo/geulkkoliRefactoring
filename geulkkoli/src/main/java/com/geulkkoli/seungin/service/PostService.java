package com.geulkkoli.seungin.service;

import com.geulkkoli.domain.Post;
import com.geulkkoli.respository.ImplPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final ImplPostRepository postRepository;

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public void savePost(Post post) {
        postRepository.save(post);
    }
}
