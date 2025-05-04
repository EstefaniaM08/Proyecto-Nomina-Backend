package com.innovatech.solution.nomina.persistence.repositories;


import com.innovatech.solution.nomina.persistence.entities.Pensiones;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PensionRepositorio extends JpaRepository<Pensiones, Long> {
}
