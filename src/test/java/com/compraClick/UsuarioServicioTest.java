package com.compraClick;


import com.compraClick.DTO.User.ResetPasswordDTO;
import com.compraClick.DTO.User.UsuarioDTO;
import com.compraClick.Model.entities.Usuario;
import com.compraClick.Model.enums.Ciudad;
import com.compraClick.Repository.UsuarioRepository;
import com.compraClick.Service.Interfaces.UsuarioServicio;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.jdbc.Sql;

import static org.aspectj.bridge.MessageUtil.fail;

@SpringBootTest
@Transactional
@Sql("classpath:dataset.sql")
public class UsuarioServicioTest {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private UsuarioRepository usuarioRepo;

    @MockBean
    private JavaMailSender mailSender;

    @Test
    public void crearUsuarioTest() {
        UsuarioDTO usuarioDTO = new UsuarioDTO(
                "pedritooo@gmail.com",
                "1234",
                "1005",
                "pedro",
                "pedrito",
                "311",
                "cra4",
                Ciudad.Armenia
        );
        try {
            usuarioServicio.registrarse(usuarioDTO);
        }catch (Exception e){
            e.printStackTrace(); // imprime el error real en consola
            fail("Error al crear el usuario: " + e.getMessage());
        }
    }

    @Test
    public void enviarCodigoResetTest() throws Exception {
        String email = "pedritooo@gmail.com";

        usuarioServicio.enviarCodigoReset(email);

        Usuario usuario = usuarioRepo.findByEmail(email);
        Assertions.assertNotNull(usuario.getResetCode(), "El código de reset no fue generado");
        Assertions.assertNotNull(usuario.getResetCodeExpiry(), "El tiempo de expiración no fue generado");

        // Verifica que se llamó al envío de correo
        Mockito.verify(mailSender, Mockito.times(1)).send(Mockito.any(SimpleMailMessage.class));
    }

    @Test
    public void resetPasswordTest() throws Exception {
        String email = "pedritooo@gmail.com";

        // Primero, se genera el código
        usuarioServicio.enviarCodigoReset(email);

        Usuario usuario = usuarioRepo.findByEmail(email);
        String resetCode = usuario.getResetCode();
        Assertions.assertNotNull(resetCode);

        // Crear DTO con nueva contraseña
        ResetPasswordDTO resetDTO = new ResetPasswordDTO(
                email,
                resetCode,
                "nuevaPasswordSegura123"
        );

        String resultado = usuarioServicio.resetPassword(resetDTO);
        Assertions.assertEquals("Contraseña restablecida con éxito", resultado);

        // Verifica que la contraseña se haya encriptado
        Usuario usuarioActualizado = usuarioRepo.findByEmail(email);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Assertions.assertTrue(encoder.matches("nuevaPasswordSegura123", usuarioActualizado.getPassword()));

        // Verifica que el código ya no esté
        Assertions.assertNull(usuarioActualizado.getResetCode());
        Assertions.assertNull(usuarioActualizado.getResetCodeExpiry());
    }
}
