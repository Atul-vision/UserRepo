package com.user.services.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.services.Entity.User;
import com.user.services.services.UserService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	private Logger logger = LoggerFactory.getLogger(UserController.class);
	
//	int retryCount=10;
	
	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody User user){
		User user1 = userService.saveUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(user1);
	}
	
	@GetMapping("/{userId}")
//	@CircuitBreaker(name="ratingHotelBreaker",fallbackMethod = "ratingHotelFallback")
//	@Retry(name="ratingHotelService", fallbackMethod = "ratingHotelFallback")
	@RateLimiter(name="userRateLimiter", fallbackMethod ="ratingHotelFallback")
	public ResponseEntity<User> getSingleUser(@PathVariable String userId){
		logger.info("Get Single user handler: UserController");
//		logger.info("Retry Count: {}", retryCount);
		User user = userService.getUser(userId);
		return ResponseEntity.ok(user);	
	}
	
	@GetMapping
	public ResponseEntity<List<User>> getAllUser(){
		List<User> allUser = userService.getAllUsers();
	    return ResponseEntity.ok(allUser);	
	}
	
	
	
	//creating fallback method for circuitBreaker
	public ResponseEntity<User> ratingHotelFallback(String userId, Exception ex){
		logger.info("FallBack is executed because service is Down :", ex.getMessage());
		User user = new User();
		user.setEmail("dummy@gmail.com");
		user.setName("Dummy");
		user.setAbout("This user is created dummy because some services down");
		user.setUserId("1234566");
		return new ResponseEntity<User>(user,HttpStatus.OK); 
	}

}
