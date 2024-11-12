<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="br.mendonca.testemaven.services.dto.NoteDTO"%>

<% if (session.getAttribute("user") != null) { %>

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
                </ul>
                <span class="navbar-text">
                    <a class="btn btn-success" href="/auth/logoff">Logoff</a>
                </span>
            </div>
        </div>
    </nav>

    <h1 class="h3 mb-3 fw-normal">Notes</h1>
    <table class="table">
        <thead>
        <tr>
            <th scope="col">Note name</th>
            <th scope="col">Status</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <%
            List<NoteDTO> lista = (List<NoteDTO>) request.getAttribute("lista");
            for (NoteDTO note : lista) {
        %>
        <tr>
            <td><a href="/dashboard/note?note=<%= note.getUuid() %>"><%= note.getNoteTitle() %></a></td>
            <td>
                <input type="checkbox" disabled <%= note.isDone() ? "checked" : "" %> />
            </td>
            <td>
                <form action="/dashboard/update-visibility" method="POST">
                    <% int currentPageIndex = (int) request.getAttribute("currentPageIndex"); %>
                    <input type="hidden" name="noteId" value="<%=note.getUuid() %>" />
                    <input type="hidden" name="pageIndex" value="<%=currentPageIndex %>" />

                    <button type="submit" class="btn btn-danger">
                        Delete
                    </button>
                </form>
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>

    <div class="d-flex justify-content-between">
        <%
            int currentPageIndex = (int) request.getAttribute("currentPageIndex");
            int totalPages = (int) request.getAttribute("totalPages");
        %>

        <% if (currentPageIndex > 1) { %>
        <a href="/dashboard/notes/page?pageIndex=<%= currentPageIndex - 1 %>" class="btn btn-primary">Previous</a>
        <% } %>

        <% if (currentPageIndex < totalPages) { %>
        <a href="/dashboard/notes/page?pageIndex=<%= currentPageIndex + 1 %>" class="btn btn-primary">Next</a>
        <% } %>
    </div>

    <span class="navbar-text">
          <a class="btn btn-success" href="/dashboard/dashboard.jsp">Dashboard</a>
    </span>

</main>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>

<% } %>
