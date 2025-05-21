package com.innovatech.solution.nomina.services;

import com.innovatech.solution.nomina.dto.JasperDTO;
import com.innovatech.solution.nomina.dto.NominaDTO;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface NominaServicios {
    NominaDTO pagoNomina(NominaDTO pago);
    ResponseEntity<ByteArrayResource> crearPdf(JasperDTO jasper);
    Map<String, Object> procesarExcel(MultipartFile file);
}
