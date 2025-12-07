package org.gpc.template.handlers.commands;

import java.util.UUID;

public record UpdateProteinCommand(UpdateProteinRequestDTO updateProteinRequestDTO, UUID proteinID) {
}
