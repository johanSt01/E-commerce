package com.compraClick.Repository;

import com.compraClick.Model.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository <Usuario, Integer>{

    // Buscar un usuario por su correo electrónico
    Usuario findByEmail(String email);

    Usuario findByCedula(String cedula);
}
