package com.geulkkoli.domain.hashtag;

import com.geulkkoli.domain.post.Post;
import com.geulkkoli.domain.posthashtag.PostHashTag;

import java.util.List;

public interface HashTagRepositoryCustom {
    List<Long> hashIdsByHashTagNames(List<String> hashTagNames);


}
