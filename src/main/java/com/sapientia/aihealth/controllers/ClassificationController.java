package com.sapientia.aihealth.controllers;

import ai.djl.modality.Classifications;
import ai.djl.modality.cv.Image;
import com.sapientia.aihealth.ai.classification.InferenceService;
import com.sapientia.aihealth.ai.preprocessing.ImagePreprocessingService;
import com.sapientia.aihealth.util.ImageTypeConverter;
import org.opencv.core.Mat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.Buffer;

@RestController
public class ClassificationController {

    @PostMapping("/upload")
    public boolean classify(MultipartFile image){
        boolean res = false;
        try {
            ImagePreprocessingService imgPreprocessor = new ImagePreprocessingService();
            ImageTypeConverter imageTypeConverter = new ImageTypeConverter();

            Mat matImage = imageTypeConverter.byteArrayToMat(image.getBytes());

            //matImage = imgPreprocessor.cropBrainContour(matImage);
            //imgPreprocessor.resizeImage(matImage, 240, 240);
            //Mat normalizedMatImage = imgPreprocessor.normalizeImage(matImage);
            //System.out.println("Normalized value in controller at (100,100): " + normalizedMatImage.get(100, 100)[0]);

            Image djlImage = imageTypeConverter.matToDJLImage(matImage);
            Classifications result = InferenceService.predict(djlImage);
            System.out.println(result.getProbabilities().toString());
            /*//for debugging
            //for some reason after normalizing, the ImageIO.write function cant create a jpg from the mat file
            BufferedImage bufferedImage = imageTypeConverter.matToBufferedImage(normalizedMatImage);
            res = ImageIO.write(bufferedImage, "jpg", new File("./processedNormalizedImg.jpg"));
            System.out.println("Normalized value in controller at (100,100): " + normalizedMatImage.get(100, 100)[0]);*/
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
}
