
package lk.anan.ri.dataviewer.repository;

import lk.anan.ri.dataviewer.entity.DataEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface DataRepository extends ReactiveCrudRepository<DataEntity, Long> {
}