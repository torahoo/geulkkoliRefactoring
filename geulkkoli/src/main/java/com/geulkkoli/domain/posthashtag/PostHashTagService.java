package com.geulkkoli.domain.posthashtag;

import com.geulkkoli.domain.hashtag.HashTag;
import com.geulkkoli.domain.hashtag.HashTagRepository;
import com.geulkkoli.domain.post.Post;
import com.geulkkoli.domain.post.PostRepository;
import com.geulkkoli.web.post.dto.ListDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PostHashTagService {

    private final HashTagRepository hashTagRepository;
    private final PostHashTagRepository postHashTagRepository;

    public Long addHashTagToPost(Post post, HashTag tag) {
        return postHashTagRepository.save(post.addHashTag(tag)).getPostHashTagId();
    }

    public void addHashTagsToPost(Post post, List<HashTag> tags) {
        for (HashTag tag : tags) {
            addHashTagToPost(post, tag);
        }
    }

    public PostHashTag findByPostHashTagId(Long postHashTagId) {
        return postHashTagRepository.findById(postHashTagId).orElseThrow(
                () -> new NoSuchElementException("no such postHashTag by Id:" + postHashTagId)
        );
    }

    public HashTag findByHashTagId(Long hashTagId){
        return hashTagRepository.findHashTagByHashTagId(hashTagId);
    }

    public Page<ListDTO> searchPostsListByHashTag(Pageable pageable, String searchType, String searchWords) {

        String searchWord = searchWordExtractor(searchWords);
        List<HashTag> tags = hashTagSeparator(searchWords);

        List<Post> posts = searchPostContainAllHashTags(tags);

        List<Post> resultList = new ArrayList<>();

        switch (searchType) {
            case "제목":
                resultList = posts.stream()
                        .filter(post -> post.getTitle().contains(searchWord))
                        .collect(Collectors.toList());
                break;
            case "본문":
                resultList = posts.stream()
                        .filter(post -> post.getPostBody().contains(searchWord))
                        .collect(Collectors.toList());
                break;
            case "닉네임":
                resultList = posts.stream()
                        .filter(post -> post.getNickName().contains(searchWord))
                        .collect(Collectors.toList());
                break;
            case "해시태그":
                List<PostHashTag> postHashTagList = postHashTagRepository.findAllByHashTag(hashTagRepository.findHashTagByHashTagName(searchWord));
                List<Post> resultPosts = new ArrayList<>();
                for (PostHashTag postHashTag : postHashTagList) {
                    resultPosts.add(postHashTag.getPost());
                }
                posts = resultPosts;
                break;
            default:
                resultList = new ArrayList<>(posts);
                break;
        }


        Page<Post> finalPostsList = new PageImpl<>(resultList, pageable, resultList.size());
        return finalPostsList.map(post -> new ListDTO(
                post.getPostId(),
                post.getTitle(),
                post.getNickName(),
                post.getUpdatedAt(),
                post.getPostHits()
        ));
    }


    public List<HashTag> hashTagSeparator(String searchWords) {
        Set<HashTag> hashTags = new LinkedHashSet<>();
        String[] splitter = searchWords.split("#");
        for (int i = 1; i < splitter.length; i++) {
            String stripper = splitter[i].strip();

            HashTag hashTagByHashTagName = hashTagRepository.findHashTagByHashTagName(stripper);

            if (hashTagByHashTagName != null) {
                hashTags.add(hashTagByHashTagName);
            } else {
                HashTag save = hashTagRepository.save(new HashTag(stripper));
                hashTags.add(save);
            }
        }

        return new ArrayList<>(hashTags);
    }

    public String searchWordExtractor(String searchWords) {
        String[] splitter = searchWords.split("#");
        return splitter[0].strip();
    }

    public List<Post> searchPostContainAllHashTags(List<HashTag> tags) {
        HashTag tag = tags.get(0);
        List<Post> posts = new ArrayList<>();
        List<PostHashTag> postHashTagList = postHashTagRepository.findAllByHashTag(tag);

        for (PostHashTag postHashTag : postHashTagList) {
            Set<HashTag> postHashTags = postHashTag.
                    getPost().
                    getPostHashTags().
                    stream().
                    map(PostHashTag::getHashTag).
                    collect(java.util.stream.Collectors.toSet());

            if (postHashTags.containsAll(tags)) {
                posts.add(postHashTag.getPost());
            }
        }

        return posts;
    }
}