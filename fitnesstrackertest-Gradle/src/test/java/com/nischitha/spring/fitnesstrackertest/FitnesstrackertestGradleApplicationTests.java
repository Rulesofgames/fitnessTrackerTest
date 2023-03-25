package com.nischitha.spring.fitnesstrackertest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.nischitha.spring.fitnesstrackertest.repos.SetsRepository;
import com.nischitha.spring.fitnesstrackertest.repos.WorkoutReopository;



@SpringBootTest
class FitnesstrackertestGradleApplicationTests {
	
	@Autowired
	WorkoutReopository workoutRepo;
	
	@Autowired
	SetsRepository setsRepo;

	@Test
	void contextLoads() {
		
		setsRepo.deleteById(1);
		setsRepo.deleteById(2);
	}

}
