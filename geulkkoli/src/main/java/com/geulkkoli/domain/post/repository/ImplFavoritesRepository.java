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
    public Favorites save(Favorites favorites) {
        em.persist(favorites);
        return favorites;
    }

    @Override
    public Optional<Favorites> findById(Long favoritesId) {
        Favorites favorites = em.find(Favorites.class, favoritesId);
        return Optional.of(favorites);
    }

    @Override
    public List<Favorites> findAll() {
        return em.createQuery("select f from Favorites f", Favorites.class)
                .getResultList();
    }

    @Override
    public void update(Long favoritesId, Favorites updateParam) {
        Favorites findFavorites = em.find(Favorites.class, favoritesId);
        findFavorites.setPost(updateParam.getPost());
        findFavorites.setUser(updateParam.getUser());
    }

    @Override
    public void delete(Long favoritesId) {
        Favorites findFavorites = em.find(Favorites.class, favoritesId);
        em.remove(findFavorites);
    }

    public void clear () {
        em.close();
    }
}
