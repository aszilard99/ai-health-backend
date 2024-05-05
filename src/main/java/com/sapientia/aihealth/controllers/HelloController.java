package com.sapientia.aihealth.controllers;

import ai.djl.modality.Classifications;
import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.ImageFactory;
import ai.djl.translate.TranslateException;
import com.sapientia.aihealth.ai.classification.DJLInferenceService;
import com.sapientia.aihealth.util.CustomImageReader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.image.BufferedImage;

@RestController
public class HelloController {
    @GetMapping("/")
    public Double index() {
        Classifications result;
        double probability = 0;
        try {
            //Image img2 = ImageFactory.getInstance().fromFile(Paths.get("N48.jpeg"));

            BufferedImage bufferedImage = CustomImageReader.loadImage("Y103.jpg");

            Image img = ImageFactory.getInstance().fromImage(bufferedImage);


            result = DJLInferenceService.predict(img);
            //probability = result.getProbabilities().get(0);
            System.out.println(" result " + probability);
        } catch (TranslateException e) {
            e.printStackTrace();
        }
        return null;
    }
}
