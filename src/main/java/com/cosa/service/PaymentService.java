package com.cosa.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cosa.model.Payment;
import com.cosa.model.UserModel;
import com.cosa.repo.IPaymentRepo;
import com.cosa.repo.IUserModelRepo;

@Service
public class PaymentService {

	@Autowired
	private IPaymentRepo paymentRepository;

	@Autowired
	private IUserModelRepo userRepository;

	public Payment addAmount(String email, BigDecimal amount) {
		UserModel user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

		Payment payment = paymentRepository.findByUserModel(user).orElse(new Payment());

		payment.setUserModel(user);
		payment.setTotalAmount(payment.getTotalAmount() == null ? amount : payment.getTotalAmount().add(amount));

		return paymentRepository.save(payment);
	}

	public Payment getTotal(String email) {
		UserModel user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
		return paymentRepository.findByUserModel(user).orElse(null);
	}
}