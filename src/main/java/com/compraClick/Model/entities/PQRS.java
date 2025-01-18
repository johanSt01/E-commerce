package com.compraClick.Model.entities;

import com.compraClick.Model.enums.EstadoPQRS;
import com.compraClick.Model.enums.TipoPQRS;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PQRS implements Serializable {

    /**
     * Identificador único de la PQRS.
     */
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Fecha y hora en la que se creó la PQRS.
     */
    @Column(nullable = false)
    private LocalDateTime fechaCreacion;

    /**
     * Motivo o descripción del motivo de la PQRS (puede ser una queja, sugerencia, etc.).
     */
    @Column(nullable = false, length = 500)
    private String motivo;

    /**
     * Usuario que creó la PQRS.
     * Relación @ManyToOne con la entidad Usuario.
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Usuario idUsuario;

    /**
     * Tipo de la PQRS (Petición, Queja, Reclamo, Sugerencia).
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoPQRS idTipo;

    /**
     * Estado de la PQRS (pendiente, resuelta, etc.).
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPQRS idEstado;
}
