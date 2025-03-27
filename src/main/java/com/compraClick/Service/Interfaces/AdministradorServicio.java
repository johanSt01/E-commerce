package com.compraClick.Service.Interfaces;

import com.compraClick.DTO.Producto.DetalleProductoDTO;
import com.compraClick.DTO.Producto.ProductoDTO;
import com.compraClick.DTO.Suscripcion.DetalleSuscripcionDTO;
import com.compraClick.DTO.Suscripcion.SuscripcionDTO;
import com.compraClick.DTO.User.DetalleUsuarioDTO;

import java.util.List;

public interface AdministradorServicio {
    int crearProducto(ProductoDTO productoDTO) throws Exception;
    int actualizarProducto(DetalleProductoDTO productoDTO) throws Exception;
    int eliminarProducto(int id) throws Exception;
    /*int actualizarSuscripcion(DetalleSuscripcionDTO detalleSuscripcionDTO) throws Exception;
    int eliminarSuscripcion(int id) throws Exception;*/
    List<DetalleSuscripcionDTO> obtenerDetalleSuscripciones();
    List<DetalleUsuarioDTO> obtenerDetalleUsuarios() throws Exception;
}
