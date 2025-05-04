package com.innovatech.solution.nomina.controllers;

import com.innovatech.solution.nomina.dto.NominaDTO;
import com.innovatech.solution.nomina.services.NominaServicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pagonomina")
public class PagoNominaControlador {
    @Autowired
    NominaServicios nominaServicios;

    @PostMapping("/pago-nomina")
    public NominaDTO pagoNomina(@RequestBody NominaDTO pago){
        return nominaServicios.pagoNomina(pago);
    }


}
