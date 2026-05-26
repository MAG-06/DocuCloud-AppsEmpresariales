package com.eam.demo.persistenceLayer.controller;

import com.eam.demo.bussinesLayer.dto.LoginRequestDTO;
import com.eam.demo.bussinesLayer.dto.LoginResponseDTO;
import com.eam.demo.bussinesLayer.dto.UsuarioDTO;
import com.eam.demo.bussinesLayer.service.UsuarioService;
import com.eam.demo.config.security.JwtUtil;
import com.eam.demo.config.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Autenticación", description = "Operaciones de inicio de sesión")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UsuarioService usuarioService;

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request) {
        log.info("Intento de login para usuario: {}", request.getCorreo());
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getCorreo(), request.getContrasena())
            );

            UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
            
            if (!userDetails.getUsuario().getEstado()) {
                log.warn("Usuario inactivo intentó iniciar sesión: {}", request.getCorreo());
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Usuario inactivo.");
            }

            String token = jwtUtil.generateToken(userDetails);
            UsuarioDTO usuarioDTO = usuarioService.getUsuarioByCorreo(request.getCorreo());

            LoginResponseDTO response = new LoginResponseDTO(token, usuarioDTO);
            return ResponseEntity.ok(response);

        } catch (BadCredentialsException e) {
            log.warn("Credenciales inválidas para usuario: {}", request.getCorreo());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas.");
        } catch (Exception e) {
            log.error("Error durante el login para {}: {}", request.getCorreo(), e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor.");
        }
    }
}
