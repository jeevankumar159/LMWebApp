package com.example.LMWebApp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainPage {
	@GetMapping("/register")
	public String userAlreadyExists() {
		return "register";
	}
}
