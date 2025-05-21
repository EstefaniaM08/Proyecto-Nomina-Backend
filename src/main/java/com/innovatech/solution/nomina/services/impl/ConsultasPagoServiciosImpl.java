package com.innovatech.solution.nomina.services.impl;

import com.innovatech.solution.nomina.dto.BusquedaPagosDTO;
import com.innovatech.solution.nomina.dto.PagoNominaCriterios;
import com.innovatech.solution.nomina.persistence.entities.Nomina;
import com.innovatech.solution.nomina.persistence.entities.Nomina_;
import com.innovatech.solution.nomina.persistence.entities.Persona_;
import com.innovatech.solution.nomina.persistence.repositories.NominaRepositorio;
import com.innovatech.solution.nomina.services.ConsultasPagoServicios;
import io.github.jhipster.service.QueryService;
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.LocalDateFilter;
import io.github.jhipster.service.filter.StringFilter;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ConsultasPagoServiciosImpl extends QueryService<Nomina> implements ConsultasPagoServicios {

    @Autowired
    NominaRepositorio nominaRepositorio;

    @Override
    public ByteArrayInputStream generarExcelNominas(List<Nomina> nominas) {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Nóminas");

            // Encabezados
            String[] columnas = {
                    "ID Nómina", "Identificación", "Nombres", "Apellidos", "Fecha de Pago",
                    "Salario", "Subsidio Transporte", "Prima", "Cesantías", "Vacaciones", "Comisiones",
                    "Viáticos", "Gastos Representación", "Horas Extra Día", "Horas Extra Noche",
                    "Salud", "Pensión", "Retención Fuente", "Total Deducciones", "Total Devengado", "Pago Final"
            };

            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < columnas.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columnas[i]);
            }

            // Datos
            int rowIdx = 1;
            for (Nomina nomina : nominas) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(nomina.getId());
                row.createCell(1).setCellValue(nomina.getPersonal().getIdentificacion());
                row.createCell(2).setCellValue(nomina.getPersonal().getNombres());
                row.createCell(3).setCellValue(nomina.getPersonal().getApellidos());
                row.createCell(4).setCellValue(nomina.getFechaPago().toString());
                row.createCell(5).setCellValue(nomina.getPersonal().getSalario().doubleValue());
                row.createCell(6).setCellValue(nomina.getSubsidioTransporte().doubleValue());
                row.createCell(7).setCellValue(nomina.getPrima().doubleValue());
                row.createCell(8).setCellValue(nomina.getCesantias().doubleValue());
                row.createCell(9).setCellValue(nomina.getVacaciones() != null ? nomina.getVacaciones().doubleValue() : 0);
                row.createCell(10).setCellValue(nomina.getComisiones().doubleValue());
                row.createCell(11).setCellValue(nomina.getViaticos().doubleValue());
                row.createCell(12).setCellValue(nomina.getGastosRepresentacion().doubleValue());
                row.createCell(13).setCellValue(nomina.getHorExtraDiu());
                row.createCell(14).setCellValue(nomina.getHorExtraNoc());
                row.createCell(15).setCellValue(nomina.getSalud().doubleValue());
                row.createCell(16).setCellValue(nomina.getPencion().doubleValue());
                row.createCell(17).setCellValue(nomina.getRetencionFuente().doubleValue());
                row.createCell(18).setCellValue(nomina.getTotDescuetos().doubleValue());
                row.createCell(19).setCellValue(nomina.getTotDevengados().doubleValue());
                row.createCell(20).setCellValue(nomina.getPagoFinal().doubleValue());
            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Error generando el archivo Excel", e);
        }
    }

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



