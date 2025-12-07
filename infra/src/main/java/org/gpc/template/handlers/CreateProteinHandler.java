package org.gpc.template.handlers;

import lombok.AllArgsConstructor;
import org.gpc.template.adapters.in.http.dto.CreateProteinRequestDTO;
import org.gpc.template.adapters.in.http.dto.CreateProteinResponseDTO;
import org.gpc.template.adapters.in.http.dto.DTO;
import org.gpc.template.kernel.Favorite;
import org.gpc.template.usecase.CreateFavoriteUseCaseImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;


@AllArgsConstructor
public class CreateProteinHandler implements Handler<CreateProteinRequestDTO, ResponseEntity<DTO>> {
  private final CreateFavoriteUseCaseImpl createProteinUseCase;

  @Override
  public ResponseEntity<DTO> handle(CreateProteinRequestDTO proteinRequestDto) {
    UUID id = createProteinUseCase.execute(new Favorite(
        proteinRequestDto.fastaNombre(),
        proteinRequestDto.fastaSecuencia(),
        proteinRequestDto.fuente(),
        proteinRequestDto.organismo(),
        proteinRequestDto.clasificacion(),
        proteinRequestDto.ecClasificacion(),
        proteinRequestDto.autores()
    ));
    return new ResponseEntity<>(new CreateProteinResponseDTO(id), HttpStatus.CREATED);
  }
}
