package com.firly.store.service;

import com.firly.store.exception.ProductException;
import com.firly.store.modal.Cart;
import com.firly.store.modal.User;
import com.firly.store.request.AddItemRequest;

public interface CartService {
	
	public Cart createCart(User user);
	
	public String addCartItem(Long userId,AddItemRequest req) throws ProductException;
	
	public Cart findUserCart(Long userId);

}
