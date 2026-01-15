package org.gpc.template.handlers;

import lombok.AllArgsConstructor;
import org.gpc.template.adapters.in.http.dto.CreateFavoriteRequestDTO;
import org.gpc.template.adapters.in.http.dto.CreateFavoriteResponseDTO;
import org.gpc.template.adapters.in.http.dto.DTO;
import org.gpc.template.kernel.Favorite;
import org.gpc.template.usecase.CreateFavoriteUseCaseImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;


@AllArgsConstructor
public class CreateFavoriteHandler implements Handler<CreateFavoriteRequestDTO, ResponseEntity<DTO>> {
  private final CreateFavoriteUseCaseImpl createFavoriteUseCase;

  @Override
  public ResponseEntity<DTO> handle(CreateFavoriteRequestDTO favoriteRequestDto) {
    UUID id = createFavoriteUseCase.execute(new Favorite(null,
        favoriteRequestDto.userId(),
        favoriteRequestDto.proteinId(),
        favoriteRequestDto.fastaName()
    ));
    return new ResponseEntity<>(new CreateFavoriteResponseDTO(id), HttpStatus.CREATED);
  }
}
