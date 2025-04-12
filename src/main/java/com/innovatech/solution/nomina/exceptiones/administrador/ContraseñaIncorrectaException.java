package com.innovatech.solution.nomina.exceptiones.administrador;

public class ContraseñaIncorrectaException extends RuntimeException {
    public ContraseñaIncorrectaException(String message) {
        super(message);
    }
}