package com.innovatech.solution.nomina.persistencia.repositorio;

import com.innovatech.solution.nomina.persistencia.dta.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdministradorRepositorio extends JpaRepository<Administrador, Long> {
    boolean existsByEmail(String email);
    Administrador findByEmail(String email);
}
