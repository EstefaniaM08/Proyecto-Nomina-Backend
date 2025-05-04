package com.innovatech.solution.nomina.utils;

import java.time.LocalDateTime;

public class TokenInfo {
    private String email;
    private LocalDateTime expirationTime;

    public TokenInfo(String email, int minutesToExpire) {
        this.email = email;
        this.expirationTime = LocalDateTime.now().plusMinutes(minutesToExpire);
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getExpirationTime() {
        return expirationTime;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expirationTime);
    }
}