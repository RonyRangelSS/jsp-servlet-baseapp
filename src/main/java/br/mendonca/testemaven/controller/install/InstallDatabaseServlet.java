package br.mendonca.testemaven.controller.install;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.UUID;

import br.mendonca.testemaven.services.InstallService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/install")
public class InstallDatabaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter page = response.getWriter();
		
		try {
			InstallService service = new InstallService();
			String msg = "<h1>INSTALL DATABASE</h1>";
			
			service.testConnection();
			msg += "<h2>Connection DB sucessful!</h2>\n";

			service.deleteNoteTable();
			msg += "<h2>Delete table note sucessful!</h2>\n";

			service.deleteEventTable();
			msg += "<h2>Delete table events sucessful!</h2>\n";

			service.deleteTaskTable();
			msg += "<h2>Delete table task sucessful!</h2>\n";

			service.deleteUserTable();
			msg += "<h2>Delete table user sucessful!</h2>\n";

			service.createUserTable();
			msg += "<h2>Create table user sucessful!</h2>\n";

			service.createNoteTable();
			msg += "<h2>Create table note sucessful!</h2>\n";

			service.createEventTable();
			msg += "<h2>Create table events sucessful!</h2>\n";

			service.createTaskTable();
			msg += "<h2>Create table user sucessful!</h2>\n";

			service.insertInitialUser();
			msg += "<h2>Initial user created sucessfully!</h2>\n";

			service.populateNotes();
			msg += "<h2>Populate table notes sucessful!</h2>\n";

			service.populateEvents();
			msg += "<h2>Populate table events sucessful!</h2>\n";

			UUID userId = service.createSingleUser("Fernando", "fernando@gmail.com", "1234");
			msg += "<h2>Create user sucessful! Your id is </h2>\n" + userId;

			service.createTasksForUser(userId);


			page.println("<html lang='pt-br'><head><title>Teste</title></head><body>");
			page.println(msg);
			/*/
			page.println("<code>");
			for (Map.Entry<String,String> pair : env.entrySet()) {
			    page.println(pair.getKey());
			    page.println(pair.getValue());
			}
			//*/
			page.println("</code>");
			page.println("</body></html>");
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
