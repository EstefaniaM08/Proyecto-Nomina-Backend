package com.innovatech.solution.nomina.services.impl;

import com.innovatech.solution.nomina.dto.AdministradorDTO;
import com.innovatech.solution.nomina.exceptiones.administrador.ContraseñaIncorrectaException;
import com.innovatech.solution.nomina.exceptiones.administrador.GeneralException;
import com.innovatech.solution.nomina.exceptiones.administrador.TokenInvalidoException;
import com.innovatech.solution.nomina.exceptiones.administrador.UsuarioNoEncontradoException;
import com.innovatech.solution.nomina.persistence.entities.Administrador;
import com.innovatech.solution.nomina.persistence.repositories.AdministradorRepositorio;
import com.innovatech.solution.nomina.services.AdministradorServicios;
import com.innovatech.solution.nomina.utils.TokenInfo;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Repository
@Transactional
public class AdministradorServiciosImp implements AdministradorServicios {
    @Autowired
    AdministradorRepositorio administradorRepositorio;

    private Map<String, TokenInfo> tokens = new HashMap<>();

    @Override
    public String registrar(AdministradorDTO administradorDTO) {
        try {
            // Validar si el usuario ya existe
            if (valExisCorreo(administradorDTO.getEmail())) {
                throw new UsuarioNoEncontradoException("El correo ya esta está registrado");
            }

            // Hash de la contraseña con Argon2
            Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
            String hash = argon2.hash(1, 1024, 1, administradorDTO.getPassword());
            administradorDTO.setPassword(hash);

            // Crear objeto administrador y guardar en la base de datos
            Administrador admin = Administrador.builder()
                    .email(administradorDTO.getEmail())
                    .password(administradorDTO.getPassword())
                    .build();

            administradorRepositorio.save(admin);

            return "Usuario registrado con éxito";

        } catch (Exception e) {
            throw new GeneralException("Error interno del servidor");
        }
    }
    @Override
    public boolean valExisCorreo(String correo) {
        boolean existe = administradorRepositorio.existsByEmail(correo.trim().toLowerCase());
        return existe;
    }
}
