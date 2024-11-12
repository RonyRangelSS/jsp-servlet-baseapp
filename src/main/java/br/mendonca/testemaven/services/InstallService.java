package br.mendonca.testemaven.services;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

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

	public void createTaskTable() throws  ClassNotFoundException, SQLException {
		statement("CREATE TABLE tasks ("
				+ "    uuid UUID DEFAULT gen_random_uuid() PRIMARY KEY,"
				+ "    userId UUID REFERENCES users(uuid),"
				+ "    taskName VARCHAR(255) NOT NULL,"
				+ "    isCompleted BOOLEAN,"
				+ "    priority INTEGER"
				+ ")");
	}

	public void insertInitialUser() throws ClassNotFoundException, SQLException {
		String[] emails = {
				"michael-alb@hotmail.com",
		};

		String sql = String.format(
				"INSERT INTO users (name, email, password) VALUES ('%s', '%s', '%s')",
				"michael", "michael-alb@hotmail.com", "123"
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





}
