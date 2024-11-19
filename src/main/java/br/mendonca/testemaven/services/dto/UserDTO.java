package br.mendonca.testemaven.services.dto;

import br.mendonca.testemaven.model.entities.User;

public class UserDTO {
	private String uuid;
	private String name;
	private String email;
	private Integer idade;
	private Boolean status;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUuid() { return uuid;}
	public void setUuid(String uuid) { this.uuid = uuid;}

	public Integer getIdade() {return idade;}

	public void setIdade(Integer idade) {this.idade = idade;}

	public Boolean getStatus() {return status;}

	public void setStatus(Boolean status) {this.status = status;}

	public static UserDTO userMapper(User user) {
		UserDTO dto = new UserDTO();
		dto.setUuid(user.getUuid());
		dto.setName(user.getName());
		dto.setEmail(user.getEmail());
		dto.setIdade(user.getIdade());
		dto.setStatus(user.getStatus());

		return dto;
	}
}
