package com.example.reporteadorBackEnd.Security.Auth;

import lombok.Data;

@Data
public class AuthCredentials {
    private String email;
    private String password;
}