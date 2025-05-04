package com.innovatech.solution.nomina.services;

import com.innovatech.solution.nomina.dto.JasperDTO;
import com.innovatech.solution.nomina.dto.NominaDTO;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;

public interface NominaServicios {
    NominaDTO pagoNomina(NominaDTO pago);
    ResponseEntity<ByteArrayResource> crearPdf(JasperDTO jasper);
}
