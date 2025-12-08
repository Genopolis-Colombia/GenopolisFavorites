package org.gpc.template.adapters.in.http;

import lombok.AllArgsConstructor;
import org.gpc.template.adapters.in.http.dto.CreateFavoriteRequestDTO;
import org.gpc.template.adapters.in.http.dto.DTO;
import org.gpc.template.handlers.CreateFavoriteHandler;
import org.gpc.template.handlers.DeleteFavoriteHandler;
import org.gpc.template.handlers.FindFavoritesByUserHandler;
import org.gpc.template.handlers.GetFavoriteHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@AllArgsConstructor
public class FavoriteControllerAdapter {
  private final CreateFavoriteHandler createFavoriteHandler;
  private final GetFavoriteHandler getFavoriteHandler;
  private final DeleteFavoriteHandler deleteFavoriteHandler;
  private final FindFavoritesByUserHandler findFavoritesByUserHandler;

  private static final Logger logger = LoggerFactory.getLogger(FavoriteControllerAdapter.class);

  @PostMapping("/favorites")
  public ResponseEntity<DTO> createFavorite(@RequestBody CreateFavoriteRequestDTO favoriteRequestDto) {
    return createFavoriteHandler.handle(favoriteRequestDto);
  }

  @GetMapping("/favorites/{favorite_id}")
  public ResponseEntity<DTO> getFavorites(@PathVariable UUID favorite_id) {
    return getFavoriteHandler.handle(favorite_id);
  }

  @GetMapping("/favorites/user/{user_id}")
  public ResponseEntity<DTO> getFavoritesByUser(@PathVariable("user_id") UUID userId) {
      return findFavoritesByUserHandler.handle(userId);
  }

  @DeleteMapping("/favorites/{favorite_id}")
  public ResponseEntity<DTO> deleteFavorite(@PathVariable UUID favorite_id) {
    return deleteFavoriteHandler.handle(favorite_id);
  }

}
