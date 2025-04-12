package com.innovatech.solution.nomina.controlador;

import com.innovatech.solution.nomina.persistencia.dta.Nomina;
import com.innovatech.solution.nomina.persistencia.dta.Persona;
import com.innovatech.solution.nomina.persistencia.dto.BusquedaPagosDTO;
import com.innovatech.solution.nomina.persistencia.dto.BusquedaPersonasDTO;
import com.innovatech.solution.nomina.persistencia.dto.JasperDTO;
import com.innovatech.solution.nomina.persistencia.dto.NominaDTO;
import com.innovatech.solution.nomina.servicios.ConsultasPagoServicios;
import com.innovatech.solution.nomina.servicios.NominaServicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pagonomina")
public class PagoNominaControlador {
    @Autowired
    NominaServicios nominaServicios;

    @Autowired
    ConsultasPagoServicios consultasPagoServicios;

    @PostMapping("/pago-nomina")
    public NominaDTO pagoNomina(@RequestBody NominaDTO pago){
        return nominaServicios.pagoNomina(pago);
    }
    @PostMapping("/busqueda-nominas")
    public ResponseEntity<List<Nomina>> busquedaPagos(@RequestBody BusquedaPagosDTO busquedaDTO){
        return new ResponseEntity<List<Nomina>>(consultasPagoServicios.busquedaPagos(busquedaDTO), HttpStatus.OK);
    }
    @GetMapping("/crear-pdf")
    public ResponseEntity<ByteArrayResource> crearPdf(@RequestBody JasperDTO jasper){
        System.out.println(jasper);
        return nominaServicios.crearPdf(jasper);
    }

}
