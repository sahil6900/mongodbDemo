package com.example.mongodbdemo.controller;

import com.example.mongodbdemo.collection.Person;
import com.example.mongodbdemo.service.PersonService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @PostMapping("/save")
    public ResponseEntity<Person> save(@RequestBody Person person){
        Person savePerson = personService.save(person);

        return new ResponseEntity<Person>(savePerson, HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<List<Person>> getPersonNameStartingWith(@RequestParam String firstName){
        List<Person> personNameStartingWith = personService.getPersonNameStartingWith(firstName);

        return new ResponseEntity<>(personNameStartingWith,HttpStatus.OK);
    }

    @GetMapping("/age")
    public ResponseEntity<List<Person>> getPersonByAge(@RequestParam Integer min,@RequestParam Integer max){
        List<Person> personByAge = personService.getPersonByAge(min, max);

        return new ResponseEntity<List<Person>>(personByAge,HttpStatus.OK);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<Person>> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer minAge,
            @RequestParam(required = false) Integer maxAge,
            @RequestParam(required = false) String city,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size
    ){
        Pageable pageable = PageRequest.of(page,size);
        Page<Person> search = personService.search(name, minAge, maxAge, city, pageable);

        return new ResponseEntity<>(search,HttpStatus.OK);
    }

    @GetMapping("/oldestPerson")
    public ResponseEntity<List<Document>> getOldestPerson(){
        List<Document> oldestPersonByCity = personService.getOldestPersonByCity();
        return new ResponseEntity<>(oldestPersonByCity,HttpStatus.OK);
    }
}
