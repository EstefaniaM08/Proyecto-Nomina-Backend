package com.innovatech.solution.nomina.servicios;

import com.innovatech.solution.nomina.persistencia.dto.PersonaDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PersonaServicios {

    PersonaDTO registrar(PersonaDTO persona);
    boolean valExisCorreo(String correo);
    boolean valExisIdenti(String identificacion);
}
