package com.nischitha.spring.fitnesstrackertest.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nischitha.spring.fitnesstrackertest.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	boolean existsByEmail(String email);
	User findByEmail(String email);

}
