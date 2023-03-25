package com.nischitha.spring.fitnesstrackertest.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nischitha.spring.fitnesstrackertest.entities.Exercise;
import com.nischitha.spring.fitnesstrackertest.entities.Sets;
import com.nischitha.spring.fitnesstrackertest.entities.Workout;
import com.nischitha.spring.fitnesstrackertest.repos.ExerciseRepository;
import com.nischitha.spring.fitnesstrackertest.repos.SetsRepository;
import com.nischitha.spring.fitnesstrackertest.repos.UserRepository;
import com.nischitha.spring.fitnesstrackertest.repos.WorkoutReopository;
import com.nischitha.spring.fitnesstrackertest.services.FitnessTrackerService;

@Controller
public class WorkoutController {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	FitnessTrackerService fitnessTrackerServiceImpl;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	SetsRepository setsRepo;
	
	@Autowired
	WorkoutReopository workoutRepo;
	
	@Autowired
	ExerciseRepository exerciseRepo;
	
	@RequestMapping("addWorkout")
	public String addWorkout(@RequestParam(name="id",required=false) Integer id,ModelMap modelmap) {
		LOGGER.info("Inside addWorkout,Start");
		modelmap.addAttribute("userId",id);
		List<Workout> workoutList=workoutRepo.findAll(Sort.by(new Sort.Order(Direction.DESC, "date"),new Sort.Order(Direction.DESC,"startTime")));
		modelmap.addAttribute("workoutList",workoutList);
		modelmap.addAttribute("exerciseList",exerciseRepo.findAll());
		LOGGER.info("Inside addWorkout,end");
		return "AddWorkout";
	}
	
	@RequestMapping("addSets")
	public String addSets(ModelMap modelmap){
		List<Workout> list=workoutRepo.findAll();
		modelmap.addAttribute("list",list);
		return "addSets";
	}
	
	
	
	@PostMapping("saveWorkout")
	public String saveWorkout(@ModelAttribute("workout")Workout workout,ModelMap modelmap,@RequestParam("userId") Integer id) {
		LOGGER.info("Inside saveWorkout");
		
		//saving all workouts to user id 1
		workout.setUser(userRepo.findById(1).get());
		
		Workout savedWorkout=fitnessTrackerServiceImpl.saveWorkout(workout);
		
		/*you are repeating code..see if u can avoid it
		modelmap.addAttribute("userId",id);
		List<Workout> workoutList=workoutRepo.findAll(Sort.by(new Sort.Order(Direction.DESC, "date"),new Sort.Order(Direction.DESC,"startTime")));
		modelmap.addAttribute("workoutList",workoutList);*/
		return "redirect:addWorkout";
		
	}
	
	@PostMapping("saveEditWorkout")
	public String saveEditWorkout(@ModelAttribute("workout")Workout workout) {
		
		Workout savedWorkout=fitnessTrackerServiceImpl.saveWorkout(workout);
		return "redirect:addWorkout";
	}
	@PostMapping("saveSet")
	public String saveSet(@ModelAttribute("Sets")Sets sets,ModelMap modelmap,@RequestParam("exerciseId")Integer exerciseId,@RequestParam("workoutId")Integer workoutId) {
		
		Exercise exercise=exerciseRepo.findById(exerciseId).get();
		Workout workout=workoutRepo.findById(workoutId).get();
		sets.setWorkout(workout);
		sets.setExercise(exercise);
		setsRepo.save(sets);
		
		return "redirect:addWorkout";
	}
	
	@RequestMapping("viewWorkoutLog")
    public String viewWorkoutLog(@RequestParam("id") Integer workoutId,ModelMap modelmap) {
		
		
		modelmap.addAttribute("setsList",setsRepo.findAllByWorkoutId(workoutId));
		return "displayWorkoutLog";
	}
	
	@RequestMapping("deleteWorkout")
	public String deleteWorkout(@RequestParam("id") Integer workoutId) {
		workoutRepo.deleteById(workoutId);
		
		return "redirect:addWorkout";
	}
	
	@RequestMapping("deleteSet")
	public String deleteSet(@RequestParam("id") Integer setId){
		int workoutId=setsRepo.findById(setId).get().getWorkout().getId();
		setsRepo.deleteById(setId);
		return "redirect:viewWorkoutLog?id="+workoutId;
	}
	
	

}
