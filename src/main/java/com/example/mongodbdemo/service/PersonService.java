package com.example.mongodbdemo.service;

import com.example.mongodbdemo.collection.Person;
import org.bson.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PersonService {

    Person save(Person person);

    List<Person> getPersonNameStartingWith(String firstName);

    List<Person> getPersonByAge(Integer min, Integer max);

    Page<Person> search(String name, Integer minAge, Integer maxAge, String city, Pageable pageable);

    List<Document> getOldestPersonByCity();
}
