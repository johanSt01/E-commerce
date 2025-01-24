package com.compraClick.Repository;

import com.compraClick.Model.entities.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta,Integer> {
    Optional<Cuenta> findByCorreo(String correo);
}
