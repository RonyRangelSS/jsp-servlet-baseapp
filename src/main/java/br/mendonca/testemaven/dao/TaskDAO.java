package br.mendonca.testemaven.dao;

import br.mendonca.testemaven.model.entities.Task;
import br.mendonca.testemaven.model.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO {
    public void register(Task task) throws ClassNotFoundException, SQLException {
        Connection conn = ConnectionPostgres.getConexao();
        conn.setAutoCommit(true);

        PreparedStatement ps = conn.prepareStatement("INSERT INTO tasks (taskName, isCompleted, priority) values (?,?,?)");
        ps.setString(1, task.getTaskName());
        ps.setBoolean(2, task.getCompleted());
        ps.setInt(3, task.getPriority());
        ps.execute();
        ps.close();
    }

    public List<Task> listAllUserTasks() throws ClassNotFoundException, SQLException {
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
            task.setPriority(rs.getInt("priority"));

            lista.add(task);
        }

        rs.close();

        return lista;
    }
}
