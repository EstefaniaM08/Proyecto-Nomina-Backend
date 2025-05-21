package com.innovatech.solution.nomina.persistence.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Nomina.class)
public abstract class Nomina_ {

	public static volatile SingularAttribute<Nomina, BigDecimal> cesantias;
	public static volatile SingularAttribute<Nomina, BigDecimal> gastosRepresentacion;
	public static volatile SingularAttribute<Nomina, Long> horExtraNoc;
	public static volatile SingularAttribute<Nomina, BigDecimal> prima;
	public static volatile SingularAttribute<Nomina, BigDecimal> totValHorExtra;
	public static volatile SingularAttribute<Nomina, BigDecimal> pagoFinal;
	public static volatile SingularAttribute<Nomina, Persona> personal;
	public static volatile SingularAttribute<Nomina, LocalDate> fechaPago;
	public static volatile SingularAttribute<Nomina, BigDecimal> pencion;
	public static volatile SingularAttribute<Nomina, BigDecimal> totDescuetos;
	public static volatile SingularAttribute<Nomina, BigDecimal> salud;
	public static volatile SingularAttribute<Nomina, Long> horExtraDiuDomFes;
	public static volatile SingularAttribute<Nomina, BigDecimal> comisiones;
	public static volatile SingularAttribute<Nomina, BigDecimal> retencionFuente;
	public static volatile SingularAttribute<Nomina, BigDecimal> fondoSolid;
	public static volatile SingularAttribute<Nomina, BigDecimal> viaticos;
	public static volatile SingularAttribute<Nomina, Long> horExtraDiu;
	public static volatile SingularAttribute<Nomina, BigDecimal> vacaciones;
	public static volatile SingularAttribute<Nomina, Long> id;
	public static volatile SingularAttribute<Nomina, BigDecimal> subsidioTransporte;
	public static volatile SingularAttribute<Nomina, Long> horExtraNocDomFes;
	public static volatile SingularAttribute<Nomina, BigDecimal> totDevengados;

	public static final String CESANTIAS = "cesantias";
	public static final String GASTOS_REPRESENTACION = "gastosRepresentacion";
	public static final String HOR_EXTRA_NOC = "horExtraNoc";
	public static final String PRIMA = "prima";
	public static final String TOT_VAL_HOR_EXTRA = "totValHorExtra";
	public static final String PAGO_FINAL = "pagoFinal";
	public static final String PERSONAL = "personal";
	public static final String FECHA_PAGO = "fechaPago";
	public static final String PENCION = "pencion";
	public static final String TOT_DESCUETOS = "totDescuetos";
	public static final String SALUD = "salud";
	public static final String HOR_EXTRA_DIU_DOM_FES = "horExtraDiuDomFes";
	public static final String COMISIONES = "comisiones";
	public static final String RETENCION_FUENTE = "retencionFuente";
	public static final String FONDO_SOLID = "fondoSolid";
	public static final String VIATICOS = "viaticos";
	public static final String HOR_EXTRA_DIU = "horExtraDiu";
	public static final String VACACIONES = "vacaciones";
	public static final String ID = "id";
	public static final String SUBSIDIO_TRANSPORTE = "subsidioTransporte";
	public static final String HOR_EXTRA_NOC_DOM_FES = "horExtraNocDomFes";
	public static final String TOT_DEVENGADOS = "totDevengados";

}

