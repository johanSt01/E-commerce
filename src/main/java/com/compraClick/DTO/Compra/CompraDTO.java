package com.compraClick.DTO.Compra;

public record CompraDTO(
        int idUsuario,
        float montoTotal,
        int idMetodoPago
) {
}
