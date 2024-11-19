package br.mendonca.testemaven.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import br.mendonca.testemaven.services.UserService;
import br.mendonca.testemaven.services.dto.UserDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/dashboard/user-search")
public class SearchUsersServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter page = response.getWriter();
        // Recupera os parâmetros do request
        String search = request.getParameter("search");
        String idadeMinimaStr = request.getParameter("idadeMinima");
        String idadeMaximaStr = request.getParameter("idadeMaxima");
        String statusStr = request.getParameter("status");

        // Define valores padrão ou converte os valores recebidos
        search = (search == null) ? "" : search;

        Integer idadeMinima = (idadeMinimaStr != null && !idadeMinimaStr.isEmpty())
                ? Integer.parseInt(idadeMinimaStr)
                : 0;

        Integer idadeMaxima = (idadeMaximaStr != null && !idadeMaximaStr.isEmpty())
                ? Integer.parseInt(idadeMaximaStr)
                : 150;

        Boolean status = (statusStr != null && !statusStr.isEmpty())
                ? Boolean.parseBoolean(statusStr)
                : null;

        UserService userService = new UserService();

        try {
            List<UserDTO> lista = userService.searchUsers(search, idadeMinima, idadeMaxima, status);

            request.setAttribute("lista", lista);
            request.getRequestDispatcher("list-users.jsp").forward(request, response);
        } catch (Exception e) {
            // Escreve as mensagens de Exception em uma p�gina de resposta.
            // N�o apagar este bloco.
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);

            page.println("<html lang='pt-br'><head><title>Error</title></head><body>");
            page.println("<h1>Error</h1>");
            page.println("<code>");
            page.println(sw.toString());
            page.println("</code>");
            page.println("</body></html>");
            page.close();
        } finally {

        }
    }
}
