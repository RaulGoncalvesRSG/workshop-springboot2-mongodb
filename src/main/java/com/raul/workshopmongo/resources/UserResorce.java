package com.raul.workshopmongo.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.raul.workshopmongo.domain.Post;
import com.raul.workshopmongo.domain.User;
import com.raul.workshopmongo.dto.UserDTO;
import com.raul.workshopmongo.services.UserService;

@RestController
@RequestMapping(value = "/users")				//Caminho do Endpoint
/*O controlador Rest acessa a camada de serviços; o serviço acessa o repositório*/
public class UserResorce {
	
	/*Controlador Rest recebe a requisição do usuário e passa a chamada para o serviço. O serviço passa a
	 chamada para o repository. O service é o meio campo entre as duas camadas*/
	@Autowired
	private UserService service;
	
	/*ResponseEntity - esse obj encapsula td a estrutura necessária para retornar uma resposta http já com 
	possíveis cabeçalhos, erros*/
	@GetMapping
	public ResponseEntity<List<UserDTO>> findAll(){
		List<User> list = service.findAll();				//Pega a lista no BD
		//Faz o mapeamento e converte a lista de User para uma lista de UserDTO
		List<UserDTO> listDto = list.stream().map(user -> new UserDTO(user)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(listDto);			//Passa a lista no corpo da resposta
	}
	
	@GetMapping(value="/{id}")		//@PathVariable - faz o parâmetro do método ser o msmo recebido na url
 	public ResponseEntity<UserDTO> findById(@PathVariable String id) {
		User obj = service.findById(id);
		return ResponseEntity.ok().body(new UserDTO(obj));
	}

	@PostMapping			//@RequestBody - Para o endpoint aceitar o obj
 	public ResponseEntity<Void> insert(@RequestBody UserDTO objDto) {
		User obj = service.fromDTO(objDto);				//Converte o DTO em obj User
		obj = service.insert(obj);
		
		//Coloca na resposta o cabeçalho do novo recurso criado, isso é uma boa prática
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();			//created retorna o status 201 do http
	}

	@DeleteMapping(value="/{id}")
 	public ResponseEntity<Void> delete(@PathVariable String id) {
		service.delete(id);
		return ResponseEntity.noContent().build();		//código 204 qnd n retorna nada
	}

	@PutMapping(value="/{id}")
 	public ResponseEntity<Void> update(@RequestBody UserDTO objDto, @PathVariable String id) {
		User obj = service.fromDTO(objDto);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping(value="/{id}/posts")
 	public ResponseEntity<List<Post>> findPosts(@PathVariable String id) {
		User obj = service.findById(id);
		return ResponseEntity.ok().body(obj.getPosts());
	}
	
	@GetMapping(value="/ageSearch")
 	public ResponseEntity<List<User>> ageSearch(@RequestParam(value="minAge", defaultValue="0") Integer minAge,
 			@RequestParam(value="maxAge", defaultValue="100") Integer maxAge) {
		
		List<User> list = service.findByAge(minAge, maxAge);
		
		return ResponseEntity.ok().body(list);
	}
}
