package com.innovatech.solution.nomina.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class BusquedaPagosDTO {
    private String identificacion;
    private LocalDate fecha;
    private BigDecimal totDesDesde;
    private BigDecimal totDesHasta;
    private BigDecimal totDevDesde;
    private BigDecimal totDevHasta;
    private BigDecimal pagFinDesde;
    private BigDecimal pagFinHasta;
}
