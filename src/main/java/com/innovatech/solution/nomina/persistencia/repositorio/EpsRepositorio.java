package com.innovatech.solution.nomina.persistencia.repositorio;


import com.innovatech.solution.nomina.persistencia.dta.Eps;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EpsRepositorio extends JpaRepository<Eps, Long> {
}
