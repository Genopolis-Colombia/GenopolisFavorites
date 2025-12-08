package org.gpc.template.handlers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gpc.template.adapters.in.http.dto.DTO;
import org.gpc.template.adapters.in.http.dto.ErrorResponse;
import org.gpc.template.adapters.in.http.dto.FavoriteListResponseDTO;
import org.gpc.template.adapters.in.http.dto.FavoriteResponseDTO;
import org.gpc.template.kernel.Favorite;
import org.gpc.template.usecase.FindFavoritesByUserUseCaseImpl;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
public class FindFavoritesByUserHandler implements Handler<UUID, ResponseEntity<DTO>> {

    private final FindFavoritesByUserUseCaseImpl findFavoritesByUserUseCase;

    @Override
    public ResponseEntity<DTO> handle(UUID userId) {
        try {
            List<Favorite> favorites = findFavoritesByUserUseCase.execute(userId);

            List<FavoriteResponseDTO> responseList = favorites.stream()
                    .map(favorite -> new FavoriteResponseDTO(
                            favorite.id(),
                            favorite.userId(),
                            favorite.proteinId(),
                            favorite.fastaName()
                    ))
                    .toList();

            FavoriteListResponseDTO response = new FavoriteListResponseDTO(responseList);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("Error getting favorites for userId={}", userId, e);
            return ResponseEntity.internalServerError()
                    .body(new ErrorResponse("Error getting favorites", e.getMessage()));
        }
    }
}
