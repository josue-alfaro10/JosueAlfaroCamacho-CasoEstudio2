/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Caso2_JosueAlfaroCamacho.controller;

import Caso2_JosueAlfaroCamacho.service.EventoService;
import Caso2_JosueAlfaroCamacho.service.RolService;
import Caso2_JosueAlfaroCamacho.service.UsuarioService;
import org.springframework.ui.Model;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Alfaro
 */
@Controller
@RequestMapping("/consultas")
public class ConsultaController {

    private final EventoService eventoService;
    private final UsuarioService usuarioService;
    private final RolService rolService;

    public ConsultaController(EventoService eventoService,
            UsuarioService usuarioService,
            RolService rolService) {
        this.eventoService = eventoService;
        this.usuarioService = usuarioService;
        this.rolService = rolService;
    }

    @GetMapping
    public String panelConsultas(Model modelo) {
        modelo.addAttribute("roles", rolService.listarRoles());
        modelo.addAttribute("totalActivos", eventoService.contarActivos());
        return "consultas/panel";
    }

    @GetMapping("/por-estado")
    public String buscarPorEstado(@RequestParam boolean activo, Model modelo) {
        modelo.addAttribute("resultados", eventoService.buscarPorEstado(activo));
        modelo.addAttribute("roles", rolService.listarRoles());
        modelo.addAttribute("totalActivos", eventoService.contarActivos());
        return "consultas/panel";
    }

    @GetMapping("/por-fechas")
    public String buscarPorFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin,
            Model modelo) {
        modelo.addAttribute("resultados", eventoService.buscarPorRangoFechas(inicio, fin));
        modelo.addAttribute("roles", rolService.listarRoles());
        modelo.addAttribute("totalActivos", eventoService.contarActivos());
        return "consultas/panel";
    }

    @GetMapping("/por-nombre")
    public String buscarPorNombre(@RequestParam String nombre, Model modelo) {
        modelo.addAttribute("resultados", eventoService.buscarPorNombre(nombre));
        modelo.addAttribute("roles", rolService.listarRoles());
        modelo.addAttribute("totalActivos", eventoService.contarActivos());
        return "consultas/panel";
    }

    @GetMapping("/usuarios-por-rol")
    public String usuariosPorRol(@RequestParam Long rolId, Model modelo) {
        rolService.buscarPorId(rolId).ifPresent(rol -> {
            modelo.addAttribute("usuariosResultado", usuarioService.buscarPorRol(rol));
            modelo.addAttribute("rolSeleccionado", rol.getNombre());
        });
        modelo.addAttribute("roles", rolService.listarRoles());
        modelo.addAttribute("totalActivos", eventoService.contarActivos());
        return "consultas/panel";
    }
}
