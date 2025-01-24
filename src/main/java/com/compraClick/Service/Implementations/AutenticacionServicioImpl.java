package com.compraClick.Service.Implementations;

import com.compraClick.DTO.Authentication.LoginDTO;
import com.compraClick.DTO.Authentication.TokenDTO;
import com.compraClick.Model.entities.Cuenta;
import com.compraClick.Model.entities.Usuario;
import com.compraClick.Repository.CuentaRepository;
import com.compraClick.Service.Interfaces.AutenticacionServicio;
import com.compraClick.Util.JWTUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AutenticacionServicioImpl implements AutenticacionServicio {

    // Repositorio que se utiliza para interactuar con la base de datos
    private final CuentaRepository cuentaRepo;
    // Manejo de los JWT
    private final JWTUtils jwtUtils;

    @Override
    public TokenDTO login(LoginDTO loginDTO) throws Exception {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        validarLoginDTO(loginDTO);
        Optional<Cuenta> cuentaOptional = cuentaRepo.findByCorreo(loginDTO.email());
        // Validacion si la cuenta ingresada existe
        if(cuentaOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el correo ingresado");
        }
        Cuenta cuenta = cuentaOptional.get();
        // Validacion de la contraseña del usuario
        if( !passwordEncoder.matches(loginDTO.password(), cuenta.getPassword()) ){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "La contraseña ingresada es incorrecta");
        }
        // Creacion del token si las credenciales son correctas
        return new TokenDTO( crearToken(cuenta) );
    }

    /**
     * Método para validar los datos del LoginDTO.
     */
    private void validarLoginDTO(LoginDTO loginDTO) {
        if (loginDTO.email() == null || loginDTO.email().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El correo no puede estar vacío");
        }
        if (loginDTO.password() == null || loginDTO.password().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La contraseña no puede estar vacía");
        }
    }

    /*
    * Metodo para la creacion del token de cada usuario o administrador
     */
    private String crearToken(Cuenta cuenta){
        String rol;
        String nombre;
        if( cuenta instanceof Usuario){
            rol = "usuario";
            nombre = ((Usuario) cuenta).getNombre();
        }else{
            rol = "admin";
            nombre = "Administrador";
        }
        Map<String, Object> map = new HashMap<>();
        map.put("rol", rol);
        map.put("nombre", nombre);
        map.put("id", cuenta.getId());
        return jwtUtils.generarToken(cuenta.getEmail(), map);
    }
}
