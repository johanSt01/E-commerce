package com.compraClick.Service.Implementations;

import com.compraClick.DTO.User.UsuarioDTO;
import com.compraClick.Model.enums.EstadoUsuario;
import com.compraClick.Repository.UsuarioRepository;
import com.compraClick.Service.Interfaces.UsuarioServicio;
import com.compraClick.Model.entities.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor //crea el constructor de todos los metodos
public class UsuarioServicioImpl implements UsuarioServicio {

    private final UsuarioRepository usuarioRepo;

    @Override
    public int crearUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setEmail(usuarioDTO.email());
        //Encriptar la contraseña
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String passwordEncriptada = passwordEncoder.encode( usuarioDTO.password() );

        usuario.setPassword( passwordEncriptada );
        usuario.setCedula(usuarioDTO.cedula());
        usuario.setNombre(usuarioDTO.nombre());
        usuario.setApellido(usuarioDTO.apellido());
        usuario.setTelefono(usuarioDTO.telefono());
        usuario.setDireccion(usuarioDTO.direccion());
        usuario.setIdCiudad(usuarioDTO.idCiudad());
        usuario.setEstadoUsuario(EstadoUsuario.Activo);

        Usuario nuevoUsuario = usuarioRepo.save(usuario);

        return nuevoUsuario.getId();
    }
}
