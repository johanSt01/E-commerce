package com.compraClick.Model.enums;

public enum TipoSuscripcion {
    BASICO(
            "Básico",
            "Acceso a descuentos semanales del 10%",
            10.0,
            30
    ),
    MEDIO(
            "Medio",
            "Descuentos del 20% + Envío gratis",
            20.0,
            60
    ),
    PRO(
            "Pro",
            "30% de descuento + Prioridad en envíos",
            30.0,
            90
    );

    private final String nombre;
    private final String descripcion;
    private final double porcentajeDescuento;
    private final int duracionDias;

    // Constructor
    TipoSuscripcion(String nombre, String descripcion, double porcentajeDescuento, int duracionDias) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.porcentajeDescuento = porcentajeDescuento;
        this.duracionDias = duracionDias;
    }

    // Métodos getter
    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public int getDuracionDias() {
        return duracionDias;
    }
}
