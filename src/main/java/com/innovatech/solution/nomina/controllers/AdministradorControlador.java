package com.innovatech.solution.nomina.controllers;


import com.innovatech.solution.nomina.dto.AdministradorDTO;
import com.innovatech.solution.nomina.dto.CambioClaveDTO;
import com.innovatech.solution.nomina.services.AdministradorServicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;


@RestController
@RequestMapping("/administrador")
public class AdministradorControlador {
    @Autowired
    private AdministradorServicios administradorServicios;

    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(@Valid @RequestBody AdministradorDTO administradorDTO) {
        System.out.println("hoal");
        String mensaje = administradorServicios.registrar(administradorDTO);
        return ResponseEntity.ok(Map.of("message", mensaje));
    }
    @GetMapping("/validar-existencia-correo")
    public boolean valExisCorreo(@RequestParam("correo") String correo){
        return administradorServicios.valExisCorreo(correo);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AdministradorDTO administradorDTO) {
        String mensaje = administradorServicios.login(administradorDTO);
        return ResponseEntity.ok(Map.of("message", mensaje));
    }

    @PostMapping("/envio-cambio-clave")
    public ResponseEntity<?> enviarCambioClave(@RequestParam String email) {
        String mensaje = administradorServicios.enviarCambioClave(email);
        return ResponseEntity.ok(Collections.singletonMap("message", mensaje));
    }

    @PostMapping("/cambiar-clave")
    public ResponseEntity<?> cambiarClave(@RequestBody CambioClaveDTO request) {
        String mensaje = administradorServicios.cambiarClave(request.getToken(), request.getNuevaClave());
        return ResponseEntity.ok(Collections.singletonMap("message", mensaje));
    }


}