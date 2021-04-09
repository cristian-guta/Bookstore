package com.apbdoo.BooksStore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication()
public class BooksStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(BooksStoreApplication.class, args);
	}

}
