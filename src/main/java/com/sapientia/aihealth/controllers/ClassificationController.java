package com.sapientia.aihealth.controllers;

import com.sapientia.aihealth.services.ClassificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ClassificationController {

    @Autowired
    ClassificationService classificationService;

    @PostMapping("/classify")
    public ResponseEntity<String> classify(MultipartFile image) {
        return classificationService.classify(image);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<String> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        return new ResponseEntity<>(HttpStatus.PAYLOAD_TOO_LARGE);
    }
}