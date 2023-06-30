package com.example.reporteadorBackEnd.Security.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.reporteadorBackEnd.Security.Repository.UsuariosRepository;
import com.example.reporteadorBackEnd.Security.Usuarios.UsuariosEntity;

@Service
public class UserDetailServiceImp implements UserDetailsService{
    
    @Autowired
    private UsuariosRepository usuariosRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UsuariosEntity usuariosEntity = usuariosRepository
        .findOneByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("El usuario con emal " + email + " no existe"));
        
        return new UserDetailImp(usuariosEntity);
    }
}
