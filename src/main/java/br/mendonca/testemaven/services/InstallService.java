package br.mendonca.testemaven.services;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

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
					+ "    uuid UUID DEFAULT gen_random_uuid() PRIMARY KEY,"
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

}
