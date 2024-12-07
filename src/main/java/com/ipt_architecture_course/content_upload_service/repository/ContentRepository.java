package com.ipt_architecture_course.content_upload_service.repository;


import com.ipt_architecture_course.content_upload_service.model.Content;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ContentRepository extends MongoRepository<Content, String> {
}


