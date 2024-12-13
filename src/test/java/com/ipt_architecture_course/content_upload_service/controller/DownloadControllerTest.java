package com.ipt_architecture_course.content_upload_service.controller;

import com.ipt_architecture_course.content_upload_service.service.ContentRetrievalService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DownloadController.class)
class DownloadControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContentRetrievalService contentRetrievalService;

    @Test
    void testDownloadFileSuccess() throws Exception {
        String fileName = "test.jpg";

        // Mock do Resource com nome configurado
        Resource resource = new ByteArrayResource("test content".getBytes()) {
            @Override
            public String getFilename() {
                return fileName; // Retorne o nome do arquivo esperado
            }
        };

        when(contentRetrievalService.loadFileAsResource(fileName)).thenReturn(resource);

        mockMvc.perform(get("/api/download/" + fileName))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", "attachment; filename=\"" + fileName + "\""))
                .andExpect(content().bytes("test content".getBytes()));
    }

    @Test
    void testDownloadFileNotFound() throws Exception {
        String fileName = "nonexistent.jpg";

        // Simula uma exceção ao tentar carregar um arquivo inexistente
        when(contentRetrievalService.loadFileAsResource(fileName)).thenThrow(new RuntimeException("File not found"));

        mockMvc.perform(get("/api/download/" + fileName))
                .andExpect(status().isNotFound());
    }
}
