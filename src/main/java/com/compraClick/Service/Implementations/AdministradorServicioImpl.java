package com.compraClick.Service.Implementations;

import com.compraClick.DTO.Producto.DetalleProductoDTO;
import com.compraClick.DTO.Producto.ProductoDTO;
import com.compraClick.DTO.Suscripcion.DetalleSuscripcionDTO;
import com.compraClick.DTO.Suscripcion.SuscripcionDTO;
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
    private final ProductoRepository productoRepo;
    private final SuscripcionRepository suscripcionRepo;
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
    public int crearSuscripcion(SuscripcionDTO suscripcionDTO) throws Exception {
        // Validar que el usuario existe
        Usuario usuario = usuarioRepo.findById(suscripcionDTO.usuarioId())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        // Verificar que el usuario no tenga ya una suscripción activa
        Optional<Suscripcion> suscripcionActiva = suscripcionRepo.findActiveByUsuario(usuario.getId());
        if (suscripcionActiva.isPresent()) {
            throw new Exception("El usuario ya tiene una suscripción activa");
        }

        // Obtener el tipo de suscripción directamente del DTO
        TipoSuscripcion tipo = suscripcionDTO.tipoSuscripcion();

        // Mapear DTO a Entidad y asignar los valores según el tipo de suscripción
        Suscripcion suscripcion = new Suscripcion();
        suscripcion.setNombre(suscripcionDTO.tipoSuscripcion().getNombre());
        suscripcion.setDescripcion(suscripcionDTO.tipoSuscripcion().getDescripcion());
        suscripcion.setFechaInicio(suscripcionDTO.fechaInicio());

        // Calcular fechaFin sumando la duración (en días) del tipo de suscripción a la fecha de inicio
        LocalDateTime fechaFin = suscripcionDTO.fechaInicio().plusDays(tipo.getDuracionDias());
        suscripcion.setFechaFin(fechaFin);
        suscripcion.setIdEstado(suscripcionDTO.idEstado());
        suscripcion.setPorcentajeDescuento(tipo.getPorcentajeDescuento());
        suscripcion.setIdUsuario(usuario);
        suscripcion.setIdEstado(EstadoSuscripcion.Activo);

        // Guardar la suscripción
        suscripcionRepo.save(suscripcion);

        return suscripcion.getId();
    }

    @Override
    public List<DetalleSuscripcionDTO> ObtenerDetalleSuscripciones() throws Exception {
        return Arrays.stream(TipoSuscripcion.values())
                .map(tipo -> new DetalleSuscripcionDTO(
                        tipo.getNombre(),
                        tipo.getDescripcion(),
                        tipo.getPorcentajeDescuento(),
                        tipo.getDuracionDias()
                ))
                .collect(Collectors.toList());
    }

    /*@Override
    public int actualizarSuscripcion(DetalleSuscripcionDTO detalleSuscripcionDTO) throws Exception {
        return 0;
    }

    @Override
    public int eliminarSuscripcion(int id) throws Exception {
        return 0;
    }*/

}
