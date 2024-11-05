package br.mendonca.testemaven.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.mendonca.testemaven.dao.TaskDAO;
import br.mendonca.testemaven.dao.UserDAO;
import br.mendonca.testemaven.model.entities.Task;
import br.mendonca.testemaven.model.entities.User;
import br.mendonca.testemaven.services.dto.TaskDTO;
import br.mendonca.testemaven.services.dto.UserDTO;

public class TaskService {

    public void registerTask(String taskName, Integer priority, Boolean isCompleted, String userId) throws ClassNotFoundException, SQLException {
        TaskDAO dao = new TaskDAO();

        Task task = new Task();
        task.setTaskName(taskName);
        task.setCompleted(isCompleted);
        task.setPriority(priority);
        task.setUserId(userId);

        dao.register(task);
    }

    public List<TaskDTO> listAllUserTasks(String userId) throws ClassNotFoundException, SQLException {
        ArrayList<TaskDTO> resp = new ArrayList<TaskDTO>();

        TaskDAO dao = new TaskDAO();
        List<Task> lista = dao.listAllUserTasks(userId);
        System.out.println(lista);

        for (Task task : lista) {
            resp.add(TaskDTO.taskMapper(task));
        }

        return resp;
    }
}
