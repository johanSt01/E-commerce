package com.compraClick.Model.entities;

import com.compraClick.Model.enums.Categoria;
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
public class Producto {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // Identificador único del producto

    @Column(unique = false, length = 200, insertable = true, updatable = false, nullable = false)
    private String nombre; // Nombre del producto

    @Column(unique = false, length = 500, insertable = true, updatable = true, nullable = true)
    private String descripcion; // Descripción del producto

    @Column(unique = false, length = 255, insertable = true, updatable = true, nullable = false)
    private String imagen; // URL o referencia de la imagen del producto

    @Column(unique = false, insertable = true, updatable = true, nullable = false)
    private double precio; // Precio del producto

    @Column(unique = false, insertable = true, updatable = true, nullable = false)
    private int stock; // Cantidad disponible en inventario

    @Enumerated(EnumType.STRING)
    @Column(unique = false, insertable = true, updatable = true, nullable = false)
    private Categoria idCategoria; // Categoría del producto (enum)

    // Relación uno a muchos con DetalleCarrito
    @OneToMany(mappedBy = "idProducto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleCarrito> detalleCarrito; // Lista de detalles del carrito asociados al producto

    // Relación uno a muchos con Comentario
    @OneToMany(mappedBy = "idProducto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comentario> comentarios; // Lista de comentarios asociados al producto
}
