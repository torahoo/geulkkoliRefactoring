package com.geulkkoli.domain.post.repository;

import com.geulkkoli.domain.post.entity.Favorites;

import java.util.List;
import java.util.Optional;

public interface FavoritesRepository {
    Favorites save (Favorites favorites);
    Optional<Favorites> findById (Long favoritesId);
    List<Favorites> findAll();
    void update (Long favoritesId, Favorites updateParam);
    void delete (Long favoritesId);
}
