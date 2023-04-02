package com.nischitha.spring.fitnesstrackertest.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nischitha.spring.fitnesstrackertest.entities.Workout;
import com.nischitha.spring.fitnesstrackertest.repos.WorkoutReopository;

@Controller
public class StatisticsController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Autowired
	WorkoutReopository workoutRepo;

	@RequestMapping("statistics")
	public String generateGraphs(ModelMap modelmap) {
		LOGGER.info("Inside statistics controller");

	
		return "displayGenerateGraphs";
	}
	
	@GetMapping("data")
	public  @ResponseBody  String getData(ModelMap modelmap){
		LOGGER.info("Inside getData mthod");
		/*Map<String,Integer> responseMap=new HashMap<>();
		List<Object[]> list=workoutRepo.findDuration();
		
		LOGGER.info("Got response from db");
		for(Object[] obj:list) {
			LOGGER.info("Date is "+obj[0].toString()+" Duration is: "+Integer.valueOf((obj[1]).toString()));
			responseMap.put(obj[0].toString(),Integer.valueOf((obj[1]).toString()));
		}
		responseMap.put("2023-3-05", 25);
		responseMap.put("2023-3-06", 26);
		responseMap.put("2023-3-07", 27);
		responseMap.put("2023-3-08", 28);
		responseMap.put("2023-3-09", 29);
		responseMap.put("2023-3-10", 30);
		responseMap.put("2023-3-12", 31);
		responseMap.put("2023-3-13", 32);
		responseMap.put("2023-3-14", 67);
		responseMap.put("2023-3-15", 90);
		responseMap.put("2023-3-16", 56);
		responseMap.put("2023-3-17", 39);
		responseMap.put("2023-3-18",90);
		responseMap.put("2023-3-19", 77);*/
		
		
		List<Workout> list=workoutRepo.findLastSixMonths();
		list.forEach(data->{
			System.out.println(data.getDate());
		});
		
		
		return "";
	}

}
