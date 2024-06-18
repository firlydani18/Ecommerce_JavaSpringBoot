package com.firly.store.controller;

import com.firly.store.domain.OrderStatus;
import com.firly.store.domain.PaymentStatus;
import com.firly.store.exception.OrderException;
import com.firly.store.exception.UserException;
import com.firly.store.modal.Order;
import com.firly.store.repository.OrderRepository;
import com.firly.store.response.ApiResponse;
import com.firly.store.response.PaymentLinkResponse;
import com.firly.store.service.OrderService;
import com.firly.store.service.UserService;
import com.midtrans.Config;
import com.midtrans.httpclient.error.MidtransError;
import com.midtrans.service.MidtransSnapApi;
import com.midtrans.service.impl.MidtransSnapApiImpl;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PaymentController {

	private OrderService orderService;
	private UserService userService;
	private OrderRepository orderRepository;
	private MidtransSnapApi midtransSnapApi;

	@Autowired
	public PaymentController(OrderService orderService, UserService userService, OrderRepository orderRepository, @Qualifier("MidtransConfig") Config midtransConfig) {
		this.orderService = orderService;
		this.userService = userService;
		this.orderRepository = orderRepository;
		this.midtransSnapApi = new MidtransSnapApiImpl(midtransConfig);
	}

	@PostMapping("/payments/{orderId}")
	public ResponseEntity<PaymentLinkResponse> createPaymentLink(@PathVariable Long orderId,
																 @RequestHeader("Authorization") String jwt)
			throws UserException, OrderException, MidtransError {
		Order order = orderService.findOrderById(orderId);
		try {
			JSONObject transactionRequest = new JSONObject();
			transactionRequest.put("transaction_details", new JSONObject()
					.put("order_id", order.getId().toString())
					.put("gross_amount", order.getTotalPrice()));

			JSONObject customerDetails = new JSONObject();
			customerDetails.put("first_name", order.getUser().getFirstName());
			customerDetails.put("last_name", order.getUser().getLastName());
			customerDetails.put("email", order.getUser().getEmail());
			customerDetails.put("phone", order.getUser().getMobile());
			transactionRequest.put("customer_details", customerDetails);

			JSONObject response = new JSONObject(midtransSnapApi.createTransaction(transactionRequest.toMap()));
			String redirectUrl = response.getString("redirect_url");

			PaymentLinkResponse res = new PaymentLinkResponse(redirectUrl, order.getId().toString());
			return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
		} catch (MidtransError e) {
			throw new MidtransError(e.getMessage());
		}
	}

	@GetMapping("/payments")
	public ResponseEntity<ApiResponse> redirect(@RequestParam(name = "transaction_status") String transactionStatus,
												@RequestParam("order_id") Long orderId) throws MidtransError, OrderException {
		Order order = orderService.findOrderById(orderId);

		try {
			if (transactionStatus.equals("settlement")) {
				order.getPaymentDetails().setStatus(PaymentStatus.COMPLETED);
				order.setOrderStatus(OrderStatus.PLACED);
				orderRepository.save(order);
			}
			ApiResponse res = new ApiResponse("Your order has been placed", true);
			return new ResponseEntity<>(res, HttpStatus.OK);
		} catch (Exception e) {
			throw new MidtransError(e.getMessage());
		}
	}
}
