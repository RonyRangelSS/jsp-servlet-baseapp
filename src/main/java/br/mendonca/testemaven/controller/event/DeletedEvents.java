package br.mendonca.testemaven.controller.event;

import br.mendonca.testemaven.services.EventService;
import br.mendonca.testemaven.services.dto.EventDTO;
import br.mendonca.testemaven.services.dto.UserDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

@WebServlet("/dashboard/events-deleted")
public class DeletedEvents extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        HttpSession session = request.getSession();
        PrintWriter page = response.getWriter();
        EventService eventService = new EventService();

		/*
		int currentPageIndex = Integer.parseInt(request.getParameter("pageIndex"));
        int maxNotesPerPage = 3;
        int offset = (currentPageIndex - 1) * maxNotesPerPage;
		 */

        try {
            UserDTO user = (UserDTO) session.getAttribute("user");
            String userId = user.getUuid();
            //int totalPages = (int) Math.ceil((double) noteService.countDeletedNotes(userId) / maxNotesPerPage);

            List<EventDTO> lista = eventService.listAllDeletedEvent(userId);

            request.setAttribute("lista", lista);
            //request.setAttribute("totalPages", totalPages);
            //request.setAttribute("currentPageIndex", currentPageIndex);
            request.getRequestDispatcher("list-deleted-events.jsp").forward(request, response);
        } catch (Exception e) {
            // Escreve as mensagens de Exception em uma p�gina de resposta.
            // N�o apagar este bloco.
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


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter page = response.getWriter();

        try {
            // A programa��o do servlet deve ser colocada neste bloco try.
            // Apague o conte�do deste bloco try e escreva seu c�digo.
            String parametro = request.getParameter("nomeparametro");

            page.println("Parametro: " + parametro);
            page.close();


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
