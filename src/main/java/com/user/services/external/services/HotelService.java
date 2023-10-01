package com.user.services.external.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.user.services.Entity.Hotel;

@FeignClient(name="HOTEL-SERVICE")
public interface HotelService {
	
	 @GetMapping("/hotels/{hotelId}")
	 Hotel getHotel(@PathVariable String hotelId);

}
