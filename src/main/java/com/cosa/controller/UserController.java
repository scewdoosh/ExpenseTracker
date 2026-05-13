package com.cosa.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cosa.model.UserModel;
import com.cosa.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/signup")
	public ResponseEntity<UserModel> signup(@Valid @RequestBody UserModel user) {
		return ResponseEntity.ok(userService.signup(user));
	}

	@PostMapping("/signin")
	public ResponseEntity<String> signin(@RequestBody UserModel user) {
		String token = userService.signin(user.getEmail(), user.getPassword());
		return ResponseEntity.ok(token);
	}

	@PutMapping("/update-webhook")
	public ResponseEntity<UserModel> updateWebhook(@RequestBody Map<String, String> body, HttpServletRequest request) {
		String email = (String) request.getAttribute("email");
		return ResponseEntity.ok(userService.updateWebhook(email, body.get("webhookUrl")));
	}
}
