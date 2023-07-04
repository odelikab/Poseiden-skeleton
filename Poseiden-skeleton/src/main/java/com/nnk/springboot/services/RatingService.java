package com.nnk.springboot.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;

@Service
public class RatingService {

	@Autowired
	RatingRepository ratingRepository;

	public List<Rating> getAllRatings() {
		return ratingRepository.findAll();
	}

	public Rating addRating(@Valid Rating rating) {
		return ratingRepository.save(rating);
	}

	public Rating findRatingById(Integer id) {
		Optional<Rating> ratingFound = ratingRepository.findById(id);
		if (ratingFound.isPresent()) {
			return ratingFound.get();
		} else
			return null;
	}

	public Rating updateRating(Rating rating) {
		return ratingRepository.save(rating);
	}

	public void deleteRating(Rating rating) {
		ratingRepository.delete(rating);
	}

}
