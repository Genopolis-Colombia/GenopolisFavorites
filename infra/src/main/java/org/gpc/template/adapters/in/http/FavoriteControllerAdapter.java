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
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@CrossOrigin(
        origins = "http://localhost:5173",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE},
        allowedHeaders = {"Content-Type", "Authorization"}
)
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
  public ResponseEntity<DTO> getFavorites(@PathVariable("favorite_id") UUID favoriteId) {
    return getFavoriteHandler.handle(favoriteId);
  }

  @GetMapping("/favorites/user/{user_id}")
  public ResponseEntity<DTO> getFavoritesByUser(@PathVariable("user_id") UUID userId) {
    return findFavoritesByUserHandler.handle(userId);
  }

  @DeleteMapping("/favorites/{favorite_id}")
  public ResponseEntity<DTO> deleteFavorite(@PathVariable("favorite_id") UUID favoriteId) {
    return deleteFavoriteHandler.handle(favoriteId);
  }

}
