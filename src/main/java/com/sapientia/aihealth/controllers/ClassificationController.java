package com.sapientia.aihealth.controllers;

import com.sapientia.aihealth.services.ClassificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class ClassificationController {

    //TODO the crossOrigin config was from the video: https://www.youtube.com/watch?v=Ly79YDERpas
    //TODO when dockerizing the app, might need to remove the port from this url
    @PostMapping("/classify")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> classify(MultipartFile image) {
        ClassificationService classificationService = new ClassificationService();
        return classificationService.classify(image);
    }
}