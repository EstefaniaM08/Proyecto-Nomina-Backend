package com.innovatech.solution.nomina.servicios;

import com.innovatech.solution.nomina.persistencia.dto.PersonaDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PersonaServicios {

    List<PersonaDTO> personas();
    PersonaDTO persona(String id);
    PersonaDTO registrar(PersonaDTO persona);
    void actualizar(PersonaDTO persona);
    void desactivar(String id);
    boolean valExisCorreo(String correo);

    boolean valExisIdenti(String identificacion);
}
