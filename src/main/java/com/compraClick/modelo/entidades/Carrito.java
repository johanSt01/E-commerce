package com.compraClick.modelo.entidades;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.service.annotation.GetExchange;

import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Carrito implements Serializable {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    private Usuario idUsuario;

    @ManyToOne
    private DetalleCarrito idDetalleCarrito;
}
