package com.innovatech.solution.nomina.persistence.repositories;

import com.innovatech.solution.nomina.persistence.entities.DevengadosPrestaciones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

public interface DevengadosPrestacionesRepositorio extends JpaRepository<DevengadosPrestaciones, Long> {
    @Query("SELECT d FROM DevengadosPrestaciones d WHERE d.identificacion = :identificacion")
    Optional<DevengadosPrestaciones> findByIdentificacion(@Param("identificacion") String identificacion);

    //CONSULTA PARA PRIMA
    @Query("SELECT d.fecPagoPrima FROM DevengadosPrestaciones d WHERE d.identificacion = :identificacion")
    LocalDate findUltimaFechaPagoPrima(@Param("identificacion") String identificacion);
    @Query("SELECT d.salariosDevengadosPrima FROM DevengadosPrestaciones d WHERE d.identificacion = :identificacion")
    BigDecimal findSalariosDevengadosPrima(@Param("identificacion") String identificacion);
    @Modifying
    @Transactional
    @Query("UPDATE DevengadosPrestaciones d SET d.fecPagoPrima = :fechaPago, d.salariosDevengadosPrima = 0 WHERE d.identificacion = :identificacion")
    void actualizarRegistroPrima(@Param("identificacion") String identificacion, @Param("fechaPago") LocalDate fechaPago);



    //CONSULTA PARA CESANTIAS
    @Query("SELECT d.fecPagoCesantias FROM DevengadosPrestaciones d WHERE d.identificacion = :identificacion")
    LocalDate findUltimaFechaPagoCesantias(@Param("identificacion") String identificacion);

    @Query("SELECT d.salariosDevengadosCesantias FROM DevengadosPrestaciones d WHERE d.identificacion = :identificacion")
    BigDecimal findSalariosDevengadosCesantias(@Param("identificacion") String identificacion);

    @Modifying
    @Transactional
    @Query("UPDATE DevengadosPrestaciones d SET d.fecPagoCesantias = :fechaPago, d.salariosDevengadosCesantias = 0 WHERE d.identificacion = :identificacion")
    void actualizarRegistroCesantias(@Param("identificacion") String identificacion, @Param("fechaPago") LocalDate fechaPago);

    /*
    //CONSULTA PARA VACACIONES
    @Query("SELECT d.fecPagoVacaciones FROM DevengadosPrestaciones d WHERE d.identificacion = :identificacion")
    LocalDate findUltimaFechaPagoVacaciones(@Param("identificacion") String identificacion);

    @Query("SELECT d.salariosDevengadosVacaciones FROM DevengadosPrestaciones d WHERE d.identificacion = :identificacion")
    BigDecimal findSalariosDevengadosVacaciones(@Param("identificacion") String identificacion);

    @Modifying
    @Transactional
    @Query("UPDATE DevengadosPrestaciones d SET d.fecPagoVacaciones = :fechaPago, d.salariosDevengadosVacaciones = 0 WHERE d.identificacion = :identificacion")
    void actualizarRegistroVacaciones(@Param("identificacion") String identificacion, @Param("fechaPago") LocalDate fechaPago);

     */

}
