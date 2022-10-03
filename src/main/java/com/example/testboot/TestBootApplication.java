package com.example.testboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//стартовый сервис
@SpringBootApplication // запускает проект и сканирует всё глубже данного корня
public class TestBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestBootApplication.class, args);
	}

}
