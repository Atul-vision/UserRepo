package com.user.services.serviceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.user.services.Entity.Hotel;
import com.user.services.Entity.Rating;
import com.user.services.Entity.User;
import com.user.services.exception.ResourceNotFoundException;
import com.user.services.external.services.HotelService;
import com.user.services.external.services.RatingService;
import com.user.services.payload.ApiResponse;
import com.user.services.repositories.UserRepository;
import com.user.services.services.UserService;

import lombok.Setter;

import org.slf4j.Logger;


@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private HotelService hotelService;
	
	@Autowired
	private RatingService ratingService;
	
	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	public User saveUser(User user) {
		String randomUserId = UUID.randomUUID().toString();// generate unique id
		user.setUserId(randomUserId);
		User savedUser = this.userRepository.save(user);
		return savedUser;
	}

	@Override
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}

	@Override
	public User getUser(String userId) {
		// TODO Auto-generated method stub
		User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User with given id is not found on Server!!!!!!!  :"+userId));
	
//		fetch rating Of the above user From Rating Service
//	    Rating[] ratingsOfUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/user/"+userId, Rating[].class);
//		logger.info("{} "+ratingsOfUser);
	    
//	    List<Rating> ratings = Arrays.stream(ratingsOfUser).toList();
		List<Rating> ratings = ratingService.getAllRatingsByUserId(userId);
	    
	    List<Rating> ratingList = ratings.stream().map(rating->{
//	    	Api call to hotel Service to get the hotel
	    	
//	    ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/"+rating.getHotelId(), Hotel.class);
//	    Hotel hotel = forEntity.getBody();
	    Hotel hotel = hotelService.getHotel(rating.getHotelId());
//	    	Set the hotel to rating
	    rating.setHotel(hotel);
//	    	return The rating
	    return rating;
	    	
	    }).collect(Collectors.toList());
	    
		user.setRatings(ratingList);
		return user;
	}
	
	

}
