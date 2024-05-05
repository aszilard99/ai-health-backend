package com.sapientia.aihealth.controllers;

import ai.djl.modality.Classifications;
import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.ImageFactory;
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

            matImage = imgPreprocessor.cropBrainContour(matImage);
            //res = ImageIO.write(imageTypeConverter.matToBufferedImage(matImage), "jpg", new File("./processedImg.jpg"));
            imgPreprocessor.resizeImage(matImage, 240, 240);
            // = ImageIO.write(imageTypeConverter.matToBufferedImage(matImage), "jpg", new File("./processedResizedImg.jpg"));
            Mat normalizedMatImage = imgPreprocessor.normalizeImage(matImage);
            //System.out.println("Normalized value in controller at (100,100): " + normalizedMatImage.get(100, 100)[0]);

            Image djlImage = imageTypeConverter.matToDJLImage(normalizedMatImage);

            //Image djlImage = ImageFactory.getInstance().fromInputStream(image.getInputStream());
            Classifications result = InferenceService.predict(djlImage);

            System.out.println("result: " + result.getProbabilities());

            //for debugging
            BufferedImage bufferedImage = imageTypeConverter.matToBufferedImage(normalizedMatImage);
            res = ImageIO.write(bufferedImage, "jpg", new File("./javaProcessed.jpg"));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }
}
