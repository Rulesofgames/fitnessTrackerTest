package com.nischitha.spring.fitnesstrackertest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WorkoutController {
	
	@RequestMapping("addWorkout")
	public String addWorkout() {
		return "AddWorkout";
	}
	

}
