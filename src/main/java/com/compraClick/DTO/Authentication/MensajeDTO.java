package com.compraClick.DTO.Authentication;

/*
* Este record lo usaremos para la transferencia de mensajes
* tanto para respuestas válidas como para respuestas erróneas.
*/
public record MensajeDTO<T>(
        boolean error,
        T respuesta
) {
}
