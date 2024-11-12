<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="pt-br" data-bs-theme="dark">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Adicionar notes</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link href="style.css" rel="stylesheet">
</head>
<body class="d-flex align-items-center py-4 bg-body-tertiary">

<main class="w-100 m-auto form-container">
    <form action="/dashboard/register-note" method="post">
        <h1 class="h3 mb-3 fw-normal">Cadastrar nova anotação:</h1>
        <div class="form-floating">
            <input type="text" name="noteTitle" class="form-control" placehholder="Digite o título da sua anotação" />
            <input type="text" name="noteContent" class="form-control" placehholder="Digite o conteúdo da sua anotação" />
            <input type="text" name="noteDate" class="form-control" placehholder="Digite a data em que você fez essa anotação em numero de dias" />
            <input type="checkbox" name="isDone" class="form-check-input">
        </div>
        <button type='submit' class="btn btn-primary w-100 py-2 mt-2">Registrar anotação</button>

        <span class="navbar-text">
          <a class="btn btn-success" href="/dashboard/dashboard.jsp">Dashboard</a>
        </span>

    </form>
</main>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>