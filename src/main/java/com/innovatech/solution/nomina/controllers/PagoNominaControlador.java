package com.innovatech.solution.nomina.controllers;

import com.innovatech.solution.nomina.dto.BusquedaPagosDTO;
import com.innovatech.solution.nomina.dto.JasperDTO;
import com.innovatech.solution.nomina.dto.NominaDTO;
import com.innovatech.solution.nomina.persistence.entities.Nomina;
import com.innovatech.solution.nomina.services.ConsultasPagoServicios;
import com.innovatech.solution.nomina.services.NominaServicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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

    @PostMapping("/pago-nomina/excel")
    public ResponseEntity<Map<String, Object>> procesarNominasDesdeExcel(@RequestParam("file") MultipartFile file) {
        Map<String, Object> resultado = nominaServicios.procesarExcel(file);
        return ResponseEntity.ok(resultado);
    }

    @PostMapping("/crear-pdf")
    public ResponseEntity<ByteArrayResource> crearPdf(@RequestBody JasperDTO jasper) {
        System.out.println(jasper);
        return nominaServicios.crearPdf(jasper);
    }
    @GetMapping("/busqueda-nominas")
    public ResponseEntity<List<Nomina>> busquedaPagos(
        @RequestParam(required = false) String identificacion,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
        @RequestParam(required = false) BigDecimal totDesDesde,
        @RequestParam(required = false) BigDecimal totDesHasta,
        @RequestParam(required = false) BigDecimal totDevDesde,
        @RequestParam(required = false) BigDecimal totDevHasta,
        @RequestParam(required = false) BigDecimal pagFinDesde,
        @RequestParam(required = false) BigDecimal pagFinHasta
    ) {
        BusquedaPagosDTO busquedaDTO = new BusquedaPagosDTO();
        busquedaDTO.setIdentificacion(identificacion);
        busquedaDTO.setFecha(fecha);
        busquedaDTO.setTotDesDesde(totDesDesde);
        busquedaDTO.setTotDesHasta(totDesHasta);
        busquedaDTO.setTotDevDesde(totDevDesde);
        busquedaDTO.setTotDevHasta(totDevHasta);
        busquedaDTO.setPagFinDesde(pagFinDesde);
        busquedaDTO.setPagFinHasta(pagFinHasta);

        return new ResponseEntity<List<Nomina>>(consultasPagoServicios.busquedaPagos(busquedaDTO), HttpStatus.OK);
    }

    @GetMapping("/exportar-excel")
    public ResponseEntity<Resource> exportarExcel(
        @RequestParam(required = false) String identificacion,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
        @RequestParam(required = false) BigDecimal totDesDesde,
        @RequestParam(required = false) BigDecimal totDesHasta,
        @RequestParam(required = false) BigDecimal totDevDesde,
        @RequestParam(required = false) BigDecimal totDevHasta,
        @RequestParam(required = false) BigDecimal pagFinDesde,
        @RequestParam(required = false) BigDecimal pagFinHasta
    ) {
        BusquedaPagosDTO busquedaDTO = new BusquedaPagosDTO();
        busquedaDTO.setIdentificacion(identificacion);
        busquedaDTO.setFecha(fecha);
        busquedaDTO.setTotDesDesde(totDesDesde);
        busquedaDTO.setTotDesHasta(totDesHasta);
        busquedaDTO.setTotDevDesde(totDevDesde);
        busquedaDTO.setTotDevHasta(totDevHasta);
        busquedaDTO.setPagFinDesde(pagFinDesde);
        busquedaDTO.setPagFinHasta(pagFinHasta);

        List<Nomina> nominas =  consultasPagoServicios.busquedaPagos(busquedaDTO);
        ByteArrayInputStream in = consultasPagoServicios.generarExcelNominas(nominas);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=nominas.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(new InputStreamResource(in));
    }
}
