
package lk.anan.ri.dataviewer.repository;

import lk.anan.ri.dataviewer.model.Person;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface PersonRepository extends ReactiveCrudRepository<Person, Long> {
}