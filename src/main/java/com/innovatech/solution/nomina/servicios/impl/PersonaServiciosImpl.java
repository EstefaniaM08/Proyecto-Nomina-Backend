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
    public void actualizar(PersonaDTO datos){
        // Validar si la persona existe antes de buscarla
        if (!valExisIdenti(datos.getIdentificacion())) {
            throw new RuntimeException("La persona con identificación " + datos.getIdentificacion() + " no existe.");
        }
        Optional<Persona> person = personaRepositorio.findByIdentificacion(datos.getIdentificacion());
        //person.get().setId(datos.getId());
        person.get().setIdentificacion(datos.getIdentificacion());
        person.get().setNombres(datos.getNombres());
        person.get().setApellidos(datos.getApellidos());
        person.get().setSalario(datos.getSalario());
        person.get().setCuentaBancaria(datos.getCuentaBancaria());
        person.get().setFechaIngreso(datos.getFechaIngreso());
        person.get().setFechaRetiro(datos.getFechaRetiro());
        person.get().setFechaNac(datos.getFechaNac());
        person.get().setTelefono(datos.getTelefono());
        person.get().setCorreo(datos.getCorreo());
        person.get().setCargo(datos.getCargo());
        person.get().setArea(datos.getArea());
        person.get().setTipoContrato(datos.getTipoContrato());
        person.get().setBanco(datos.getBanco());
        person.get().setEps(datos.getEps());
        person.get().setPensiones(datos.getPensiones());
        personaRepositorio.save(person.get());
    }
    @Override
    public void desactivar(String id){
        Optional<Persona> persona = personaRepositorio.findByIdentificacion(id);
        LocalDate fechaActual = LocalDate.now();
        persona.get().setEstado("Desactivado");
        persona.get().setFechaRetiro(fechaActual);
        personaRepositorio.save(persona.get());
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
