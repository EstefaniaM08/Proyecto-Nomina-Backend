package com.innovatech.solution.nomina.persistence.repositories;

import com.innovatech.solution.nomina.persistence.entities.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface PersonaRepositorio extends JpaRepository<Persona, Long> {
    Optional<Persona> findByIdentificacion(String identificacion);
    boolean existsByCorreo(String lowerCase);
    boolean existsByIdentificacion(String identificacion);
}
