package com.innovatech.solution.nomina.controlador;

import com.innovatech.solution.nomina.persistencia.dto.NominaDTO;
import com.innovatech.solution.nomina.servicios.NominaServicios;
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
