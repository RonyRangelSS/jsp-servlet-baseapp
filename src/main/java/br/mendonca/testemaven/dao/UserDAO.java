package br.mendonca.testemaven.dao;

import java.util.UUID;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.mendonca.testemaven.model.entities.User;

public class UserDAO {

	public void register(User user) throws ClassNotFoundException, SQLException {
		Connection conn = ConnectionPostgres.getConexao();
		conn.setAutoCommit(true);
		
		PreparedStatement ps = conn.prepareStatement("INSERT INTO users (name, email, password, idade, status) values (?,?,?,?,?)");
		ps.setString(1, user.getName());
		ps.setString(2, user.getEmail());
		ps.setString(3, user.getPassword());
		ps.setInt(4, user.getIdade());
		ps.setBoolean(5, user.getStatus());
		ps.execute();
		ps.close();
	}
	
	public List<User> listAllUser() throws ClassNotFoundException, SQLException {
		ArrayList<User> lista = new ArrayList<User>();
		
		Connection conn = ConnectionPostgres.getConexao();
		conn.setAutoCommit(true);
		
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM users");
		
		while (rs.next()) {
			User user = new User();
			user.setUuid(rs.getString("uuid"));
			user.setName(rs.getString("name"));
			user.setEmail(rs.getString("email"));
			user.setPassword(rs.getString("password"));
			user.setIdade(rs.getInt("idade"));
			user.setStatus(rs.getBoolean("status"));
			
			lista.add(user);
		}
		
		rs.close();
		
		return lista;
	}

	public User search(String email, String password) throws ClassNotFoundException, SQLException {
		User user = null;
		
		Connection conn = ConnectionPostgres.getConexao();
		conn.setAutoCommit(true);
		
		// Apesar de qualquer SQL funcionar com Statement, a abordagem de usar Prepared Statement evita SQL Injection.
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE email = ? AND password = ?");
		ps.setString(1, email);
		ps.setString(2, password);
		System.out.println(ps); // Exibe no console do Docker a query j� montada.
		
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			
			user = new User();
			user.setUuid(rs.getString("uuid"));
			user.setName(rs.getString("name"));
			user.setEmail(rs.getString("email"));
			user.setPassword(rs.getString("password"));
		}
		
		rs.close();
		
		return user;
	}

	// TODO: N�o testado
	public List<User> search(String name) throws ClassNotFoundException, SQLException {
		ArrayList<User> lista = new ArrayList<User>();
		
		Connection conn = ConnectionPostgres.getConexao();
		conn.setAutoCommit(true);
		
		// Apesar de qualquer SQL funcionar com Statement, a abordagem de usar Prepared Statement evita SQL Injection.
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE ? LIKE LOWER(?) || LOWER(name) || '%'");
		ps.setString(1, name);
		
		ResultSet rs = ps.executeQuery();
		
		if (rs.next()) {
			User user = new User();
			user.setUuid(rs.getString("uuid"));
			user.setName(rs.getString("name"));
			user.setEmail(rs.getString("email"));
			user.setPassword(rs.getString("password"));
			
			lista.add(user);
		}
		
		rs.close();
		
		return lista;
	}

	public void createFollowRelation(String followerId, String followedId) throws ClassNotFoundException, SQLException {
		Connection conn = ConnectionPostgres.getConexao();
		conn.setAutoCommit(true);

		PreparedStatement ps = conn.prepareStatement("INSERT INTO follows (followerId, followedId) values (?,?)");
		ps.setObject(1, UUID.fromString(followerId));
		ps.setObject(2, UUID.fromString(followedId));
		ps.execute();
		ps.close();
	}
	public List<User> searchUsers(String search, Integer idadeMinima, Integer idadeMaxima, Boolean status) throws ClassNotFoundException, SQLException {
		List<User> users = new ArrayList<>();
		Connection conn = ConnectionPostgres.getConexao();

		StringBuilder sql = new StringBuilder("SELECT * FROM users WHERE name ILIKE ? AND idade BETWEEN ? AND ?");


		if (status != null) {
			sql.append(" AND status = ?");
		}

		try (PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
			stmt.setString(1, "%" + search + "%");
			stmt.setInt(2, idadeMinima);
			stmt.setInt(3, idadeMaxima);
			int index = 4;

			if (status != null) {
				stmt.setBoolean(index, status);
			}

			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				User user = new User();
				user.setUuid(rs.getString("uuid"));
				user.setName(rs.getString("name"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setIdade(rs.getInt("idade"));
				user.setStatus(rs.getBoolean("status"));
				users.add(user);
			}
		}
		return users;
	}




	public void deleteFollowRelation(String followerId, String followedId) throws ClassNotFoundException, SQLException {
		Connection conn = ConnectionPostgres.getConexao();
		conn.setAutoCommit(true);

		PreparedStatement ps = conn.prepareStatement("DELETE FROM follows WHERE followerId = ? AND followedId = ?");
		ps.setObject(1, UUID.fromString(followerId));
		ps.setObject(2, UUID.fromString(followedId));
		ps.execute();
		ps.close();
	}

	public User getUserById(String userId) throws ClassNotFoundException, SQLException {
		ArrayList<User> lista = new ArrayList<User>();

		Connection conn = ConnectionPostgres.getConexao();
		conn.setAutoCommit(true);

		PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE uuid = ?");
		ps.setObject(1, UUID.fromString(userId));
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			User user = new User();
			user.setUuid(rs.getString("uuid"));
			user.setName(rs.getString("name"));
			user.setEmail(rs.getString("email"));
			user.setPassword(rs.getString("password"));

			lista.add(user);
		}

		return lista.get(0);

	}
	public List<User> listFollowingUsers(String followerId) throws ClassNotFoundException, SQLException {
		ArrayList<User> lista = new ArrayList<User>();

		Connection conn = ConnectionPostgres.getConexao();
		conn.setAutoCommit(true);

		PreparedStatement ps = conn.prepareStatement("SELECT * FROM follows WHERE followerId = ?");
		ps.setObject(1, UUID.fromString(followerId));
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			User user = new User();
			String followedId = (rs.getString("followedId"));

			user = getUserById(followedId);

			lista.add(user);
		}

		rs.close();

		return lista;
	}

}
