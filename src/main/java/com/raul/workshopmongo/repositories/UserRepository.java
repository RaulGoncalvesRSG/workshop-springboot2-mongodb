package com.raul.workshopmongo.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.raul.workshopmongo.domain.User;

@Repository							
//Tipo da classe de domínimo e tipo do ID. Isso é o suficiente para fazer as requisições no BD
public interface UserRepository extends MongoRepository<User, String> {

	@Query("{ $and: [ { 'age': {$gte: ?0} }, { 'age': { $lte: ?1} } ] }")
	List<User> betweenAge(Integer minAge, Integer maxAge);
}
