package com.eam.demo.config;

import com.eam.demo.persistenceLayer.entity.UsuarioEntity;
import com.eam.demo.persistenceLayer.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class PasswordMigrationRunner implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        log.info("Iniciando migración de contraseñas si es necesario...");
        List<UsuarioEntity> usuarios = usuarioRepository.findAll();
        
        int actualizados = 0;
        for (UsuarioEntity usuario : usuarios) {
            String pass = usuario.getContrasena();
            if (pass != null && !pass.startsWith("$2a$")) {
                // No está encriptada con BCrypt
                usuario.setContrasena(passwordEncoder.encode(pass));
                usuarioRepository.save(usuario);
                actualizados++;
            }
        }
        
        if (actualizados > 0) {
            log.info("Se actualizaron {} contraseñas a BCrypt.", actualizados);
        } else {
            log.info("No fue necesario actualizar contraseñas.");
        }
    }
}
