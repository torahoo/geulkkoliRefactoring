package com.geulkkoli.domain.post_hashtag;

import com.geulkkoli.domain.hashtag.HashTag;
import com.geulkkoli.domain.post.Post;
import com.geulkkoli.web.post.dto.ListDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@Transactional
public class Post_HashTagService {

    private final Post_HashTagRepository postHashTagRepository;

    public Post_HashTagService(Post_HashTagRepository postHashTagRepository) {
        this.postHashTagRepository = postHashTagRepository;
    }

    public Long addPostHashTag (Post post, HashTag tag){
        return postHashTagRepository.save(post.addHashTag(tag)).getPostHashTagId();
    }


}
