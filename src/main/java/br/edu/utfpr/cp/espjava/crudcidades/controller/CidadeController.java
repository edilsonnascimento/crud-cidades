package br.edu.utfpr.cp.espjava.crudcidades.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;
import java.util.Set;

@Controller
public class CidadeController {

    private Set<Cidade> cidades;


    public CidadeController() {
        this.cidades = new HashSet<>();
    }

    @GetMapping("/")
    public String listar(Model memoria){
        memoria.addAttribute("listaCidades", cidades);
        return "crud";
    }

    @PostMapping("/criar")
    public String criar(Cidade cidade){
        cidades.add(cidade);
        return "redirect:/";
    }

    @GetMapping("/excluir")
    public String excluir(@RequestParam String nome,
                          @RequestParam String estado){

        cidades.removeIf(cidadeSelecionada ->
                cidadeSelecionada.getNome().equals(nome) &&
                cidadeSelecionada.getEstado().equals(estado));

        return "redirect:/";
    }

    @GetMapping("/prepararAlterar")
    public String prepararAlterar(@RequestParam String nome,
                                  @RequestParam String estado,
                                  Model memoria){
        var cidadeSelecionada = cidades
                .parallelStream()
                .filter(cidade ->
                        cidade.getNome().equals(nome) &&
                        cidade.getEstado().equals(estado))
                .findAny();
        if(cidadeSelecionada.isPresent()){
            memoria.addAttribute("cidadeSelecionada", cidadeSelecionada.get());
            memoria.addAttribute("listaCidades", cidades);
        }
        return "crud";
    }

    @PostMapping("/alterar")
    public String alterar(
            @RequestParam String nomeSelecionado,
            @RequestParam String estadoSelecionado,
            Cidade cidade){

           cidades.removeIf(cidadeSelecionada ->
                cidadeSelecionada.getNome().equals(nomeSelecionado) &&
                cidadeSelecionada.getEstado().equals(estadoSelecionado));

           criar(cidade);

           return "redirect:/";
    }

}
