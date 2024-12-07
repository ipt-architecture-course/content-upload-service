package com.ipt_architecture_course.content_upload_service.controller;

import com.ipt_architecture_course.content_upload_service.service.ContentRetrievalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/download")
public class DownloadController {

    @Autowired
    private ContentRetrievalService contentRetrievalService;

    @GetMapping("/{fileName}")
    @Operation(
            summary = "Download de arquivo",
            description = "Permite o download de um arquivo com base no nome fornecido.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Arquivo encontrado e retornado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Arquivo não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Erro interno no servidor durante o download")
            }
    )
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        try {
            // Recupera o arquivo do serviço
            Resource resource = contentRetrievalService.loadFileAsResource(fileName);

            // Configura os cabeçalhos para o download
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
