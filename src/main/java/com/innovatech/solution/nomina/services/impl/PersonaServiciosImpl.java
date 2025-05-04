package com.innovatech.solution.nomina.services.impl;

import com.innovatech.solution.nomina.dto.PersonaDTO;
import com.innovatech.solution.nomina.persistence.entities.Persona;
import com.innovatech.solution.nomina.persistence.repositories.PersonaRepositorio;
import com.innovatech.solution.nomina.services.PersonaServicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PersonaServiciosImpl implements PersonaServicios {
    @Autowired
    PersonaRepositorio personaRepositorio;

    @Override
    public PersonaDTO registrar(PersonaDTO persona) {
        Persona person = Persona.builder()
                .id(persona.getId())
                .identificacion(persona.getIdentificacion())
                .nombres(persona.getNombres())
                .apellidos(persona.getApellidos())
                .salario(persona.getSalario())
                .cuentaBancaria(persona.getCuentaBancaria())
                .fechaIngreso(persona.getFechaIngreso())
                .fechaNac(persona.getFechaNac())
                .fechaRetiro(persona.getFechaRetiro())
                .estado(persona.getEstado())
                .telefono(persona.getTelefono())
                .correo(persona.getCorreo())
                .cargo(persona.getCargo())
                .area(persona.getArea())
                .tipoContrato(persona.getTipoContrato())
                .banco(persona.getBanco())
                .eps(persona.getEps())
                .pensiones(persona.getPensiones())
                .build();
        personaRepositorio.save(person);
        return persona;
    }

}
