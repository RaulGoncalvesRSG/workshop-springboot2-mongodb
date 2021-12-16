package com.raul.workshopmongo.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raul.workshopmongo.domain.Post;
import com.raul.workshopmongo.repositories.PostRepository;
import com.raul.workshopmongo.services.excepition.ObjectNotFoundException;

@Service				
public class PostService {

	@Autowired
	private PostRepository repository;
	
	public Post findById(String id) {
		Optional<Post> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado. ID: " + id));
	}
	
	public List<Post> findByTitle(String text) {
		return repository.searchTitle(text);
	}
	
	public List<Post> fullSearch(String text, Date minDate, Date maxDate) {
		//maxDate <= meia noite do prox dia para pegar o dia atual completo. soma 24hs: 24 * 60 * 60 * 1000
		maxDate = new Date(maxDate.getTime() + 24 * 60 * 60 * 1000);
		return repository.fullSearch(text, minDate, maxDate);
	}
}
