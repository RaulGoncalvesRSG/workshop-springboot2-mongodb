package com.raul.workshopmongo.services.excepition;

/*Classe para exceção personalizada na camada de serviço.
  Exception exige q o tratamento da exceção seja diferente do RuntimeException*/
public class ObjectNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ObjectNotFoundException(String msg) {
		super(msg);
	}
}
