package com.compraClick.DTO.Authentication;

import jakarta.validation.constraints.NotBlank;

public record TokenDTO(
        @NotBlank
        String Token
) {
}
