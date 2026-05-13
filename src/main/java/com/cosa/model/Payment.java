package com.cosa.model;

import java.math.BigDecimal;

import jakarta.persistence.*;

@Entity
@Table(name = "payments")
public class Payment {
	
	@OneToOne
	@JoinColumn(name = "users_id")
	private UserModel userModel;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "total_amount", precision = 19, scale = 2)
	private BigDecimal totalAmount;

	public UserModel getUserModel() {
		return userModel;
	}

	public void setUserModel(UserModel userModel) {
		this.userModel = userModel;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Payment(UserModel userModel, BigDecimal totalAmount) {
		super();
		this.userModel = userModel;
		this.totalAmount = totalAmount;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Payment() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	
	
}
