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
    public void testSaveAndFindFileEntity() {
        FileEntity fileEntity = new FileEntity();
        fileEntity.setPath("/path/to/file");
        fileEntity.setDatatype("text");
        fileEntity.setDevelopers("Owner Name"); // Set new field
        fileEntity.setLead("Lead Name");   // Set new field

        repository.save(fileEntity);

        List<FileEntity> fileEntities = repository.findAll();
        assertThat(fileEntities).hasSize(1);
        FileEntity savedFileEntity = fileEntities.get(0);
        assertThat(savedFileEntity.getPath()).isEqualTo("/path/to/file");
        assertThat(savedFileEntity.getDatatype()).isEqualTo("text");
        assertThat(savedFileEntity.getDevelopers()).isEqualTo("Owner Name"); // Assert new field
        assertThat(savedFileEntity.getLead()).isEqualTo("Lead Name");   // Assert new field
    }
}