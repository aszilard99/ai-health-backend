package com.sapientia.aihealth.ai.classification;

import org.tensorflow.SavedModelBundle;

public class TfInferenceService {
    private SavedModelBundle model;
    public SavedModelBundle loadModel() {
        if (model != null) {
            return model;
        }
        String path = "./colab_simplecnn_savedmodel_format";
        model = SavedModelBundle.load(path, "serve");
        return model;
    }
}
