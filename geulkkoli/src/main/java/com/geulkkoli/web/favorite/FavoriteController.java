package com.geulkkoli.web.favorite;

import com.geulkkoli.application.user.CustomAuthenticationPrinciple;
import com.geulkkoli.domain.favorites.FavoriteService;
import com.geulkkoli.domain.favorites.Favorites;
import com.geulkkoli.domain.post.Post;
import com.geulkkoli.domain.post.service.PostService;
import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@Slf4j
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;
    private final PostService postService;
    private final UserService userService;

    @PostMapping("pressFavorite/{postId}")
    public String pressFavoriteButton (@PathVariable("postId") Long postId,
                                       @AuthenticationPrincipal CustomAuthenticationPrinciple user,
                                       RedirectAttributes redirectAttributes) throws Exception {
        int favoriteResult;
        log.info("==========favoriteController==========");

        Post post = postService.findById(postId);
        User loginUser = userService.findById(Long.parseLong(user.getUserId()));
        if(favoriteService.favoriteCheck(post, loginUser)==null) {
            favoriteService.addFavorite(post, loginUser);
        } else {
            Favorites findFavorite = favoriteService.favoriteCheck(post, loginUser);
            favoriteService.undoFavorite(findFavorite);
        }
        return "redirect:/post/read/{postId}";
    }
}