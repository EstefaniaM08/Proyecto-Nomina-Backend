package com.innovatech.solution.nomina.services;

import com.innovatech.solution.nomina.dto.PersonaDTO;

import java.util.List;

public interface PersonaServicios {

    List<PersonaDTO> personas();
    PersonaDTO persona(String id);
    PersonaDTO registrar(PersonaDTO persona);
    boolean valExisCorreo(String correo);
    boolean valExisIdenti(String identificacion);
}
