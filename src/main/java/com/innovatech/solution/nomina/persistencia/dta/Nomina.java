package com.innovatech.solution.nomina.persistencia.dta;


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
@Table(name = "pagos_nomina")
public class Nomina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "identificacion", referencedColumnName = "identificacion")
    private Persona personal;
    @Column(name ="fecha_pago", nullable = false)
    private LocalDate fechaPago;

    @Column(name = "subsidio_transporte")
    private BigDecimal subsidioTransporte;
    @Column(name = "prima")
    private BigDecimal prima;
    @Column(name = "cesantias")
    private BigDecimal cesantias;
    @Column(name = "vacaciones")
    private BigDecimal vacaciones;

    @Column(name = "comisiones")
    private BigDecimal comisiones;
    @Column(name = "viaticos")
    private BigDecimal viaticos;
    @Column(name = "gastosRepresentacion")
    private BigDecimal gastosRepresentacion;
    @Column(name = "horExtraDiu")
    private Long horExtraDiu;
    @Column(name = "horExtraNoc")
    private Long horExtraNoc;
    @Column(name = "horExtraDiuDomFes")
    private Long horExtraDiuDomFes;
    @Column(name = "horExtraNocDomFes")
    private Long horExtraNocDomFes;

    @Column(name = "salud")
    private BigDecimal salud;
    @Column(name = "pencion")
    private BigDecimal pencion;
    @Column(name = "retencionFuente")
    private BigDecimal retencionFuente;
    @Column(name = "fondoSolid")
    private BigDecimal fondoSolid;

    @Column(name = "totDescuetos")
    private BigDecimal totDescuetos;
    @Column(name = "totDevengados")
    private BigDecimal totDevengados;
    @Column(name = "pagoFinal")
    private BigDecimal pagoFinal;

}
