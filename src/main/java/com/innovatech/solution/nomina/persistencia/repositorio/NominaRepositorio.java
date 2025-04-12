package com.innovatech.solution.nomina.persistencia.repositorio;

import com.innovatech.solution.nomina.persistencia.dta.Nomina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface NominaRepositorio extends JpaRepository<Nomina, Long>, JpaSpecificationExecutor<Nomina> {
    @Query("SELECT n FROM Nomina n JOIN FETCH n.personal WHERE n.id = :idNomina")
    Nomina findByIdWithPersonal(Long idNomina);

    Optional<Nomina> findTopByPersonalIdentificacionOrderByFechaPagoDesc(String identificacion);

}
