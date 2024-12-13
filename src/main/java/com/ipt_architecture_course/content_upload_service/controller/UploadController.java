package com.ipt_architecture_course.content_upload_service.controller;

import com.ipt_architecture_course.content_upload_service.dto.UploadRequest;
import com.ipt_architecture_course.content_upload_service.service.ContentProcessingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/upload")
public class UploadController {

    @Autowired
    private ContentProcessingService contentProcessingService;

    private static final List<String> ALLOWED_TYPES = List.of("image/", "video/");

    @PostMapping
    @Operation(
            summary = "Upload de arquivos",
            description = "Realiza o upload de um ou mais arquivos (imagens ou vídeos) e processa o conteúdo enviado.",
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
        try {
            // Processar uploads bem-sucedidos
            List<String> successfulUploads = uploadRequest.getFiles().stream()
                    .filter(this::isAllowedType)
                    .map(file -> processFile(file, uploadRequest.getType()))
                    .filter(result -> result.startsWith("SUCCESS:"))
                    .map(result -> result.replace("SUCCESS:", ""))
                    .collect(Collectors.toList());

            // Identificar falhas no upload
            List<String> failedUploads = uploadRequest.getFiles().stream()
                    .filter(file -> !isAllowedType(file))
                    .map(file -> file.getOriginalFilename() + " (tipo inválido: " + file.getContentType() + ")")
                    .collect(Collectors.toList());

            // Construir mensagem de resposta
            String responseMessage = buildResponseMessage(successfulUploads, failedUploads);

            // Retornar 500 se houver falhas no upload
            if (!failedUploads.isEmpty()) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
            }

            return ResponseEntity.ok(responseMessage);

        } catch (Exception e) {
            // Capturar exceções inesperadas e retornar 500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao processar upload: " + e.getMessage());
        }
    }

    private boolean isAllowedType(MultipartFile file) {
        return ALLOWED_TYPES.stream().anyMatch(type -> file.getContentType().startsWith(type));
    }

    private String processFile(MultipartFile file, String type) {
        try {
            contentProcessingService.processUpload(file, type);
            return "SUCCESS:" + file.getOriginalFilename();
        } catch (Exception e) {
            return "ERROR:" + file.getOriginalFilename() + ": " + e.getMessage();
        }
    }

    private String buildResponseMessage(List<String> successfulUploads, List<String> failedUploads) {
        return String.format("Resultado do upload:\nSucessos: %d\n%s\nFalhas: %d\n%s",
                successfulUploads.size(),
                String.join(", ", successfulUploads),
                failedUploads.size(),
                String.join(", ", failedUploads));
    }
}
