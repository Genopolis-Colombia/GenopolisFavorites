package org.gpc.template.handlers;

import lombok.AllArgsConstructor;
import org.gpc.template.adapters.in.http.dto.DTO;
import org.gpc.template.adapters.in.http.dto.ErrorResponse;
import org.gpc.template.adapters.in.http.dto.FavoriteResponseDTO;
import org.gpc.template.usecase.GetFavoriteUseCaseImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

@AllArgsConstructor
public class GetFavoriteHandler implements Handler<UUID, ResponseEntity<DTO>> {
  private final GetFavoriteUseCaseImpl getFavoriteUseCase;

  @Override
  public ResponseEntity<DTO> handle(UUID favoriteID) {
    return getFavoriteUseCase.execute(favoriteID)
        .map(favorite -> new ResponseEntity<DTO>(
                new FavoriteResponseDTO(favoriteID, favorite.userId(),favorite.proteinId(),favorite.fastaName()),
                HttpStatus.OK
            )
        ).orElse( //Entra aquí si el optional tiene nulo. Es decir, que no trae una proteína.
            new ResponseEntity<>(
                new ErrorResponse("Favorite not found", "the favorite with id: " + favoriteID + " was not found"),
                HttpStatus.NOT_FOUND)
        );
  }
}
