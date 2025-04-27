package com.innovatech.solution.nomina.servicios;

import com.innovatech.solution.nomina.persistencia.dto.*;

import java.util.List;

public interface CargarCamposServicios {

    List<EpsDTO> listaEps();

    List<PensionesDTO> pensiones();

    List<BancosDTO> bancos();

    List<TipoContratoDTO> tipoContratos();

    List<CargoDTO> cargos();

    List<AreaDTO> areas();
}
