package org.gpc.template.handlers;

import lombok.AllArgsConstructor;
import org.gpc.template.adapters.in.http.dto.DTO;
import org.gpc.template.adapters.in.http.dto.ErrorResponse;
import org.gpc.template.adapters.in.http.dto.ProteinResponseDTO;
import org.gpc.template.usecase.GetFavoriteUseCaseImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

@AllArgsConstructor
public class GetProteinHandler implements Handler<UUID, ResponseEntity<DTO>> {
  private final GetFavoriteUseCaseImpl getProteinUseCase;

  @Override
  public ResponseEntity<DTO> handle(UUID proteinID) {
    return getProteinUseCase.execute(proteinID)
        .map(protein -> new ResponseEntity<DTO>(
                new ProteinResponseDTO(proteinID, protein.fastaNombre(),protein.fastaSecuencia(),protein.fuente(),protein.organismo(),protein.clasificacion(),protein.ecClasificacion(),protein.autores()),
                HttpStatus.OK
            )
        ).orElse( //Entra aquí si el optional tiene nulo. Es decir, que no trae una proteína.
            new ResponseEntity<>(
                new ErrorResponse("Protein not found", "the protein with id: " + proteinID + " was not found"),
                HttpStatus.NOT_FOUND)
        );
  }
}
