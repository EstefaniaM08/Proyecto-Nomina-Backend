package com.innovatech.solution.nomina.servicios;

import com.innovatech.solution.nomina.persistencia.dto.JasperDTO;
import com.innovatech.solution.nomina.persistencia.dto.NominaDTO;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;

public interface NominaServicios {
    NominaDTO pagoNomina(NominaDTO pago);
    ResponseEntity<ByteArrayResource> crearPdf(JasperDTO jasper);
    //NominaDTO obtenerNomina(Long id);
}
