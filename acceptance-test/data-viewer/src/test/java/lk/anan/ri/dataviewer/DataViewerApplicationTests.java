package lk.anan.ri.dataviewer;

import lk.anan.ri.dataviewer.entity.DataEntity;
import lk.anan.ri.dataviewer.repository.DataRepository;
import lk.anan.ri.dataviewer.DataViewerApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
@AutoConfigureWebTestClient
public class DataViewerApplicationTests {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private DataRepository dataRepository;

    @BeforeEach
    public void setup() {
        DataEntity dataEntity = new DataEntity();
        dataEntity.setPath("/example/path");
        dataEntity.setDatatype("String");
        dataRepository.save(dataEntity);
    }

    @Test
    public void testLdapAuthentication() {
        webTestClient.get().uri("/data")
            .headers(headers -> headers.setBasicAuth("user", "password"))
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(DataEntity.class).hasSize(1);
    }
}