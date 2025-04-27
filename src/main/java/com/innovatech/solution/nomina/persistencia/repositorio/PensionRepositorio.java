package com.innovatech.solution.nomina.persistencia.repositorio;


import com.innovatech.solution.nomina.persistencia.dta.Pensiones;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PensionRepositorio extends JpaRepository<Pensiones, Long> {
}
