package com.geulkkoli.domain.favorites;

import com.geulkkoli.domain.favorites.Favorites;

import java.util.List;
import java.util.Optional;

public interface FavoritesRepository {
    Favorites save (Favorites favorite);
    Optional<Favorites> findById (Long favoriteId);
    List<Favorites> findAll();
    void delete (Long favoriteId);
}
