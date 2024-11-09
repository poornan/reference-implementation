package lk.anan.ri.dataviewer.controller;

import lk.anan.ri.dataviewer.entity.DataEntity;
import lk.anan.ri.dataviewer.repository.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class DataController {

    @Autowired
    private DataRepository dataRepository;

    @GetMapping("/data")
    public Flux<DataEntity> getData() {
        return dataRepository.findAll();
    }
}