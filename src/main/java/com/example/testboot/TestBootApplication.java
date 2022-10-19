package com.example.testboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Видос: https://www.youtube.com/watch?v=0QV9Wxz8_rM
//стартовый сервис.
@SpringBootApplication // запускает проект и сканирует всё глубже данного корня
public class TestBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestBootApplication.class, args);
	}

}



