package com.ipt_architecture_course.microservices.content_upload_service.dto;

import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public class UploadRequest {

    private List<MultipartFile> files;

    public List<MultipartFile> getFiles() {
        return files;
    }

    public void setFiles(List<MultipartFile> files) {
        this.files = files;
    }

    // Add any other fields and their getters/setters if necessary
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}