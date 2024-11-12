package br.mendonca.testemaven.controller.event;

import br.mendonca.testemaven.services.EventService;
import br.mendonca.testemaven.services.UserService;
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

@WebServlet("/dashboard/events")
public class ListEventsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter page = response.getWriter();
		HttpSession session = request.getSession();
		
		try {
			UserDTO user = (UserDTO) session.getAttribute("user");
			String userId = user.getUuid();

			int pageEvent = 1;
			int eventsPerPage = 3;
			if (request.getParameter("page") != null) {
				pageEvent = Integer.parseInt(request.getParameter("page"));
			}
			int offset = (pageEvent - 1) * eventsPerPage;
			EventService eventService = new EventService();
			List<EventDTO> lista = eventService.listAllEventPaginated(userId, offset, 3);

			// Anexa � requisi��o um objeto ArrayList e despacha a requisi��o para uma JSP.
			request.setAttribute("lista", lista);
			request.setAttribute("currentPage", pageEvent);
			request.getRequestDispatcher("list-events.jsp").forward(request, response);
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
		HttpSession session = request.getSession();
		EventService eventService = new EventService();
		
		try {
			UserDTO user = (UserDTO) session.getAttribute("user");
			String userId = user.getUuid();

			int pageEvent = 1;
			int eventsPerPage = 3;
			if (request.getParameter("page") != null) {
				pageEvent = Integer.parseInt(request.getParameter("page"));
			}
			int offset = (pageEvent - 1) * eventsPerPage;

			String eventName = request.getParameter("eventName");
			Integer date = Integer.valueOf(request.getParameter("date"));
			Boolean hasPassed = Boolean.valueOf(request.getParameter("hasPassed"));

			eventService.register(userId, eventName, date, hasPassed);

			List<EventDTO> lista = eventService.listAllEventPaginated(userId, offset, 3);

			request.setAttribute("lista", lista);
			request.setAttribute("currentPage", pageEvent);
			request.getRequestDispatcher("list-events.jsp").forward(request, response);
			
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
