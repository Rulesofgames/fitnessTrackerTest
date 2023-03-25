package com.nischitha.spring.fitnesstrackertest.services;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nischitha.spring.fitnesstrackertest.entities.Workout;
import com.nischitha.spring.fitnesstrackertest.repos.ExerciseRepository;
import com.nischitha.spring.fitnesstrackertest.repos.UserRepository;
import com.nischitha.spring.fitnesstrackertest.repos.WorkoutReopository;

@Service
public class FitnessTrackerServiceImpl implements FitnessTrackerService {

	@Autowired
	UserRepository userRepo;
	
	@Autowired
	ExerciseRepository exerciseRepo;
	
	@Autowired
	WorkoutReopository workoutRepo;
	
	@Override
	public Map<String, String> checkPassword(String password) {

		Map<String, String> response=new HashMap<>();
		boolean isUpperCase=false,isSpecialCharacter=false,isDigit=false,isLowerCase=false;
		boolean passwordLengthOk=(password.length()>=8)?true:false;
		
		for(char c:password.toCharArray()) {
			if(Character.isLetter(c)&&Character.isUpperCase(c)) {
				isUpperCase=true;
			}else if(Character.isDigit(c)) {
				isDigit=true;
			}else if(Character.isLetter(c) && Character.isLowerCase(c)) {
				isLowerCase=true;
			}else if(!Character.isWhitespace(c)) {
				isSpecialCharacter=true;
			}
		}
		
		if(!isUpperCase||!isSpecialCharacter||!isDigit||!passwordLengthOk||!isLowerCase) {
			response.put("msg", "Password should be atleast 8 characters,including 1 uppercase,1 lowercase,1 digit and 1 special character");
		}else {
			response.put("msg", "");
		}
		return response;
	}

	@Override
	public Map<String, List<String>> findExerciseCategories() {
		
		Map<String,List<String>> LoadExercise=new HashMap<>();
		List<String> categoryList=exerciseRepo.findDistinctExerciseCategories();
		categoryList.forEach(c->{
			List<String>list=exerciseRepo.findListOfExercises(c);
			LoadExercise.put(c, list);
			
		});
		return LoadExercise;
		
	}

	@Override
	public Workout saveWorkout(Workout workout) {
		//calculate Type of workout
		String workoutType="";
		int hour=workout.getStartTime().getHour();
		if(hour>=5&&hour<12) {
			workoutType="Morning";
		}else if(hour>=12&&hour<16) {
			workoutType="MidDay";
		}else if(hour>=16&&hour<18) {
			workoutType="Afternoon";
		}else if(hour>=18&&hour<21) {
			workoutType="Evening";
		}else {
			workoutType="Night";
		}
		workout.setWorkoutType(workoutType);
		
		//calculate duration of workout
		Duration duration=Duration.between(workout.getStartTime(), workout.getEndTime());
		workout.setDuration((int)duration.toMinutes());
		
		return workoutRepo.save(workout);
	}
	
	/*
	@Override
	public List<String> findExerciseCategories() {
		
		List<String> exerciseCategoryList=new ArrayList<>();
		exerciseCategoryList=exerciseRepo.findDistinctExerciseCategories();
		return exerciseCategoryList;
	}
	*/
	

}
