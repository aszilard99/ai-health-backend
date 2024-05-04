package com.sapientia.aihealth.controllers;

import ai.djl.modality.cv.BufferedImageFactory;
import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.ImageFactory;
import com.sapientia.aihealth.ai.preprocessing.ImagePreprocessingService;
import com.sapientia.aihealth.util.ImageResizer;
import com.sapientia.aihealth.util.ImageTypeConverter;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

import static org.opencv.imgcodecs.Imgcodecs.CV_LOAD_IMAGE_UNCHANGED;
import static org.opencv.imgcodecs.Imgcodecs.imdecode;

@RestController
public class ClassificationController {

    @PostMapping("/upload")
    public boolean classify(MultipartFile image){
        boolean res = false;

        try {
            //Image img2 = ImageFactory.getInstance().fromInputStream(image.getInputStream());

            //BufferedImage img = ImageIO.read(image.getInputStream());


            ImagePreprocessingService imgPreprocessor = new ImagePreprocessingService();
            ImageTypeConverter imageTypeConverter = new ImageTypeConverter();

            Mat matImage = imageTypeConverter.byteArrayToMat(image.getBytes());
            //crop brain contour
            matImage = imgPreprocessor.cropBrainContour(matImage);



            //resize
            //BufferedImage processedImg = ImageResizer.resizeImage(img, 240, 240);


            //for debugging
            //res = ImageIO.write(processedImg, "jpg", new File("./processedImg.jpg"));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }
}
