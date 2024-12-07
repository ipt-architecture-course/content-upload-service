package com.ipt_architecture_course.content_upload_service.service;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ContentRetrievalService {

    private final Path fileStorageLocation = Paths.get("uploads").toAbsolutePath().normalize();

    public Resource loadFileAsResource(String fileName) throws Exception {
        Path filePath = fileStorageLocation.resolve(fileName).normalize();

        if (Files.exists(filePath)) {
            return new FileSystemResource(filePath);
        } else {
            throw new Exception("Arquivo não encontrado: " + fileName);
        }
    }
}
