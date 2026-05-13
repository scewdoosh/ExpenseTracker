package com.cosa.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cosa.model.Payment;
import com.cosa.model.UserModel;

@Repository
public interface IPaymentRepo extends JpaRepository<Payment, Long>{
	Optional<Payment> findByUserModel(UserModel userModel);
}
