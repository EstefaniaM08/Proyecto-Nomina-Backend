package com.innovatech.solution.nomina.servicios.impl;

import com.innovatech.solution.nomina.persistencia.dto.PersonaDTO;
import com.innovatech.solution.nomina.persistencia.dta.Persona;
import com.innovatech.solution.nomina.persistencia.repositorio.PersonaRepositorio;
import com.innovatech.solution.nomina.servicios.PersonaServicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    @Override
    public boolean valExisCorreo(String correo) {
        boolean existe = personaRepositorio.existsByCorreo(correo.trim().toLowerCase());
        return existe;
    }
    @Override
    public boolean valExisIdenti(String identificacion) {
        boolean existe = personaRepositorio.existsByIdentificacion(identificacion);
        return existe;
    }
}
