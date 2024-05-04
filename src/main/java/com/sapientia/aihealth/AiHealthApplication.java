package com.sapientia.aihealth;

import nu.pattern.OpenCV;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AiHealthApplication {

	public static void main(String[] args) {
		OpenCV.loadLocally();
		SpringApplication.run(AiHealthApplication.class, args);
	}

}
