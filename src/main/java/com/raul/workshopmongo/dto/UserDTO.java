package com.raul.workshopmongo.dto;

import java.io.Serializable;

import com.raul.workshopmongo.domain.User;

//O padrão DTO possui os atributos necessários do OBJ para uma determinada necessidade
public class UserDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String name;
	private String email;
	
	public UserDTO() {
	}
	
	public UserDTO(User obj) {			//Instancia um UserDTO a partir de um User
		id = obj.getId();
		name = obj.getName();
		email = obj.getEmail();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
}
