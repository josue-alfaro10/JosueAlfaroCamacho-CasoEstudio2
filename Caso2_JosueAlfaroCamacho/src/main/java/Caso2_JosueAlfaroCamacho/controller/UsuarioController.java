/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Caso2_JosueAlfaroCamacho.controller;

import Caso2_JosueAlfaroCamacho.domain.Usuario;
import Caso2_JosueAlfaroCamacho.service.RolService;
import Caso2_JosueAlfaroCamacho.service.UsuarioService;
import org.springframework.ui.Model;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Alfaro
 */
@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final RolService rolService;

    public UsuarioController(UsuarioService usuarioService, RolService rolService) {
        this.usuarioService = usuarioService;
        this.rolService = rolService;
    }

    @GetMapping("/listado")
    public String listar(Model modelo) {
        modelo.addAttribute("usuarios", usuarioService.listarUsuarios());
        return "usuario/listado";
    }

    @GetMapping("/nuevo")
    public String formularioNuevo(Model modelo) {
        modelo.addAttribute("usuario", new Usuario());
        modelo.addAttribute("roles", rolService.listarRoles());
        return "usuario/formulario";
    }

    @GetMapping("/editar/{id}")
    public String formularioEditar(@PathVariable Long id, Model modelo) {
        usuarioService.buscarPorId(id).ifPresent(u -> modelo.addAttribute("usuario", u));
        modelo.addAttribute("roles", rolService.listarRoles());
        return "usuario/formulario";
    }

    @GetMapping("/detalle/{id}")
    public String verDetalle(@PathVariable Long id, Model modelo) {
        usuarioService.buscarPorId(id).ifPresent(u -> modelo.addAttribute("usuario", u));
        return "usuario/detalle";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Usuario usuario, RedirectAttributes flash) {
        try {
            boolean esNuevo = (usuario.getId() == null);
            usuarioService.guardar(usuario, esNuevo);
            flash.addFlashAttribute("mensaje", "Usuario guardado correctamente.");
        } catch (Exception e) {
            flash.addFlashAttribute("error", "Error al guardar: " + e.getMessage());
        }
        return "redirect:/usuario/listado";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes flash) {
        usuarioService.eliminar(id);
        flash.addFlashAttribute("mensaje", "Usuario eliminado.");
        return "redirect:/usuario/listado";
    }
}
