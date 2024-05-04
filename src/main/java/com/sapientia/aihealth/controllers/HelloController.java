package com.sapientia.aihealth.controllers;

import ai.djl.modality.Classifications;
import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.ImageFactory;
import ai.djl.translate.TranslateException;
import com.sapientia.aihealth.ai.classification.InferenceService;
import com.sapientia.aihealth.util.CustomImageReader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Paths;

@RestController
public class HelloController {
    @GetMapping("/")
    public Double index() {
        Classifications result;
        Classifications result2;
        double probability = 0;
        try {
            Image img2 = ImageFactory.getInstance().fromFile(Paths.get("aug_N16_0_9930_python_processed.jpg"));
            result2 = InferenceService.predict(img2);
            System.out.println(" result2 djl Image " + result2.getProbabilities());

            BufferedImage bufferedImage = CustomImageReader.loadImage("aug_N16_0_9930_python_processed.jpg");

            Image img = ImageFactory.getInstance().fromImage(bufferedImage);


            result = InferenceService.predict(img);
            probability = result.getProbabilities().get(0);
            System.out.println(" result " + result.getProbabilities());
        } catch (TranslateException | IOException e) {
            e.printStackTrace();
        }
        return probability;
    }
}
