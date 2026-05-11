package com.compraClick.DTO.Producto;

import com.compraClick.Model.enums.Categoria;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record ProductoDTO(
        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 200, message = "El nombre no puede exceder los 200 caracteres")
        String nombre,                // Nombre del producto

        @NotBlank(message = "La descripción es obligatoria")
        @Size(max = 200, message = "La descripción no puede exceder los 500 caracteres")
        String descripcion,           // Descripción del producto

        @NotEmpty(message = "Debe incluir al menos una imagen")
        List<MultipartFile> imagenes,        // Lista de URLs de imágenes

        @NotNull(message = "El precio es obligatorio")
        double precio,                // Precio del producto

        @NotNull(message = "Agregar cantidad de productos")
        int stock,                    // Cantidad disponible en inventario

        @NotNull(message = "Agregar la categoria correspondiente")
        Categoria idCategoria         // Categoría del producto (enum)
) {
}
