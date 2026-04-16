/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Caso2_JosueAlfaroCamacho.controller;

import Caso2_JosueAlfaroCamacho.domain.Rol;
import Caso2_JosueAlfaroCamacho.service.RolService;
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
@RequestMapping("/rol")
public class RolController {

    private final RolService rolService;

    public RolController(RolService rolService) {
        this.rolService = rolService;
    }

    @GetMapping("/listado")
    public String listar(Model modelo) {
        modelo.addAttribute("roles", rolService.listarRoles());
        return "rol/listado";
    }

    @GetMapping("/nuevo")
    public String formularioNuevo(Model modelo) {
        modelo.addAttribute("rol", new Rol());
        return "rol/formulario";
    }

    @GetMapping("/editar/{id}")
    public String formularioEditar(@PathVariable Long id, Model modelo) {
        rolService.buscarPorId(id).ifPresent(r -> modelo.addAttribute("rol", r));
        return "rol/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Rol rol, RedirectAttributes flash) {
        rolService.guardar(rol);
        flash.addFlashAttribute("mensaje", "Rol guardado correctamente.");
        return "redirect:/rol/listado";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes flash) {
        rolService.eliminar(id);
        flash.addFlashAttribute("mensaje", "Rol eliminado.");
        return "redirect:/rol/listado";
    }
}
