package lk.anan.ri.dataviewer.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

import java.util.Optional;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import lk.anan.ri.dataviewer.config.SecurityConfig;
import lk.anan.ri.dataviewer.model.FileEntity;
import lk.anan.ri.dataviewer.repository.FileEntityRepository;

@WebMvcTest(FileEntityController.class)
@Import(SecurityConfig.class)
public class FileEntityControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FileEntityRepository repository;

    private FileEntity fileEntity;

    @BeforeEach
    void setUp() {
        fileEntity = new FileEntity();
        fileEntity.setId(1L);
        fileEntity.setPath("/path/to/file");
        fileEntity.setDatatype("text");
    }

    @Test
     @WithMockUser(username = "admin")
    void testGetAllFiles() throws Exception {
        given(repository.findAll()).willReturn(Collections.singletonList(fileEntity));

        mockMvc.perform(get("/api/files"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].path").value("/path/to/file"))
                .andExpect(jsonPath("$[0].datatype").value("text"))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "admin")
    void testGetFileById() throws Exception {
        given(repository.findById(1L)).willReturn(Optional.of(fileEntity));

        mockMvc.perform(get("/api/files/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.path").value("/path/to/file"))
                .andExpect(jsonPath("$.datatype").value("text"))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "admin")
    void testCreateFile() throws Exception {
        given(repository.save(any(FileEntity.class))).willReturn(fileEntity);

        mockMvc.perform(post("/api/files")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(fileEntity)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.path").value("/path/to/file"))
                .andExpect(jsonPath("$.datatype").value("text"))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "admin")
    void testUpdateFile() throws Exception {
        given(repository.findById(1L)).willReturn(Optional.of(fileEntity));
        given(repository.save(any(FileEntity.class))).willReturn(fileEntity);

        fileEntity.setPath("/new/path/to/file");
        fileEntity.setDatatype("binary");

        mockMvc.perform(put("/api/files/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(fileEntity)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.path").value("/new/path/to/file"))
                .andExpect(jsonPath("$.datatype").value("binary"))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "admin")
    void testDeleteFile() throws Exception {
        given(repository.findById(1L)).willReturn(Optional.of(fileEntity));

        mockMvc.perform(delete("/api/files/1"))
                .andExpect(status().isNoContent())
                .andDo(print());
    }
}