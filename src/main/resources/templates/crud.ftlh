<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>CRUD Cidades</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
    </head>
<body>

    <nav class="navbar navbar-expand-sm bg-dark">
        <span class="navbar-brand text-white">${Session.usuarioAtual}</span>
        <ul class="navbar-nav ml-auto">
            <li class="nav-item">
                <a href="/logout" class="nav-link btn-secondary">Sair da aplicação</a>
            </li>
        </ul>
    </nav>

    <div class="container-fluid">
        <div class="jumbotron mt-5">
            <h1>GERENCIAMENTO DE CIDADES</h1>
            <p>Um CRUD para Criar, Alterar, Excluir e Listar Cidades</p>
        </div>

        <#if cidadeSelecionada??>
            <form action="/alterar" method="post" class="needs-validation" novalidate>
                <input type="hidden" name="nomeSelecionado" value="${(cidadeSelecionada.nome)!}" />
                <input type="hidden" name="estadoSelecionado" value="${(cidadeSelecionada.estado)!}"/>
        <#else>
                <form action="/criar" method="post" class="needs-validation" novalidate>
        </#if>

            <div class="form-group">
                <label for="nome">Cidades:</label>
                <input type="text" value="${(cidadeSelecionada.nome)!} ${nomeInformado!}" name="nome"  class="form-control ${(nome??)?then('is-invalid','')}" placeholder="Informe o nome da cidade" id="id-nome">
                <div class="invalid-feedback">
                    ${nome!}
                </div>
            </div>
            <div class="form-group">
                <label for="estado">Estado:</label>
                <input type="text" value="${(cidadeSelecionada.estado)!} ${(estadoInformado)!}" name="estado"  class="form-control ${(estado??)?then('is-invalid','')}" placeholder="Informe o estado que a Cidade pertence" id="id-estado">
                <div class="invalid-feedback">
                    ${estado!}
                </div>
            </div>

            <#if cidadeSelecionada??>
                <button type="submit" class="btn btn-warning">Concluir Alteração</button>
            <#else>
                <button type="submit" class="btn btn-primary">Criar</button>
            </#if>

        </form>
        <table class="table table-striped table-houver mt-5">
            <thead class="thead-dark">
                <tr>
                    <th>Nome</th>
                    <th>Estado</th>
                    <th>Ações</th>
                </tr>
            </thead>
            <tbody>
            <#list listaCidades as cidade>
                <tr>
                    <td>${cidade.nome}</td>
                    <td>${cidade.estado}</td>
                    <td>
                        <div class="d-flex d-justify-content-center">
                            <a href="/prepararAlterar?nome=${cidade.nome}&estado=${cidade.estado}" class="btn btn-warning mr-3">ALTERAR</a>
                            <a href="/excluir?nome=${cidade.nome}&estado=${cidade.estado}" class="btn btn-danger mr-3">EXCLUIR</a>
                        </div>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
</body>
</html>