package com.cosa.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cosa.jwt.JwtUtil;
import com.cosa.model.UserModel;
import com.cosa.repo.IUserModelRepo;

@Service
public class UserService {
	@Autowired
	private JwtUtil jwtUtil;

    @Autowired
    private IUserModelRepo userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserModel signup(UserModel user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    public UserModel findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }


    public String signin(String email, String password) {
        UserModel existing = findByEmail(email);
        if (passwordEncoder.matches(password, existing.getPassword())) {
            return jwtUtil.generateToken(email);
        }
        throw new RuntimeException("Invalid credentials");
    }
}
