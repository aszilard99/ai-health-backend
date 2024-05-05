package com.sapientia.aihealth.controllers;

/*import ai.djl.modality.Classifications;
import ai.djl.modality.cv.Image;
import com.sapientia.aihealth.ai.classification.DJLInferenceService;*/
import com.sapientia.aihealth.ai.classification.TfInferenceService;
import com.sapientia.aihealth.ai.preprocessing.ImagePreprocessingService;
import com.sapientia.aihealth.util.ImageTypeConverter;
import org.opencv.core.Mat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.tensorflow.Result;
import org.tensorflow.SavedModelBundle;
import org.tensorflow.Tensor;
import org.tensorflow.ndarray.IntNdArray;
import org.tensorflow.ndarray.NdArray;
import org.tensorflow.ndarray.NdArrays;
import org.tensorflow.ndarray.Shape;
import org.tensorflow.op.image.DecodeImage;
import org.tensorflow.op.image.DecodeJpeg;
import org.tensorflow.types.TFloat32;
import org.tensorflow.types.TUint8;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ClassificationController {

    /*@PostMapping("/v1/upload")
    public boolean classifyV1(MultipartFile image){
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
            System.out.println(normalizedMatImage.dims());

            Image djlImage = imageTypeConverter.matToDJLImage(normalizedMatImage);

            //Image djlImage = ImageFactory.getInstance().fromInputStream(image.getInputStream());
            Classifications result = DJLInferenceService.predict(djlImage);

            System.out.println("result: ");

            //for debugging
            BufferedImage bufferedImage = imageTypeConverter.matToBufferedImage(normalizedMatImage);
            res = ImageIO.write(bufferedImage, "jpg", new File("./javaProcessed.jpg"));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }*/

    @PostMapping("/v2/upload")
    public void classifyV2(MultipartFile image) {

        try {
            ImageTypeConverter imageTypeConverter = new ImageTypeConverter();
            BufferedImage img = ImageIO.read(image.getInputStream());

            // Load model
            TfInferenceService inferenceService = new TfInferenceService();
            SavedModelBundle model = inferenceService.loadModel();

            // Get pixels from image
            Raster raster = img.getRaster();

            // Convert
            Tensor inputTensor = imageTypeConverter.rasterToTensor(raster);


            Map<String, Tensor> feed_dict = new HashMap<>();
            feed_dict.put("input_1", inputTensor);

            Result res = model.function("serving_default").call(feed_dict);


            System.out.println("a");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
