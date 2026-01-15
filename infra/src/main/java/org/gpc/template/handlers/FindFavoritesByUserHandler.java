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
        log.info("Handling request to find favorites for user: {}", userId);
        try {
            List<Favorite> favorites = findFavoritesByUserUseCase.execute(userId);
            
            List<FavoriteResponseDTO> favoriteDTOs = favorites.stream()
                    .map(favorite -> new FavoriteResponseDTO(
                            favorite.id(),
                            favorite.userId(),
                            favorite.proteinId(),
                            favorite.fastaName()))
                    .toList();

            return ResponseEntity.ok(new FavoriteListResponseDTO(favoriteDTOs));
        } catch (Exception e) {
            log.error("Error finding favorites for user {}: {}", userId, e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(new ErrorResponse("Error internal server", e.getMessage()));
        }
    }
}
