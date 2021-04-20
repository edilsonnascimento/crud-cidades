package br.edu.utfpr.cp.espjava.crudcidades.controller;

import br.edu.utfpr.cp.espjava.crudcidades.Cidade;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
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
    public String criar(@Valid Cidade cidade, BindingResult validacao, Model memoria){

        if(validacao.hasErrors()){
            validacao
                    .getFieldErrors()
                    .forEach(error ->
                               memoria.addAttribute(error.getField(), error.getDefaultMessage())
                            );
            memoria.addAttribute("nomeInformado", cidade.getNome());
            memoria.addAttribute("estadoInformado", cidade.getEstado());
            memoria.addAttribute("listaCidades", cidades);
            return ("/crud");
        }else {
            cidades.add(cidade);
        }

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

           criar(cidade, null, null);

           return "redirect:/";
    }

}
