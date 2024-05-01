package com.sapientia.aihealth.classification;

import ai.djl.MalformedModelException;
import ai.djl.inference.Predictor;
import ai.djl.modality.Classifications;
import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.translator.ImageClassificationTranslator;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.repository.zoo.ZooModel;
import ai.djl.translate.Pipeline;
import ai.djl.translate.TranslateException;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class ClassificationService {
    private static ZooModel<Image, Classifications> model;
    private static Predictor<Image, Classifications> predictor;

    private static void loadModel() {
        if (model != null) {
            return;
        }
        try {
            Criteria<Image, Classifications> criteria = Criteria.builder()
                    .setTypes(Image.class, Classifications.class)
                    .optModelPath(Paths.get("simple-cnn-kaggle-brain-converted"))
                    //.optTranslator(ImageClassificationTranslator.builder().setSynsetArtifactName("synset.txt").build())
                    .optTranslator(ImageClassificationTranslator.builder().optSynset(List.of("No Tumor", "Tumor")).setPipeline(new Pipeline()).build())
                    .optModelName("saved_model")
                    .build();

            model = criteria.loadModel();
            System.out.println("Successfully loaded model");

        } catch (IOException | ModelNotFoundException | MalformedModelException e) {
            e.printStackTrace();
            System.out.println("Error with loading model");
        }
    }

    public static ZooModel<Image, Classifications> getModel() {
        loadModel();
        return model;
    }

    public static Predictor<Image, Classifications> getPredictor() {
        if (predictor == null){
            predictor = ClassificationService.getModel().newPredictor();
        }
        return predictor;
    }

    public static Classifications predict(Image img) throws TranslateException {
        return getPredictor().predict(img);
    }
}
