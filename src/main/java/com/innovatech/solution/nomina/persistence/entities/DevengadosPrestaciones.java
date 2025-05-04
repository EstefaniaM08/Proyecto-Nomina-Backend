package com.innovatech.solution.nomina.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "devengados_prestaciones", uniqueConstraints = @UniqueConstraint(columnNames = "identificacion"))
public class DevengadosPrestaciones {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String identificacion;

    @Column(name = "fec_pago_prima")
    private LocalDate fecPagoPrima;

    @Column(name = "salarios_devengados_prima")
    private BigDecimal salariosDevengadosPrima;

    @Column(name = "fec_pago_cesantias")
    private LocalDate fecPagoCesantias;

    @Column(name = "salarios_devengados_cesantias")
    private BigDecimal salariosDevengadosCesantias;

    @Column(name = "fec_pago_vacaciones")
    private LocalDate fecPagoVacaciones;

    @Column(name = "salarios_devengados_vacaciones")
    private BigDecimal salariosDevengadosVacaciones;
}
