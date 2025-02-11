package com.compraClick.Model.entities;

import com.compraClick.Model.enums.Ciudad;
import com.compraClick.Model.enums.EstadoUsuario;
import com.compraClick.Model.enums.TipoUsuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Entidad que representa a un usuario en el sistema.
 * Extiende de la clase Cuenta, por lo que hereda atributos como el correo y la contraseña.
 * Incluye detalles como la cédula, nombre, dirección, estado y relaciones con otras entidades como Carrito, Compra, etc.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Usuario extends Cuenta implements Serializable {

    /**
     * Cédula del usuario, debe ser única y no nula.
     */
    @Column(unique = true, length = 12, insertable = true, updatable = true, nullable = false)
    private String cedula;

    /**
     * Nombre del usuario, no debe exceder los 200 caracteres y es obligatorio.
     */
    @Column(unique = false, length = 200, insertable = true, updatable = true, nullable = false)
    private String nombre;

    /**
     * Apellido del usuario, no debe exceder los 200 caracteres y es obligatorio.
     */
    @Column(unique = false, length = 200, insertable = true, updatable = true, nullable = false)
    private String apellido;

    /**
     * Teléfono del usuario, debe ser único, no nulo y tener un máximo de 10 caracteres.
     */
    @Column(unique = true, length = 10, insertable = true, updatable = true, nullable = false)
    @Pattern(regexp = "^\\d{10}$", message = "El teléfono debe tener exactamente 10 dígitos")
    private String telefono;

    /**
     * Dirección del usuario, no debe exceder los 100 caracteres y es obligatoria.
     */
    @Column(unique = false, length = 100, insertable = true, updatable = true, nullable = false)
    private String direccion;

    /**
     * Ciudad del usuario, se almacena como un valor de la enumeración Ciudad.
     * Este campo es obligatorio.
     */
    @Column(nullable = false, length = 15)
    @Enumerated(EnumType.STRING)
    private Ciudad idCiudad;

    /**
     * Estado del usuario (activo, inactivo).
     * Se almacena como un valor de la enumeración EstadoUsuario.
     */
    @Enumerated(EnumType.STRING)
    private EstadoUsuario estadoUsuario;

    /**
     * Tipo del usuario (Vendedor, comprador).
     * Se almacena como un valor de la enumeración TipoUsuario.
     */
    @Enumerated(EnumType.STRING)
    private TipoUsuario tipoUsuario;

    /**
     * Suscripción asociada al usuario.
     * Relación uno a uno con la entidad Suscripcion.
     */
    @OneToOne(mappedBy = "idUsuario")
    private Suscripcion suscripcion;

    /**
     * Carrito de compras asociado al usuario.
     * Relación uno a uno con la entidad Carrito.
     */
    @OneToOne(mappedBy = "idUsuario")
    private Carrito carrito;

    /**
     * Comentarios realizados por el usuario.
     * Relación uno a muchos con la entidad Comentario.
     */
    @OneToMany(mappedBy = "idUsuario")
    private List<Comentario> comentarios;

    /**
     * PQRS (Petición, Queja, Reclamo o Sugerencia) generadas por el usuario.
     * Relación uno a muchos con la entidad PQRS.
     */
    @OneToMany(mappedBy = "idUsuario")
    private List<PQRS> pqrs;

    /**
     * Compras realizadas por el usuario.
     * Relación uno a muchos con la entidad Compra.
     */
    @OneToMany(mappedBy = "idUsuario")
    private List<Compra> compras;

    /**
     * Métodos de pago asociados al usuario.
     * Relación uno a muchos con la entidad MetodoPago.
     */
    @OneToMany(mappedBy = "idUsuario")
    private List<MetodoPago> metodosPagos;
}
