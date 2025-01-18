package com.compraClick.Model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Pedido implements Serializable {

    /**
     * Identificador único del pedido.
     */
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Fecha en la que se realizó el pedido.
     */
    @Column(nullable = false)
    private LocalDate fechaPedido;

    /**
     * Total pagado por el pedido.
     */
    @Column(nullable = false)
    @Positive(message = "El total pagado debe ser mayor a cero")
    private double totalPagado;
}
