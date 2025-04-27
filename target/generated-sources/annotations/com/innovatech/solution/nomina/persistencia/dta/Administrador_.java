package com.innovatech.solution.nomina.persistencia.dta;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Administrador.class)
public abstract class Administrador_ {

	public static volatile SingularAttribute<Administrador, String> password;
	public static volatile SingularAttribute<Administrador, Long> id;
	public static volatile SingularAttribute<Administrador, String> email;

	public static final String PASSWORD = "password";
	public static final String ID = "id";
	public static final String EMAIL = "email";

}

