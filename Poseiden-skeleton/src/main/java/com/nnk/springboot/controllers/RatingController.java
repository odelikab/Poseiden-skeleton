package com.nnk.springboot.controllers;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;

@Controller
public class RatingController {

	private static final Logger logger = LoggerFactory.getLogger(RatingController.class);

	@Autowired
	private RatingService ratingService;

	@GetMapping("/rating/list")
	public String home(Model model) {
		logger.info("displaying ratings list");
		List<Rating> ratings = ratingService.getAllRatings();
		model.addAttribute("ratings", ratings);
		return "rating/list";
	}

	@GetMapping("/rating/add")
	public String addRatingForm(Rating rating, Model model) {
		logger.info("add rating page");
		model.addAttribute("rating", new Rating());
		return "rating/add";
	}

	@PostMapping("/rating/validate")
	public String validate(@Valid Rating rating, BindingResult result, Model model) {
		// TODO: check data valid and save to db, after saving return Rating list
		if (!result.hasErrors()) {
			logger.info("add rating");
			ratingService.addRating(rating);
			return "redirect:/rating/list";
		} else {
			logger.error("rating not valid");
			return "rating/add";
		}
	}

	@GetMapping("/rating/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		logger.info("update rating page");
		Rating ratingFound = ratingService.findRatingById(id);
		model.addAttribute("rating", ratingFound);
		return "rating/update";
	}

	@PostMapping("/rating/update/{id}")
	public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating, BindingResult result,
			Model model) {
		if (!result.hasErrors()) {
			logger.info("update rating");
			ratingService.updateRating(rating);
			return "redirect:/rating/list";
//			}
		} else {
			logger.error("updating rating fail");
			return "rating/update";
		}
	}

	@GetMapping("/rating/delete/{id}")
	public String deleteRating(@PathVariable("id") Integer id, Model model) {
		logger.info("deleting rating");
		Rating ratingFound = ratingService.findRatingById(id);
		if (ratingFound != null) {
			ratingService.deleteRating(ratingFound);
		}
		return "redirect:/rating/list";
	}
}
