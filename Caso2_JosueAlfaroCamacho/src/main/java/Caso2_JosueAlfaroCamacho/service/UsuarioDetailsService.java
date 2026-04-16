/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Caso2_JosueAlfaroCamacho.service;

import Caso2_JosueAlfaroCamacho.domain.Usuario;
import Caso2_JosueAlfaroCamacho.repository.UsuarioRepository;
import java.util.List;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Alfaro
 */
@Service
public class UsuarioDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final Logger logger = LoggerFactory.getLogger(UsuarioDetailsService.class);

    public UsuarioDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));

        String rol = "ROLE_" + usuario.getRol().getNombre().toUpperCase();
        boolean enabled = usuario.isActivo();
        logger.debug("Cargando usuario '{}' activo={} rol={}", usuario.getEmail(), enabled, rol);

        return User.builder()
            .username(usuario.getEmail())
            .password(usuario.getPassword())
            .authorities(new SimpleGrantedAuthority(rol))
            .disabled(!enabled)
            .build();
    }
}