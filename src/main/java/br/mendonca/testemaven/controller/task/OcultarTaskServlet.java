package br.mendonca.testemaven.controller.task;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import br.mendonca.testemaven.model.entities.Task;
import br.mendonca.testemaven.services.TaskService;
import br.mendonca.testemaven.services.UserService;
import br.mendonca.testemaven.services.dto.TaskDTO;
import br.mendonca.testemaven.services.dto.UserDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/dashboard/ocultar-task")
public class OcultarTaskServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter page = response.getWriter();
        HttpSession session = request.getSession();
        TaskService taskService = new TaskService();

        try {

            UserDTO user = (UserDTO) session.getAttribute("user");
            String userId = user.getUuid();

            int pageTask = 1;
            int tasksPerPage = 3;

            if (request.getParameter("page") != null) {
                pageTask = Integer.parseInt(request.getParameter("page"));
            }

            int offset = (pageTask - 1) * tasksPerPage;

            String taskId = request.getParameter("taskId");
            System.out.println(taskId);


            taskService.ocultarTask(taskId);

            List<TaskDTO> lista = taskService.listAllUserTasksPagineted(userId, offset);

            request.setAttribute("lista", lista);
            request.setAttribute("currentPage", pageTask);
            request.getRequestDispatcher("list-tasks.jsp").forward(request, response);

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