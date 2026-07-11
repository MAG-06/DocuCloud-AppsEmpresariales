package com.eam.demo.config.security;

import com.eam.demo.persistenceLayer.entity.UsuarioEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UserDetailsImpl implements UserDetails {

    private final UsuarioEntity usuario;

    public UserDetailsImpl(UsuarioEntity usuario) {
        this.usuario = usuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (usuario.getRol() != null && usuario.getRol().getNombre() != null) {
            String roleName = usuario.getRol().getNombre().toUpperCase();
            if (!roleName.startsWith("ROLE_")) {
                roleName = "ROLE_" + roleName;
            }
            return Collections.singletonList(new SimpleGrantedAuthority(roleName));
        }
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return usuario.getContrasena();
    }

    @Override
    public String getUsername() {
        return usuario.getCorreo();
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
        return usuario.getEstado() != null ? usuario.getEstado() : false;
    }
    
    public UsuarioEntity getUsuario() {
        return usuario;
    }
}
