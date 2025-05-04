package com.innovatech.solution.nomina.controllers;


import com.innovatech.solution.nomina.dto.PersonaDTO;
import com.innovatech.solution.nomina.services.PersonaServicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/personal")
public class PersonalControlador {

    @Autowired
    PersonaServicios personaServicios;

    @PostMapping("/registrar-persona")
        public PersonaDTO registrarPersona(@RequestBody PersonaDTO persona) {
        return personaServicios.registrar(persona);
    }


}
