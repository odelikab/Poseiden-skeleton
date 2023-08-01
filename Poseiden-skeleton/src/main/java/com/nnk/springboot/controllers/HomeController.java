package com.nnk.springboot.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@GetMapping("/")
	public String home(Model model) {
		logger.info("displaying home page");

		return "home";
	}

	@GetMapping("/admin/home")
	public String adminHome(Model model) {
		logger.info("displaying admin home page");
		return "redirect:/user/list";
	}

}
