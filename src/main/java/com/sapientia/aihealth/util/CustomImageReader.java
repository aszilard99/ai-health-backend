package com.sapientia.aihealth.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CustomImageReader {

    public static BufferedImage loadImage(String path) {
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(new File(path));
            bufferedImage = ImageResizer.resizeImage(bufferedImage, 240, 240);


            //for debugging purposes
            ImageIO.write(bufferedImage , "jpg", new File("./test.jpg"));

        } catch (
                IOException e) {
            e.printStackTrace();
        }
        return bufferedImage;
    }
}
