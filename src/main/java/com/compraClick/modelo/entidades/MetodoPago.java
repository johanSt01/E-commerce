package com.compraClick.modelo.entidades;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class MetodoPago implements Serializable {

    /**
     * Identificador único del método de pago.
     */
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Tipo de método de pago (por ejemplo: tarjeta de crédito, PayPal, etc.).
     */
    @Column(nullable = false, length = 50)
    private String tipo;

    /**
     * Detalles específicos del método de pago (por ejemplo: número de cuenta, referencia, etc.).
     */
    @Column(nullable = false, length = 250)
    private String detalle;

    /**
     * Usuario propietario del método de pago.
     * Relación @ManyToOne con la entidad Usuario.
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Usuario idUsuario;

    /**
     * Compra asociada a este método de pago.
     * Relación @OneToOne con la entidad Compra.
     * Uso de `mappedBy` para indicar que la relación está mapeada en la clase Compra.
     */
    @OneToOne(mappedBy = "idMetodoPago", fetch = FetchType.LAZY)
    private Compra compra;
}
