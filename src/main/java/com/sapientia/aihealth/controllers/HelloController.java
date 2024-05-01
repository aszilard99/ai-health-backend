package com.sapientia.aihealth.controllers;

import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.ImageFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class HelloController {
    @GetMapping("/")
    public String index() {

        try {
            Image img = ImageFactory.getInstance().fromUrl("http://...");
        }catch (IOException e) {

        }
        return "Greetings!";
    }
}
