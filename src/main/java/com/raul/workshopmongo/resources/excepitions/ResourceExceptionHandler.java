package com.raul.workshopmongo.resources.excepitions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.raul.workshopmongo.services.excepition.ObjectNotFoundException;

/*Classe para ser o manipulador de exceções na camada de Resources. Fazendo isso, evita utilizar try catch
  e colocar tratamento em cada método no Resource. 
  @ControllerAdvice indica q a classe é responsável por tratar possíveis erros nas requisições e consegue interceptar os erros*/
@ControllerAdvice
public class ResourceExceptionHandler {
	
	//@ExceptionHandler - Chama o método qnd ocorrer exceção na classe especificada (ObjectNotFoundException)
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;			//NOT_FOUND é o status 404	
		
		StandardError error = new StandardError();
		error.setTimestamp(System.currentTimeMillis());
		error.setStatus(status.value());		
		error.setError("Não encontrado");
		error.setMessage(e.getMessage());
		error.setPath(request.getRequestURI());				//Caminho da requisição
		
		return ResponseEntity.status(status).body(error);
	}
}