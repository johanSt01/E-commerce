package com.compraClick.modelo.entidades;

import com.compraClick.modelo.enumeraciones.Categoria;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Producto {

    @Id
    @EqualsAndHashCode.Include
    private int id;
    private String nombre;
    private String descripcion;
    private String imagen;
    private double precio;
    private int stock;

    private Categoria idCategoria;

    @OneToMany(mappedBy = "idProducto")
    private List<DetalleCarrito> detalleCarrito;

    @OneToMany(mappedBy = "idProducto")
    private List<DetallePedido> detallePedido;
}
