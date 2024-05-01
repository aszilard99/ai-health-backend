package com.sapientia.aihealth.controllers;

import com.sapientia.aihealth.classification.ClassificationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/")
    public String index() {

        ClassificationService.loadModel();

        return "Greetings!";
    }
}
