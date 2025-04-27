package com.innovatech.solution.nomina.servicios;

import com.innovatech.solution.nomina.persistencia.dto.AdministradorDTO;

public interface AdministradorServicios {

    String registrar(AdministradorDTO usuario);
    boolean valExisCorreo(String correo);
    String login(AdministradorDTO administrador);
    String enviarCambioClave(String email);
    String cambiarClave(String token, String nuevaClave);
}
