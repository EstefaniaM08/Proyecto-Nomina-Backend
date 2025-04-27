package com.innovatech.solution.nomina.controlador;

import com.innovatech.solution.nomina.persistencia.dta.Persona;
import com.innovatech.solution.nomina.persistencia.dto.BusquedaPersonasDTO;
import com.innovatech.solution.nomina.persistencia.dto.PersonaDTO;
import com.innovatech.solution.nomina.servicios.PersonaServicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/personal")
public class PersonalControlador {

    @Autowired
    PersonaServicios personaServicios;

    @PostMapping("/registrar-persona")
        public PersonaDTO registrarPersona(@RequestBody PersonaDTO persona) {
        return personaServicios.registrar(persona);
    }

    @GetMapping("/validar-correo")
    public boolean validarCorreo(@RequestParam("correo") String correo){
        return personaServicios.valExisCorreo(correo);
    }
    @GetMapping("/validar-identificacion")
    public boolean validarIdentificacion(@RequestParam("identificacion") String identificacion){
        return personaServicios.valExisIdenti(identificacion);
    }

}
