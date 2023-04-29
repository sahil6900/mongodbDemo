package com.example.mongodbdemo.repository;

import com.example.mongodbdemo.collection.Person;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface PersonRepository extends MongoRepository<Person,String> {

    List<Person> findByFirstName(String firstName);

    @Query(value = "{'age': {$gt : ?0,$lt : ?1}}",
    fields = "{addresses : 0}")
    List<Person> findPersonByAgeBetween(Integer min,Integer max);
}
