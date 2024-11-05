package br.mendonca.testemaven.controller.note;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import br.mendonca.testemaven.services.NoteService;
import br.mendonca.testemaven.services.dto.UserDTO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/dashboard/register-note")
public class RegisterNoteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("/dashboard/add-note.jsp");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter page = response.getWriter();
        HttpSession session = request.getSession();
        NoteService noteService = new NoteService();

        try {

            UserDTO user = (UserDTO) session.getAttribute("user");
            String userId = user.getUuid();

            String noteTitle = request.getParameter("noteTitle");
            String noteContent = request.getParameter("noteContent");
            int noteDate = Integer.parseInt(request.getParameter("noteDate"));
            boolean isDone = Boolean.parseBoolean(request.getParameter("isDone"));

            noteService.register(userId, noteTitle, noteContent, noteDate, isDone);

            response.sendRedirect("dashboard.jsp");

        } catch (Exception e) {
            // Escreve as mensagens de Exception em uma p�gina de resposta.
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);

            page.println("<html lang='pt-br'><head><title>Error</title></head><body>");
            page.println("<h1>Error</h1>");
            page.println("<code>" + sw.toString() + "</code>");
            page.println("</body></html>");
            page.close();
        } finally {

        }
    }
}
