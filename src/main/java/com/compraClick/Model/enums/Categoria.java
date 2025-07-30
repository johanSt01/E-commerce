package com.compraClick.Model.enums;

public enum Categoria {
    Tecnologia("Tecnología"),
    Electrodomesticos("Electrodomésticos"),
    Deportes("Deportes"),
    Juegos("Juegos y Juguetes"),
    Contruccion("Contrucción");

    private String nombre;

    private Categoria(String nombre) {
        this.nombre = nombre;
    }

    // Getter para el nombre
    public String getNombre() {
        return nombre;
    }
}
