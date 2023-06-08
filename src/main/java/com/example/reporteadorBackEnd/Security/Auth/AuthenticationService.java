package com.example.reporteadorBackEnd.Security.Auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.reporteadorBackEnd.Security.Config.JwtService;
import com.example.reporteadorBackEnd.Security.Entity.Role;
import com.example.reporteadorBackEnd.Security.Entity.UsuariosEntity;
import com.example.reporteadorBackEnd.Security.Repository.UsuariosRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UsuariosRepository usuarioRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;
    
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var usuarios = UsuariosEntity.builder()
            .nombre(request.getNombre())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(Role.ADMIN)
            .build();

        usuarioRepository.save(usuarios);
        var jwtToken = jwtService.generateToken(usuarios);

        return AuthenticationResponse.builder()
            .token(jwtToken)
            .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
            )
        );
        var usuarios = usuarioRepository.findOneByEmail(request.getEmail())
            .orElseThrow();
    
        var jwt = jwtService.generateToken(usuarios);

        return AuthenticationResponse.builder()
            .token(jwt)
            .build();
    }

}