package com.compraClick.modelo.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DetallePedido {
    @Id
    @EqualsAndHashCode.Include
    private int id;

    @ManyToOne
    private Producto idProducto;
    private int cantidad;
    private double precioUnitario;
}
