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
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PostHashTagService {

    private final HashTagRepository hashTagRepository;
    private final PostHashTagRepository postHashTagRepository;
    private final PostRepository postRepository;

    public Long addHashTagToPost (Post post, HashTag tag){
        return postHashTagRepository.save(post.addHashTag(tag)).getPostHashTagId();
    }

    public PostHashTag findByPostHashTagId (Long postHashTagId) {
        return postHashTagRepository.findById(postHashTagId).orElseThrow(
                () -> new NoSuchElementException("no such postHashTag by Id:" + postHashTagId)
        );
    }

    public Page<ListDTO> searchPostsListByHashTag (Pageable pageable, String searchType, String searchWords, HashTag tag) {
        List<Post> posts;
        String searchWord = searchWordExtractor(searchWords);
        List<HashTag> tags = hashTagSeparator(searchWords);
        switch (searchType) {
            case "제목":
                posts=postRepository.findPostsByTitleContaining(searchWords);
                break;
            case "본문":
                posts=postRepository.findPostsByPostBodyContaining(searchWords);
                break;
            case "닉네임":
                posts=postRepository.findPostsByNickNameContaining(searchWords);
                break;
            case "해시태그" :
                List<PostHashTag> postHashTagList = postHashTagRepository.findAllByHashTag(tag);
                List<Post> resultPosts = new ArrayList<>();
                for(PostHashTag postHashTag : postHashTagList) {
                    resultPosts.add(postHashTag.getPost());
                }
                posts = resultPosts;
                break;
            default:
                posts=postRepository.findAll();
                break;
        }
        List<Post> resultList = new ArrayList<>();
        for(int i=0; i<posts.size(); i++) {
            Post post = posts.get(i);
            List<PostHashTag> comparePosts = new ArrayList<>(post.getPostHashTags());
            for (int j=0; j<comparePosts.size(); j++) {
                if(comparePosts.get(j).getHashTag()==tag) {
                    resultList.add(comparePosts.get(j).getPost());
                }
            }
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

    public List<HashTag> hashTagSeparator(String searchWords){
        List<HashTag> hashTags = new ArrayList<>();
        String[] splitter = searchWords.split("#");
        for (int i = 1; i<splitter.length; i++){
            String stripper = splitter[i].strip();

            HashTag hashTagByHashTagName = hashTagRepository.findHashTagByHashTagName(stripper);

            if(hashTagByHashTagName!=null)
                hashTags.add(hashTagByHashTagName);
        }

        return hashTags;
    }

    public String searchWordExtractor(String searchWords){
        String[] splitter = searchWords.split("#");
        return splitter[0].strip();
    }
}
