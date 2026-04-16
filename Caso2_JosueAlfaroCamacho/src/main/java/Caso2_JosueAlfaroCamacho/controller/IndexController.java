/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Caso2_JosueAlfaroCamacho.controller;

import Caso2_JosueAlfaroCamacho.service.EventoService;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author Alfaro
 */
@Controller
public class IndexController {

    private final EventoService eventoService;

    public IndexController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @GetMapping("/")
    public String index(Model modelo) {
        modelo.addAttribute("eventos", eventoService.listarEventos());
        modelo.addAttribute("totalActivos", eventoService.contarActivos());
        return "index";
    }
}
