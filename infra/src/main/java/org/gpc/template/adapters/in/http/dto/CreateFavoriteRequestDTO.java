package org.gpc.template.adapters.in.http.dto;

import java.util.UUID;

public record CreateFavoriteRequestDTO(UUID userId,
                                       UUID proteinId,
                                       String fastaName) {

}
