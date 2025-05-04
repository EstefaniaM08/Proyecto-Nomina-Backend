package com.innovatech.solution.nomina.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NominaDTO {

    //Sacado
    private String identificacion;
    private LocalDate fechaPago;
    private BigDecimal salario; //sacado de relacion

    //Ingresado
    private BigDecimal comisiones;
    private BigDecimal viaticos;
    private BigDecimal gastosRepresentacion;
    private Long horExtraDiu;
    private Long horExtraNoc;
    private Long horExtraDiuDomFes;
    private Long horExtraNocDomFes;

    //Calculado
    private BigDecimal subsidioTransporte;
    private BigDecimal prima;
    private BigDecimal cesantias;
    private BigDecimal vacaciones;
    private BigDecimal totValHorExtra;
    private BigDecimal salud;
    private BigDecimal pension;
    private BigDecimal retencionFuente;
    private BigDecimal fondoSolid;

    //Calculados
    private BigDecimal totDescuetos;
    private BigDecimal totDevengados;
    private BigDecimal pagoFinal;

}
