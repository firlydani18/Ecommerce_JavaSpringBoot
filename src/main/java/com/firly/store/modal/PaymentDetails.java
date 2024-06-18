package com.firly.store.modal;

import com.firly.store.domain.PaymentMethod;
import com.firly.store.domain.PaymentStatus;

public class PaymentDetails {

	private PaymentMethod paymentMethod;
	private PaymentStatus status;
	private String paymentId;

	public PaymentDetails() {
		// Default constructor
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public PaymentStatus getStatus() {
		return status;
	}

	public void setStatus(PaymentStatus status) {
		this.status = status;
	}
}