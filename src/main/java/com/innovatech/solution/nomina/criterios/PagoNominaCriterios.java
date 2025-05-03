package com.innovatech.solution.nomina.criterios;

import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.LocalDateFilter;
import io.github.jhipster.service.filter.StringFilter;
import lombok.Data;

@Data
public class PagoNominaCriterios {
    private StringFilter idPersona;
    private LocalDateFilter fecha;
    private BigDecimalFilter totDev;
    private BigDecimalFilter totDes;
    private BigDecimalFilter pagoFinal;
}
