package com.firly.store.service;

import com.firly.store.exception.ProductException;
import com.firly.store.modal.Review;
import com.firly.store.modal.User;
import com.firly.store.request.ReviewRequest;

import java.util.List;

public interface ReviewService {

	public Review createReview(ReviewRequest req,User user) throws ProductException;
	
	public List<Review> getAllReview(Long productId);
	
	
}
