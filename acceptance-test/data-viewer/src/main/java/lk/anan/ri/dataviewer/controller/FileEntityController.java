package lk.anan.ri.dataviewer.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lk.anan.ri.dataviewer.model.FileEntity;
import lk.anan.ri.dataviewer.repository.FileEntityRepository;

@RestController
@RequestMapping("/api/files")
public class FileEntityController {

    @Autowired
    private FileEntityRepository repository;

    @GetMapping
    public List<FileEntity> getAllFiles() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FileEntity> getFileById(@PathVariable Long id) {
        Optional<FileEntity> fileEntity = repository.findById(id);
        if (fileEntity.isPresent()) {
            return ResponseEntity.ok(fileEntity.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public FileEntity createFile(@RequestBody FileEntity fileEntity) {
        return repository.save(fileEntity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FileEntity> updateFile(@PathVariable Long id, @RequestBody FileEntity fileDetails) {
        Optional<FileEntity> fileEntity = repository.findById(id);
        if (fileEntity.isPresent()) {
            FileEntity updatedFile = fileEntity.get();
            updatedFile.setPath(fileDetails.getPath());
            updatedFile.setDatatype(fileDetails.getDatatype());
            return ResponseEntity.ok(repository.save(updatedFile));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFile(@PathVariable Long id) {
        Optional<FileEntity> fileEntity = repository.findById(id);
        if (fileEntity.isPresent()) {
            repository.delete(fileEntity.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}