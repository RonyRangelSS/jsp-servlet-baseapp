<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="br.mendonca.testemaven.services.dto.UserDTO"%>

<%
if (session.getAttribute("user") != null) {
%>

<!doctype html>
<html lang="pt-br" data-bs-theme="dark">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Gerência de Configuração</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
	crossorigin="anonymous">
<link href="style.css" rel="stylesheet">
</head>
<body class="d-flex align-items-center py-4 bg-body-tertiary">

	<main class="w-100 m-auto form-container">

		<form action="/register-event" method="post">
			<div>
				<label>Nome do evento:</label>
				<input type="text" id="eventName" name="eventName" required>
			</div>

			<div>
				<label>Data:</label>
				<input type="number" id="date" name="date" required>
			</div>

			<div>
				<label>
					<input type="checkbox" id="hasPassed" name="hasPassed">
					Has the event passed?
				</label>
			</div>

			<span class="navbar-text">
				<button class="btn btn-primary w-100 py-2 mt-2">Registrar evento</button>
			</span>
		</form>

	</main>



	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
		crossorigin="anonymous">
		
	</script>
</body>
</html>
<%
}
%>
