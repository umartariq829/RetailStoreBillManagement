package com.example.bm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "com.example.bm" })
public class BmApplication {

	public static void main(String[] args) {
		SpringApplication.run(BmApplication.class, args);
	}

}
