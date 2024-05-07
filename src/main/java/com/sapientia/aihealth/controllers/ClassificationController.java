package com.sapientia.aihealth.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class ClassificationController {

    @PostMapping("/v2/upload")
    public void classifyV2(MultipartFile image) {
    }
}