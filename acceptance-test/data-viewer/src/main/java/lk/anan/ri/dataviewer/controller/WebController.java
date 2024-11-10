package lk.anan.ri.dataviewer.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import jakarta.servlet.http.HttpServletRequest;
import lk.anan.ri.dataviewer.model.FileEntity;
import lk.anan.ri.dataviewer.repository.FileEntityRepository;

@Controller
@RequestMapping("/files")
public class WebController {

    @Autowired
    private FileEntityRepository repository;

    @Autowired
    private HttpServletRequest request;

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
        String currentUser = request.getUserPrincipal().getName();
        fileEntity.setCreatedBy(currentUser); 
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
            updatedFile.setDevelopers(fileDetails.getDevelopers());
            updatedFile.setLead(fileDetails.getLead());
            updatedFile.setCreatedBy(fileDetails.getCreatedBy());
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

    @GetMapping("/view/{id}")
    public String viewFile(@PathVariable Long id, Model model) {
        Optional<FileEntity> fileEntity = repository.findById(id);
        if (fileEntity.isPresent()) {
            String path = fileEntity.get().getPath();
            try {
                String xmlContent = new String(Files.readAllBytes(Paths.get(path)));
                XmlMapper xmlMapper = new XmlMapper();
                Object xmlObject = xmlMapper.readValue(xmlContent, Object.class);
                String formattedXml = xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(xmlObject);
                model.addAttribute("xmlContent", formattedXml);
            } catch (IOException e) {
                model.addAttribute("xmlContent", "Error reading file: " + e.getMessage());
            }
            return "view";
        } else {
            return "redirect:/files";
        }
    }
}