package com.compraClick.Service.Implementations;

import com.compraClick.DTO.Producto.DetalleProductoDTO;
import com.compraClick.DTO.Producto.ProductoDTO;
import com.compraClick.Model.entities.Producto;
import com.compraClick.Repository.ProductoRepository;
import com.compraClick.Service.Interfaces.AdministradorServicio;

import java.util.Optional;

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
        Optional<Producto> opcional = productoRepo.findById(productoDTO.id());
        if (opcional.isEmpty()) {
            throw new Exception("No existe el producto");
        }
        Producto productoBuscado = opcional.get();
        productoBuscado.setNombre(productoDTO.nombre());
        productoBuscado.setDescripcion(productoDTO.descripcion());
        productoBuscado.setImagenes(productoDTO.imagenes());
        productoBuscado.setPrecio(productoDTO.precio());
        productoBuscado.setStock(productoDTO.stock());
        productoRepo.save(productoBuscado);

        return productoBuscado.getId();
    }

    @Override
    public int eliminarProducto(int id) throws Exception {
        Producto producto = productoRepo.findById(id).
                orElseThrow(() -> new Exception("Producto no encontrado"));

        if (producto.getStock() == 0) {
            producto.setActivo(false); // Deshabilita el producto
            productoRepo.save(producto);
            return 1; // Indica que el producto fue deshabilitado
        } else {
            throw new Exception("El producto aún tiene stock, no se puede deshabilitar");
        }
    }
}
