package org.gpc.template.handlers;

import lombok.AllArgsConstructor;
import org.gpc.template.adapters.in.http.dto.DTO;
import org.gpc.template.usecase.DeleteFavoriteUseCaseImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

@AllArgsConstructor
public class DeleteProteinHandler implements Handler<UUID, ResponseEntity<DTO>> {
  private final DeleteFavoriteUseCaseImpl deleteProteinUseCase;

  @Override
  public ResponseEntity<DTO> handle(UUID petID) {
    deleteProteinUseCase.execute(petID);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
