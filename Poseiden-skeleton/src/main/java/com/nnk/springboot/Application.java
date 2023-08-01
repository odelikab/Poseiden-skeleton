package com.nnk.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		BCryptPasswordEncoder pass = new BCryptPasswordEncoder();
		System.out.println(pass.encode("abcde"));
		SpringApplication.run(Application.class, args);
	}

}
