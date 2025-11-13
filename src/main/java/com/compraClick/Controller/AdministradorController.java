package com.compraClick.Controller;


import com.compraClick.DTO.Authentication.MensajeDTO;
import com.compraClick.DTO.Producto.ProductoDTO;
import com.compraClick.Model.enums.Categoria;
import com.compraClick.Service.Interfaces.AdministradorServicio;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class AdministradorController {

    private final AdministradorServicio administradorServicio;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/crearProducto", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MensajeDTO<String>> crearProducto(
            @RequestParam("nombre") String nombre,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("precio") Double precio,
            @RequestParam("stock") Integer stock,
            @RequestParam("idCategoria") Categoria idCategoria,
            @RequestParam("imagenes") List<MultipartFile> imagenes) {

        try {
            ProductoDTO productoDTO = new ProductoDTO(
                    nombre, descripcion, imagenes, precio, stock, idCategoria
            );

            int idProducto = administradorServicio.crearProducto(productoDTO);

            return ResponseEntity.ok().body(new MensajeDTO<>(false,
                    "Producto creado exitosamente, Id del producto: "  + idProducto
            ));

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
}
