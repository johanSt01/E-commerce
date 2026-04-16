package com.compraClick.Util;

import io.jsonwebtoken.*;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FiltroToken extends OncePerRequestFilter {

    private final JWTUtils jwtUtils;

    @Override
    public void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {

        String token = getToken(req);
        try {
            if (token != null) {
                Jws<Claims> jws = jwtUtils.parseJwt(token);
                Claims claims = jws.getBody();

                // Extraer información del token
                String email = claims.getSubject();
                String rol = claims.get("rol", String.class);
                Integer id = claims.get("id", Integer.class);

                // Crear la autoridad basada en el rol
                List<GrantedAuthority> authorities =
                        List.of(new SimpleGrantedAuthority(rol));

                // Crear token de autenticación de Spring Security
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(email, null, authorities);

                // Establecer la autenticación en el contexto de Spring Security
                SecurityContextHolder.getContext().setAuthentication(authToken);

                System.out.println("Usuario autenticado: " + email + " con rol: " + rol);
            }
        } catch (Exception e) {
            System.err.println("Error al validar token: " + e.getMessage());
            e.printStackTrace();
        }

        chain.doFilter(req, res);
    }

    private String getToken(HttpServletRequest req) {
        String header = req.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer "))
            return header.replace("Bearer ", "");
        return null;
    }


}

