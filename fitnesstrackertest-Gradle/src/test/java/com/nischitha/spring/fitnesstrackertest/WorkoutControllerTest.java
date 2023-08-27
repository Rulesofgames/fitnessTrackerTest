package com.nischitha.spring.fitnesstrackertest;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.ui.ModelMap;

import com.nischitha.spring.fitnesstrackertest.repos.ExerciseRepository;
import com.nischitha.spring.fitnesstrackertest.repos.SetsRepository;
import com.nischitha.spring.fitnesstrackertest.repos.UserRepository;
import com.nischitha.spring.fitnesstrackertest.repos.WorkoutReopository;
import com.nischitha.spring.fitnesstrackertest.services.FitnessTrackerService;
import com.nischitha.spring.fitnesstrackertest.services.FitnessTrackerServiceImpl;
import com.nischitha.spring.fitnesstrackertest.controller.WorkoutController;
import com.nischitha.spring.fitnesstrackertest.entities.*;
import jakarta.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.Simple.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class WorkoutControllerTest {

	@Mock
	UserRepository userRepo;

	@Mock
	SetsRepository setsRepo;

	@Mock
	WorkoutReopository workoutRepo;

	@Mock
	ExerciseRepository exerciseRepo;

	@Mock
	private HttpSession session;

	@Mock
	private ModelMap modelMap;

	@InjectMocks
	WorkoutController workoutController;

	@Mock
	FitnessTrackerServiceImpl fitnessTrackerServiceImpl;

	/*@Test...Yet to implement has SORT
	void addWorkoutTest() {
		int userId = 1;
		// Mocking the behavior of workoutRepo.findByUserId()
		List<Workout> mockWorkoutList = new ArrayList<>();
		// when(workoutRepo.findByUserId(eq(userId),
		// ArgumentMatchers.<Sort>any())).thenReturn(mockWorkoutList);

		// Mocking the behavior of exerciseRepo.findAll()
		List<Exercise> mockExerciseList = new ArrayList<>();
		when(exerciseRepo.findAll()).thenReturn(mockExerciseList);

		// Mocking the HttpSession behavior
		when(session.getAttribute("userId")).thenReturn(userId);

		// Call the method to be tested
		String result = workoutController.addWorkout(modelMap, session);

		verify(workoutRepo, times(1)).findByUserId(eq(userId), (Sort) any(Sort.class));
		verify(exerciseRepo, times(1)).findAll();
		verify(session, times(1)).getAttribute("userId");
		verify(modelMap, times(1)).addAttribute("workoutList", mockWorkoutList);
		verify(modelMap, times(1)).addAttribute("exerciseList", mockExerciseList);
		assertEquals(result, "AddWorkout");
	}
*/
	@Test
	void addSetsTest() {
		List<Workout> mockWorkoutList = new ArrayList<>();
		when(workoutRepo.findAll()).thenReturn(mockWorkoutList);
		String result = workoutController.addSets(modelMap);
		verify(modelMap, times(1)).addAttribute("list", mockWorkoutList);
		assertNotNull(result);
		assertEquals(result, "addSets");
	}

	@Test
	void saveWorkoutTest() {
		int userId = 1;
		Workout workout = new Workout();
		User user=new User();
		when(session.getAttribute("userId")).thenReturn(userId);
		when(userRepo.findById(userId)).thenReturn(Optional.ofNullable(user));
		when(fitnessTrackerServiceImpl.saveWorkout(workout)).thenReturn(workout);
		String result = workoutController.saveWorkout(workout, modelMap, session);
		verify(session, times(1)).getAttribute("userId");
		verify(fitnessTrackerServiceImpl, times(1)).saveWorkout(workout);
		assertNotNull(result);
		assertEquals(result, "redirect:addWorkout");

	}
	
	@Test
	void saveEditSetTest() {
		Workout workout=new Workout();
		Sets set=new Sets();
		Sets savedSet=new Sets();
		Exercise exercise=new Exercise();
		set.setWorkout(workout);
		when(workoutRepo.findById(1)).thenReturn(Optional.ofNullable(workout));
		when(exerciseRepo.findById(1)).thenReturn(Optional.ofNullable(exercise));
		when(setsRepo.save(set)).thenReturn(savedSet);
		String result=workoutController.saveEditSet(set, 1, 1, modelMap);
		verify(workoutRepo,times(1)).findById(1);
		verify(exerciseRepo,times(1)).findById(1);
		verify(setsRepo,times(1)).save(set);
		assertNotNull(result);
		assertEquals(result, "redirect:/viewWorkoutLog?id=1");
	}
	
	@Test
	void saveSetTest() {
		Workout workout=new Workout();
		workout.setId(1);
		Exercise exercise=new Exercise();
		exercise.setId(1);
		Sets set=new Sets();
		set.setExercise(exercise);
		set.setWorkout(workout);
		Sets savedSet=new Sets();
		when(workoutRepo.findById(1)).thenReturn(Optional.ofNullable(workout));
		when(exerciseRepo.findById(1)).thenReturn(Optional.ofNullable(exercise));
		when(setsRepo.save(set)).thenReturn(savedSet);
		String result=workoutController.saveSet(set, modelMap, 1, 1);
		verify(workoutRepo,times(1)).findById(1);
		verify(exerciseRepo,times(1)).findById(1);
		verify(setsRepo,times(1)).save(set);
		assertNotNull(result);
		assertEquals(result, "redirect:/viewWorkoutLog?id=1");
	}
	
	@Test
	void viewWorkoutLogTest() {
		List<Exercise> exerciseList=new ArrayList<>();
		when(exerciseRepo.findAll()).thenReturn(exerciseList);
		String result=workoutController.viewWorkoutLog(1, modelMap);
		verify(modelMap,times(1)).addAttribute("exerciseList",exerciseList);
		verify(modelMap,times(1)).addAttribute("workoutId",1);
		/*verify(modelMap,times(1)).addAttribute("exerciseList",exerciseList);
		verify(modelMap,times(1)).addAttribute("workoutId",1);*/
		assertNotNull(result);
		assertEquals(result,"displayWorkoutLog");
	}
	
	@Test
	void deleteWorkoutTest_WithWorkoutidArgument() {
		doNothing().when(workoutRepo).deleteById(1);
		String result=workoutController.deleteWorkout(1);
		verify(workoutRepo,times(1)).deleteById(1);
		assertNotNull(result);
		assertEquals(result,"redirect:addWorkout");
	}
	@Test
	void deleteSetTest_WithoutWorkoutidArgument() {
		Sets set=new Sets();
		Workout workout=new Workout();
		workout.setId(1);
		set.setWorkout(workout);
		doNothing().when(setsRepo).deleteById(1);
		when(setsRepo.findById(1)).thenReturn(Optional.ofNullable(set));
		String result=workoutController.deleteSet(1);
		verify(setsRepo,times(1)).deleteById(1);
		assertNotNull(result);
		assertEquals(result,"redirect:viewWorkoutLog?id=1");
	}
	
	@Test
	void workoutSummaryTest() {
		
		/*Yet to complete ,has SORT*/
		int userId = 1;
		when(session.getAttribute("userId")).thenReturn(userId);
		List<Workout> workoutList=workoutController.workoutSummary(session);
		verify(session,times(1)).getAttribute("userId");
		assertNotNull(workoutList);
		assertEquals(2,workoutList.size());
	}
	
	@Test 
	void DisplaySignInPageTest_ValidUserSession() {
		int userId=1;
		when(session.getAttribute("userId")).thenReturn(1);
		doNothing().when(session).invalidate();
		String result=workoutController.DisplaySignInPage(session);
		verify(session,times(1)).removeAttribute("userId");
		assertNotNull(result);
		assertEquals(result,"redirect:SignInPage");
	}
	@Test 
	void DisplaySignInPageTest_NullUserSession() {
		int userId=1;
		when(session.getAttribute("userId")).thenReturn(null);
		doNothing().when(session).invalidate();
		String result=workoutController.DisplaySignInPage(session);
		verify(session,never()).removeAttribute("userId");
		assertNotNull(result);
		assertEquals(result,"redirect:SignInPage");
	}

}
