package com.compraClick.Service.Implementations;

import com.compraClick.DTO.Mail.EmailDTO;
import com.compraClick.Model.entities.Compra;
import com.compraClick.Model.entities.Usuario;
import com.compraClick.Service.Interfaces.MailService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;

    private static final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);

    @Override
    public void enviarEmail(EmailDTO emailDTO) throws Exception {
        MimeMessage mensaje = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensaje);

        helper.setSubject(emailDTO.asunto());
        helper.setText(emailDTO.cuerpo(), true);
        helper.setTo(emailDTO.destinatario());
        helper.setFrom("no_reply@compraclick.com");

        mailSender.send(mensaje);
    }

    public void enviarConfirmacionCompra(Usuario usuario, Compra compra) {
        // Crear el DTO con los datos de la compra
        EmailDTO emailDTO = new EmailDTO(
                usuario.getEmail(),
                "Confirmación de compra en CompraClick",
                "<h1>Hola " + usuario.getNombre() + "!</h1>" +
                        "<p>Tu compra ha sido registrada con éxito.</p>" +
                        "<ul>" +
                        "<li><strong>Monto:</strong> $" + compra.getMontoTotal() + "</li>" +
                        "<li><strong>Método de pago:</strong> " + compra.getMetodoPago().getTipo() + "</li>" +
                        "<li><strong>Fecha de compra:</strong> " + compra.getFechaCompra() + "</li>" +
                        "</ul>" +
                        "<p>Gracias por comprar con nosotros.</p>"
        );

        try {
            enviarEmail(emailDTO);
        } catch (Exception e) {
            logger.error("Error al enviar correo de confirmación de compra a {}: {}", usuario.getEmail(), e.getMessage(), e);
        }
    }
}
