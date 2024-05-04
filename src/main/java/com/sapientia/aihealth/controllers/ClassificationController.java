package com.sapientia.aihealth.controllers;

import ai.djl.modality.cv.BufferedImageFactory;
import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.ImageFactory;
import com.sapientia.aihealth.util.ImageResizer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

@RestController
public class ClassificationController {

    @PostMapping("/upload")
    public boolean classify(MultipartFile image){
        boolean res = false;

        try {
            //Image img2 = ImageFactory.getInstance().fromInputStream(image.getInputStream());

            BufferedImage img = ImageIO.read(image.getInputStream());

            BufferedImage processedImg = ImageResizer.resizeImage(img, 240, 240);


            //for debugging
            res = ImageIO.write(processedImg, "jpg", new File("./processedImg.jpg"));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }
}
