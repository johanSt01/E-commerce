package com.compraClick.Model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DetalleCarrito {

    /**
     * Identificador único del detalle del carrito.
     */
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Producto asociado al detalle del carrito.
     * Relación @ManyToOne con la entidad Producto.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private Producto idProducto;

    /**
     * Cantidad del producto en el carrito.
     * Este valor es obligatorio.
     */
    @Column(insertable = true, updatable = true, nullable = false)
    private int cantidad;

    /**
     * Precio total del producto (cantidad * precio unitario).
     * Este valor es obligatorio.
     */
    @Column(insertable = true, updatable = true, nullable = false)
    private int precioTotal;

    /**
     * Carrito asociado al detalle del carrito.
     * Relación @OneToMany con la entidad Carrito.
     */
    @OneToMany(mappedBy = "idDetalleCarrito", fetch = FetchType.LAZY)
    private List<Carrito> carrito;
}
