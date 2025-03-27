package com.compraClick.Service.Implementations;

import com.compraClick.DTO.Producto.DetalleProductoDTO;
import com.compraClick.DTO.Producto.ProductoDTO;
import com.compraClick.DTO.Suscripcion.DetalleSuscripcionDTO;
import com.compraClick.DTO.Suscripcion.SuscripcionDTO;
import com.compraClick.DTO.User.DetalleUsuarioDTO;
import com.compraClick.Model.entities.Producto;
import com.compraClick.Model.entities.Suscripcion;
import com.compraClick.Model.entities.Usuario;
import com.compraClick.Model.enums.EstadoSuscripcion;
import com.compraClick.Model.enums.TipoSuscripcion;
import com.compraClick.Repository.ProductoRepository;
import com.compraClick.Repository.SuscripcionRepository;
import com.compraClick.Repository.UsuarioRepository;
import com.compraClick.Service.Interfaces.AdministradorServicio;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor //crea el constructor de todos los metodos
public class AdministradorServicioImpl implements AdministradorServicio {

    // Repositorios para la búsqueda de información en la base de datos
    private final ProductoRepository productoRepo;
    private final UsuarioRepository usuarioRepo;

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

    @Override
    public List<DetalleSuscripcionDTO> obtenerDetalleSuscripciones() {
        return List.of(TipoSuscripcion.values()).stream()
                .map(tipo -> new DetalleSuscripcionDTO(
                        tipo.getNombre(),
                        tipo.getDescripcion(),
                        tipo.getPorcentajeDescuento(),
                        tipo.getDuracionDias()
                ))
                .collect(Collectors.toList());
    }


    @Override
    public List<DetalleUsuarioDTO> obtenerDetalleUsuarios() throws Exception {
        return usuarioRepo.findAll().stream()
                .map(usuario -> new DetalleUsuarioDTO(
                        usuario.getId(),
                        usuario.getEmail(),
                        usuario.getCedula(),
                        usuario.getNombre(),
                        usuario.getApellido(),
                        usuario.getTelefono(),
                        usuario.getDireccion(),
                        usuario.getIdCiudad()
                )).collect(Collectors.toList());
    }

}
