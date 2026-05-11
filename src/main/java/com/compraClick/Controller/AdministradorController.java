package com.compraClick.Controller;


import com.compraClick.DTO.Authentication.MensajeDTO;
import com.compraClick.DTO.Producto.DetalleProductoDTO;
import com.compraClick.DTO.Producto.ProductoDTO;
import com.compraClick.Model.enums.Categoria;
import com.compraClick.Service.Interfaces.AdministradorServicio;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Arrays;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
//@PreAuthorize("hasRole('ADMIN')")
public class AdministradorController {

    private final AdministradorServicio administradorServicio;

    //@PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/crearProducto", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MensajeDTO<String>> crearProducto(@Valid @ModelAttribute ProductoDTO productoDTO) {
        try {
            int idProducto = administradorServicio.crearProducto(productoDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body(new MensajeDTO<>(false,
                    "Producto creado exitosamente, Id del producto: " + idProducto));

        } catch (MethodArgumentTypeMismatchException e) {
            // Manejo específico para errores de enum
            return ResponseEntity.badRequest().body(new MensajeDTO<>(true,
                    "Categoría inválida. Opciones válidas: " +
                            Arrays.toString(Categoria.values())
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MensajeDTO<>(true,
                    "Error al crear producto: " + e.getMessage()
            ));
        }
    }

    @GetMapping
    public ResponseEntity<DetalleProductoDTO> obtenerProducto(@RequestParam int id) throws Exception{
        return administradorServicio.obtenerProducto(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
