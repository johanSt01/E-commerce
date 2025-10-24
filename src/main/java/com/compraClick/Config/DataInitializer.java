package com.compraClick.Config;

import com.compraClick.Model.entities.Administrador;
import com.compraClick.Repository.CuentaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final CuentaRepository cuentaRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Value("${app.admin.email}")
    private String adminEmail;

    @Value("${app.admin.password}")
    private String adminPassword;

    @Value("${app.admin.create-default}")
    private boolean createDefaultAdmin;

    @Bean
    public CommandLineRunner initAdminUser() {
        return args -> {
            if (!createDefaultAdmin) {
                log.info("⏭️ Creación de administrador por defecto deshabilitada");
                return;
            }

            if (cuentaRepository.findByEmail(adminEmail).isEmpty()) {
                Administrador admin = new Administrador();
                admin.setEmail(adminEmail);
                admin.setPassword(passwordEncoder.encode(adminPassword));
                // Configurar otros campos necesarios

                cuentaRepository.save(admin);
                log.warn("Administrador por defecto creado: {} - CAMBIAR CONTRASEÑA EN PRODUCCIÓN", adminEmail);
            } else {
                log.info("Administrador ya existe: {}", adminEmail);
            }
        };
    }
}
