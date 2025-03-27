package com.compraClick.DTO.MetodosPago;

import com.compraClick.Model.enums.TipoMetodoPago;

public record MetodoPagoDTO(
        int id,
        TipoMetodoPago tipo,
        String detalle,
        int idUsuario
) {
}
