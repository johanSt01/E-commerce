package com.compraClick.Service.Interfaces;

import com.compraClick.DTO.User.DetalleUsuarioDTO;
import com.compraClick.DTO.User.UsuarioDTO;

public interface UsuarioServicio {

    int crearUsuario(UsuarioDTO usuarioDTO) throws Exception;
    int actualizarUsuario(DetalleUsuarioDTO usuarioDTO) throws Exception;
    int eliminarUsuario(int id) throws Exception;
    DetalleUsuarioDTO obtenerUsuario(int id) throws Exception;
    void enviarCodigoReset(String email) throws Exception;
}
