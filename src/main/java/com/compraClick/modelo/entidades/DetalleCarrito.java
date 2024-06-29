package com.compraClick.modelo.entidades;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DetalleCarrito {

    @Id
    @EqualsAndHashCode.Include
    private int id;

    @ManyToOne
    private Producto idProducto;

    private int cantidad;

    @OneToMany(mappedBy = "idDetalleCarrito")
    private List<Carrito> carrito;
}
