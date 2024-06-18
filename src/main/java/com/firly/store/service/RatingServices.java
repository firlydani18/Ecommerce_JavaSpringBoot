package com.firly.store.service;

import com.firly.store.exception.ProductException;
import com.firly.store.modal.Rating;
import com.firly.store.modal.User;
import com.firly.store.request.RatingRequest;

import java.util.List;

public interface RatingServices {
	
	public Rating createRating(RatingRequest req,User user) throws ProductException;
	
	public List<Rating> getProductsRating(Long productId);

}
