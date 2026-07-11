package com.eam.demo.config.security;

import com.eam.demo.persistenceLayer.repository.UsuarioRepository;
import com.eam.demo.persistenceLayer.entity.UsuarioEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsuarioEntity usuario = usuarioRepository.findByCorreo(username);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado con correo: " + username);
        }
        
        return new UserDetailsImpl(usuario);
    }
}
