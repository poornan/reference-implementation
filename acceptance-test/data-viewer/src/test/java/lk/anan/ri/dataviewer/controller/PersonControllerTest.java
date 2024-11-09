
package lk.anan.ri.dataviewer.controller;

import lk.anan.ri.dataviewer.model.Person;
import lk.anan.ri.dataviewer.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.when;

@WebFluxTest(PersonController.class)
public class PersonControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private PersonRepository personRepository;

    private Person person;

    @BeforeEach
    public void setUp() {
        person = new Person();
        person.setId(1L);
        person.setName("John Doe");
    }

    @Test
    public void testGetAllPersons() {
        when(personRepository.findAll()).thenReturn(Flux.just(person));

        webTestClient.get().uri("/persons")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Person.class).hasSize(1).contains(person);
    }

    @Test
    public void testCreatePerson() {
        when(personRepository.save(person)).thenReturn(Mono.just(person));

        webTestClient.post().uri("/persons")
                .bodyValue(person)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Person.class).isEqualTo(person);
    }
}