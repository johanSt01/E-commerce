package com.compraClick.Service.Interfaces;

import com.compraClick.DTO.Compra.CompraDTO;
import com.compraClick.DTO.MetodosPago.MetodoPagoDTO;
import com.compraClick.DTO.Suscripcion.SuscripcionDTO;
import com.compraClick.DTO.User.DetalleUsuarioDTO;
import com.compraClick.DTO.User.ListaComprasDTO;
import com.compraClick.DTO.User.ResetPasswordDTO;
import com.compraClick.DTO.User.UsuarioDTO;

import java.util.List;

public interface UsuarioServicio {

    int crearUsuario(UsuarioDTO usuarioDTO) throws Exception;
    int actualizarUsuario(DetalleUsuarioDTO usuarioDTO) throws Exception;
    int eliminarUsuario(int id) throws Exception;
    DetalleUsuarioDTO obtenerUsuario(int id) throws Exception;
    List<ListaComprasDTO> obtenerComprasDeUsuario(int usuarioId) throws Exception;
    void enviarCodigoReset(String email) throws Exception;
    String resetPassword(ResetPasswordDTO resetPasswordDTO) throws Exception;
    int crearSuscripcion(SuscripcionDTO suscripcionDTO) throws Exception;
    void cancelarSuscripcion(int usuarioId) throws Exception;
    int registrarMetodoPago(MetodoPagoDTO metodoPagoDTO);
    List<MetodoPagoDTO> obtenerMetodosPagoPorUsuario(int usuarioId);
    int registrarCompra(CompraDTO compraDTO) throws Exception;
}
