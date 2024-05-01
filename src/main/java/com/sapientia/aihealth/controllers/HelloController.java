package com.sapientia.aihealth.controllers;

import ai.djl.modality.Classifications;
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
    public Double index() {
        Classifications result;
        double probability = 0;
        try {
            Image img = ImageFactory.getInstance().fromFile(Paths.get("N48.jpeg"));
            result = ClassificationService.predict(img);
            probability = result.getProbabilities().get(0);
            System.out.println(" result " + probability);
        } catch (IOException | TranslateException e) {
            e.printStackTrace();
        }
        return probability;
    }
}
