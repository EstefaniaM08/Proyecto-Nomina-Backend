package com.innovatech.solution.nomina.persistence.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(DevengadosPrestaciones.class)
public abstract class DevengadosPrestaciones_ {

	public static volatile SingularAttribute<DevengadosPrestaciones, LocalDate> fecPagoCesantias;
	public static volatile SingularAttribute<DevengadosPrestaciones, BigDecimal> salariosDevengadosCesantias;
	public static volatile SingularAttribute<DevengadosPrestaciones, LocalDate> fecPagoPrima;
	public static volatile SingularAttribute<DevengadosPrestaciones, String> identificacion;
	public static volatile SingularAttribute<DevengadosPrestaciones, BigDecimal> salariosDevengadosPrima;
	public static volatile SingularAttribute<DevengadosPrestaciones, Long> id;
	public static volatile SingularAttribute<DevengadosPrestaciones, LocalDate> fecPagoVacaciones;
	public static volatile SingularAttribute<DevengadosPrestaciones, BigDecimal> salariosDevengadosVacaciones;

	public static final String FEC_PAGO_CESANTIAS = "fecPagoCesantias";
	public static final String SALARIOS_DEVENGADOS_CESANTIAS = "salariosDevengadosCesantias";
	public static final String FEC_PAGO_PRIMA = "fecPagoPrima";
	public static final String IDENTIFICACION = "identificacion";
	public static final String SALARIOS_DEVENGADOS_PRIMA = "salariosDevengadosPrima";
	public static final String ID = "id";
	public static final String FEC_PAGO_VACACIONES = "fecPagoVacaciones";
	public static final String SALARIOS_DEVENGADOS_VACACIONES = "salariosDevengadosVacaciones";

}

