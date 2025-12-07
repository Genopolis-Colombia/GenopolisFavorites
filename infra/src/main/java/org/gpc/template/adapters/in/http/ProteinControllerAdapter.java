package org.gpc.template.adapters.in.http;

import lombok.AllArgsConstructor;
import org.gpc.template.adapters.in.http.dto.CreateProteinRequestDTO;
import org.gpc.template.adapters.in.http.dto.DTO;
import org.gpc.template.handlers.CreateProteinHandler;
import org.gpc.template.handlers.DeleteProteinHandler;
import org.gpc.template.handlers.GetProteinHandler;
import org.gpc.template.handlers.commands.UpdateProteinCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@AllArgsConstructor
public class ProteinControllerAdapter {
  private final CreateProteinHandler createProteinHandler;
  private final GetProteinHandler getProteinHandler;
  private final DeleteProteinHandler deleteProteinHandler;
  private final UpdateProteinHandler updateProteinHandler;
  private static final Logger logger = LoggerFactory.getLogger(ProteinControllerAdapter.class);

  @PostMapping("/proteins")
  public ResponseEntity<DTO> createProtein(@RequestBody CreateProteinRequestDTO proteinRequestDto) {
    return createProteinHandler.handle(proteinRequestDto);
  }

  @GetMapping("/proteins/{protein_id}")
  public ResponseEntity<DTO> getProtein(@PathVariable UUID protein_id) {
    return getProteinHandler.handle(protein_id);
  }

  @DeleteMapping("/proteins/{protein_id}")
  public ResponseEntity<DTO> deleteProtein(@PathVariable UUID protein_id) {
    return deleteProteinHandler.handle(protein_id);
  }

  @PutMapping("/proteins/{protein_id}")
  public ResponseEntity<DTO> putPet(@PathVariable UUID protein_id, @RequestBody UpdateProteinRequestDTO proteinRequestDto) {
    return updateProteinHandler.handle(new UpdateProteinCommand(proteinRequestDto, protein_id));
  }

}
