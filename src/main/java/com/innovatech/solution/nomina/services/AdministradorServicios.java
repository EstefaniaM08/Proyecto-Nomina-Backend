package com.innovatech.solution.nomina.services;

import com.innovatech.solution.nomina.dto.AdministradorDTO;
public interface AdministradorServicios {

    String registrar(AdministradorDTO usuario);
    boolean valExisCorreo(String correo);
    String login(AdministradorDTO administrador);
}
