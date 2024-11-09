
package lk.anan.ri.dataviewer.controller;

import lk.anan.ri.dataviewer.model.Person;
import lk.anan.ri.dataviewer.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/persons")
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @GetMapping
    public Flux<Person> getAllPersons() {
        return personRepository.findAll();
    }

    @PostMapping
    public Mono<Person> createPerson(@RequestBody Person person) {
        return personRepository.save(person);
    }
}