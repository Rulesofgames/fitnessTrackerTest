package com.nischitha.spring.fitnesstrackertest.services;

import java.util.List;
import java.util.Map;

public interface FitnessTrackerService {
	
	public Map<String,String> checkPassword(String password);
	
	//public List<String> findExerciseCategories();
	
	public Map<String,List<String>>findExerciseCategories();

}
