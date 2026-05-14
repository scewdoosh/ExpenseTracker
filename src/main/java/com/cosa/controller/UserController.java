package com.cosa.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cosa.model.UserModel;
import com.cosa.service.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/auth")
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/me")
	public ResponseEntity<String> me(HttpServletRequest request) {
	    String email = (String) request.getAttribute("email");
	    if (email == null) return ResponseEntity.status(401).build();
	    return ResponseEntity.ok(email);
	}

	@PostMapping("/signin")
	public ResponseEntity<String> signin(@RequestBody UserModel user, HttpServletResponse response) {
	    String token = userService.signin(user.getEmail(), user.getPassword());
	    
	    Cookie cookie = new Cookie("token", token);
	    cookie.setHttpOnly(true);
	    cookie.setPath("/");
	    cookie.setMaxAge(60 * 60 * 24 * 30);
	    cookie.setAttribute("SameSite", "None");
	    cookie.setSecure(true);
	    response.addCookie(cookie);
	    
	    return ResponseEntity.ok("Login successful");
	}
	
	@PostMapping("/signup")
	public ResponseEntity<UserModel> signup(@RequestBody UserModel user) {
	    return ResponseEntity.ok(userService.signup(user));
	}
	
	@PutMapping("/update-webhook")
	public ResponseEntity<UserModel> updateWebhook(@RequestBody Map<String, String> body, HttpServletRequest request) {
		String email = (String) request.getAttribute("email");
		return ResponseEntity.ok(userService.updateWebhook(email, body.get("webhookUrl")));
	}
}
