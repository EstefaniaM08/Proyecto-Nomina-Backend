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
    @Autowired
    private JavaMailSender mailSender;

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
    @Override
    public String  login(AdministradorDTO administradorDTO) {
        if (!valExisCorreo(administradorDTO.getEmail())) {
            throw new UsuarioNoEncontradoException("El correo no está registrado");
        }
        Administrador admin = administradorRepositorio.findByEmail(administradorDTO.getEmail());


        // Desencriptar y verificar la contraseña
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        if (!argon2.verify(admin.getPassword(), administradorDTO.getPassword())) {
            throw new ContraseñaIncorrectaException("Credenciales incorrectas");
        }
        // Si todo es correcto, retornar éxito
        return "Inicio de sesión exitoso";
    }
    @Override
    public String enviarCambioClave(String email) {
        //Valida si esta vacio
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("El correo no puede estar vacío");
        }
        //Validacion si correo existe
        if (!valExisCorreo(email)) {
            throw new UsuarioNoEncontradoException("El correo no está registrado");
        }
        // Generar un token único
        String token = UUID.randomUUID().toString();
        tokens.put(token,new TokenInfo(email, 10));

        // Generar el enlace de recuperación
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(email);
        mensaje.setSubject("Recuperación de Contraseña");
        mensaje.setText("Tu código de recuperación es: " + token + " Expira en 10 minutos.");

        try {
            mailSender.send(mensaje);
        } catch (MailException e) {
            throw new GeneralException("Error al enviar el correo: " + e.getMessage());
        }
        return "Enlace de recuperacion enviado";
    }
    @Override
    public String cambiarClave(String token, String nuevaClave) {

        if (token == null || token.trim().isEmpty()) {
            throw new IllegalArgumentException("El token no puede estar vacío");
        }
        if (!tokens.containsKey(token)) {
            throw new TokenInvalidoException("El token es inválido");
        }
        TokenInfo tokenInfo = tokens.get(token);

        // Validar si el token ha expirado
        if (tokenInfo.isExpired()) {
            tokens.remove(token);
            throw new TokenInvalidoException("El token ha expirado");
        }

        if (nuevaClave == null || nuevaClave.trim().isEmpty()) {
            throw new IllegalArgumentException("La nueva contraseña no puede estar vacía");
        }
        if (!nuevaClave.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$")) {
            throw new IllegalArgumentException("La contraseña no cumple con los requisitos de seguridad");
        }

        String email = tokenInfo.getEmail();
        Administrador admin = administradorRepositorio.findByEmail(email);
        if (admin == null) {
            throw new UsuarioNoEncontradoException("Usuario no encontrado");
        }

        // Hash de la contraseña con Argon2
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hash = argon2.hash(1, 1024, 1, nuevaClave);
        admin.setPassword(hash);
        //guardar clave
        administradorRepositorio.save(admin);
        tokens.remove(token); // Eliminar el token después de usarlo

        return "Contraseña cambiada con exito";
    }
}
