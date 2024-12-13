package com.ipt_architecture_course.content_upload_service.service;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ContentProcessingServiceTest {

    private final ContentProcessingService contentProcessingService = new ContentProcessingService();

    @Test
    void testProcessUploadSuccess() {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test.jpg",
                "image/jpeg",
                "test content".getBytes()
        );

        assertDoesNotThrow(() -> contentProcessingService.processUpload(file, "imagem"));
    }

    @Test
    void testProcessUploadFailure() {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test.exe",
                "application/octet-stream",
                "test content".getBytes()
        );

        assertThrows(RuntimeException.class, () -> contentProcessingService.processUpload(file, "imagem"));
    }
}
