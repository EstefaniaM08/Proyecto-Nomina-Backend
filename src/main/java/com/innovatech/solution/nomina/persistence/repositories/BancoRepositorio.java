package com.innovatech.solution.nomina.persistence.repositories;


import com.innovatech.solution.nomina.persistence.entities.Bancos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BancoRepositorio extends JpaRepository<Bancos, Long> {
}
