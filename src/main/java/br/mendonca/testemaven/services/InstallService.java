package br.mendonca.testemaven.services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import br.mendonca.testemaven.dao.ConnectionPostgres;

public class InstallService {

	private void statement(String sql) throws ClassNotFoundException, SQLException {
		Connection conn = ConnectionPostgres.getConexao();
		conn.setAutoCommit(true);

		Statement st = conn.createStatement();
		st.executeUpdate(sql);
		st.close();
	}

	public void testConnection() throws ClassNotFoundException, SQLException {
		ConnectionPostgres.getConexao();
	}

	public void deleteUserTable() throws ClassNotFoundException, SQLException {
		statement("DROP TABLE IF EXISTS users");
	}

	public void createUserTable() throws ClassNotFoundException, SQLException {
		statement("CREATE TABLE users ("
				+ "    uuid UUID PRIMARY KEY DEFAULT gen_random_uuid(),"
				+ "    name VARCHAR(255) NOT NULL,"
				+ "    email VARCHAR(255) NOT NULL,"
				+ "    password VARCHAR(255) NOT NULL)");
	}

	public void deleteNoteTable() throws ClassNotFoundException, SQLException {
		statement("DROP TABLE IF EXISTS notes");
	}

	public void createNoteTable() throws ClassNotFoundException, SQLException {
		statement("CREATE TABLE notes ("
				+ "    uuid UUID DEFAULT gen_random_uuid() PRIMARY KEY,"
				+ "    userId UUID REFERENCES users(uuid),"
				+ "    noteTitle VARCHAR(255) NOT NULL,"
				+ "    noteContent VARCHAR(255) NOT NULL,"
				+ "    date INTEGER NOT NULL,"
				+ "    isDone BOOLEAN NOT NULL)");
	}

	public void deleteEventTable() throws ClassNotFoundException, SQLException {
		statement("DROP TABLE IF EXISTS events");
	}

	public void createEventTable() throws ClassNotFoundException, SQLException {
		statement("CREATE TABLE events ("
				+ "    uuid UUID PRIMARY KEY DEFAULT gen_random_uuid(),"
				+ "    userId UUID REFERENCES users(uuid),"
				+ "    eventName VARCHAR(255) NOT NULL,"
				+ "    date INT NOT NULL,"
				+ "    hasPassed BOOLEAN NOT NULL)");
	}

	public void deleteTaskTable() throws ClassNotFoundException, SQLException {
		statement("DROP TABLE IF EXISTS tasks");
	}

	public void createTaskTable() throws ClassNotFoundException, SQLException {
		statement("CREATE TABLE tasks ("
				+ "    uuid UUID DEFAULT gen_random_uuid() PRIMARY KEY,"
				+ "    userId UUID REFERENCES users(uuid),"
				+ "    taskName VARCHAR(255) NOT NULL,"
				+ "    isCompleted BOOLEAN,"
				+ "    isVisible BOOLEAN,"
				+ "    priority INTEGER"
				+ ")");
	}

	public UUID createSingleUser(String name, String email, String password) throws ClassNotFoundException, SQLException {
		String sql = String.format("INSERT INTO users (name, email, password) VALUES ('%s', '%s', '%s') RETURNING uuid",
				name, email, password);
		Connection conn = ConnectionPostgres.getConexao();
		Statement st = conn.createStatement();

		ResultSet rs = st.executeQuery(sql);
		UUID userId = null;
		if (rs.next()) {
			userId = (UUID) rs.getObject("uuid");
		}

		rs.close();
		st.close();

		return userId;
	}

	public void createTasksForUser(UUID userId) throws ClassNotFoundException, SQLException {
		String[] taskNames = {"Task 1", "Task 2", "Task 3", "Task 4", "Task 5", "Task 6", "Task 7"};
		boolean[] isCompleted = {true, false, true, false, true, false, true};
		int[] priorities = {1, 2, 3, 1, 2, 3, 1};

		for (int i = 0; i < 7; i++) {
			String sql = String.format(
					"INSERT INTO tasks (userId, taskName, isCompleted, isVisible, priority) VALUES ('%s', '%s', %b, %b , %d)",
					userId, taskNames[i], isCompleted[i], true, priorities[i]);
			statement(sql);
		}
	}




}
