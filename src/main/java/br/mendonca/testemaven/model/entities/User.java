package br.mendonca.testemaven.model.entities;

public class User {
	
	private String uuid;
	private String name;
	private String email;
	private String password;
	private Integer idade;
	private Boolean status;
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getIdade() {return idade;}

	public void setIdade(Integer idade) {this.idade = idade;}

	public Boolean getStatus() {return status;}

	public void setStatus(Boolean status) {this.status = status;}
}
