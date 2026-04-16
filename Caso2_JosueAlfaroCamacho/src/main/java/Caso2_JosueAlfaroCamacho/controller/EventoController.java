/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Caso2_JosueAlfaroCamacho.controller;

import Caso2_JosueAlfaroCamacho.domain.Evento;
import Caso2_JosueAlfaroCamacho.service.EventoService;
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
@RequestMapping("/evento")
public class EventoController {

    private final EventoService eventoService;

    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @GetMapping("/listado")
    public String listar(Model modelo) {
        modelo.addAttribute("eventos", eventoService.listarEventos());
        return "evento/listado";
    }

    @GetMapping("/nuevo")
    public String formularioNuevo(Model modelo) {
        modelo.addAttribute("evento", new Evento());
        return "evento/formulario";
    }

    @GetMapping("/editar/{id}")
    public String formularioEditar(@PathVariable Long id, Model modelo) {
        eventoService.buscarPorId(id).ifPresent(e -> modelo.addAttribute("evento", e));
        return "evento/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Evento evento, RedirectAttributes flash) {
        eventoService.guardar(evento);
        flash.addFlashAttribute("mensaje", "Evento guardado correctamente.");
        return "redirect:/evento/listado";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes flash) {
        eventoService.eliminar(id);
        flash.addFlashAttribute("mensaje", "Evento eliminado.");
        return "redirect:/evento/listado";
    }
}
