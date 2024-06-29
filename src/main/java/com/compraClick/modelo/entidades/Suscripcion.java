package com.compraClick.modelo.entidades;

import com.compraClick.modelo.enumeraciones.EstadoSuscripcion;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.io.Serializable;
import java.sql.Date;


@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Suscripcion implements Serializable {

    @Id
    @EqualsAndHashCode.Include
    private int id;
    private String nombre;
    private Date fechaInicio;
    private Date fechaFin;
    private EstadoSuscripcion idEstado;
    private double porcentajeDescuento;

    @OneToOne(mappedBy = "suscripcion")
    private Usuario usuario;
}
