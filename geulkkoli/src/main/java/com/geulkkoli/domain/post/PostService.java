package com.geulkkoli.domain.post;

import com.geulkkoli.domain.post.Post;
import com.geulkkoli.domain.post.ImplPostRepository;
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
