package com.innovatech.solution.nomina.services.impl;

import com.innovatech.solution.nomina.dto.JasperDTO;
import com.innovatech.solution.nomina.dto.NominaDTO;
import com.innovatech.solution.nomina.dto.PersonaDTO;
import com.innovatech.solution.nomina.persistence.entities.DevengadosPrestaciones;
import com.innovatech.solution.nomina.persistence.entities.Nomina;
import com.innovatech.solution.nomina.persistence.entities.Persona;
import com.innovatech.solution.nomina.persistence.repositories.DevengadosPrestacionesRepositorio;
import com.innovatech.solution.nomina.persistence.repositories.NominaRepositorio;
import com.innovatech.solution.nomina.persistence.repositories.PersonaRepositorio;
import com.innovatech.solution.nomina.services.NominaServicios;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class NominaServiciosImpl implements NominaServicios {
    @Autowired
    NominaRepositorio nominaRepositorio;
    @Autowired
    PersonaRepositorio personaRepositorio;
    @Autowired
    DevengadosPrestacionesRepositorio devPresRepositorio;

    private static final BigDecimal SALARIO_MINIMO = BigDecimal.valueOf(1423500);
    private static final BigDecimal AUXILIO_TRANSPORTE = BigDecimal.valueOf(200000);
    private static final int HORAS_MES = 240;
    private static final int DIAS_ANIO = 360;

    @Override
    public ResponseEntity<ByteArrayResource> crearPdf(JasperDTO jasper) {
        if (true) {
            try {
                final File file = ResourceUtils.getFile("classpath:Blank_A41.jasper");
                final File codBarras = ResourceUtils.getFile("classpath:static/img/qr.jpeg");
                final File logo = ResourceUtils.getFile("classpath:static/img/logo.jpeg");
                final JasperReport report = (JasperReport) JRLoader.loadObject(file);

                final HashMap<String, Object> parameters = new HashMap<>();


                parameters.put("nombre", jasper.getNombre());
                parameters.put("fecha", jasper.getFecha());
                parameters.put("cargo", jasper.getCargo());
                parameters.put("cedula", jasper.getCedula());
                parameters.put("cuenta", jasper.getCuenta());
                parameters.put("banco", jasper.getBanco());
                parameters.put("totalPagar", jasper.getTotalPagar());
                parameters.put("salario", jasper.getSalario());
                parameters.put("totDev", jasper.getTotDev());
                parameters.put("totDes", jasper.getTotDes());
                parameters.put("codBarras", new FileInputStream(codBarras));
                parameters.put("logo", new FileInputStream(logo));
                System.out.println("hasta aqui 1");
                JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());
                byte[] reporte = JasperExportManager.exportReportToPdf(jasperPrint);
                String sdf = (new SimpleDateFormat("dd/MM/yyyy")).format(new Date());
                StringBuilder stringBuilder = new StringBuilder().append("InvoicePDF:");
                ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
                        .filename(stringBuilder.append("pago")
                                .append("generateDate:")
                                .append(sdf)
                                .append(".pdf")
                                .toString())
                        .build();
                HttpHeaders headers = new HttpHeaders();
                headers.setContentDisposition(contentDisposition);
                return ResponseEntity.ok().contentLength((long) reporte.length)
                        .contentType(MediaType.APPLICATION_PDF)
                        .headers(headers).body(new ByteArrayResource(reporte));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return ResponseEntity.noContent().build();
        }
        return null;
    }

    @Override
    public Map<String, Object> procesarExcel(MultipartFile file) {
        List<NominaDTO> nominasProcesadas = new ArrayList<>();
        List<Map<String, Object>> errores = new ArrayList<>();

        try (InputStream inputStream = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                try {
                    NominaDTO dto = new NominaDTO();

                    String identificacion = obtenerString(row.getCell(0));
                    LocalDate fechaPago = obtenerFecha(row.getCell(1));

                    // Validaciones de negocio
                    validarIdentificacionYFecha(identificacion, fechaPago);

                    dto.setIdentificacion(identificacion);
                    dto.setFechaPago(fechaPago);
                    dto.setComisiones(validarNumeroDecimalPositivo(row.getCell(2), "comisiones"));
                    dto.setViaticos(validarNumeroDecimalPositivo(row.getCell(3), "viaticos"));
                    dto.setGastosRepresentacion(validarNumeroDecimalPositivo(row.getCell(4), "gastosRepresentacion"));

                    dto.setHorExtraDiu(validarNumeroEnteroPositivo(row.getCell(5), "horExtraDiu"));
                    dto.setHorExtraNoc(validarNumeroEnteroPositivo(row.getCell(6), "horExtraNoc"));
                    dto.setHorExtraDiuDomFes(validarNumeroEnteroPositivo(row.getCell(7), "horExtraDiuDomFes"));
                    dto.setHorExtraNocDomFes(validarNumeroEnteroPositivo(row.getCell(8), "horExtraNocDomFes"));

                    NominaDTO procesado = this.pagoNomina(dto); // crea la nómina
                    nominasProcesadas.add(procesado);

                } catch (Exception e) {
                    Map<String, Object> error = new HashMap<>();
                    error.put("fila", i + 1);
                    error.put("identificacion", row.getCell(0) != null ? obtenerString(row.getCell(0)) : "N/A");
                    error.put("error", e.getMessage());
                    errores.add(error);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Error procesando el archivo", e);
        }

        Map<String, Object> resultado = new HashMap<>();
        resultado.put("nominasProcesadas", nominasProcesadas);
        resultado.put("errores", errores);

        return resultado;
    }

    @Override
    public NominaDTO pagoNomina(NominaDTO nominaDTO) {
        Persona persona = personaRepositorio.findByIdentificacion(nominaDTO.getIdentificacion())
                .orElseThrow(() -> new RuntimeException("Persona no encontrada "));

        LocalDate fechaIngreso = persona.getFechaIngreso();
        if (nominaDTO.getFechaPago().isBefore(fechaIngreso)) {
            throw new IllegalArgumentException("No se puede liquidar la nómina antes de la fecha de ingreso del empleado.");
        }

        Optional<Nomina> ultimaNomina = nominaRepositorio.findTopByPersonalIdentificacionOrderByFechaPagoDesc(nominaDTO.getIdentificacion());

        if (ultimaNomina.isPresent()) {
            LocalDate fechaUltimoPago = ultimaNomina.get().getFechaPago();
            long diasDiferencia = ChronoUnit.DAYS.between(fechaUltimoPago, nominaDTO.getFechaPago());

            if (diasDiferencia < 23) {
                throw new IllegalArgumentException("No se puede liquidar la nómina: deben pasar al menos 23 días desde el último pago.");
            }

            if (diasDiferencia > 31) {
                throw new IllegalArgumentException("No se puede liquidar la nómina: el periodo supera los 31 días permitidos.");
            }
        }

        nominaDTO = calcularPrestacionesSociales(nominaDTO);
        nominaDTO = calcularDeducciones(nominaDTO);
        nominaDTO = calcularTotales(nominaDTO);

        Nomina nomina = Nomina.builder()
                .personal(persona)
                .fechaPago(nominaDTO.getFechaPago())

                .comisiones(nominaDTO.getComisiones())
                .viaticos(nominaDTO.getViaticos())
                .gastosRepresentacion(nominaDTO.getGastosRepresentacion())
                .horExtraDiu(nominaDTO.getHorExtraDiu())
                .horExtraNoc(nominaDTO.getHorExtraNoc())
                .horExtraDiuDomFes(nominaDTO.getHorExtraDiuDomFes())
                .horExtraNocDomFes(nominaDTO.getHorExtraNocDomFes())

                .subsidioTransporte(nominaDTO.getSubsidioTransporte())
                .prima(nominaDTO.getPrima())
                .cesantias(nominaDTO.getCesantias())
                .vacaciones(nominaDTO.getVacaciones())

                .salud(nominaDTO.getSalud())
                .pencion(nominaDTO.getPension()) //
                .retencionFuente(nominaDTO.getRetencionFuente())
                .fondoSolid(nominaDTO.getFondoSolid())

                .totValHorExtra(nominaDTO.getTotValHorExtra())
                .totDescuetos(nominaDTO.getTotDescuetos())
                .totDevengados(nominaDTO.getTotDevengados())
                .pagoFinal(nominaDTO.getPagoFinal())
                .build();

        nominaRepositorio.save(nomina);
        return nominaDTO;
    }

    public NominaDTO calcularPrestacionesSociales(NominaDTO nominaDTO) {
        Persona persona = personaRepositorio.findByIdentificacion(nominaDTO.getIdentificacion())
                .orElseThrow(() -> new RuntimeException("Persona no encontrada con identificación: " + nominaDTO.getIdentificacion()));

        nominaDTO.setSalario(persona.getSalario());

        //PAGOS MENSUALES
        BigDecimal valAuxTrasporte = calcularAuxilioTransporte(nominaDTO.getSalario()); //SE CALCULA EL AUXILIO DE TRASNPORTE
        BigDecimal valHorExtra = calcularPagoHorasExtras(nominaDTO); //SE CALCULA EL VALOR A PAGAR POR HORAS EXTRA EN EL MES

        //Calculos Devengados Prestaciones
        BigDecimal salDev = valAuxTrasporte.add(valHorExtra).add(nominaDTO.getSalario()).add(nominaDTO.getComisiones());
        guardarDevPrima(nominaDTO.getIdentificacion(), persona.getFechaIngreso(), salDev);
        guardarDevCesantias(nominaDTO.getIdentificacion(), persona.getFechaIngreso(), salDev);


        //PAGOS PRESTACIONES SOCIALES
        BigDecimal prima = calcularPrima(persona, nominaDTO);
        BigDecimal cesantias = calcularCesantias(persona, nominaDTO);

        //guardarDevVacaiones(nominaDTO.getIdentificacion(),persona.getFechaIngreso(), salDev);
        //BigDecimal vacaciones = calcularVacaciones(persona, nominaDTO);


        nominaDTO.setSubsidioTransporte(valAuxTrasporte);
        nominaDTO.setPrima(prima);
        nominaDTO.setCesantias(cesantias);
        // nominaDTO.setVacaciones(vacaciones); // cuando lo actives
        nominaDTO.setTotValHorExtra(valHorExtra);

        return nominaDTO;
    }

    public NominaDTO calcularDeducciones(NominaDTO nominaDTO) {
        BigDecimal salud = calcularSalud(nominaDTO);
        nominaDTO.setSalud(salud);

        BigDecimal pension = calcularPension(nominaDTO);
        nominaDTO.setPension(pension);

        BigDecimal fondoSolidaridad = calcularFondoSolidaridad(nominaDTO);
        nominaDTO.setFondoSolid(fondoSolidaridad);

        BigDecimal retencionFuente = calcularRetencionFuente(nominaDTO);
        nominaDTO.setRetencionFuente(retencionFuente);

        return nominaDTO;
    }

    private NominaDTO calcularTotales(NominaDTO nominaDTO) {
        // Calcular total devengado
        BigDecimal totDevengados = nominaDTO.getSalario()
                .add(nominaDTO.getSubsidioTransporte())
                .add(nominaDTO.getPrima())
                .add(nominaDTO.getCesantias())
                //.add(nominaDTO.getVacaciones())
                .add(nominaDTO.getTotValHorExtra())
                .add(nominaDTO.getComisiones())
                .add(nominaDTO.getViaticos())
                .add(nominaDTO.getGastosRepresentacion());

        // Calcular total descuentos
        BigDecimal totDescuentos = nominaDTO.getSalud()
                .add(nominaDTO.getPension())
                .add(nominaDTO.getRetencionFuente())
                .add(nominaDTO.getFondoSolid());

        // Calcular pago final
        BigDecimal pagoFinal = totDevengados.subtract(totDescuentos);

        // Asignar los valores a nominaDTO
        nominaDTO.setTotDevengados(totDevengados);
        nominaDTO.setTotDescuetos(totDescuentos);
        nominaDTO.setPagoFinal(pagoFinal);

        return nominaDTO;
    }

    //Metodo para calcular si aplica auxilio de transporte
    public BigDecimal calcularAuxilioTransporte(BigDecimal sueldo) {
        if (sueldo.compareTo(this.SALARIO_MINIMO.multiply(BigDecimal.valueOf(2))) <= 0) {
            return this.AUXILIO_TRANSPORTE;
        } else {
            return BigDecimal.ZERO;
        }
    }

    //se calcula el valor a pagar de las horas extra
    private BigDecimal calcularPagoHorasExtras(NominaDTO nominaDTO) {
        BigDecimal valorHoraOrdinaria = nominaDTO.getSalario().divide(BigDecimal.valueOf(HORAS_MES), 2, BigDecimal.ROUND_HALF_UP);
        BigDecimal totalPagoHorasExtras = BigDecimal.ZERO;

        // Cálculo de horas extras diurnas
        totalPagoHorasExtras = totalPagoHorasExtras.add(valorHoraOrdinaria.multiply(BigDecimal.valueOf(nominaDTO.getHorExtraDiu())).multiply(new BigDecimal("1.25")));

        // Cálculo de horas extras nocturnas
        totalPagoHorasExtras = totalPagoHorasExtras.add(valorHoraOrdinaria.multiply(BigDecimal.valueOf(nominaDTO.getHorExtraNoc())).multiply(new BigDecimal("1.75")));

        // Cálculo de horas extras diurnas en domingos y festivos
        totalPagoHorasExtras = totalPagoHorasExtras.add(valorHoraOrdinaria.multiply(BigDecimal.valueOf(nominaDTO.getHorExtraDiuDomFes())).multiply(new BigDecimal("1.75")));

        // Cálculo de horas extras nocturnas en domingos y festivos
        totalPagoHorasExtras = totalPagoHorasExtras.add(valorHoraOrdinaria.multiply(BigDecimal.valueOf(nominaDTO.getHorExtraNocDomFes())).multiply(new BigDecimal("2.0")));

        return totalPagoHorasExtras;
    }

    public void guardarDevPrima(String identificacion, LocalDate fechaIngreso, BigDecimal salDev) {
        DevengadosPrestaciones devengadosPrestaciones = devPresRepositorio.findByIdentificacion(identificacion)
                .orElseGet(() -> DevengadosPrestaciones.builder()
                        .identificacion(identificacion)
                        .fecPagoPrima(fechaIngreso)
                        .fecPagoCesantias(fechaIngreso)
                        .fecPagoVacaciones(fechaIngreso)
                        .salariosDevengadosCesantias(BigDecimal.ZERO)
                        .salariosDevengadosVacaciones(BigDecimal.ZERO)
                        .salariosDevengadosPrima(BigDecimal.ZERO) // Se inicia en 0 si es nuevo
                        .build()
                );

        // Sumar el salario devengado, ya sea un registro existente o nuevo
        devengadosPrestaciones.setSalariosDevengadosPrima(
                devengadosPrestaciones.getSalariosDevengadosPrima().add(salDev)
        );

        // Guardar el registro en la base de datos
        devPresRepositorio.save(devengadosPrestaciones);
    }

    public BigDecimal calcularPrima(Persona persona, NominaDTO nominaDTO) {
        LocalDate fechaPago = nominaDTO.getFechaPago();
        int mes = fechaPago.getMonthValue();

        // Validar que la fecha de pago sea antes del 30 de junio o del 20 de diciembre
        if (!(mes == 6 || mes == 12)) {
            return BigDecimal.ZERO;
        }

        LocalDate fechaInicio = persona.getFechaIngreso();
        LocalDate ultimaFechaPagoPrima = devPresRepositorio.findUltimaFechaPagoPrima(nominaDTO.getIdentificacion());
        BigDecimal salariosDevengados = devPresRepositorio.findSalariosDevengadosPrima(nominaDTO.getIdentificacion());

        // Determinar la fecha de inicio para el cálculo
        LocalDate fechaInicioCalculo = (ultimaFechaPagoPrima != null && ultimaFechaPagoPrima.isAfter(fechaInicio))
                ? ultimaFechaPagoPrima.plusDays(1)
                : fechaInicio;

        // Calcular los días trabajados en el período
        long diasTrabajados = ChronoUnit.DAYS.between(fechaInicioCalculo, fechaPago) + 1;

        // Calcular la prima de servicios
        BigDecimal prima = salariosDevengados
                .divide(BigDecimal.valueOf(6), 2, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(diasTrabajados))
                .divide(BigDecimal.valueOf(DIAS_ANIO), 2, RoundingMode.HALF_UP);

        devPresRepositorio.actualizarRegistroPrima(nominaDTO.getIdentificacion(), fechaPago);
        return prima;
    }

    public void guardarDevCesantias(String identificacion, LocalDate fechaIngreso, BigDecimal salDev) {
        DevengadosPrestaciones devengadosPrestaciones = devPresRepositorio.findByIdentificacion(identificacion)
                .orElseGet(() -> DevengadosPrestaciones.builder()
                        .identificacion(identificacion)
                        .fecPagoCesantias(fechaIngreso)
                        .salariosDevengadosCesantias(BigDecimal.ZERO) // Inicia en 0 si es nuevo
                        .build()
                );
        // Sumar el salario devengado, ya sea un registro existente o nuevo
        devengadosPrestaciones.setSalariosDevengadosCesantias(
                devengadosPrestaciones.getSalariosDevengadosCesantias().add(salDev)
        );
        // Guardar el registro en la base de datos
        devPresRepositorio.save(devengadosPrestaciones);
    }

    public BigDecimal calcularCesantias(Persona persona, NominaDTO nominaDTO) {
        LocalDate fechaPago = nominaDTO.getFechaPago();
        int mes = fechaPago.getMonthValue();
        int dia = fechaPago.getDayOfMonth();

        // Validar que solo se puedan liquidar cesantías entre el 1 de enero y el 15 de febrero
        if (!(mes == 1 || (mes == 2 && dia <= 15))) {
            return BigDecimal.ZERO;
        }

        LocalDate fechaInicio = persona.getFechaIngreso();
        LocalDate ultimaFechaPagoCesantias = devPresRepositorio.findUltimaFechaPagoCesantias(nominaDTO.getIdentificacion());
        BigDecimal salariosDevengadosCesantias = devPresRepositorio.findSalariosDevengadosCesantias(nominaDTO.getIdentificacion());

        // Determinar la fecha de inicio para el cálculo
        LocalDate fechaInicioCalculo = (ultimaFechaPagoCesantias != null && ultimaFechaPagoCesantias.isAfter(fechaInicio))
                ? ultimaFechaPagoCesantias.plusDays(1)
                : fechaInicio;

        // Calcular los días trabajados en el período
        long diasTrabajados = ChronoUnit.DAYS.between(fechaInicioCalculo, fechaPago) + 1;

        // Calcular las cesantías
        BigDecimal cesantias = salariosDevengadosCesantias
                .divide(BigDecimal.valueOf(12), 2, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(diasTrabajados))
                .divide(BigDecimal.valueOf(DIAS_ANIO), 2, RoundingMode.HALF_UP);

        // Actualizar la tabla después del pago
        devPresRepositorio.actualizarRegistroCesantias(nominaDTO.getIdentificacion(), fechaPago);
        return cesantias;
    }

    /*
    public void guardarDevVacaiones(String identificacion, LocalDate fechaIngreso, BigDecimal salDev) {
        Optional<DevengadosPrestaciones> registroExistente = devPresRepositorio.findByIdentificacion(identificacion);

        if (registroExistente.isPresent()) {
            // Si el usuario ya existe, sumar el nuevo salario devengado por vacaciones
            DevengadosPrestaciones devengadosPrestaciones = registroExistente.get();
            devengadosPrestaciones.setSalariosDevengadosVacaciones(devengadosPrestaciones.getSalariosDevengadosVacaciones().add(salDev));
            devPresRepositorio.save(devengadosPrestaciones);
        } else {
            // Si el usuario no existe, crear un nuevo registro
            DevengadosPrestaciones nuevoRegistro = DevengadosPrestaciones.builder()
                    .identificacion(identificacion)
                    .fecPagoVacaciones(fechaIngreso) // Se coloca la fecha de ingreso como la fecha de pago inicial de vacaciones
                    .salariosDevengadosVacaciones(salDev)
                    .build();
            devPresRepositorio.save(nuevoRegistro);
        }
    }
    public BigDecimal calcularVacaciones(Persona persona, NominaDTO nominaDTO) {
        LocalDate fechaInicio = persona.getFechaIngreso();
        LocalDate ultimaFechaPagoVacaciones = devPresRepositorio.findUltimaFechaPagoVacaciones(nominaDTO.getIdentificacion());
        BigDecimal salariosDevengadosVacaciones = devPresRepositorio.findSalariosDevengadosVacaciones(nominaDTO.getIdentificacion());

        // Determinar la fecha de inicio para el cálculo
        LocalDate fechaInicioCalculo = (ultimaFechaPagoVacaciones != null && ultimaFechaPagoVacaciones.isAfter(fechaInicio))
                ? ultimaFechaPagoVacaciones.plusDays(1)
                : fechaInicio;

        // Calcular los días trabajados en el período
        long diasTrabajados = ChronoUnit.DAYS.between(fechaInicioCalculo, nominaDTO.getFechaPago()) + 1;

        // Calcular las vacaciones
        BigDecimal vacaciones = salariosDevengadosVacaciones.multiply(BigDecimal.valueOf(diasTrabajados))
                .divide(BigDecimal.valueOf(DIAS_ANIO), 2, BigDecimal.ROUND_HALF_UP);

        // Actualizar la tabla después del pago
        devPresRepositorio.actualizarRegistroVacaciones(nominaDTO.getIdentificacion(), nominaDTO.getFechaPago());
        return vacaciones;
    }
     */
    private BigDecimal calcularSalud(NominaDTO nominaDTO) {
        BigDecimal baseCotizacion = nominaDTO.getSalario()
                .add(nominaDTO.getTotValHorExtra()) // Incluir horas extra
                .add(nominaDTO.getComisiones()); // Incluir comisiones si existen

        return baseCotizacion.multiply(new BigDecimal("0.04")); // 4% sobre la base
    }

    private BigDecimal calcularPension(NominaDTO nominaDTO) {
        BigDecimal pension = nominaDTO.getSalario()
                .add(calcularPagoHorasExtras(nominaDTO)) // Incluir horas extras
                .add(nominaDTO.getComisiones());         // Incluir comisiones si existen

        // Calcular el aporte del empleado (4% del IBC)
        return pension.multiply(new BigDecimal("0.04"));
    }

    private BigDecimal calcularFondoSolidaridad(NominaDTO nominaDTO) {
        BigDecimal cuatroSMMLV = SALARIO_MINIMO.multiply(new BigDecimal("4"));
        BigDecimal dieciseisSMMLV = SALARIO_MINIMO.multiply(new BigDecimal("16"));
        BigDecimal diecisieteSMMLV = SALARIO_MINIMO.multiply(new BigDecimal("17"));
        BigDecimal dieciochoSMMLV = SALARIO_MINIMO.multiply(new BigDecimal("18"));
        BigDecimal diecinueveSMMLV = SALARIO_MINIMO.multiply(new BigDecimal("19"));
        BigDecimal veinteSMMLV = SALARIO_MINIMO.multiply(new BigDecimal("20"));

        BigDecimal porcentaje = BigDecimal.ZERO;

        if (nominaDTO.getPension().compareTo(cuatroSMMLV) >= 0 && nominaDTO.getPension().compareTo(dieciseisSMMLV) < 0) {
            porcentaje = new BigDecimal("0.01"); // 1.0%
        } else if (nominaDTO.getPension().compareTo(dieciseisSMMLV) >= 0 && nominaDTO.getPension().compareTo(diecisieteSMMLV) <= 0) {
            porcentaje = new BigDecimal("0.012"); // 1.2%
        } else if (nominaDTO.getPension().compareTo(diecisieteSMMLV) > 0 && nominaDTO.getPension().compareTo(dieciochoSMMLV) <= 0) {
            porcentaje = new BigDecimal("0.014"); // 1.4%
        } else if (nominaDTO.getPension().compareTo(dieciochoSMMLV) > 0 && nominaDTO.getPension().compareTo(diecinueveSMMLV) <= 0) {
            porcentaje = new BigDecimal("0.016"); // 1.6%
        } else if (nominaDTO.getPension().compareTo(diecinueveSMMLV) > 0 && nominaDTO.getPension().compareTo(veinteSMMLV) <= 0) {
            porcentaje = new BigDecimal("0.018"); // 1.8%
        } else if (nominaDTO.getPension().compareTo(veinteSMMLV) > 0) {
            porcentaje = new BigDecimal("0.02"); // 2.0%
        }

        return nominaDTO.getPension().multiply(porcentaje).setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calcularRetencionFuente(NominaDTO nominaDTO) {
        // Obtener salario base, horas extras y comisiones
        BigDecimal ingresoBruto = nominaDTO.getSalario()
                .add(calcularPagoHorasExtras(nominaDTO))
                .add(nominaDTO.getComisiones());

        // Restar deducciones (salud y pensión)
        BigDecimal deducciones = calcularSalud(nominaDTO).add(calcularPension(nominaDTO));
        BigDecimal ingresoBaseRetencion = ingresoBruto.subtract(deducciones);

        // Convertir IBR a UVT
        BigDecimal valorUVT = new BigDecimal("49799"); // UVT 2025
        BigDecimal ibrUVT = ingresoBaseRetencion.divide(valorUVT, 2, RoundingMode.HALF_UP);

        // Aplicar tabla de retención en la fuente
        BigDecimal retencionUVT = BigDecimal.ZERO;

        if (ibrUVT.compareTo(new BigDecimal("95")) > 0 && ibrUVT.compareTo(new BigDecimal("150")) <= 0) {
            retencionUVT = ibrUVT.subtract(new BigDecimal("95")).multiply(new BigDecimal("0.19"));
        } else if (ibrUVT.compareTo(new BigDecimal("150")) > 0 && ibrUVT.compareTo(new BigDecimal("360")) <= 0) {
            retencionUVT = ibrUVT.subtract(new BigDecimal("150")).multiply(new BigDecimal("0.28")).add(new BigDecimal("10"));
        } else if (ibrUVT.compareTo(new BigDecimal("360")) > 0 && ibrUVT.compareTo(new BigDecimal("640")) <= 0) {
            retencionUVT = ibrUVT.subtract(new BigDecimal("360")).multiply(new BigDecimal("0.33")).add(new BigDecimal("69"));
        } else if (ibrUVT.compareTo(new BigDecimal("640")) > 0 && ibrUVT.compareTo(new BigDecimal("945")) <= 0) {
            retencionUVT = ibrUVT.subtract(new BigDecimal("640")).multiply(new BigDecimal("0.35")).add(new BigDecimal("162"));
        } else if (ibrUVT.compareTo(new BigDecimal("945")) > 0 && ibrUVT.compareTo(new BigDecimal("2300")) <= 0) {
            retencionUVT = ibrUVT.subtract(new BigDecimal("945")).multiply(new BigDecimal("0.37")).add(new BigDecimal("268"));
        } else if (ibrUVT.compareTo(new BigDecimal("2300")) > 0) {
            retencionUVT = ibrUVT.subtract(new BigDecimal("2300")).multiply(new BigDecimal("0.39")).add(new BigDecimal("770"));
        }

        // Convertir retención en UVT a pesos
        return retencionUVT.multiply(valorUVT).setScale(2, RoundingMode.HALF_UP);
    }

    //VALIDACIONES PARA CARGA DE NOMINAS CON EXCEL
    private String obtenerString(Cell cell) {
        if (cell == null || cell.getCellType() == CellType.BLANK) {
            throw new IllegalArgumentException("El campo identificación está vacío");
        }
        return cell.getCellType() == CellType.STRING
                ? cell.getStringCellValue()
                : String.valueOf((long) cell.getNumericCellValue());
    }

    private LocalDate obtenerFecha(Cell cell) {
        if (cell == null || cell.getCellType() != CellType.NUMERIC || !DateUtil.isCellDateFormatted(cell)) {
            throw new IllegalArgumentException("El campo fecha es inválido o no tiene formato de fecha.");
        }
        return cell.getLocalDateTimeCellValue().toLocalDate();
    }

    private void validarIdentificacionYFecha(String identificacion, LocalDate fechaPago) {
        Persona persona = personaRepositorio.findByIdentificacion(identificacion)
                .orElseThrow(() -> new RuntimeException("Persona no encontrada para la identificación: " + identificacion));

        LocalDate fechaIngreso = persona.getFechaIngreso();
        if (fechaPago.isBefore(fechaIngreso)) {
            throw new IllegalArgumentException("No se puede liquidar la nómina antes de la fecha de ingreso (" + fechaIngreso + ").");
        }

        Optional<Nomina> ultimaNomina = nominaRepositorio.findTopByPersonalIdentificacionOrderByFechaPagoDesc(identificacion);

        if (ultimaNomina.isPresent()) {
            LocalDate fechaUltimoPago = ultimaNomina.get().getFechaPago();
            long diasDiferencia = ChronoUnit.DAYS.between(fechaUltimoPago, fechaPago);

            if (diasDiferencia < 23) {
                throw new IllegalArgumentException("No se puede liquidar la nómina: deben pasar al menos 23 días desde el último pago.");
            }

            if (diasDiferencia > 31) {
                throw new IllegalArgumentException("No se puede liquidar la nómina: el periodo supera los 31 días permitidos.");
            }
        }
    }

    private Long validarNumeroEnteroPositivo(Cell cell, String campo) {
        if (cell == null) return 0L;

        if (cell.getCellType() != CellType.NUMERIC) {
            throw new IllegalArgumentException("El campo " + campo + " debe ser numérico.");
        }

        double valor = cell.getNumericCellValue();
        if (valor < 0) {
            throw new IllegalArgumentException("El campo " + campo + " no puede ser negativo.");
        }

        return (long) valor;
    }

    private BigDecimal validarNumeroDecimalPositivo(Cell cell, String campo) {
        if (cell == null) return BigDecimal.ZERO;

        if (cell.getCellType() != CellType.NUMERIC) {
            throw new IllegalArgumentException("El campo " + campo + " debe ser numérico.");
        }

        double valor = cell.getNumericCellValue();
        if (valor < 0) {
            throw new IllegalArgumentException("El campo " + campo + " no puede ser negativo.");
        }

        return BigDecimal.valueOf(valor);
    }
}
