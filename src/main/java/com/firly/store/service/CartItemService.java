package com.firly.store.service;

import com.firly.store.exception.CartItemException;
import com.firly.store.exception.UserException;
import com.firly.store.modal.Cart;
import com.firly.store.modal.CartItem;
import com.firly.store.modal.Product;

public interface CartItemService {
	
	public CartItem createCartItem(CartItem cartItem);
	
	public CartItem updateCartItem(Long userId, Long id,CartItem cartItem) throws CartItemException, UserException;
	
	public CartItem isCartItemExist(Cart cart,Product product,String size, Long userId);
	
	public void removeCartItem(Long userId,Long cartItemId) throws CartItemException, UserException;
	
	public CartItem findCartItemById(Long cartItemId) throws CartItemException;
	
}
