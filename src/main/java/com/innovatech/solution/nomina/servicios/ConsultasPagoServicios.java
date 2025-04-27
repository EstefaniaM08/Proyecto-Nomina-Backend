package com.innovatech.solution.nomina.servicios;

import com.innovatech.solution.nomina.persistencia.dta.Nomina;
import com.innovatech.solution.nomina.persistencia.dto.BusquedaPagosDTO;

import java.util.List;

public interface ConsultasPagoServicios {
    List<Nomina> busquedaPagos(BusquedaPagosDTO busquedaDTO);
}
