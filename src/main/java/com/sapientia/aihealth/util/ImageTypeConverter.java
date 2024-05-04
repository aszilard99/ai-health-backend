package com.sapientia.aihealth.util;

import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.ImageFactory;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.opencv.imgcodecs.Imgcodecs.IMREAD_UNCHANGED;
import static org.opencv.imgcodecs.Imgcodecs.imdecode;

public class ImageTypeConverter {

    public Mat byteArrayToMat(byte[] imageBytes) {
        Mat matImage = imdecode(new MatOfByte(imageBytes), IMREAD_UNCHANGED);

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

    public Image matToDJLImage(Mat matImage) throws IOException {
        MatOfByte matOfByte = new MatOfByte();
        Imgcodecs.imencode(".jpg", matImage, matOfByte);
        byte[] byteArray = matOfByte.toArray();
        InputStream is = new ByteArrayInputStream(byteArray);
        Image image = ImageFactory.getInstance().fromInputStream(is);

        return image;
    }
}
