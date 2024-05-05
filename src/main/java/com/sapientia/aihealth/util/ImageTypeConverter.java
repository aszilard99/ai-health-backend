package com.sapientia.aihealth.util;

/*import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.ImageFactory;*/
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.springframework.web.multipart.MultipartFile;
import org.tensorflow.Graph;
import org.tensorflow.Session;
import org.tensorflow.Tensor;
import org.tensorflow.ndarray.ByteNdArray;
import org.tensorflow.ndarray.IntNdArray;
import org.tensorflow.ndarray.NdArrays;
import org.tensorflow.ndarray.Shape;
import org.tensorflow.ndarray.buffer.ByteDataBuffer;
import org.tensorflow.ndarray.buffer.IntDataBuffer;
import org.tensorflow.op.Ops;
import org.tensorflow.op.image.DecodeImage;
import org.tensorflow.op.image.DecodeJpeg;
import org.tensorflow.types.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.opencv.imgcodecs.Imgcodecs.IMREAD_UNCHANGED;
import static org.opencv.imgcodecs.Imgcodecs.imdecode;
import static org.tensorflow.proto.framework.DataType.DT_UINT8;

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

    //might need to play around with Tensor type TFLOAT32 instead of TUint8
    public Tensor multipartFileToTensorV1(MultipartFile image) {
        try {

            BufferedImage img = ImageIO.read(image.getInputStream());
            Raster raster = img.getRaster();

            Tensor reshapedImage = reshape(raster);



            //decodeImageToTensor(raster);

            return null; //TUint8.tensorOf(byteNdArray);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Turns a 1 dimension int array to a Tensor of the shape of the original image from a Raster
     * ***/
    public Tensor reshape(Raster raster) {

        int numBands = raster.getNumBands();
        int width = raster.getWidth();
        int height = raster.getHeight();
        int[] pixels = new int[width * height * numBands];
        // Get pixel data from the Raster
        raster.getPixels(0, 0, width, height, pixels);

        //int[][][] reshapedImage = new int[width][height][numBands];
        IntNdArray input_matrix = NdArrays.ofInts(Shape.of(width, height, numBands));

        int counter = 0;
        for (int channel = 0; channel < numBands; channel++) {
            for (int h = 0; h < height; h++) {
                for (int w = 0 ; w < width; w++) {
                    input_matrix.set(NdArrays.scalarOf(pixels[counter]),w,h,channel);
                    counter++;
                }
            }
        }

        Tensor tensor = TInt32.tensorOf(input_matrix);

        return tensor;
    }

    /*public Image matToDJLImage(Mat matImage) throws IOException {
        MatOfByte matOfByte = new MatOfByte();
        Imgcodecs.imencode(".jpg", matImage, matOfByte);
        byte[] byteArray = matOfByte.toArray();
        InputStream is = new ByteArrayInputStream(byteArray);
        Image image = ImageFactory.getInstance().fromInputStream(is);

        return image;
    }*/

    /*public Tensor decodeImageToTensor(Raster raster) {

        int numBands = raster.getNumBands();
        int width = raster.getWidth();
        int height = raster.getHeight();
        int[] pixels = new int[width * height * numBands];
        // Get pixel data from the Raster
        raster.getPixels(0, 0, width, height, pixels);

        IntNdArray input_matrix = NdArrays.ofInts(Shape.of(240, 240, 3));
        input_matrix.set(NdArrays.vectorOf(pixels));

        Tensor tensor = TInt32.tensorOf(input_matrix);

        return tensor;
    }*/
}
