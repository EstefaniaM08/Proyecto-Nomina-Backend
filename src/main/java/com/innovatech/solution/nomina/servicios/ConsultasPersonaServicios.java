package com.innovatech.solution.nomina.servicios;

import com.innovatech.solution.nomina.persistencia.dta.Persona;
import com.innovatech.solution.nomina.persistencia.dto.BusquedaPersonasDTO;

import java.util.List;

public interface ConsultasPersonaServicios {
    List<Persona> busquedaPersonas(BusquedaPersonasDTO busquedaDTO);
}
