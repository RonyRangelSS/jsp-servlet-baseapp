<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="br.mendonca.testemaven.services.dto.TaskDTO"%>

<% if (session.getAttribute("user") != null && request.getAttribute("lista") != null) { %>

<!doctype html>
<html lang="pt-br" data-bs-theme="dark">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Gerência de Configuração</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link href="style.css" rel="stylesheet">
</head>
<body class="d-flex align-items-center py-4 bg-body-tertiary">

<main class="w-100 m-auto form-container">

    <nav class="navbar navbar-expand-lg bg-body-tertiary">
        <div class="container-fluid">
            <a class="navbar-brand" href="/dashboard/dashboard.jsp">Gerência de Configuração</a>
            <button class="navbar-toggler" type="button"
                    data-bs-toggle="collapse" data-bs-target="#navbarText"
                    aria-controls="navbarText" aria-expanded="false"
                    aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarText">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item"><a class="nav-link" href="/dashboard/dashboard.jsp">Home</a></li>
                    <li class="nav-item"><a class="nav-link" href="/dashboard/users">Users</a></li>
                    <li class="nav-item"><a class="nav-link" href="/dashboard/about.jsp">About</a></li>
                    <li class="nav-item"><a class="nav-link" href="/dashboard/tasks">Tasks</a></li>
                </ul>
                <span class="navbar-text">
						<a class="btn btn-success" href="/auth/logoff">Logoff</a>
					</span>
            </div>
        </div>
    </nav>

    <div class="d-flex flex-row h-auto">
        <div d-flex flex-col col-md-4 me-5>
            <form action="/dashboard/tasks" method="post">
                <h1 class="h3 mb-3 fw-normal">Cadastrar nova task</h1>
                <div>
                    <label for=>Nome da task:</label>
                    <input type="text" name="taskName" class="form-control" id="floatingInput" placehholder="Digite sua task" />
                    <br/>
                    <label>Prioridade:</label>
                    <input type="text" name="priority" class="form-control" placeholder="Digite a prioridade de sua task...">
                    <br/>
                    <label>Está feito ?</label>
                    <select id="choice" name="isCompleted" required>
                        <option value="true">Feito</option>
                        <option value="false">Não feito</option>
                    </select>
                    <br/>
                </div>
                <button class="btn btn-primary w-100 py-2 mt-2">Registrar task</button>
                <br>
                <br>

                <span class="navbar-text">
        </span>

            </form>

        </div>

        <div class="d-flex flex-column col-md-5 ms-5">
            <h1 class="h3 mb-3 fw-normal">Tasks</h1>
            <table class="table">
                <thead>
                <tr>
                    <th scope="col">Nome da task</th>
                </tr>
                </thead>
                <tbody>
                    <%
                List<TaskDTO> lista = (List<TaskDTO>) request.getAttribute("lista");
                for (TaskDTO task : lista) {
                        %>
                <tr>
                    <td><a href="/dashboard/view-task.jsp?taskId=<%= task.getUuid() %>"><%= task.getTaskName() %></a></td>
                    <form method="post">
                        <input type="hidden" name="taskId" value="<%= task.getUuid() %>" />
                        <td><button formaction="/dashboard/ocultar-task">Ocultar</button></td>
                    </form>
                </tr>
                    <% } %>
                <tbody/>
                <table/>
                <div class="pagination">
                    <% int currentPage = (int) request.getAttribute("currentPage");
                    %>
                    <nav aria-label="Navegação de página">
                        <ul class="pagination">
                            <li class="page-item <%= currentPage == 1 ? "disabled" : "" %>">
                                <a class="page-link" href="?page=<%= currentPage - 1 %>">Anterior</a>
                            </li>
                            <li class="page-item">
                                <span class="page-link"><%= currentPage %></span>
                            </li>
                            <li class="page-item">
                                <a class="page-link" href="?page=<%= currentPage + 1 %>">Próxima</a>
                            </li>
                        </ul>
                    </nav>
                </div>
                <form method="post">
                    <button formaction="/dashboard/show-tasks">Mostrar todas as tasks</button>
                </form>
        </div>
    </div>





</main>



<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>

<% } %>