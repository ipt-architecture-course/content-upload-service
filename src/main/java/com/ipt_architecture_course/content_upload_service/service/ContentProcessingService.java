package com.ipt_architecture_course.content_upload_service.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ContentProcessingService {

    private static final String UPLOAD_DIRECTORY = "uploads/";

    public void processUpload(MultipartFile file, String type) throws IOException {
        // Criar o diretório se não existir
        Path uploadPath = Paths.get(UPLOAD_DIRECTORY);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Salvar o arquivo na pasta de uploads
        String fileName = file.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);

        Files.write(filePath, file.getBytes());

        System.out.println("Imagem salva em: " + filePath.toAbsolutePath());
    }
}
