package com.compraClick;


import com.compraClick.DTO.User.UsuarioDTO;
import com.compraClick.Model.enums.Ciudad;
import com.compraClick.Service.Interfaces.UsuarioServicio;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.aspectj.bridge.MessageUtil.fail;

@SpringBootTest
@Transactional
public class UsuarioServicioTest {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Test
    @Sql("classpath:dataset.sql")
    public void crearUsuarioTest() {
        UsuarioDTO usuarioDTO = new UsuarioDTO(
                "pedrito",
                "1234",
                "1005",
                "pedro",
                "pedrito",
                "311",
                "cra4",
                Ciudad.Armenia
        );
        try {
            usuarioServicio.crearUsuario(usuarioDTO);
        }catch (Exception e){
            e.printStackTrace(); // imprime el error real en consola
            fail("Error al crear el usuario: " + e.getMessage());
        }
    }
}
