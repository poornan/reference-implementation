package lk.anan.ri.dataviewer.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import jakarta.servlet.http.HttpServletRequest;
import lk.anan.ri.dataviewer.model.FileEntity;
import lk.anan.ri.dataviewer.service.FileEntityService;

@Controller
@RequestMapping("/files")
public class WebController {

    @Autowired
    private FileEntityService fileEntityService;

    @Autowired
    private HttpServletRequest request;

    @GetMapping
    public String getAllFiles(Model model) {
        List<FileEntity> files = fileEntityService.getAllFiles();
        model.addAttribute("files", files);
        return "list";
    }

    @GetMapping("/date/{dateCreated}")
    public String getFilesByDateCreated(@PathVariable String dateCreated, Model model) {
        LocalDate date = LocalDate.parse(dateCreated);
        List<FileEntity> files = fileEntityService.getFilesByDateCreated(date.atStartOfDay());
        model.addAttribute("files", files);
        model.addAttribute("dateCreated", dateCreated);
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
        fileEntity.setConfirmedBy(currentUser);
        fileEntityService.createFile(fileEntity);
        return "redirect:/files";
    }

    @GetMapping("/edit/{id}")
    public String editFileForm(@PathVariable Long id, Model model) {
        try {
            FileEntity fileEntity = fileEntityService.getFileById(id);
            model.addAttribute("file", fileEntity);
            return "form";
        } catch (RuntimeException e) {
            return "redirect:/files";
        }
    }

    @PostMapping("/edit/{id}")
    public String updateFile(@PathVariable Long id, FileEntity fileDetails) {
        try {
            fileEntityService.updateFile(id, fileDetails);
            return "redirect:/files";
        } catch (RuntimeException e) {
            return "redirect:/files";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteFile(@PathVariable Long id) {
        try {
            fileEntityService.deleteFile(id);
            return "redirect:/files";
        } catch (RuntimeException e) {
            return "redirect:/files";
        }
    }

    @PreAuthorize("hasRole('ADMIN') or principal.username == 'admin'")
    @GetMapping("/view/{id}")
    public String viewFile(@PathVariable Long id, Model model) {
        try {
            FileEntity fileEntity = fileEntityService.getFileById(id);
            String path = fileEntity.getPath();
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
        } catch (RuntimeException e) {
            return "redirect:/files";
        }
    }

    @GetMapping("/{id}/confirm")
    public String confirmFile(@PathVariable Long id) {
        try {
            fileEntityService.confirmFile(id, request.getUserPrincipal().getName());
            return "redirect:/files";
        } catch (RuntimeException e) {
            return "redirect:/files";
        }
    }

    @GetMapping("/{id}/approve")
    public String approveFile(@PathVariable Long id) {
        try {
            fileEntityService.approveFile(id, request.getUserPrincipal().getName());
            return "redirect:/files";
        } catch (RuntimeException e) {
            return "redirect:/files";
        }
    }
}