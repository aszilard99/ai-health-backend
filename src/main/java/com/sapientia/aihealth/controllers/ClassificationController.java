package com.sapientia.aihealth.controllers;

import com.sapientia.aihealth.ai.preprocessing.ImagePreprocessingService;
import com.sapientia.aihealth.util.ImageTypeConverter;
import org.opencv.core.Mat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.File;

@RestController
public class ClassificationController {

    @PostMapping("/upload")
    public boolean classify(MultipartFile image){
        boolean res = false;

        try {

            ImagePreprocessingService imgPreprocessor = new ImagePreprocessingService();
            ImageTypeConverter imageTypeConverter = new ImageTypeConverter();

            Mat matImage = imageTypeConverter.byteArrayToMat(image.getBytes());
            //crop brain contour
            matImage = imgPreprocessor.cropBrainContour(matImage);

            //for debugging
            res = ImageIO.write(imageTypeConverter.matToBufferedImage(matImage), "jpg", new File("./processedImg.jpg"));

            imgPreprocessor.resizeImage(matImage, 240, 240);

            //for debugging
            res = ImageIO.write(imageTypeConverter.matToBufferedImage(matImage), "jpg", new File("./processedResizedImg.jpg"));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }
}
