package com.innovatech.solution.nomina.persistencia.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CargoDTO {
    private Long id;
    private String nombre;
}
