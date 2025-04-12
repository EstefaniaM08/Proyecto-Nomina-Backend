package com.innovatech.solution.nomina.persistencia.repositorio;

import com.innovatech.solution.nomina.persistencia.dta.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface PersonaRepositorio extends JpaRepository<Persona, Long>, JpaSpecificationExecutor<Persona> {
    Optional<Persona> findByIdentificacion(String identificacion);
    boolean existsByCorreo(String lowerCase);
    boolean existsByIdentificacion(String identificacion);
}
