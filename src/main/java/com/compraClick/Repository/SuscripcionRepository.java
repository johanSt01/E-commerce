package com.compraClick.Repository;

import com.compraClick.Model.entities.Suscripcion;
import com.compraClick.Model.enums.EstadoSuscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SuscripcionRepository extends JpaRepository<Suscripcion, Integer> {
    Optional<Suscripcion> findByIdUsuario_IdAndIdEstado(int usuarioId, EstadoSuscripcion estado);
}

