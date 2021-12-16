package com.raul.workshopmongo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raul.workshopmongo.domain.User;
import com.raul.workshopmongo.dto.UserDTO;
import com.raul.workshopmongo.repositories.UserRepository;
import com.raul.workshopmongo.services.excepition.ObjectNotFoundException;

@Service				//Poderia ser @Component, mas o @Service melhora na semântica
public class UserService {

	/*Para instanciar automaticamente o obj no serviço. É um mecanismo de injeção de dependência automática 
	do spring*/
	@Autowired
	private UserRepository repository;
	
	public List<User> findAll(){
		return repository.findAll();
	}
	
	public User findById(String id) {
		Optional<User> obj = repository.findById(id);
		//orElseThrow lança uma exceção e espera como arg uma função lambda. 
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado. ID: " + id));
	}
	
	public User insert(User obj) {
		return repository.insert(obj);
	}
	
	public void delete(String id) {
		findById(id);					//Busca o obj e caso ele n exista, já faz o tratamento no findById
		repository.deleteById(id);
	}

	public User update(User obj) {
		User newObj = findById(obj.getId());	//newObj é o obj original do BD
		updateData(newObj, obj);
		return repository.save(newObj);
	}

	private void updateData(User newObj, User obj) {
		newObj.setName(obj.getName());
		newObj.setEmail(obj.getEmail());
	}
	
	public User fromDTO(UserDTO objDTO) {
		return new User(objDTO.getId(), objDTO.getName(), objDTO.getEmail(), null, null);
	}

	public List<User> findByAge(Integer minAge, Integer maxAge) {
		return repository.betweenAge(minAge, maxAge);
	}
}
