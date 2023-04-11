package com.nischitha.spring.fitnesstrackertest.services;

import java.util.List;
import java.util.Map;

import com.nischitha.spring.fitnesstrackertest.entities.Workout;

public interface FitnessTrackerService {
	
	public Map<String,String> checkPassword(String password);
	
	//public List<String> findExerciseCategories();
	
	public Map<String,List<String>>findExerciseCategories();
	
	public Workout saveWorkout(Workout workout);
	
	public Map<String,String> generateGraphData(String metric,Integer timeframe,Integer userId);

}
