package com.compraClick.Model.enums;

public enum EstadoPQRS {
    pendiente("Pendiente"),
    enProceso("En proceso"),
    resuelta("Resuelta");

    private String nombre;

    private EstadoPQRS(String nombre) {}
}
