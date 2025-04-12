package com.innovatech.solution.nomina.persistencia.dta;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "usuarios")
public class Administrador {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;


}
