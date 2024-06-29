package com.compraClick.modelo.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.*;
import org.springframework.web.service.annotation.GetExchange;

import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Carrito implements Serializable {
    @Id
    @EqualsAndHashCode.Include
    private int id;

    @OneToOne
    private Usuario idUsuario;
    @ManyToOne
    private DetalleCarrito idDetalleCarrito;
}
