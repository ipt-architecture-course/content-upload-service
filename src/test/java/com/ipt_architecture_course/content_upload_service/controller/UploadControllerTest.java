package com.ipt_architecture_course.content_upload_service.controller;

import com.ipt_architecture_course.content_upload_service.service.ContentProcessingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UploadController.class)
class UploadControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContentProcessingService contentProcessingService;

    @Test
    void testUploadContentSuccess() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "files",
                "test.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "test content".getBytes()
        );

        // Configura o mock para simular o sucesso no processamento
        doNothing().when(contentProcessingService).processUpload(file, "imagem");

        mockMvc.perform(multipart("/api/upload")
                        .file(file)
                        .param("type", "imagem"))
                .andExpect(status().isOk()); // Espera 200 OK
    }

    @Test
    void testUploadContentWithInvalidParameter() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "null",
                "test.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "test content".getBytes()
        );

        // Não é necessário configurar o mock aqui, pois o erro será causado pelo parâmetro inválido.

        mockMvc.perform(multipart("/api/upload")
                        .file(file)
                        .param("invalidParam", "wrongValue")) // Enviando parâmetro inválido
                .andExpect(status().isInternalServerError()); // Espera 500
    }


}
