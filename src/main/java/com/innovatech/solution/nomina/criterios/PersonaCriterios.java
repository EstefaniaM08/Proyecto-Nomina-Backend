package com.innovatech.solution.nomina.criterios;

import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.StringFilter;
import lombok.Data;

@Data
public class PersonaCriterios {
    private StringFilter identificacion;
    private StringFilter apellidos;
    private StringFilter estado;
    private StringFilter area;
    private StringFilter cargo;
    private BigDecimalFilter salario;
}
