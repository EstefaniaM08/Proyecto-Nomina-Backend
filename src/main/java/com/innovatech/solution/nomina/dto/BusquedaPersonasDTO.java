package com.innovatech.solution.nomina.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BusquedaPersonasDTO {
    private String identificacion;
    private String apellidos;
    private String estado;
    private String area;
    private String cargo;
    private BigDecimal salarioDesde;
    private BigDecimal salarioHasta;
}
