package br.edu.utfpr.cp.espjava.crudcidades.dominio;

import br.edu.utfpr.cp.espjava.crudcidades.dominio.CidadeEntidade;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class Cidade {

    @NotBlank(message = "{app.cidade.blank}")
    @Size(min = 5, max = 60, message = "{app.cidade.size}")
    private final String nome;

    @NotBlank(message = "{app.estado.blank}")
    @Size(min = 2, max = 2, message = "{app.estado.size}")
    private final String estado;

    public Cidade(final String nome, final String estado) {
        this.nome = nome;
        this.estado = estado;
    }

    public String getNome() {
        return nome;
    }

    public String getEstado() {
        return estado;
    }

    public CidadeEntidade mapperTo(CidadeEntidade cidadeEntidade){
        cidadeEntidade.setNome(this.getNome());
        cidadeEntidade.setEstado(this.getEstado());
        return cidadeEntidade;
    }
}
