package com.raul.workshopmongo.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.raul.workshopmongo.domain.Post;

/*Referência 15.3 - Query Methods. Dentro do Repository pode colocar nomes padrão para os métodos q o Spring
 * Data irá fazer a pesquisa automática
 https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/#mongo.transactions	
 
 Query do MongoDB (sintaxe independente da linguagem) - https://docs.mongodb.com/manual/reference/operator/query/and/*/
@Repository							
public interface PostRepository extends MongoRepository<Post, String> {
	
	/*@Query com consulta própria do MDB independente da linguagem. A consulta é personalizada, então pode 
	 * nomear o método da forma desejada."title" é um atributo de Post, o param da query precisa ser igual ao 
	 * atributo do MDB. ?0 indica q é o 1° param, ?1 indica 2° param. "i" para ignore case*/
	@Query("{ 'title': { $regex: ?0, $options: 'i' } }")
	List<Post> searchTitle(String text);
	
	/*findBy(padrão spring) + nome_campo + Contains. Essa declaração faz o Spring Data montar a consuta */
	List<Post> findByTitleContainingIgnoreCase(String text);
	
	/*Consulta - Buscar posts contendo um dado string em qualquer lugar (no título, corpo ou comentários) e em 
	 * um dado intervalo de datas"*/
	//gte (>=) 			lte (<=)
	@Query("{ $and: [ { date: {$gte: ?1} }, { date: { $lte: ?2} } , { $or: [ { 'title': { $regex: ?0, $options: 'i' } }, { 'body': { $regex: ?0, $options: 'i' } }, { 'comments.text': { $regex: ?0, $options: 'i' } } ] } ] }")
	List<Post> fullSearch(String text, Date minDate, Date maxDate);
}
