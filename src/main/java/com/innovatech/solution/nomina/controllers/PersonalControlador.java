package com.innovatech.solution.nomina.controllers;


import com.innovatech.solution.nomina.dto.BusquedaPersonasDTO;
import com.innovatech.solution.nomina.dto.PersonaDTO;
import com.innovatech.solution.nomina.persistence.entities.Persona;
import com.innovatech.solution.nomina.services.ConsultasPersonaServicios;
import com.innovatech.solution.nomina.services.PersonaServicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/personal")
public class PersonalControlador {

    @Autowired
    PersonaServicios personaServicios;
    @Autowired
    ConsultasPersonaServicios consultasPersonaServicios;

    @PostMapping("/registrar-persona")
        public PersonaDTO registrarPersona(@RequestBody PersonaDTO persona) {
        return personaServicios.registrar(persona);
    }

    @GetMapping("/personas")
    public ResponseEntity<List<PersonaDTO>> personas() {
        return new ResponseEntity<>(personaServicios.personas(), null, HttpStatus.OK);
    }
    @GetMapping("/buscar-persona/{identificacion}")
    public ResponseEntity<PersonaDTO> persona(@PathVariable("identificacion") String id) {
        return new ResponseEntity<>(personaServicios.persona(id), null, HttpStatus.OK);
    }
    @GetMapping("/busqueda-personas")
    public ResponseEntity<List<Persona>> busquedaPersonas(
            @RequestParam(required = false) String identificacion,
            @RequestParam(required = false) String apellidos,
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) String area,
            @RequestParam(required = false) String cargo,
            @RequestParam(required = false) BigDecimal salarioDesde,
            @RequestParam(required = false) BigDecimal salarioHasta
    ) {
        BusquedaPersonasDTO busquedaDTO = new BusquedaPersonasDTO();
        busquedaDTO.setIdentificacion(identificacion);
        busquedaDTO.setApellidos(apellidos);
        busquedaDTO.setEstado(estado);
        busquedaDTO.setArea(area);
        busquedaDTO.setCargo(cargo);
        busquedaDTO.setSalarioDesde(salarioDesde);
        busquedaDTO.setSalarioHasta(salarioHasta);
        return new ResponseEntity<>(consultasPersonaServicios.busquedaPersonas(busquedaDTO), HttpStatus.OK);
    }

    @GetMapping("/validar-correo")
    public boolean validarCorreo(@RequestParam("correo") String correo){
        return personaServicios.valExisCorreo(correo);
    }
    @GetMapping("/validar-identificacion")
    public boolean validarIdentificacion(@RequestParam("identificacion") String identificacion){
        return personaServicios.valExisIdenti(identificacion);
    }
    @PutMapping("/actualizar-persona")
    public void actualizarPersona(@RequestBody PersonaDTO persona) {
        personaServicios.actualizar(persona);
    }
    @DeleteMapping("/desactivar-persona/{id}")
    public void desactivarPersona(@PathVariable("id") String id) {
        personaServicios.desactivar(id);
    }

}
