package com.geulkkoli.domain.favorites;

import com.geulkkoli.domain.post.Post;
import com.geulkkoli.domain.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
public class FavoriteService {

    private final FavoritesRepository favoritesRepository;

    public FavoriteService (FavoritesRepository favoritesRepository) {
        this.favoritesRepository = favoritesRepository;
    }

    //좋아요 추가
    public Long addFavorite(Post post, User user) {
        return favoritesRepository.save(user.pressFavorite(post)).getFavoritesId();
    }

    //ID로 좋아요 찾기
    public Favorites findById (Long favoriteId) {
        return favoritesRepository.findById(favoriteId)
                .orElseThrow(() -> new NoSuchElementException("No favorite id matches:"+favoriteId));
    }

    public List<Favorites> showFavoriteByPost (Post post) {
        return favoritesRepository.findAllByPost(post);
    }

    public List<Favorites> showFavoriteByUser (User user) {
        return favoritesRepository.findAllByUser(user);
    }

    public void undoFavorite (Favorites favorite) {
        Favorites deleteFavorite = favorite.getUser().cancelFavorite(favorite.getFavoritesId());
        favoritesRepository.delete(deleteFavorite);
    }

    public Favorites favoriteCheck (Post post, User user) {
        Favorites findFavorite = new Favorites();
        if (favoritesRepository.findByUserAndPost(user, post).isPresent()) {
            findFavorite = favoritesRepository.findByUserAndPost(user, post)
                    .orElseThrow(()->new NoSuchElementException("no such Favorite"));
        } else {
            findFavorite = null;
        }
        return findFavorite;
    }
}