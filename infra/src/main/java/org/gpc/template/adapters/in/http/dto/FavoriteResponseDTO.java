package org.gpc.template.adapters.in.http.dto;

import java.util.UUID;

public record FavoriteResponseDTO(UUID favoriteId,
                                  UUID userId,
                                  UUID proteinId,
                                  String fastaName) implements DTO {

}
