package com.nischitha.spring.fitnesstrackertest.services;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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

		Map<String, String> response = new HashMap<>();
		boolean isUpperCase = false, isSpecialCharacter = false, isDigit = false, isLowerCase = false;
		boolean passwordLengthOk = (password.length() >= 8) ? true : false;

		for (char c : password.toCharArray()) {
			if (Character.isLetter(c) && Character.isUpperCase(c)) {
				isUpperCase = true;
			} else if (Character.isDigit(c)) {
				isDigit = true;
			} else if (Character.isLetter(c) && Character.isLowerCase(c)) {
				isLowerCase = true;
			} else if (!Character.isWhitespace(c)) {
				isSpecialCharacter = true;
			}
		}

		if (!isUpperCase || !isSpecialCharacter || !isDigit || !passwordLengthOk || !isLowerCase) {
			response.put("msg",
					"Password should be atleast 8 characters,including 1 uppercase,1 lowercase,1 digit and 1 special character");
		} else {
			response.put("msg", "");
		}
		return response;
	}

	@Override
	public Map<String, List<String>> findExerciseCategories() {

		Map<String, List<String>> LoadExercise = new HashMap<>();
		List<String> categoryList = exerciseRepo.findDistinctExerciseCategories();
		categoryList.forEach(c -> {
			List<String> list = exerciseRepo.findListOfExercises(c);
			LoadExercise.put(c, list);

		});
		return LoadExercise;

	}

	@Override
	public Workout saveWorkout(Workout workout) {
		// calculate Type of workout
		String workoutType = "";
		int hour = workout.getStartTime().getHour();
		if (hour >= 5 && hour < 10) {
			workoutType = "Morning";
		} else if (hour >= 10 && hour < 13) {
			workoutType = "MidDay";
		} else if (hour >= 13 && hour < 17) {
			workoutType = "Afternoon";
		} else if (hour >= 17 && hour < 20) {
			workoutType = "Evening";
		} else {
			workoutType = "Night";
		}
		workout.setWorkoutType(workoutType);

		// calculate duration of workout

		long millis = Duration.between(workout.getStartTime(), workout.getEndTime()).toMillis();

		int duration = (int) TimeUnit.MILLISECONDS.toMinutes(millis);
		workout.setDuration(duration);

		return workoutRepo.save(workout);
	}

	@Override
	public Map<String, String> generateGraphData(String category, String subCategory, String metric, Integer timeframe,
			Integer userId) {

		List<Object[]> list = new ArrayList<>();
		if (category.equals("overall") && subCategory.equals("overall")) {

			if (metric.equals("duration")) {
				list = workoutRepo.findDuration(timeframe, userId);

			} else if (metric.equals("sets")) {
				list = workoutRepo.findTotalSets(timeframe, userId);

			} else if (metric.equals("reps") || metric.equals("weight") || metric.equals("minutes")
					|| metric.equals("distance") || metric.equals("kcal")) {
				list = workoutRepo.findTotal(metric, timeframe, userId);

			} else {
				// add logic to retrieve bodyweight
			}

		} else if (!category.equals("overall") && subCategory.equals("overall")) {

			System.out.println("Calling findCategoryTotal");
			if (metric.equals("sets")) {
				list = workoutRepo.findCategorySets(category, timeframe, userId);
			} else {
				list = workoutRepo.findCategoryTotal(category, metric, timeframe, userId);
			}
			

		} else if (!category.equals("overall") && !subCategory.equals("overall")) {
			if (metric.equals("sets")) {
				list = workoutRepo.findSubCategorySets(subCategory, timeframe, userId);
			} else {
				list = workoutRepo.findSubCategoryTotal(subCategory, metric, timeframe, userId);
			}

			
		}

		Map<String, String> map = new HashMap<>();
		for (Object[] obj : list) {
			map.put(obj[0].toString(), (obj[1]).toString());
			System.out.println(obj[0].toString() + " " + (obj[1]).toString());

		}
		return map;
	}

}
