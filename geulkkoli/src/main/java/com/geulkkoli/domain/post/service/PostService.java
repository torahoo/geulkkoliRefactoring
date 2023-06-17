package com.geulkkoli.domain.post.service;

import com.geulkkoli.domain.hashtag.HashTag;
import com.geulkkoli.domain.post.Post;
import com.geulkkoli.domain.post.PostRepository;
import com.geulkkoli.domain.posthashtag.PostHashTag;
import com.geulkkoli.domain.posthashtag.PostHashTagService;
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
import org.springframework.validation.BindingResult;

import java.util.*;

@Slf4j
@Service
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostHashTagService postHashTagService;

    public PostService(PostRepository postRepository, UserRepository userRepository, PostHashTagService postHashTagService) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.postHashTagService = postHashTagService;
    }

    @Transactional(readOnly = true)
    public Post findById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("No post found id matches:" + postId));
    }

    @Transactional(readOnly = true)
    //게시글 상세보기만을 담당하는 메서드
    public Post showDetailPost(Long postId) {
        postRepository.updateHits(postId);
        return postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("No post found id matches:" + postId));
    }

    @Transactional(readOnly = true)
    public Page<ListDTO> findAll(Pageable pageable) {
        Page<Post> posts = postRepository.findAll(pageable);
        return posts.map(post -> new ListDTO(
                post.getPostId(),
                post.getTitle(),
                post.getNickName(),
                post.getUpdatedAt(),
                post.getPostHits()
        ));
    }

    @Transactional(readOnly = true)
    //전체 게시글 리스트 & 조회 기능 포함
    public Page<ListDTO> searchPostFindAll(Pageable pageable, String searchType, String searchWords) {
        Page<Post> posts;
        switch (searchType) {
            case "제목":
                posts = postRepository.findPostsByTitleContaining(pageable, searchWords);
                break;
            case "본문":
                posts = postRepository.findPostsByPostBodyContaining(pageable, searchWords);
                break;
            case "닉네임":
                posts = postRepository.findPostsByNickNameContaining(pageable, searchWords);
                break;
            default:
                posts = postRepository.findAll(pageable);
                break;
        }
        return posts.map(post -> new ListDTO(
                post.getPostId(),
                post.getTitle(),
                post.getNickName(),
                post.getUpdatedAt(),
                post.getPostHits()
        ));
    }

    @Transactional
    public Post savePost(AddDTO post, User user) {
        Post writePost = user.writePost(post);
        Post save = postRepository.save(writePost);
        List<HashTag> hashTags = postHashTagService.hashTagSeparator("#일반글" + post.getTagListString() + post.getTagCategory() + post.getTagStatus());
        postHashTagService.validatePostHasType(hashTags);
        postHashTagService.addHashTagsToPost(save, hashTags);

        return save;
    }

    @Transactional

    public void updatePost(Long postId, EditDTO updateParam) {
        Post post = findById(postId)
                .getUser()
                .editPost(postId, updateParam);
        ArrayList<PostHashTag> postHashTags = new ArrayList<>(post.getPostHashTags());
        if (updateParam.getTagListString() != null && updateParam.getTagListString() != "") {
            for (int i = 0; i < postHashTags.size(); i++) {
                post.deletePostHashTag(postHashTags.get(i).getPostHashTagId());
            }
            List<HashTag> hashTags = postHashTagService.hashTagSeparator(updateParam.getTagListString() + "#일반글");
            postHashTagService.addHashTagsToPost(post, hashTags);
        }
        postRepository.save(post);
    }

    @Transactional
    public void deletePost(Long postId, String nickName) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("No post found id matches:" + postId));
        Optional<User> byNickName = userRepository.findByNickName(nickName);
        ArrayList<PostHashTag> postHashTags = new ArrayList<>(post.getPostHashTags());
        if (byNickName.isPresent() && post.getUser().equals(byNickName.get())) {
            User user = byNickName.get();
            user.deletePost(post);
            for (int i = 0; i < postHashTags.size(); i++) {
                post.deletePostHashTag(postHashTags.get(i).getPostHashTagId());
            }
        }

        postRepository.delete(post);
    }

    @Transactional
    public void deleteAll() {
        postRepository.deleteAll();
    }
}
