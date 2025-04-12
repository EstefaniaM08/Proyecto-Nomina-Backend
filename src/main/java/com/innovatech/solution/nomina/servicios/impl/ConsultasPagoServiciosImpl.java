package com.innovatech.solution.nomina.servicios.impl;


import com.innovatech.solution.nomina.criterios.PagoNominaCriterios;
import com.innovatech.solution.nomina.persistencia.dta.Nomina;
import com.innovatech.solution.nomina.persistencia.dta.Nomina_;
import com.innovatech.solution.nomina.persistencia.dta.Persona_;
import com.innovatech.solution.nomina.persistencia.dto.BusquedaPagosDTO;
import com.innovatech.solution.nomina.persistencia.repositorio.NominaRepositorio;
import com.innovatech.solution.nomina.servicios.ConsultasPagoServicios;
import io.github.jhipster.service.QueryService;
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.LocalDateFilter;
import io.github.jhipster.service.filter.StringFilter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultasPagoServiciosImpl extends QueryService<Nomina> implements ConsultasPagoServicios {

    @Autowired
    NominaRepositorio nominaRepositorio;
    @Override
    public List<Nomina> busquedaPagos(BusquedaPagosDTO busquedaDTO){
        PagoNominaCriterios pagoCriteria = createCriteria(busquedaDTO);
        List<Nomina> listPagos = findByCriteria(pagoCriteria);
        return listPagos;
    }
    private PagoNominaCriterios createCriteria(BusquedaPagosDTO dto){
        PagoNominaCriterios pagoCriteria = new PagoNominaCriterios();

        if(dto!=null){
            if(!StringUtils.isBlank(dto.getIdentificacion())){
                StringFilter filter = new StringFilter();
                filter.setContains(dto.getIdentificacion());
                pagoCriteria.setIdPersona(filter);
            }

            if(dto.getFecha()!=null){
                LocalDateFilter filter = new LocalDateFilter();
                filter.setEquals(dto.getFecha());
                pagoCriteria.setFecha(filter);
            }

            if(dto.getTotDevDesde()!=null || dto.getTotDevHasta()!=null){
                BigDecimalFilter filter = new BigDecimalFilter();
                if(dto.getTotDevDesde()!=null){
                    filter.setGreaterThanOrEqual(dto.getTotDevDesde());
                    pagoCriteria.setTotDev(filter);
                }

                if(dto.getTotDevHasta()!=null){
                    filter.setLessThanOrEqual(dto.getTotDevHasta());
                    pagoCriteria.setTotDev(filter);
                }
            }

            if(dto.getTotDesDesde()!=null || dto.getTotDesHasta()!=null){
                BigDecimalFilter filter = new BigDecimalFilter();
                if(dto.getTotDesDesde()!=null){
                    filter.setGreaterThanOrEqual(dto.getTotDesDesde());
                    pagoCriteria.setTotDes(filter);
                }

                if(dto.getTotDesHasta()!=null){
                    filter.setLessThanOrEqual(dto.getTotDesHasta());
                    pagoCriteria.setTotDes(filter);
                }
            }

            if(dto.getPagFinDesde()!=null || dto.getPagFinHasta()!=null){
                BigDecimalFilter filter = new BigDecimalFilter();
                if(dto.getPagFinDesde()!=null){
                    filter.setGreaterThanOrEqual(dto.getPagFinDesde());
                    pagoCriteria.setPagoFinal(filter);
                }

                if(dto.getPagFinHasta()!=null){
                    filter.setLessThanOrEqual(dto.getPagFinHasta());
                    pagoCriteria.setPagoFinal(filter);
                }
            }
        }
        return pagoCriteria;
    }

    private List<Nomina> findByCriteria(PagoNominaCriterios pagoCriteria){

        final Specification<Nomina> specification = createSpecification(pagoCriteria);
        List<Nomina> pagosNomina = nominaRepositorio.findAll(specification);
        return pagosNomina;
    }

    private Specification<Nomina> createSpecification(PagoNominaCriterios criteria){
        Specification<Nomina> specification = Specification.where(null);

        if(criteria != null){

            if(criteria.getIdPersona() != null){
                specification = specification.and(
                        (root, query, criteriaBuilder) ->
                                criteriaBuilder.like(root.join(Nomina_.personal).get(Persona_.identificacion),
                                        "%" + criteria.getIdPersona().getContains() + "%")
                );
            }
            if(criteria.getFecha()!=null){
                specification =
                        specification.and(buildRangeSpecification(criteria.getFecha(), Nomina_.fechaPago));
            }
            if(criteria.getTotDev()!=null){
                specification =
                        specification.and(buildRangeSpecification(criteria.getTotDev(), Nomina_.totDevengados));
            }
            if(criteria.getTotDes()!=null){
                specification =
                        specification.and(buildRangeSpecification(criteria.getTotDes(), Nomina_.totDescuetos));
            }
            if(criteria.getPagoFinal()!=null){
                specification =
                        specification.and(buildRangeSpecification(criteria.getPagoFinal(), Nomina_.pagoFinal));
            }
        }
        return specification;
    }
}



