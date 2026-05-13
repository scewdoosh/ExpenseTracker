package com.cosa.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cosa.model.UserModel;

@Repository
public interface IUserModelRepo extends JpaRepository<UserModel, Long>{
	 Optional<UserModel> findByEmail(String email);
}
