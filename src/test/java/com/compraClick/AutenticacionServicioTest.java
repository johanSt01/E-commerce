package com.compraClick;

import com.compraClick.DTO.Authentication.LoginDTO;
import com.compraClick.DTO.Authentication.TokenDTO;
import com.compraClick.Service.Interfaces.AutenticacionServicio;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@Transactional
public class AutenticacionServicioTest {

    @Autowired
    private AutenticacionServicio autenticacionServicio;

    @Test
    @Sql("classpath:dataset.sql")
    public void loginTest(){
        try {
            TokenDTO token = autenticacionServicio.login(new LoginDTO(
                    "123jak@email.com",
                    "1"
            ));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
