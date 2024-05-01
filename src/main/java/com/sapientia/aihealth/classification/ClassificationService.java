package com.sapientia.aihealth.classification;

import ai.djl.MalformedModelException;
import ai.djl.modality.Classifications;
import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.translator.ImageClassificationTranslator;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.repository.zoo.ZooModel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ClassificationService {

    public static void loadModel() {

        try {

            byte[] graphDef = Files.readAllBytes(Paths.get("./simple-cnn-kaggle-brain-converted-default"));

            Criteria<Image, Classifications> criteria = Criteria.builder()
                    .setTypes(Image.class, Classifications.class) // defines input and output data type
                    .optModelPath(Paths.get("simple-cnn-kaggle-brain-converted")) // search models in specified path
                    .optEngine("TensorFlow")
                    .build();

            ZooModel<Image, Classifications> model = criteria.loadModel();
            System.out.println("Successfully loaded model");


        } catch (IOException | ModelNotFoundException | MalformedModelException e) {
            e.printStackTrace();
            System.out.println("Error with loading model");
        }
    }
}
