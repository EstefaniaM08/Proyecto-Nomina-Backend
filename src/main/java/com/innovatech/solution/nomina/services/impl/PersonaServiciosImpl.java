package com.innovatech.solution.nomina.services.impl;

import com.innovatech.solution.nomina.dto.PersonaDTO;
import com.innovatech.solution.nomina.persistence.entities.Persona;
import com.innovatech.solution.nomina.persistence.repositories.PersonaRepositorio;
import com.innovatech.solution.nomina.services.PersonaServicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public List<PersonaDTO> personas() {
        List<PersonaDTO> lPersonaDTOS = new ArrayList<>();
        List<Persona> listPersonas = personaRepositorio.findAll();
        for (Persona persona : listPersonas) {
            lPersonaDTOS.add(PersonaDTO.builder()
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
                    .build());
        }
        return lPersonaDTOS;
    }
    @Override
    public PersonaDTO persona(String id) {

        Optional<Persona> persona = personaRepositorio.findByIdentificacion(id);

        return PersonaDTO.builder()
                .id(persona.get().getId())
                .identificacion(persona.get().getIdentificacion())
                .nombres(persona.get().getNombres())
                .apellidos(persona.get().getApellidos())
                .salario(persona.get().getSalario())
                .cuentaBancaria(persona.get().getCuentaBancaria())
                .fechaIngreso(persona.get().getFechaIngreso())
                .fechaNac(persona.get().getFechaNac())
                .fechaRetiro(persona.get().getFechaRetiro())
                .estado(persona.get().getEstado())
                .telefono(persona.get().getTelefono())
                .correo(persona.get().getCorreo())
                .cargo(persona.get().getCargo())
                .area(persona.get().getArea())
                .tipoContrato(persona.get().getTipoContrato())
                .banco(persona.get().getBanco())
                .eps(persona.get().getEps())
                .pensiones(persona.get().getPensiones())
                .build();
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
