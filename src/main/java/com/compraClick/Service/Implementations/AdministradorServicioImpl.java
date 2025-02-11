package com.compraClick.Service.Implementations;

import com.compraClick.DTO.Producto.DetalleProductoDTO;
import com.compraClick.DTO.Producto.ProductoDTO;
import com.compraClick.Model.entities.Producto;
import com.compraClick.Repository.ProductoRepository;
import com.compraClick.Service.Interfaces.AdministradorServicio;

public class AdministradorServicioImpl implements AdministradorServicio {
    private ProductoRepository productoRepo;

    @Override
    public int crearProducto(ProductoDTO productoDTO) throws Exception {
        Producto producto = new Producto();
        producto.setNombre(productoDTO.nombre());
        producto.setDescripcion(productoDTO.descripcion());
        producto.setImagenes(productoDTO.imagenes());
        producto.setPrecio(productoDTO.precio());
        producto.setStock(productoDTO.stock());
        producto.setIdCategoria(productoDTO.idCategoria());

        Producto productoNuevo = productoRepo.save(producto);

        return productoNuevo.getId();
    }

    @Override
    public int actualizarProducto(DetalleProductoDTO productoDTO) throws Exception {
        return 0;
    }

    @Override
    public int eliminarProducto(int id) throws Exception {
        return 0;
    }
}
