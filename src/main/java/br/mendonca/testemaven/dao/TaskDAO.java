package br.mendonca.testemaven.dao;

import br.mendonca.testemaven.model.entities.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskDAO {
    public void register(Task task) throws ClassNotFoundException, SQLException {
        Connection conn = ConnectionPostgres.getConexao();
        conn.setAutoCommit(true);

        PreparedStatement ps = conn.prepareStatement("INSERT INTO tasks (userId, taskName, isCompleted, isVisible, priority) values (?,?,?,?,?)");
        ps.setObject(1, UUID.fromString(task.getUserId()));
        ps.setString(2, task.getTaskName());
        ps.setBoolean(3, task.getCompleted());
        ps.setBoolean(4, true);
        ps.setInt(5, task.getPriority());
        ps.execute();
        ps.close();

        System.out.println("#######################################");
        System.out.println(task.getUserId());
        System.out.println("#######################################");
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

    public ArrayList<Task> listTasksPaginated(String userId, int offset) throws ClassNotFoundException, SQLException {
        ArrayList<Task> lista = new ArrayList<Task>();

        Connection conn = ConnectionPostgres.getConexao();
        conn.setAutoCommit(true);

        Statement st = conn.createStatement();
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM tasks WHERE userId=? AND isVisible=true LIMIT 3 OFFSET ?");
        ps.setObject(1, UUID.fromString(userId));
        ps.setInt(2, offset);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Task task = new Task();
            task.setUuid(rs.getString("uuid"));
            task.setTaskName(rs.getString("taskName"));
            task.setCompleted(rs.getBoolean("isCompleted"));
            task.setVisible(rs.getBoolean("isVisible"));
            task.setPriority(rs.getInt("priority"));
            task.setUserId(rs.getString("userId"));

            System.out.println(rs.getString("taskName"));

            lista.add(task);
        }
        rs.close();

        return lista;
    }

    public void ocultarTask(String uuid) throws ClassNotFoundException, SQLException {
        Connection conn = ConnectionPostgres.getConexao();
        conn.setAutoCommit(true);

        PreparedStatement ps = conn.prepareStatement("UPDATE tasks SET isVisible = false WHERE uuid = ?");
        ps.setObject(1, UUID.fromString(uuid));
        ps.execute();
        ps.close();
    }

    public void ShowAllTasks(String userId) throws ClassNotFoundException, SQLException {
        Connection conn = ConnectionPostgres.getConexao();
        conn.setAutoCommit(true);

        PreparedStatement ps = conn.prepareStatement("UPDATE tasks SET isVisible = true WHERE userID = ?");
        ps.setObject(1, UUID.fromString(userId));
        ps.execute();
        ps.close();
    }

    
}
