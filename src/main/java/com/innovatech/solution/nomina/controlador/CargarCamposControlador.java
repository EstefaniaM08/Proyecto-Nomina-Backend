package com.innovatech.solution.nomina.controlador;

import com.innovatech.solution.nomina.persistencia.dto.*;
import com.innovatech.solution.nomina.servicios.CargarCamposServicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cargar")
public class CargarCamposControlador {

    @Autowired
    private CargarCamposServicios cargarCamposServicios;
    @GetMapping("/cargos")
    public ResponseEntity<List<CargoDTO>> consultarCargos(){
        return new ResponseEntity<>(cargarCamposServicios.cargos(), null, HttpStatus.OK);
    }
    @GetMapping("/areas")
    public ResponseEntity<List<AreaDTO>> consultarAreas(){
        return new ResponseEntity<>(cargarCamposServicios.areas(), null, HttpStatus.OK);
    }
    @GetMapping("/contratos")
    public ResponseEntity<List<TipoContratoDTO>> consultarContratos(){
        return new ResponseEntity<>(cargarCamposServicios.tipoContratos(), null, HttpStatus.OK);
    }
    @GetMapping("/bancos")
    public ResponseEntity<List<BancosDTO>> consultarBancos(){
        return new ResponseEntity<>(cargarCamposServicios.bancos(), null, HttpStatus.OK);
    }
    @GetMapping("/eps")
    public ResponseEntity<List<EpsDTO>> consultarEps(){
        return new ResponseEntity<>(cargarCamposServicios.listaEps(), null, HttpStatus.OK);
    }
    @GetMapping("/pensiones")
    public ResponseEntity<List<PensionesDTO>> consultarPensiones(){
        return new ResponseEntity<>(cargarCamposServicios.pensiones(), null, HttpStatus.OK);
    }

}
