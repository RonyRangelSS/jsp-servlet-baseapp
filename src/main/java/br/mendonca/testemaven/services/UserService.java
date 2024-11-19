package br.mendonca.testemaven.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.mendonca.testemaven.dao.UserDAO;
import br.mendonca.testemaven.model.entities.User;
import br.mendonca.testemaven.services.dto.UserDTO;

public class UserService {
	
	public void register(String name, String email, String password, Integer idade, Boolean status) throws ClassNotFoundException, SQLException {
		UserDAO dao = new UserDAO();
		
		User user = new User();
		user.setName(name);
		user.setEmail(email);
		user.setPassword(password);
		user.setIdade(idade);
		user.setStatus(status);
		
		dao.register(user);
	}
	
	public List<UserDTO> listAllUsers() throws ClassNotFoundException, SQLException {
		ArrayList<UserDTO> resp = new ArrayList<UserDTO>();
		
		UserDAO dao = new UserDAO();
		List<User> lista = dao.listAllUser();
		
		for (User user : lista) {
			resp.add(UserDTO.userMapper(user));
		}
		
		return resp;
	}

	public void createFollowRelation(String followerId, String followedId) throws ClassNotFoundException, SQLException {
		UserDAO dao = new UserDAO();

		try {
			dao.createFollowRelation(followerId, followedId);
		}
		catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public List<UserDTO> searchUsers(String searchQuery, Integer idadeMinima, Integer idadeMaxima, Boolean status) throws Exception, SQLException {
		ArrayList<UserDTO> resp = new ArrayList<UserDTO>();

		UserDAO dao = new UserDAO();
		List<User> lista = dao.searchUsersByName(searchQuery, idadeMinima, idadeMaxima, status);
		System.out.println(lista);

		for (User user : lista) {
			resp.add(UserDTO.userMapper(user));
		}

		return resp;
	}
}
