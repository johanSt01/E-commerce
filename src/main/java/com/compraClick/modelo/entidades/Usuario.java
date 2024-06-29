package com.compraClick.modelo.entidades;

import com.compraClick.modelo.enumeraciones.Ciudad;
import com.compraClick.modelo.enumeraciones.EstadoUsuario;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Usuario implements Serializable {
    @Id
    @EqualsAndHashCode.Include
    private String cedula;

    private String nombre;
    private String apellido;
    private String telefono;
    private String direccion;

    @Column(nullable = false,length = 15)
    private Ciudad ciudad;

    private EstadoUsuario estadoUsuario;

    private String email;
    private String password;

    @OneToOne
    private Suscripcion suscripcion;

    @OneToOne(mappedBy = "idUsuario")
    private Carrito carrito;

    @OneToMany(mappedBy = "idUsuario")
    private List<Pedido> pedido;
}
