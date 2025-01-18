package com.compraClick.Model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Comentario implements Serializable {

    /**
     * Identificador único del comentario.
     */
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Usuario que realizó el comentario.
     * Relación @ManyToOne con la entidad Usuario.
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Usuario idUsuario;

    /**
     * Producto al que pertenece el comentario.
     * Relación @ManyToOne con la entidad Producto.
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Producto idProducto;

    /**
     * Calificación asignada al producto por el usuario.
     * Representado como un valor de 1 a 5 (por ejemplo, estrellas).
     */
    @Column(nullable = false)
    private byte calificacion;

    /**
     * Mensaje del comentario.
     */
    @Column(nullable = false, length = 1000)
    private String mensaje;
}
