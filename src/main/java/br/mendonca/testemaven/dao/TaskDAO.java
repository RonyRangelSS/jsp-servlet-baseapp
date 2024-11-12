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

        PreparedStatement ps = conn.prepareStatement("INSERT INTO tasks (userId, taskName, isCompleted, isVisible, priority) values (?,?,?, ?, ?)");
        ps.setObject(1, task.getUserId());
        ps.setString(2, task.getTaskName());
        ps.setBoolean(3, task.getCompleted());
        ps.setBoolean(4, true);
        ps.setInt(5, task.getPriority());
        ps.execute();
        ps.close();
    }

    public List<Task> listAllUserTasks(String userId) throws ClassNotFoundException, SQLException {
        ArrayList<Task> lista = new ArrayList<Task>();

        Connection conn = ConnectionPostgres.getConexao();
        conn.setAutoCommit(true);

        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM tasks");

        while (rs.next()) {
            Task task = new Task();
            task.setUuid(rs.getString("uuid"));
            task.setTaskName(rs.getString("taskName"));
            task.setCompleted(rs.getBoolean("isCompleted"));
            task.setVisible(rs.getBoolean("isVisible"));
            task.setPriority(rs.getInt("priority"));
            task.setUserId(rs.getString("userId"));

            lista.add(task);
        }
        System.out.println(lista);
        rs.close();

        return lista;
    }

    public List<Task> listUserTasksPaginated(String userId, int offset, int limit) throws ClassNotFoundException, SQLException {
        ArrayList<Task> lista = new ArrayList<>();

        Connection conn = ConnectionPostgres.getConexao();
        PreparedStatement st = conn.prepareStatement("SELECT * FROM tasks WHERE userId = ? LIMIT ? OFFSET ?");
        st.setObject(1, UUID.fromString(userId));
        st.setInt(2, limit);
        st.setInt(3, offset);
        ResultSet rs = st.executeQuery();
        int x = 0;

        while (rs.next()) {
            Task task = new Task();
            task.setUuid(rs.getString("uuid"));
            task.setTaskName(rs.getString("taskName"));
            task.setCompleted(rs.getBoolean("isCompleted"));
            task.setVisible(rs.getBoolean("isVisible"));
            task.setPriority(rs.getInt("priority"));
            task.setUserId(rs.getString("userId"));
            lista.add(task);
            x = x++;
        }
        System.out.println(x);
        rs.close();
        return lista;
    }
}
