package com.innovatech.solution.nomina.services.impl;

import com.innovatech.solution.nomina.dto.BusquedaPersonasDTO;
import com.innovatech.solution.nomina.dto.PersonaCriterios;
import com.innovatech.solution.nomina.persistence.entities.Area_;
import com.innovatech.solution.nomina.persistence.entities.Cargo_;
import com.innovatech.solution.nomina.persistence.entities.Persona;
import com.innovatech.solution.nomina.persistence.entities.Persona_;
import com.innovatech.solution.nomina.persistence.repositories.PersonaRepositorio;
import com.innovatech.solution.nomina.services.ConsultasPersonaServicios;
import io.github.jhipster.service.QueryService;
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.StringFilter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultasPersonaServiciosImpl extends QueryService<Persona> implements ConsultasPersonaServicios {
    @Autowired
    PersonaRepositorio personaRepositorio;

    @Override
    public List<Persona> busquedaPersonas(BusquedaPersonasDTO busquedaDTO){
        PersonaCriterios personaCriterios = createCriteria(busquedaDTO);
        List<Persona> listPersonas = findByCriteria(personaCriterios);
        return listPersonas;
    }

    private PersonaCriterios createCriteria(BusquedaPersonasDTO dto){
        PersonaCriterios personaCriterios = new PersonaCriterios();
        if(dto!= null){
            if(!StringUtils.isBlank(dto.getIdentificacion())){
                StringFilter filter = new StringFilter();
                filter.setContains(dto.getIdentificacion());
                personaCriterios.setIdentificacion(filter);
            }

            if(!StringUtils.isBlank(dto.getApellidos())){
                StringFilter filter = new StringFilter();
                filter.setContains(dto.getApellidos());
                personaCriterios.setApellidos(filter);
            }

            if(!StringUtils.isBlank(dto.getEstado())){
                StringFilter filter = new StringFilter();
                filter.setContains(dto.getEstado());
                personaCriterios.setEstado(filter);
            }

            if(dto.getSalarioDesde()!=null || dto.getSalarioHasta()!=null){
                BigDecimalFilter filter = new BigDecimalFilter();
                if(dto.getSalarioDesde()!=null){
                    filter.setGreaterThanOrEqual(dto.getSalarioDesde());
                    personaCriterios.setSalario(filter);
                }

                if(dto.getSalarioHasta()!=null){
                    filter.setLessThanOrEqual(dto.getSalarioHasta());
                    personaCriterios.setSalario(filter);
                }
            }
            if(!StringUtils.isBlank(dto.getArea())){
                StringFilter filter = new StringFilter();
                filter.setEquals(dto.getArea());
                personaCriterios.setArea(filter);
            }
            if(!StringUtils.isBlank(dto.getCargo())){
                StringFilter filter = new StringFilter();
                filter.setEquals(dto.getCargo());
                personaCriterios.setCargo(filter);
            }
        }
        return personaCriterios;
    }

    public List<Persona> findByCriteria(PersonaCriterios personaCriterios){
        final Specification<Persona> specification = createSpecification(personaCriterios);
        List<Persona> personas = personaRepositorio.findAll(specification);
        return personas;
    }

    private  Specification<Persona> createSpecification(PersonaCriterios criteria){
        Specification<Persona> specification = Specification.where(null);

        if(criteria != null){
            if(criteria.getIdentificacion()!=null){
                specification =
                        specification.and(buildStringSpecification(criteria.getIdentificacion(), Persona_.identificacion));
            }
            if(criteria.getApellidos()!=null){
                specification =
                        specification.and(buildStringSpecification(criteria.getApellidos(), Persona_.apellidos));
            }
            if(criteria.getEstado()!=null){
                specification =
                        specification.and(buildStringSpecification(criteria.getEstado(), Persona_.estado));
            }
            if(criteria.getSalario()!=null){
                specification =
                        specification.and(buildRangeSpecification(criteria.getSalario(), Persona_.salario));
            }
            if(criteria.getArea()!=null){
                specification=
                        specification.and(buildReferringEntitySpecification(criteria.getArea(), Persona_.area, Area_.nombre));

            }
            if(criteria.getCargo()!=null){
                specification=
                        specification.and(buildReferringEntitySpecification(criteria.getCargo(), Persona_.cargo, Cargo_.nombre));

            }
        }
        return specification;
    }
}
