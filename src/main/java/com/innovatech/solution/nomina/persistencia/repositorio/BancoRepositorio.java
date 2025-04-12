package com.innovatech.solution.nomina.persistencia.repositorio;


import com.innovatech.solution.nomina.persistencia.dta.Bancos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BancoRepositorio extends JpaRepository<Bancos, Long> {
}
