package com.compraClick.Service.Implementations;

import com.compraClick.DTO.User.DetalleUsuarioDTO;
import com.compraClick.DTO.User.UsuarioDTO;
import com.compraClick.Model.enums.EstadoUsuario;
import com.compraClick.Repository.UsuarioRepository;
import com.compraClick.Service.Interfaces.UsuarioServicio;
import com.compraClick.Model.entities.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor //crea el constructor de todos los metodos
public class UsuarioServicioImpl implements UsuarioServicio {

    private final UsuarioRepository usuarioRepo;

    @Override
    public int crearUsuario(UsuarioDTO usuarioDTO) throws Exception {

        //Validacion de que el usuario no esté registrado
        if( estaRepetidaCedula(usuarioDTO.cedula()) ){
            throw new Exception("La cédula "+usuarioDTO.cedula()+" ya está en uso");
        }

        //Validacion de que el usuario no esté registrado
        if( estaRepetidoCorreo(usuarioDTO.email()) ){
            throw new Exception("El correo "+usuarioDTO.email()+" ya está en uso");
        }

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

        // Guardar el usuario en la base de datos
        Usuario nuevoUsuario = usuarioRepo.save(usuario);

        return nuevoUsuario.getId();
    }

    private boolean estaRepetidaCedula(String cedula){
        return usuarioRepo.findByCedula(cedula) != null;
    }

    private boolean estaRepetidoCorreo(String correo){
        return usuarioRepo.findByEmail(correo) != null;
    }

    @Override
    public int actualizarUsuario(DetalleUsuarioDTO usuarioDTO) throws Exception {
        Optional<Usuario> opcional = usuarioRepo.findById(usuarioDTO.id());
        if(opcional.isEmpty()){
            throw new Exception("El usuario no existe");
        }
        Usuario usuarioBuscado = opcional.get();
        usuarioBuscado.setNombre(usuarioDTO.nombre());
        usuarioBuscado.setApellido(usuarioDTO.apellido());
        usuarioBuscado.setTelefono(usuarioDTO.telefono());
        usuarioBuscado.setIdCiudad(usuarioDTO.idCiudad());
        usuarioBuscado.setEmail(usuarioDTO.email());
        usuarioBuscado.setTelefono(usuarioDTO.telefono());
        usuarioBuscado.setDireccion(usuarioDTO.direccion());

        usuarioRepo.save(usuarioBuscado);

        return usuarioBuscado.getId();
    }

    @Override
    public int eliminarUsuario(int id) throws Exception {
        Optional<Usuario> optionalUsuario = usuarioRepo.findById(id);
        if (optionalUsuario.isEmpty()) {
            throw new Exception("El usuario no existe");
        }
        Usuario usuario = optionalUsuario.get();
        usuario.setEstadoUsuario(EstadoUsuario.Inactivo);
        usuarioRepo.save(usuario);
        return usuario.getId();
    }

    @Override
    public DetalleUsuarioDTO obtenerUsuario(int id) throws Exception {
        Optional<Usuario> optionalUsuario = usuarioRepo.findById(id);
        if (optionalUsuario.isEmpty()) {
            throw new Exception("El usuario no existe");
        }
        Usuario usuario = optionalUsuario.get();

        return new DetalleUsuarioDTO(
                usuario.getId(),
                usuario.getEmail(),
                usuario.getCedula(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getTelefono(),
                usuario.getDireccion(),
                usuario.getIdCiudad());
    }

    /*
    public int buscarUsuario(UsuarioDTO usuarioDTO) throws Exception {
        Usuario usuario = usuarioRepo.findByEmail(usuarioDTO.email());
        if (usuario == null) {
            throw new Exception("Usuario no encontrado");
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(usuarioDTO.password(), usuario.getPassword())) {
            throw new Exception("Credenciales inválidas");
        }
        if (usuario.getEstadoUsuario() != EstadoUsuario.Activo) {
            throw new Exception("El usuario no está activo");
        }
        return usuario.getId();
    }*/

}
