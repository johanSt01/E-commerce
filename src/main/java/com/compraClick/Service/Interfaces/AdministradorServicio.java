package com.compraClick.Service.Interfaces;

import com.compraClick.DTO.Producto.DetalleProductoDTO;
import com.compraClick.DTO.Producto.ProductoDTO;

public interface AdministradorServicio {
    int crearProducto(ProductoDTO productoDTO) throws Exception;
    int actualizarProducto(DetalleProductoDTO productoDTO) throws Exception;
    int eliminarProducto(int id) throws Exception;
}
