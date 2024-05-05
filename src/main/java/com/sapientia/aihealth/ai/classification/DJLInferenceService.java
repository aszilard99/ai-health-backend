package com.sapientia.aihealth.ai.classification;

/*import ai.djl.MalformedModelException;
import ai.djl.inference.Predictor;
import ai.djl.modality.Classifications;
import ai.djl.modality.cv.Image;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.repository.zoo.ZooModel;
import ai.djl.translate.TranslateException;

import java.io.IOException;
import java.nio.file.Paths;

public class DJLInferenceService {
    private static ZooModel<Image, Classifications> model;
    private static Predictor<Image, Classifications> predictor;

    private static void loadModel() {
        if (model != null) {
            return;
        }
        try {
            Criteria<Image, Classifications> criteria = Criteria.builder()
                    .setTypes(Image.class, Classifications.class)
                    .optModelPath(Paths.get("colab_simplecnn_savedmodel_format"))
                    //.optTranslator(ImageClassificationTranslator.builder().setSynsetArtifactName("synset.txt").build())
                    //.optModelName("saved_model")
                    .optEngine("TensorFlow")
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
            predictor = DJLInferenceService.getModel().newPredictor();
        }
        return predictor;
    }

    public static Classifications predict(Image img) throws TranslateException {
        return getPredictor().predict(img);
    }
}
*/