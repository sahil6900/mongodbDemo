package com.example.mongodbdemo.service;

import com.example.mongodbdemo.collection.Person;
import com.example.mongodbdemo.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonServiceImpl implements PersonService{

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private MongoTemplate mongoTemplate;
    @Override
    public Person save(Person person) {
        Person savePerson = personRepository.save(person);
        return savePerson;
    }

    @Override
    public List<Person> getPersonNameStartingWith(String firstName) {
        List<Person> byFirstName = personRepository.findByFirstName(firstName);
        return byFirstName;
    }

    @Override
    public List<Person> getPersonByAge(Integer min, Integer max) {
        List<Person> personByAgeBetween = personRepository.findPersonByAgeBetween(min, max);
        return personByAgeBetween;
    }

    @Override
    public Page<Person> search(String name, Integer minAge, Integer maxAge, String city, Pageable pageable) {
        Query query = new Query().with(pageable);

        List<Criteria> criteria = new ArrayList<>();

        if(name!=null && !name.isEmpty()){
            criteria.add(Criteria.where("firstName").regex(name));
        }
        if(minAge!=null && maxAge!=null){
            criteria.add(Criteria.where("age").gte(minAge).lte(maxAge));
        }
        if(city!=null && !city.isEmpty()){
            criteria.add(Criteria.where("addresses.city").is(city));
        }
        if(!criteria.isEmpty()){
            query.addCriteria(new Criteria()
                    .andOperator(criteria.toArray(new Criteria[0])));
        }
        Page<Person> people = PageableExecutionUtils.getPage(
              mongoTemplate.find(query,Person.class),
                pageable,()->mongoTemplate.count(query.skip(0).limit(0),Person.class));

        return people;
    }
}
