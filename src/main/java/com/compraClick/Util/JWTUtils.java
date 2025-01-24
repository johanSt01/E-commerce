package com.compraClick.Util;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

// Permite que esta clase sea detectada como un bean de Spring
@Component
public class JWTUtils {
    // Inyecta el valor de la clave secreta
    @Value("${jwt.secret}")
    private String claveSecreta;

    /*
    * Email, para identificar el sujeto del token
    * claims, mapa de clave, valor que representa los datos del payload del token
    */
    public String generarToken(String email, Map<String, Object> claims){
        Instant now = Instant.now();
        // COnfiguracion del payload
        return Jwts.builder()
                .addClaims(claims) // Agregar datos adicionales al token
                .setSubject(email) // Define el indicador principal del token
                .setIssuedAt(Date.from(now)) // Fecha y hora de expedicion
                .setExpiration(Date.from(now.plus(5L, ChronoUnit.MINUTES))) // Fecha de expiracion
                .signWith( getKey() ) // Firma el token con la clave obtenida
                .compact(); // Convierte el token en un valor String compacto y devuelve el valor

    }
    /*
    * Este método valida y analiza un JWT.*/
    /*
    * ExpiredJwtException: Lanza una excepción si el token ha expirado.
    * UnsupportedJwtException: Indica que el formato del token no es soportado.
    * MalformedJwtException: El token está mal formado.
    * IllegalArgumentException: El token proporcionado es nulo o vacío.
     */
    public Jws<Claims> parseJwt(String jwtString) throws ExpiredJwtException,
            UnsupportedJwtException, MalformedJwtException, IllegalArgumentException {
        return Jwts.parserBuilder()
                .setSigningKey( getKey() )
                .build()
                .parseClaimsJws(jwtString);

    }

    /*
    * Este método genera una clave de firma a partir de la clave secreta. */
    private Key getKey(){
        return new SecretKeySpec(Base64.getDecoder().decode(claveSecreta),
                SignatureAlgorithm.HS256.getJcaName());
    }
}
