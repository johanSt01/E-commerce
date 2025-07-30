package com.compraClick.Service.Implementations;

import com.compraClick.DTO.Compra.CompraDTO;
import com.compraClick.DTO.MetodosPago.MetodoPagoDTO;
import com.compraClick.DTO.Suscripcion.SuscripcionDTO;
import com.compraClick.DTO.User.DetalleUsuarioDTO;
import com.compraClick.DTO.User.ListaComprasDTO;
import com.compraClick.DTO.User.ResetPasswordDTO;
import com.compraClick.DTO.User.UsuarioDTO;
import com.compraClick.Model.entities.Compra;
import com.compraClick.Model.entities.MetodoPago;
import com.compraClick.Model.entities.Suscripcion;
import com.compraClick.Model.enums.EstadoSuscripcion;
import com.compraClick.Model.enums.EstadoUsuario;
import com.compraClick.Model.enums.TipoSuscripcion;
import com.compraClick.Repository.CompraRepository;
import com.compraClick.Repository.MetodoPagoRepository;
import com.compraClick.Repository.SuscripcionRepository;
import com.compraClick.Repository.UsuarioRepository;
import com.compraClick.Service.Interfaces.UsuarioServicio;
import com.compraClick.Model.entities.Usuario;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor //crea el constructor de todos los metodos
public class UsuarioServicioImpl implements UsuarioServicio {

    private final UsuarioRepository usuarioRepo;
    private final JavaMailSender mailSender;
    private final SuscripcionRepository suscripcionRepo;
    private final MetodoPagoRepository metodoPagoRepo;
    private final CompraRepository compraRepo;

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

    @Override
    public List<ListaComprasDTO> obtenerComprasDeUsuario(int usuarioId) {
        // Buscar el usuario y lanzar excepción si no existe
        Usuario usuario = usuarioRepo.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        // Convertir las compras del usuario a DTO
        return usuario.getCompras().stream()
                .map(compra -> new ListaComprasDTO(
                        compra.getId(),
                        compra.getFechaCompra(),
                        compra.getMontoTotal()
                ))
                .collect(Collectors.toList());
    }



    // Método para generar y enviar el código de restablecimiento
    public void enviarCodigoReset(String email){
        // Buscar el usuario por email
        Usuario usuario = usuarioRepo.findByEmail(email);
        if (usuario == null) {
            throw new EntityNotFoundException("Usuario no encontrado");
        }

        // Generar un código único
        String resetCode = UUID.randomUUID().toString();
        // Establecer un tiempo de expiración (5 minutos)
        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(5);

        usuario.setResetCode(resetCode);
        usuario.setResetCodeExpiry(expiryTime);
        usuarioRepo.save(usuario);

        // Enviar el código por correo
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(usuario.getEmail());
        message.setSubject("Código de restablecimiento de contraseña");
        message.setText("Tu código para restablecer la contraseña es: " + resetCode +
                "\nEste código expirará en 15 minutos.");
        mailSender.send(message);
    }

    // Método para restablecer la contraseña usando el código recibido
    public String resetPassword(ResetPasswordDTO resetPasswordDTO) throws Exception {
        // Buscar el usuario por correo
        Usuario usuario = usuarioRepo.findByEmail(resetPasswordDTO.email());
        if (usuario == null) {
            throw new EntityNotFoundException("Usuario no encontrado");
        }

        // Verificar que el código coincida y no haya expirado
        if (usuario.getResetCode() == null ||
                !usuario.getResetCode().equals(resetPasswordDTO.resetCode()) ||
                usuario.getResetCodeExpiry() == null ||
                LocalDateTime.now().isAfter(usuario.getResetCodeExpiry())) {
            throw new Exception("El código de restablecimiento es inválido o ha expirado");
        }

        // Encriptar la nueva contraseña y actualizar el usuario
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String passwordEncriptada = passwordEncoder.encode(resetPasswordDTO.newPassword());
        usuario.setPassword(passwordEncriptada);

        // Limpiar el código de restablecimiento para que no pueda reutilizarse
        usuario.setResetCode(null);
        usuario.setResetCodeExpiry(null);

        usuarioRepo.save(usuario);
        return "Contraseña restablecida con éxito";
    }

    @Override
    public int crearSuscripcion(SuscripcionDTO suscripcionDTO) throws Exception {
        Usuario usuario = usuarioRepo.findById(suscripcionDTO.usuarioId())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        Optional<Suscripcion> suscripcionActiva = suscripcionRepo.findByIdUsuario_IdAndIdEstado(
                suscripcionDTO.usuarioId(), EstadoSuscripcion.Activo);

        if (suscripcionActiva.isPresent()) {
            throw new Exception("El usuario ya tiene una suscripción activa");
        }

        TipoSuscripcion tipo = suscripcionDTO.tipoSuscripcion();
        LocalDateTime fechaFin = suscripcionDTO.fechaInicio().plusDays(tipo.getDuracionDias());

        Suscripcion suscripcion = new Suscripcion();
        suscripcion.setNombre(tipo.getNombre());
        suscripcion.setDescripcion(tipo.getDescripcion());
        suscripcion.setFechaInicio(suscripcionDTO.fechaInicio());
        suscripcion.setFechaFin(fechaFin);
        suscripcion.setIdEstado(EstadoSuscripcion.Activo);
        suscripcion.setPorcentajeDescuento(tipo.getPorcentajeDescuento());
        suscripcion.setIdUsuario(usuario);

        suscripcionRepo.save(suscripcion);

        return suscripcion.getId();
    }

    @Override
    public void cancelarSuscripcion(int usuarioId) {
        Suscripcion suscripcion = suscripcionRepo.findByIdUsuario_IdAndIdEstado(usuarioId, EstadoSuscripcion.Activo)
                .orElseThrow(() -> new EntityNotFoundException("No hay suscripción activa para cancelar"));

        suscripcion.setIdEstado(EstadoSuscripcion.Cancelado);
        suscripcionRepo.save(suscripcion);
    }

    @Override
    public int registrarMetodoPago(MetodoPagoDTO metodoPagoDTO){
        Usuario usuario = usuarioRepo.findById(metodoPagoDTO.idUsuario())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        MetodoPago metodoPago = new MetodoPago();
        metodoPago.setTipo(metodoPagoDTO.tipo());
        metodoPago.setDetalle(metodoPagoDTO.detalle());
        metodoPago.setIdUsuario(usuario);

        metodoPagoRepo.save(metodoPago);
        return metodoPago.getId();
    }

    @Override
    public List<MetodoPagoDTO> obtenerMetodosPagoPorUsuario(int usuarioId) {
        return metodoPagoRepo.findById(usuarioId).stream()
                .map(metodo -> new MetodoPagoDTO(
                        metodo.getId(),
                        metodo.getTipo(),
                        metodo.getDetalle(),
                        usuarioId
                ))
                .collect(Collectors.toList());
    }

    @Override
    public int registrarCompra(CompraDTO compraDTO) {
        Usuario usuario = usuarioRepo.findById(compraDTO.idUsuario())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        MetodoPago metodoPago = metodoPagoRepo.findById(compraDTO.idMetodoPago())
                .orElseThrow(() -> new EntityNotFoundException("Método de pago no encontrado"));

        Compra compra = new Compra();
        compra.setIdUsuario(usuario);
        compra.setMontoTotal(compraDTO.montoTotal());
        compra.setFechaCompra(LocalDateTime.now());
        compra.setMetodoPago(metodoPago);

        compraRepo.save(compra);
        return compra.getId();
    }

}
