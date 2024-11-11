package lk.anan.ri.dataviewer.controller;

import lk.anan.ri.dataviewer.model.FileEntity;
import lk.anan.ri.dataviewer.service.FileEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/files")
public class FileEntityController {

    @Autowired
    private FileEntityService fileEntityService;

    @GetMapping
    public List<FileEntity> getAllFiles() {
        return fileEntityService.getAllFiles();
    }

    @GetMapping("/{id}")
    public FileEntity getFileById(@PathVariable Long id) {
        return fileEntityService.getFileById(id);
    }

    @PostMapping
    public FileEntity createFile(@RequestBody FileEntity fileEntity) {
        return fileEntityService.createFile(fileEntity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FileEntity> updateFile(@PathVariable Long id, @RequestBody FileEntity fileDetails) {
        return ResponseEntity.ok(fileEntityService.updateFile(id, fileDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFile(@PathVariable Long id) {
        fileEntityService.deleteFile(id);
        return ResponseEntity.noContent().build();
    }
}