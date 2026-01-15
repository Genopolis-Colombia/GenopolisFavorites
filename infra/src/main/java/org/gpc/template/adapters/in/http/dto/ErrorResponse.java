package org.gpc.template.adapters.in.http.dto;

public record ErrorResponse(String failure, String detail) implements DTO {
}
