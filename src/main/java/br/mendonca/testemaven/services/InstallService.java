package br.mendonca.testemaven.services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.UUID;

import br.mendonca.testemaven.dao.ConnectionPostgres;
import br.mendonca.testemaven.services.dto.UserDTO;

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
				+ "    password VARCHAR(255) NOT NULL,"
				+ "    idade INTEGER NOT NULL,"
				+ "    status BOOLEAN NOT NULL"
				+ ")");
	}


	public void createFollowsTable() throws ClassNotFoundException, SQLException {
		statement("CREATE TABLE follows ("
				+ "    followerId UUID NOT NULL,"
				+ "    followedId UUID NOT NULL,"
				+ "    PRIMARY KEY (followerId, followedId),"
				+ "    FOREIGN KEY (followerId) REFERENCES users(uuid) ON DELETE CASCADE,"
				+ "    FOREIGN KEY (followedId) REFERENCES users(uuid) ON DELETE CASCADE"
				+ ")");
	}

	public void deleteFollowsTable() throws ClassNotFoundException, SQLException {
		statement("DROP TABLE IF EXISTS follows");
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
				+ "    isVisible BOOLEAN NOT NULL DEFAULT TRUE,"
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
				+ "    date INTEGER NOT NULL,"
				+ "    hasPassed BOOLEAN NOT NULL,"
				+ "    isVisible BOOLEAN NOT NULL DEFAULT TRUE)");
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

	public void insertInitialUser() throws ClassNotFoundException, SQLException {

		String sql = String.format(
				"INSERT INTO users (name, email, password, idade, status) VALUES ('%s', '%s', '%s', '%d', '%b')",
				"michael", "michael-alb@hotmail.com", "123", 23, true
		);

		statement(sql);
	}

	public void populateNotes() throws ClassNotFoundException, SQLException {
		String[] titles = {
				"Aula de gerência 01", "Aula de web 01", "Aula programção Java", "Machine Learning",
				"Sistemas operacionais 02", "Docker 01", "Aula de IA"
		};
		String[] contents = {
				"Aprendendo sobre git", "Aprendendo sobre endpoints",
				"Aprendendo sobre classes", "Aprendendo sobre validação",
				"aprendendo sobre linux", "introdução a docker",
				"aprendendo ia simbólica"
		};

		UserService userService = new UserService();
		List<UserDTO> users = userService.listAllUsers();

		int[] dates = {20241101, 20241102, 20241103, 20241104, 20241105, 20241106, 20241107};
		boolean[] isDoneStatus = {false, true, false, true, false, true, false};

		for (int i = 0; i < 7; i++) {
			String sql = String.format(
					"INSERT INTO notes (userId, noteTitle, noteContent, date, isDone) " +
							"VALUES ('%s', '%s', '%s', %d, %b)",
					users.get(0).getUuid(), titles[i], contents[i], dates[i], isDoneStatus[i]
			);
			statement(sql);
		}
	}

	public UUID createSingleUser(String name, String email, String password, Integer idade, Boolean status) throws ClassNotFoundException, SQLException {
		String sql = String.format("INSERT INTO users (name, email, password, idade, status) VALUES ('%s', '%s', '%s', '%d', '%b') RETURNING uuid",
				name, email, password, idade, status);
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

	public void populateEvents() throws ClassNotFoundException, SQLException {
		String[] eventName = {
				"Evento 1", "Evento 2", "Evento 3", "Evento 4", "Evento 5",
				"Evento 6", "Evento 7"
		};

		UserService userService = new UserService();
		List<UserDTO> users = userService.listAllUsers();

		int[] dates = {01012024, 02012024, 03012025, 04012025, 05012025, 06012024, 07012025};
		boolean[] hasPassed = {true, true, false, false, false, true, false};

		for (int i = 0; i < 7; i++) {
			String sql = String.format(
					"INSERT INTO events (userId, eventName, date, hasPassed) " +
							"VALUES ('%s','%s', %d, %b)",
					users.get(0).getUuid(), eventName[i], dates[i], hasPassed[i]
			);
			statement(sql);
		}
	}
}
