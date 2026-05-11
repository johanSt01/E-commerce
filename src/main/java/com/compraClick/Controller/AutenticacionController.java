package com.compraClick.Controller;

import com.compraClick.DTO.Authentication.LoginDTO;
import com.compraClick.DTO.Authentication.MensajeAuthDTO;
import com.compraClick.DTO.Authentication.MensajeDTO;
import com.compraClick.DTO.Authentication.TokenDTO;
import com.compraClick.DTO.User.UsuarioDTO;
import com.compraClick.Exception.CredencialesInvalidasException;
import com.compraClick.Service.Interfaces.AutenticacionServicio;
import com.compraClick.Service.Interfaces.UsuarioServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AutenticacionController {

    private final AutenticacionServicio autenticacionServicio;
    private final UsuarioServicio usuarioServicio;

    @PostMapping("/auth")
    public ResponseEntity<MensajeAuthDTO<TokenDTO>> login(@Valid @RequestBody LoginDTO loginDTO) {
        try{
            TokenDTO tokenDTO = autenticacionServicio.login(loginDTO);
            return ResponseEntity.ok().body(new MensajeAuthDTO<>(false, "Inicio de sesión exitoso", tokenDTO));
        }catch (CredencialesInvalidasException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MensajeAuthDTO<>(true, "Correo electrónico o contraseña incorrectos", null));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MensajeAuthDTO<>(true, "Ocurrió un error interno en el servidor. Intente nuevamente más tarde", null));
        }
    }

    @PostMapping("/registrarse")
    public ResponseEntity<MensajeDTO<String>> registrarse(@Valid @RequestBody UsuarioDTO usuarioDTO)
            throws Exception {
        usuarioServicio.registrarse(usuarioDTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "Usuario registrado correctamente"));
    }

}
