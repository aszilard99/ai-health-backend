package com.sapientia.aihealth.controllers;

import com.sapientia.aihealth.services.ClassificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class ClassificationController {

    @PostMapping("/classify")
    public String classify(MultipartFile image) {
        ClassificationService classificationService = new ClassificationService();
        return classificationService.classify(image).getBody();
    }
}