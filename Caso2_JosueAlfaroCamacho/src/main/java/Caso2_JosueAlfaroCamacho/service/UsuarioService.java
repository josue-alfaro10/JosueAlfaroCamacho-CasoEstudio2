/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Caso2_JosueAlfaroCamacho.service;

import Caso2_JosueAlfaroCamacho.domain.Rol;
import Caso2_JosueAlfaroCamacho.domain.Usuario;
import Caso2_JosueAlfaroCamacho.repository.UsuarioRepository;
import jakarta.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author Alfaro
 */
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final CorreoService correoService;

    public UsuarioService(UsuarioRepository usuarioRepository,
                          PasswordEncoder passwordEncoder,
                          CorreoService correoService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.correoService = correoService;
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public List<Usuario> buscarPorRol(Rol rol) {
        return usuarioRepository.findByRol(rol);
    }

    private void enviarCorreoBienvenida(Usuario usuario) throws MessagingException {
        String asunto = "Bienvenido a la Plataforma de Eventos";
        String servidor = null;
        String contenido = "<h2>Buenas " + usuario.getNombre() + "!</h2>"
                + "<p>Tu cuenta ha sido creada de forma exitosa.</p>"
                + "<p>Tu correo es: <strong>" + usuario.getEmail() + "</strong></p>"
                + "<p>Ingresa en: <a href='http://" + servidor + "'>http://" + servidor + "</a></p>";
        correoService.enviarCorreoHtml(usuario.getEmail(), asunto, contenido);
    }
    
     public void guardar(Usuario usuario, boolean esNuevo) throws MessagingException {
        if (esNuevo) {
            usuario.setFechaCreacion(LocalDateTime.now());
            String claveEncriptada = passwordEncoder.encode(usuario.getPassword());
            usuario.setPassword(claveEncriptada);
            usuario.setActivo(true);
            usuarioRepository.save(usuario);
            correoService.enviarCorreoHtml(claveEncriptada, claveEncriptada, claveEncriptada);
        } else {
            usuarioRepository.save(usuario);
        }
    }

    public void eliminar(Long id) {
        usuarioRepository.deleteById(id);
    }
}
