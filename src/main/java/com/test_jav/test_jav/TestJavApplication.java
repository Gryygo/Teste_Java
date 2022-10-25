package com.test_jav.test_jav;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
public class TestJavApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestJavApplication.class, args);
	}

	@GetMapping("/")
	public String home(Model model) {

		model.addAttribute("titulo", "home");

        return "index";
    }

}
