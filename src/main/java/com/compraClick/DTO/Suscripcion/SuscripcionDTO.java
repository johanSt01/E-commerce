package com.compraClick.DTO.Suscripcion;

import com.compraClick.Model.enums.EstadoSuscripcion;
import com.compraClick.Model.enums.TipoSuscripcion;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record SuscripcionDTO(
        TipoSuscripcion tipoSuscripcion,

        @NotNull(message = "La fecha de inicio es obligatoria")
        @FutureOrPresent(message = "La fecha de inicio debe ser presente o futura")
        LocalDateTime fechaInicio,

        @NotNull(message = "La fecha de fin es obligatoria")
        @Future(message = "La fecha de fin debe ser futura")
        LocalDateTime fechaFin,

        @NotNull(message = "El estado es obligatorio")
        EstadoSuscripcion idEstado,

        @NotNull(message = "El ID del usuario es obligatorio")
        int usuarioId
) {}