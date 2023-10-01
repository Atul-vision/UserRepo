package com.user.services.services;

import java.util.List;

import com.user.services.Entity.User;

public interface UserService {
	
	User saveUser(User user);
	
	List<User> getAllUsers();
	
	User getUser(String userId);
	
	

}
