package com.innovatech.solution.nomina.persistence.repositories;

import com.innovatech.solution.nomina.persistence.entities.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdministradorRepositorio extends JpaRepository<Administrador, Long> {
    boolean existsByEmail(String email);
    Administrador findByEmail(String email);
}
