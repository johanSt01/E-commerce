package com.compraClick.Service.Implementations;

import com.compraClick.DTO.Authentication.LoginDTO;
import com.compraClick.DTO.Authentication.TokenDTO;
import com.compraClick.Model.entities.Administrador;
import com.compraClick.Model.entities.Cuenta;
import com.compraClick.Model.entities.Usuario;
import com.compraClick.Model.enums.EstadoUsuario;
import com.compraClick.Repository.CuentaRepository;
import com.compraClick.Service.Interfaces.AutenticacionServicio;
import com.compraClick.Util.JWTUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j  // Para logging
public class AutenticacionServicioImpl implements AutenticacionServicio {

    // Repositorio que se utiliza para interactuar con la base de datos
    private final CuentaRepository cuentaRepo;
    private final JWTUtils jwtUtils; // Manejo de los JWT
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();  // Inyectado como dependencia

    @Override
    @Transactional(readOnly = true)
    public TokenDTO login(LoginDTO loginDTO) {
        log.info("Intento de inicio de sesión para el email: {}", loginDTO.email());

        // Buscar la cuenta
        Cuenta cuenta = cuentaRepo.findByEmail(loginDTO.email())
                .orElseThrow(() -> {
                    log.warn("Intento de login con email inexistente: {}", loginDTO.email());
                    return new ResponseStatusException(
                            HttpStatus.UNAUTHORIZED,
                            "Credenciales inválidas"  // Mensaje genérico por seguridad
                    );
                });

        // Validar contraseña
        validarPassword(loginDTO.password(), cuenta.getPassword());

        // Validar estado de la cuenta
        validarEstadoCuenta(cuenta);

        // Generar y retornar token
        String token = crearToken(cuenta);
        log.info("Login exitoso para el usuario: {}", cuenta.getEmail());

        return new TokenDTO(token);
    }

    /**
     * Valida que la contraseña proporcionada coincida con la almacenada.
     *
     * @param passwordIngresada Contraseña en texto plano
     * @param passwordAlmacenada Contraseña hasheada
     * @throws ResponseStatusException si las contraseñas no coinciden
     */
    private void validarPassword(String passwordIngresada, String passwordAlmacenada) {
        if (!passwordEncoder.matches(passwordIngresada, passwordAlmacenada)) {
            log.warn("Intento de login con contraseña incorrecta");
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Credenciales inválidas"  // Mensaje genérico por seguridad
            );
        }
    }

    /**
     * Valida el estado de la cuenta según su tipo.
     *
     * @param cuenta La cuenta a validar
     * @throws ResponseStatusException si la cuenta no está activa o no es válida
     */
    private void validarEstadoCuenta(Cuenta cuenta) {
        if (cuenta instanceof Usuario usuario) {
            if (usuario.getEstadoUsuario() != EstadoUsuario.Activo) {
                log.warn("Intento de login con usuario inactivo: {}", cuenta.getEmail());
                throw new ResponseStatusException(
                        HttpStatus.FORBIDDEN,
                        "La cuenta no está activa"
                );
            }
        } else if (cuenta instanceof Administrador) {
            log.info("Inicio de sesión como administrador: {}", cuenta.getEmail());
            // Aquí podrías agregar validaciones adicionales para administradores
        } else {
            log.error("Tipo de cuenta no reconocido: {}", cuenta.getClass().getSimpleName());
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error en el sistema de autenticación"
            );
        }
    }

    /*
    * Metodo para la creacion del token de cada usuario o administrador
     */
    private String crearToken(Cuenta cuenta){
        String rol;
        String nombre;
        if( cuenta instanceof Usuario){
            rol = "ROLE_USER";
            nombre = ((Usuario) cuenta).getNombre();
        }else{
            rol = "ROLE_ADMIN";
            nombre = "Administrador";
        }
        Map<String, Object> map = new HashMap<>();
        map.put("rol", rol);
        map.put("nombre", nombre);
        map.put("id", cuenta.getId());
        return jwtUtils.generarToken(cuenta.getEmail(), map);
    }
}
