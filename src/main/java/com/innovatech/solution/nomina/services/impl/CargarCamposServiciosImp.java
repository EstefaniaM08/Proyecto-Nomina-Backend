package com.innovatech.solution.nomina.services.impl;

import com.innovatech.solution.nomina.dto.*;
import com.innovatech.solution.nomina.persistence.entities.*;
import com.innovatech.solution.nomina.persistence.repositories.*;
import com.innovatech.solution.nomina.services.CargarCamposServicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CargarCamposServiciosImp implements CargarCamposServicios {
    @Autowired
    private EpsRepositorio epsRepositorio;
    @Autowired
    private PensionRepositorio pensionRepositorio;
    @Autowired
    private BancoRepositorio bancoRepository;
    @Autowired
    private TipoContratoRepositorio tipoContratoRepositorio;
    @Autowired
    CargoRepositorio cargoRepositorio;
    @Autowired
    private AreaRepositorio areaRepositorio;

    @Override
    public List<EpsDTO> listaEps() {
        List<EpsDTO> lEpsDTO = new ArrayList<>();
        List<Eps> listaEps = epsRepositorio.findAll();
        for(Eps eps: listaEps){
            lEpsDTO.add(EpsDTO.builder()
                    .id(eps.getId())
                    .nombre(eps.getNombre())
                    .build());
        }
        return lEpsDTO;
    }

    @Override
    public List<PensionesDTO> pensiones() {
        List<PensionesDTO> lpensionDTO = new ArrayList<>();
        List<Pensiones> listaPensiones = pensionRepositorio.findAll();
        for(Pensiones pen: listaPensiones){
            lpensionDTO.add(PensionesDTO.builder()
                    .id(pen.getId())
                    .nombre(pen.getNombre())
                    .build());
        }
        return lpensionDTO;
    }

    @Override
    public List<BancosDTO> bancos() {
        List<BancosDTO> lBancosDTO = new ArrayList<>();
        List<Bancos> listaBancos = bancoRepository.findAll();
        for(Bancos ban: listaBancos){
            lBancosDTO.add(BancosDTO.builder()
                    .id(ban.getId())
                    .nombre(ban.getNombre())
                    .build());
        }
        return lBancosDTO;
    }

    @Override
    public List<TipoContratoDTO> tipoContratos() {

        List<TipoContratoDTO> lTipoContratoDTO = new ArrayList<>();
        List<TipoContrato> listaTipoContratos = tipoContratoRepositorio.findAll();
        for(TipoContrato are: listaTipoContratos){
            lTipoContratoDTO.add(TipoContratoDTO.builder()
                    .id(are.getId())
                    .nombre(are.getNombre())
                    .build());
        }
        return lTipoContratoDTO;
    }
    @Override
    public List<CargoDTO> cargos() {
        List<CargoDTO> lCargosDTO = new ArrayList<>();
        List<Cargo> listaCargos = cargoRepositorio.findAll();

        for (Cargo car : listaCargos) {
            lCargosDTO.add(CargoDTO.builder()
                    .id(car.getId())
                    .nombre(car.getNombre())
                    .build());
        }
        return lCargosDTO;
    }
    @Override
    public List<AreaDTO> areas() {
        List<AreaDTO> lAreasDTO = new ArrayList<>();
        List<Area> listaAreas = areaRepositorio.findAll();
        for(Area are: listaAreas){
            lAreasDTO.add(AreaDTO.builder()
                    .id(are.getId())
                    .nombre(are.getNombre())
                    .build());
        }
        return lAreasDTO;
    }

}
