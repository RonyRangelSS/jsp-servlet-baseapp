package br.mendonca.testemaven.controller.event;

import br.mendonca.testemaven.dao.UserDAO;
import br.mendonca.testemaven.model.entities.User;
import br.mendonca.testemaven.services.EventService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

@WebServlet("/register-event")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Caso o usu�rio tente acessar este end point pelo m�todo GET, recebe a p�gina de formul�rio JSP.
		response.sendRedirect("form-register.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		HttpSession session = request.getSession();
		PrintWriter page = response.getWriter();
		EventService eventService = new EventService();
		
		try {
			String userId = (String) session.getAttribute("uuid");
			String eventName = request.getParameter("eventName");
			int date = Integer.parseInt(request.getParameter("date"));
			Boolean hasPassed = Boolean.valueOf(request.getParameter("hasPassed"));
			
			eventService.register(userId, eventName, date, hasPassed);

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
