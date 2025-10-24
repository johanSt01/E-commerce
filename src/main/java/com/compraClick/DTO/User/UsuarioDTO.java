package com.compraClick.DTO.User;

import com.compraClick.Model.enums.Ciudad;
import jakarta.validation.constraints.*;

public record UsuarioDTO(
        @NotBlank(message = "El correo es obligatorio")
        @Email(message = "El correo debe tener un formato válido")
        String email,

        @NotBlank(message = "La contraseña es obligatoria")
        @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
        String password,

        @NotBlank(message = "La cédula es obligatoria")
        @Size(min = 5, max = 12)
        String cedula,

        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 200, message = "El nombre no puede exceder los 200 caracteres")
        String nombre,

        @NotBlank(message = "El apellido es obligatorio")
        @Size(max = 200, message = "El apellido no puede exceder los 200 caracteres")
        String apellido,

        @NotBlank(message = "El teléfono es obligatorio")
        @Pattern(regexp = "^\\d{10}$", message = "El teléfono debe tener exactamente 10 dígitos")
        String telefono,

        @NotBlank(message = "La dirección es obligatoria")
        @Size(max = 100, message = "La dirección no puede exceder los 100 caracteres")
        String direccion,

        @NotNull(message = "La ciudad es obligatoria")
        Ciudad idCiudad
) {
}

