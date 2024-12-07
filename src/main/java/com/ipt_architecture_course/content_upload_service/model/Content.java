package com.ipt_architecture_course.content_upload_service.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "content")
public class Content {

    @Id
    private String id;
    private String fileName;
    private String type;
    private String status;

    // Getters and Setters
}

