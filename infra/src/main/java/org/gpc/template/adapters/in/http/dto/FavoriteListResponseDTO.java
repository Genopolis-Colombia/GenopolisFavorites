package org.gpc.template.adapters.in.http.dto;

import java.util.List;

public record FavoriteListResponseDTO(List<FavoriteResponseDTO> favorites) implements DTO {}

