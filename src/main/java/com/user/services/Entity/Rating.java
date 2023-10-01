package com.user.services.Entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Rating {
	
	private String ratingId;
	private String userId;
    private String hotelId;
    private String rating;
    private String feedback;
    private Hotel hotel;
}
