package com.ipt_architecture_course.microservices.content_upload_service.controller;

import com.ipt_architecture_course.microservices.content_upload_service.dto.UploadRequest;
import com.ipt_architecture_course.microservices.content_upload_service.service.ContentProcessingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/upload")
public class UploadController {

    @Autowired
    private ContentProcessingService contentProcessingService;

    @PostMapping
    @Operation(
            summary = "Upload de arquivos",
            description = "Realiza o upload de um ou mais arquivos e processa o conteúdo enviado.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "multipart/form-data",
                            schema = @Schema(implementation = UploadRequest.class)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Uploads processados com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Um ou mais arquivos enviados não são válidos"),
                    @ApiResponse(responseCode = "500", description = "Erro interno no servidor durante o processamento")
            }
    )
    public ResponseEntity<String> uploadContent(@ModelAttribute UploadRequest uploadRequest) {

        List<String> successfulUploads = new ArrayList<>();
        List<String> failedUploads = new ArrayList<>();

        // Processar cada arquivo
        for (MultipartFile file : uploadRequest.getFiles()) {
            if (!file.getContentType().startsWith("image/")) {
                failedUploads.add(file.getOriginalFilename() + " (tipo inválido)");
                continue;
            }

            try {
                // Processar o arquivo utilizando o serviço
                contentProcessingService.processUpload(file, uploadRequest.getType());
                successfulUploads.add(file.getOriginalFilename());
            } catch (Exception e) {
                failedUploads.add(file.getOriginalFilename() + ": " + e.getMessage());
            }
        }

        // Montar a resposta com os resultados
        StringBuilder responseMessage = new StringBuilder("Resultado do upload:\n");
        responseMessage.append("Sucessos: ").append(successfulUploads.size()).append("\n")
                .append(String.join(", ", successfulUploads)).append("\n")
                .append("Falhas: ").append(failedUploads.size()).append("\n")
                .append(String.join(", ", failedUploads));

        return ResponseEntity.ok(responseMessage.toString());
    }
}
