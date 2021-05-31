package br.edu.utfpr.cp.espjava.crudcidades.controller;

import br.edu.utfpr.cp.espjava.crudcidades.dominio.Cidade;
import br.edu.utfpr.cp.espjava.crudcidades.repository.CidadeRepository;
import br.edu.utfpr.cp.espjava.crudcidades.dominio.CidadeEntidade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class CidadeController {

    private Set<Cidade> cidades;

    @Autowired
    private CidadeRepository cidadeRepository;

    public CidadeController() {
        this.cidades = new HashSet<>();
    }

    @GetMapping("/")
    public String listar(Model memoria, Principal usuario, HttpSession sessao, HttpServletResponse response){

        response.addCookie(new Cookie("listar", LocalDateTime.now().toString()));

        memoria.addAttribute("listaCidades", cidadeRepository
                                                .findAll()
                                                .stream()
                                                 .map(cidade -> new Cidade(
                                                         cidade.getNome(),
                                                         cidade.getEstado()))
                                                 .collect(Collectors.toList()));
        sessao.setAttribute("usuarioAtual", usuario.getName());
        return "/crud";
    }

    @PostMapping("/criar")
    public String criar(@Valid Cidade cidade, BindingResult validacao, Model memoria, HttpServletResponse response){

        response.addCookie(new Cookie("criar", LocalDateTime.now().toString()));

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
            cidadeRepository.save(cidade.mapperTo(new CidadeEntidade()));
        }

        return "redirect:/";
    }

    @GetMapping("/excluir")
    public String excluir(@RequestParam String nome,
                          @RequestParam String estado,
                          HttpServletResponse response){

        response.addCookie(new Cookie("excluir", LocalDateTime.now().toString()));

        var cidadeEncontrada = cidadeRepository.findByNomeAndEstado(nome,estado);
        cidadeEncontrada.ifPresent(cidadeRepository::delete);
        return "redirect:/";
    }

    @GetMapping("/prepararAlterar")
    public String prepararAlterar(@RequestParam String nome,
                                  @RequestParam String estado,
                                  Model memoria){
        var cidadeSelecionada = cidadeRepository.findByNomeAndEstado(nome,estado);

        cidadeSelecionada.ifPresent(cidadeEncontrada -> {
            memoria.addAttribute("cidadeSelecionada", cidadeEncontrada);
            memoria.addAttribute("listaCidades", cidadeRepository.findAll());
        });
        return "/crud";
    }

    @PostMapping("/alterar")
    public String alterar(
            @RequestParam String nomeSelecionado,
            @RequestParam String estadoSelecionado,
            Cidade cidade,
            HttpServletResponse response){

        response.addCookie(new Cookie("alterar", LocalDateTime.now().toString()));

           var cidadeSelecionada = cidadeRepository.findByNomeAndEstado(nomeSelecionado, estadoSelecionado);

           if(cidadeSelecionada.isPresent()){
               var cidadeEncontrada = cidadeSelecionada.get();
               cidadeEncontrada.setNome(cidade.getNome());
               cidadeEncontrada.setEstado(cidade.getEstado());
               cidadeRepository.saveAndFlush(cidadeEncontrada);
           }

           return "redirect:/";
    }

    @GetMapping("/mostrar")
    @ResponseBody
    public String mostrarCookieAlterar(@CookieValue String listar){
        return "último acesso ao método listar(): " + listar;
    }

}
