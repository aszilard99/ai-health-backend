package com.sapientia.aihealth.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

public class ClassificationService {
    public ResponseEntity<String> classify(MultipartFile image) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", image.getResource());

            //TODO this url might have to be changed when running inside containers
            String serverUrl = "http://inference-microservice:5000/predict";
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.postForEntity(serverUrl, requestEntity, String.class);

            return response;

        } catch (HttpClientErrorException.UnprocessableEntity e) {
            return ResponseEntity.unprocessableEntity().body("File extension is not supported");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
