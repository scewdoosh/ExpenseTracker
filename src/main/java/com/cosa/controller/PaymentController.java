package com.cosa.controller;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cosa.model.Payment;
import com.cosa.service.PaymentService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

	@Autowired
	private PaymentService paymentService;

	@PostMapping("/add")
	public ResponseEntity<Payment> addPayment(@RequestBody Map<String, BigDecimal> body, HttpServletRequest request) {
		String email = (String) request.getAttribute("email");
		return ResponseEntity.ok(paymentService.addAmount(email, body.get("amount")));
	}

	@GetMapping("/total")
	public ResponseEntity<Payment> getTotal(HttpServletRequest request) {
		String email = (String) request.getAttribute("email");
		return ResponseEntity.ok(paymentService.getTotal(email));
	}
}
