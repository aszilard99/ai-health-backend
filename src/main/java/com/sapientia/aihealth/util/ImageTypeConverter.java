package com.sapientia.aihealth.util;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.opencv.imgcodecs.Imgcodecs.CV_LOAD_IMAGE_UNCHANGED;
import static org.opencv.imgcodecs.Imgcodecs.imdecode;

public class ImageTypeConverter {

    public Mat byteArrayToMat(byte[] imageBytes) {
        //this might not work properly beacuse opencv package has to be staticly imported
        //might have to change constant to: IMREAD_UNCHANGED
        Mat matImage = imdecode(new MatOfByte(imageBytes), CV_LOAD_IMAGE_UNCHANGED);

        return matImage;
    }

    public BufferedImage matToBufferedImage(Mat matImage) throws IOException {
        MatOfByte matOfByte = new MatOfByte();
        Imgcodecs.imencode(".jpg", matImage, matOfByte);
        byte[] byteArray = matOfByte.toArray();
        InputStream is = new ByteArrayInputStream(byteArray);
        BufferedImage bufImage = ImageIO.read(is);

        return bufImage;
    }
}
