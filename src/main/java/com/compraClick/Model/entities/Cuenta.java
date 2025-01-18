package com.compraClick.Model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cuenta implements Serializable {

    /**
     * Identificador único de la cuenta.
     */
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Dirección de correo electrónico asociada a la cuenta.
     * Es única, obligatoria y no actualizable después de ser creada.
     */
    @Column(nullable = false, updatable = false, unique = true)
    @Email(message = "El email debe tener un formato válido")
    @NotBlank(message = "El email no puede estar vacío")
    private String email;

    /**
     * Contraseña de la cuenta.
     * Es obligatoria y tiene una longitud máxima de 250 caracteres.
     */
    @Column(nullable = false, updatable = true, length = 250)
    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 8, max = 250, message = "La contraseña debe tener entre 8 y 250 caracteres")
    private String password;

}
