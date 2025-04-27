package com.innovatech.solution.nomina.persistencia.dto;


import com.innovatech.solution.nomina.persistencia.dta.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonaDTO {
    private Long id;
    private String identificacion;
    private String nombres;
    private String apellidos;
    private BigDecimal salario;
    private String cuentaBancaria;
    private String estado;
    private String telefono;
    private String correo;
    private LocalDate fechaIngreso;
    private LocalDate fechaNac;
    private LocalDate fechaRetiro;
    private Cargo cargo;
    private Area area;
    private TipoContrato tipoContrato;
    private Bancos banco;
    private Eps eps;
    private Pensiones pensiones;
    private String mensaje;
}
