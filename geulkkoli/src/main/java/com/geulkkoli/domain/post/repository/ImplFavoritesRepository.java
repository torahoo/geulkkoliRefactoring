package com.geulkkoli.domain.post.repository;

import com.geulkkoli.domain.post.entity.Favorites;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@Transactional
@RequiredArgsConstructor
public class ImplFavoritesRepository implements FavoritesRepository{

    private final EntityManager em;

    @Override
    public Favorites save(Favorites favorite) {
        em.persist(favorite);
        return favorite;
    }

    @Override
    public Optional<Favorites> findById(Long favoriteId) {
        Favorites favorite = em.find(Favorites.class, favoriteId);
        return Optional.of(favorite);
    }

    @Override
    public List<Favorites> findAll() {
        return em.createQuery("select f from Favorites f", Favorites.class)
                .getResultList();
    }

    @Override
    public void delete(Long favoriteId) {
        Favorites findFavorite = em.find(Favorites.class, favoriteId);
        findFavorite.getPost().getFavorites().remove(findFavorite);
        findFavorite.getUser().getFavorites().remove(findFavorite);
        em.remove(findFavorite);
    }

    public void clear () {
        em.close();
    }
}
