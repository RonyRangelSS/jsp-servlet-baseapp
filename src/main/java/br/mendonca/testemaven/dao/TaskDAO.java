package br.mendonca.testemaven.dao;

import br.mendonca.testemaven.model.entities.Task;
import br.mendonca.testemaven.model.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskDAO {
    public void register(Task task) throws ClassNotFoundException, SQLException {
        Connection conn = ConnectionPostgres.getConexao();
        conn.setAutoCommit(true);

        PreparedStatement ps = conn.prepareStatement("INSERT INTO tasks (userId, taskName, isCompleted, priority) values (?,?,?, ?)");
        ps.setString(1, task.getUserId());
        ps.setString(2, task.getTaskName());
        ps.setBoolean(3, task.getCompleted());
        ps.setInt(4, task.getPriority());
        ps.execute();
        ps.close();
    }

    public List<Task> listAllUserTasks(String userId) throws ClassNotFoundException, SQLException {
        ArrayList<Task> lista = new ArrayList<Task>();

        Connection conn = ConnectionPostgres.getConexao();
        conn.setAutoCommit(true);

        PreparedStatement ps = conn.prepareStatement("SELECT * FROM tasks WHERE userId = ?");
        ps.setObject(1, UUID.fromString(userId));
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Task task = new Task();
            task.setUuid(rs.getString("uuid"));
            task.setTaskName(rs.getString("taskName"));
            task.setCompleted(rs.getBoolean("isCompleted"));
            task.setUserId(rs.getString("userId"));

            lista.add(task);
        }

        rs.close();

        return lista;
    }
}
