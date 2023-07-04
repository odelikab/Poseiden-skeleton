package com.nnk.springboot.controllers;

import java.util.List;

import javax.validation.Valid;

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
	// TODO: Inject Rating service
	@Autowired
	RatingService ratingService;

	@GetMapping("/rating/list")
	public String home(Model model) {
		// TODO: find all Rating, add to model
		List<Rating> ratings = ratingService.getAllRatings();
		model.addAttribute("ratings", ratings);
		return "rating/list";
	}

	@GetMapping("/rating/add")
	public String addRatingForm(Rating rating, Model model) {
		model.addAttribute("rating", new Rating());
		return "rating/add";
	}

	@PostMapping("/rating/validate")
	public String validate(@Valid Rating rating, BindingResult result, Model model) {
		// TODO: check data valid and save to db, after saving return Rating list
		if (!result.hasErrors()) {
			ratingService.addRating(rating);
		}
		return "redirect:/rating/list";
	}

	@GetMapping("/rating/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		// TODO: get Rating by Id and to model then show to the form
		Rating ratingFound = ratingService.findRatingById(id);
		model.addAttribute("rating", ratingFound);
		return "rating/update";
	}

	@PostMapping("/rating/update/{id}")
	public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating, BindingResult result,
			Model model) {
		// TODO: check required fields, if valid call service to update Rating and
		// return Rating list
		if (!result.hasErrors()) {
//			Rating ratingFound = ratingService.findRatingById(id);
//			if (ratingFound != null) {
//				ratingFound.setMoodysRating(rating.getMoodysRating());
//				ratingFound.setFitchRating(rating.getFitchRating());
//				ratingFound.setOrderNumber(rating.getOrderNumber());
//				ratingFound.setSandPRating(rating.getSandPRating());
			ratingService.updateRating(rating);
//			}
		}
		return "redirect:/rating/list";
	}

	@GetMapping("/rating/delete/{id}")
	public String deleteRating(@PathVariable("id") Integer id, Model model) {
		// TODO: Find Rating by Id and delete the Rating, return to Rating list
		Rating ratingFound = ratingService.findRatingById(id);
		if (ratingFound != null) {
			ratingService.deleteRating(ratingFound);
		}
		return "redirect:/rating/list";
	}
}
