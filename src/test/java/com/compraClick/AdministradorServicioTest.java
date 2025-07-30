package com.compraClick;

import com.compraClick.DTO.Producto.ProductoDTO;
import com.compraClick.Model.enums.Categoria;
import com.compraClick.Service.Interfaces.AdministradorServicio;
import com.compraClick.Service.Interfaces.ImagesService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static org.aspectj.bridge.MessageUtil.fail;

@SpringBootTest
@Transactional
public class AdministradorServicioTest {

    @Autowired
    private AdministradorServicio administradorServicio;

    @Test
    @Sql("classpath:dataset.sql")
    public void crearProductoTest(){

        List<MultipartFile> imagenes = new ArrayList<>();

        ProductoDTO productoDTO = new ProductoDTO(
                "Monitor Gamer",
                "Monitor 144Hz Full HD",
                imagenes,
                899.99,
                25,
                Categoria.Tecnologia
        );
        try {
            administradorServicio.crearProducto(productoDTO);
        }catch (Exception e){
            e.printStackTrace(); // imprime el error real en consola
            fail("Error al crear el producto: " + e.getMessage());
        }
    }
}
