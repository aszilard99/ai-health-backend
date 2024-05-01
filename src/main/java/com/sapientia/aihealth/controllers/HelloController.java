package com.sapientia.aihealth.controllers;

import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.ImageFactory;
import ai.djl.translate.TranslateException;
import com.sapientia.aihealth.classification.ClassificationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Paths;

@RestController
public class HelloController {
    @GetMapping("/")
    public String index() {

        try {
            Image img = ImageFactory.getInstance().fromFile(Paths.get("Y11.jpg"));
            ClassificationService.predict(img);

        } catch (IOException | TranslateException e) {
            e.printStackTrace();
        }
        return "Greetings!";
    }
}
