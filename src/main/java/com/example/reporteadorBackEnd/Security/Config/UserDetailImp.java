package com.example.reporteadorBackEnd.Security.Config;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.reporteadorBackEnd.Security.Usuarios.UsuariosEntity;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserDetailImp implements UserDetails{

    private final UsuariosEntity usuariosEntity;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    /* @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(role.toString()));
        return roles;
    } */

    @Override
    public String getPassword() {
        return usuariosEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return usuariosEntity.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    
    public String getNombre(){
        return usuariosEntity.getNombre();
    }

    public UsuariosEntity getUser() {
        return usuariosEntity;
    }
}
