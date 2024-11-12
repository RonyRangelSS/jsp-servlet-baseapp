package br.mendonca.testemaven.controller.note;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import br.mendonca.testemaven.services.NoteService;
import br.mendonca.testemaven.services.dto.NoteDTO;
import br.mendonca.testemaven.services.dto.UserDTO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/dashboard/notes/page")
public class ListNotesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter page = response.getWriter();
        HttpSession session = request.getSession();
        NoteService noteService = new NoteService();


        int currentPageIndex = Integer.parseInt(request.getParameter("pageIndex"));
        int maxNotesPerPage = 3;
        int offset = (currentPageIndex - 1) * maxNotesPerPage;

        try {

            UserDTO user = (UserDTO) session.getAttribute("user");
            String userId = user.getUuid();
            int totalPages = (int) Math.ceil((double) noteService.countUserNotes(userId) / maxNotesPerPage);


            List<NoteDTO> lista = noteService.listNotesForPagination(userId, maxNotesPerPage, offset);

            request.setAttribute("lista", lista);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("currentPageIndex", currentPageIndex);
            request.getRequestDispatcher("list-notes.jsp").forward(request, response);
        } catch (Exception e) {
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
