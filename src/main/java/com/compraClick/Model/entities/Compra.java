package com.compraClick.Model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Compra implements Serializable {

    /**
     * Identificador único de la compra.
     */
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Usuario que realizó la compra.
     * Relación @ManyToOne con la entidad Usuario.
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Usuario idUsuario;

    /**
     * Fecha y hora en que se realizó la compra.
     */
    @Column(nullable = false)
    private LocalDateTime fechaCompra;

    /**
     * Monto total de la compra.
     */
    @Column(nullable = false)
    private float montoTotal;

    /**
     * Método de pago utilizado en la compra.
     * Relación @ManyToOne con la entidad MetodoPago.
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private MetodoPago metodoPago;
}
