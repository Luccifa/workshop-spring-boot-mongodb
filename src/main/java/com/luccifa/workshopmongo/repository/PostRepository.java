package com.luccifa.workshopmongo.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.luccifa.workshopmongo.domain.Post;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {

	List<Post> findByTitleContainingIgnoreCase(String text);

	// ?0 = indica o primeiro parametro(text) e i = ignore CaseSensitive
	@Query("{ 'title': { $regex: ?0, $options: 'i' } }")
	List<Post> searchTitle(String text);

	// ?0 - primeiro parametro
	// ?1 - segundo parametro
	// ?2 - terceiro parametro
	// i = ignore CaseSensitive
	@Query("{ $and: [ { date: {$gte: ?1} }, { date: { $lte: ?2} } , "
			+ "{ $or: [ { 'title': { $regex: ?0, $options: 'i' } },"
		 	        + " { 'body': { $regex: ?0, $options: 'i' } }, "
			         + "{ 'comments.text': { $regex: ?0, $options: 'i' } } ] } ] }")
	List<Post> fullSearch(String text, Date minDate, Date maxDate);
	
}
