package com.innovatech.solution.nomina.services;

import com.innovatech.solution.nomina.dto.BusquedaPersonasDTO;
import com.innovatech.solution.nomina.persistence.entities.Persona;

import java.util.List;

public interface ConsultasPersonaServicios {
    List<Persona> busquedaPersonas(BusquedaPersonasDTO busquedaDTO);
}
