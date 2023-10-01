package com.user.services.external.services;


import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.user.services.Entity.Rating;

@FeignClient(name="RATING-SERVICE")
public interface RatingService {
	
	
	
	@PostMapping("/ratings")
	public Rating createRating(Rating values);
	
	@GetMapping("/ratings/user/{userId}")
	List<Rating> getAllRatingsByUserId(@PathVariable String userId);
	
}
