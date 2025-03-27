package com.compraClick.DTO.User;

import java.time.LocalDateTime;

public record ListaComprasDTO(
        int idCompra,
        LocalDateTime fechaCompra,
        float montoTotal
) {
}
