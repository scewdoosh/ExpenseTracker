package com.cosa.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "users")
public class UserModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	@Email(message = "Invalid email address")
	@NotBlank(message = "Email is required")
	private String email;

	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String password;

	@Column(name = "discord_webhook")
	private String discordWebhook;

	@Column(name = "created_at")
	private LocalDateTime createdAt;
	
	@OneToOne(mappedBy = "userModel",cascade = CascadeType.ALL)
	private Payment payment;

	public UserModel() {
	}

	public UserModel(String email, String password, String name) {
		this.email = email;
		this.password = password;
		this.name = name;
		this.createdAt = LocalDateTime.now();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDiscordWebhook() {
		return discordWebhook;
	}

	public void setDiscordWebhook(String discordWebhook) {
		this.discordWebhook = discordWebhook;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}
