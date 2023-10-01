package com.user.services.exception;

public class ResourceNotFoundException extends RuntimeException{
	
	public ResourceNotFoundException() {
		super("Resource not found on server!!!");
	}
	
	public ResourceNotFoundException(String msg) {
		super(msg);
	}

}
