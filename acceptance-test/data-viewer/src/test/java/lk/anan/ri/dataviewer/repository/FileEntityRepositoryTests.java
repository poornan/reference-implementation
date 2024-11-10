package lk.anan.ri.dataviewer.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import lk.anan.ri.dataviewer.model.FileEntity;

@DataJpaTest
public class FileEntityRepositoryTests {

    @Autowired
    private FileEntityRepository repository;

    @Test
    void testSaveAndFind() {
        FileEntity fileEntity = new FileEntity();
        fileEntity.setPath("/path/to/file");
        fileEntity.setDatatype("text");

        repository.save(fileEntity);

        List<FileEntity> foundEntities = repository.findAll();
        assertThat(foundEntities).hasSize(1);
        assertThat(foundEntities.get(0).getPath()).isEqualTo("/path/to/file");
        assertThat(foundEntities.get(0).getDatatype()).isEqualTo("text");
    }
}