package com.compraClick.Model.entities;

import com.compraClick.Model.enums.EstadoSuscripcion;
import com.compraClick.Model.enums.TipoSuscripcion;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Suscripcion implements Serializable {

    /**
     * Identificador único de la suscripción.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Estrategia de generación de claves primarias.
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Nombre asociado a la suscripción.
     * Este campo es obligatorio, no puede ser nulo, y tiene una longitud máxima de 200 caracteres.
     */
    @Column(unique = false, length = 200, insertable = true, updatable = true, nullable = false)
    private String nombre;

    /**
     * Descripción de la suscripción
     * Este campo es obligatorio, no puede ser nulo, y tiene una longitud máxima de 500 caracteres.
     */
    @Column(unique = false, length = 500, insertable = true, updatable = true, nullable = false)
    private String descripcion;
    /**
     * Fecha y hora en que comienza la suscripción.
     * Este campo no puede ser nulo.
     */
    @Column(nullable = false)
    private LocalDateTime fechaInicio;

    /**
     * Fecha y hora en que termina la suscripción.
     * Este campo no puede ser nulo.
     */
    @Column(nullable = false)
    private LocalDateTime fechaFin;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoSuscripcion tipo;

    /**
     * Estado actual de la suscripción, representado por una enumeración.
     * Se guarda como un valor ordinal en la base de datos.
     */
    @Enumerated(EnumType.STRING) // Almacena el nombre de la constante en lugar de su posición ordinal.
    @Column(nullable = false)
    private EstadoSuscripcion idEstado;

    /**
     * Porcentaje de descuento aplicado a la suscripción.
     * Este valor no puede ser negativo.
     */
    @Column(nullable = false)
    private double porcentajeDescuento;

    /**
     * Usuario asociado a la suscripción mediante una relación uno a uno.
     */
    @OneToOne
    private Usuario idUsuario;
}
