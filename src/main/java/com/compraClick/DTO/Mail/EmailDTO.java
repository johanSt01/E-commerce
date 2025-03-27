package com.compraClick.DTO.Mail;

public record EmailDTO(
        String asunto,
        String cuerpo,
        String destinatario
) {
}
