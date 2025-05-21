package com.innovatech.solution.nomina.services;

import com.innovatech.solution.nomina.dto.BusquedaPagosDTO;
import com.innovatech.solution.nomina.persistence.entities.Nomina;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface ConsultasPagoServicios {
    List<Nomina> busquedaPagos(BusquedaPagosDTO busquedaDTO);
    ByteArrayInputStream generarExcelNominas(List<Nomina> nominas);
}
