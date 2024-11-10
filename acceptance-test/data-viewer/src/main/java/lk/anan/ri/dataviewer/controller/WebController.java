package lk.anan.ri.dataviewer.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import lk.anan.ri.dataviewer.model.FileEntity;
import lk.anan.ri.dataviewer.repository.FileEntityRepository;

@Controller
@RequestMapping("/files")
public class WebController {

    @Autowired
    private FileEntityRepository repository;

    @GetMapping
    public String getAllFiles(Model model) {
        List<FileEntity> files = repository.findAll();
        model.addAttribute("files", files);
        return "list";
    }

    @GetMapping("/new")
    public String createFileForm(Model model) {
        model.addAttribute("file", new FileEntity());
        return "form";
    }

    @PostMapping
    public String createFile(FileEntity fileEntity) {
        repository.save(fileEntity);
        return "redirect:/files";
    }

    @GetMapping("/edit/{id}")
    public String editFileForm(@PathVariable Long id, Model model) {
        Optional<FileEntity> fileEntity = repository.findById(id);
        if (fileEntity.isPresent()) {
            model.addAttribute("file", fileEntity.get());
            return "form";
        } else {
            return "redirect:/files";
        }
    }

    @PostMapping("/edit/{id}")
    public String updateFile(@PathVariable Long id, FileEntity fileDetails) {
        Optional<FileEntity> fileEntity = repository.findById(id);
        if (fileEntity.isPresent()) {
            FileEntity updatedFile = fileEntity.get();
            updatedFile.setPath(fileDetails.getPath());
            updatedFile.setDatatype(fileDetails.getDatatype());
            repository.save(updatedFile);
        }
        return "redirect:/files";
    }

    @GetMapping("/delete/{id}")
    public String deleteFile(@PathVariable Long id) {
        Optional<FileEntity> fileEntity = repository.findById(id);
        if (fileEntity.isPresent()) {
            repository.delete(fileEntity.get());
        }
        return "redirect:/files";
    }
}