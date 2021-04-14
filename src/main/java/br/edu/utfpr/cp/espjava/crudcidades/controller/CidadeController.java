package br.edu.utfpr.cp.espjava.crudcidades.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Set;

@Controller
public class CidadeController {

    @GetMapping("/")
    public String listar(Model memoria){

        var cidades = Set.of(
          new Cidade("Cascavel", "PR"),
          new Cidade("Porto Alegre", "RS"),
          new Cidade("Florianopolis", "SC")
        );

        memoria.addAttribute("listaCidades", cidades);

        return "crud";
    }
}
