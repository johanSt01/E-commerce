package com.compraClick.DTO.Suscripcion;

public record DetalleSuscripcionDTO(
        String nombre,
        String descripcion,
        double porcentajeDescuento,
        int duracionDias
) {}
