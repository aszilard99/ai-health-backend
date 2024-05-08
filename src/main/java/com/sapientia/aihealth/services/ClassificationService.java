package com.sapientia.aihealth.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class ClassificationService {
    public ResponseEntity<String> classify(MultipartFile image) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            //TODO i might need to change the type of this image file idk
            body.add("file", image.getResource());

            String serverUrl = "http://127.0.0.1:5000/predict";
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.postForEntity(serverUrl, requestEntity, String.class);

            return response;

        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().build();
        }
    }
}
