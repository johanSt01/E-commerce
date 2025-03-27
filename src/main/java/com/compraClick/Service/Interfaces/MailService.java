package com.compraClick.Service.Interfaces;

import com.compraClick.DTO.Mail.EmailDTO;
import com.compraClick.Model.entities.Compra;
import com.compraClick.Model.entities.Usuario;

public interface MailService {
    void enviarConfirmacionCompra(Usuario usuario, Compra compra);
    void enviarEmail(EmailDTO emailDTO) throws Exception;
}
