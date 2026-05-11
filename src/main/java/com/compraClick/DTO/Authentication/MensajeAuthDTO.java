package com.compraClick.DTO.Authentication;

public record MensajeAuthDTO<T>(
        boolean error,
        String mensaje,
        T respuesta
) {
}
