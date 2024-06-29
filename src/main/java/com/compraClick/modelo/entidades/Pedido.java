package com.compraClick.modelo.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.io.Serializable;
import java.sql.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Pedido implements Serializable {

    @Id
    @EqualsAndHashCode.Include
    private int id;

    @ManyToOne
    private Usuario idUsuario;
    private Date fechaPedido;
    private double totalPagado;
    @ManyToOne
    private DetallePedido idDetallePedido;
}
