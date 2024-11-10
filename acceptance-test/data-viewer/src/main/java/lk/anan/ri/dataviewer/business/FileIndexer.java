package lk.anan.ri.dataviewer.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.boot.context.event.ApplicationReadyEvent;

import lk.anan.ri.dataviewer.model.FileEntity;
import lk.anan.ri.dataviewer.repository.FileEntityRepository;

@Component
public class FileIndexer {

    @Autowired
    private FileEntityRepository fileEntityRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        // Insert data
        FileEntity fileEntity = new FileEntity();
        fileEntity.setPath("/path/to/file");
        fileEntity.setDatatype("text");
        fileEntity.setModuleOwner("Owner Name " + Math.random());
        fileEntity.setModuleLead("Lead Name");

        fileEntityRepository.save(fileEntity);

        // Add more data as needed
    }
}