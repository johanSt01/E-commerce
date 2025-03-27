package com.compraClick.Model.entities;

import com.compraClick.Model.enums.TipoMetodoPago;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

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
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private TipoMetodoPago tipo;

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
     * Relación @OneToMany con la entidad Compra.
     * Uso de `mappedBy` para indicar que la relación está mapeada en la clase Compra.
     */
    @OneToMany(mappedBy = "metodoPago", fetch = FetchType.LAZY)
    private List<Compra> compras;
}
