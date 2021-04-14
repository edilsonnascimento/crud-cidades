<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>CRUD Cidades</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
</head>
<body>
    <div class="container-fluid">
        <div class="jumbotron mt-5">
            <h1>GERENCIAMENTO DE CIDADES</h1>
            <p>Um CRUD para Criar, Alterar, Excluir e Listar Cidades</p>
        </div>

        <form>
            <div class="form-group">
                <label for="nome">Cidades:</label>
                <input type="text" class="form-control" placeholder="Informe o nome da cidade" id="id-nome">
            </div>
            <div class="form-group">
                <label for="estado">Estado:</label>
                <input type="text" class="form-control" placeholder="Informe o estado que a Cidade pertence" id="id-estado">
            </div>
            <button type="submit" class="btn btn-primary">Criar</button>
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
                            <a class="btn btn-warning mr-3">ALTERAR</a>
                            <a class="btn btn-danger mr-3">EXCLUIR</a>
                        </div>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
</body>
</html>