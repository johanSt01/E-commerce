package com.compraClick.Repository;

import com.compraClick.Model.entities.Producto;
import com.compraClick.Model.enums.EstadoProducto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    List<Producto> findByEstadoProducto(EstadoProducto estadoProducto);

    // Para búsqueda por nombre (útil para buscador)
    List<Producto> findByEstadoProductoAndNombreContainingIgnoreCase(EstadoProducto estadoProducto, String nombre);

    // Para productos con stock bajo (alertas de inventario)
    List<Producto> findByEstadoProductoAndStockLessThan(EstadoProducto estadoProducto, Integer stock);

    // Para rango de precios
    List<Producto> findByEstadoProductoAndPrecioBetween(EstadoProducto estadoProducto, Double minPrecio, Double maxPrecio);
}
