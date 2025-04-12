package com.innovatech.solution.nomina.exceptiones.administrador;

public class UsuarioNoEncontradoException extends RuntimeException {
    public UsuarioNoEncontradoException(String message) {
        super(message);
    }
}