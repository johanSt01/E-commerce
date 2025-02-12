package com.compraClick.DTO.Producto;

import jakarta.validation.constraints.*;

import java.util.List;

public record DetalleProductoDTO(
        @Positive
        int id,

        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 200, message = "El nombre no puede exceder los 200 caracteres")
        String nombre,                //Nombre del producto

        @NotBlank(message = "La descripción es obligatoria")
        @Size(max = 200, message = "La descripción no puede exceder los 500 caracteres")
        String descripcion,           // Descripción del producto

        @NotBlank(message = "Las imagenes son obligatorias")
        List<String> imagenes,        // Lista de URLs de imágenes

        @NotBlank(message = "El precio es obligatorio")
        double precio,                // Precio del producto

        @NotBlank(message = "Agregar cantidad de productos")
        int stock                    // Cantidad disponible en inventario
) {
}
