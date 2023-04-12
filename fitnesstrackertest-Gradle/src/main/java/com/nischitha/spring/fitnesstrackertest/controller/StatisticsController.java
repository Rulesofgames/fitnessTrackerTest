package com.nischitha.spring.fitnesstrackertest.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nischitha.spring.fitnesstrackertest.entities.Workout;
import com.nischitha.spring.fitnesstrackertest.repos.WorkoutReopository;
import com.nischitha.spring.fitnesstrackertest.services.FitnessTrackerServiceImpl;

import jakarta.servlet.http.HttpSession;

@Controller
public class StatisticsController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Autowired
	WorkoutReopository workoutRepo;
	
	@Autowired
	FitnessTrackerServiceImpl fitnessTrackerServiceImpl;

	@RequestMapping("statistics")
	public String generateGraphs(ModelMap modelmap) {
		LOGGER.info("Inside statistics controller");

	
		return "displayGenerateGraphs";
	}
	
	@GetMapping("data")
	public  @ResponseBody  Map<String,String> getData(ModelMap modelmap,@RequestParam("category")String category,@RequestParam("subCategory")String subCategory,@RequestParam("metric")String metric,@RequestParam("timeframe")Integer timeframe,HttpSession session){
		LOGGER.info("Inside getData mthod");
		LOGGER.info("Timeframe is"+timeframe+"Metric is"+metric);
		int userId=(int)session.getAttribute("userId");
		
		return fitnessTrackerServiceImpl.generateGraphData(category,subCategory,metric, timeframe, userId);
		
		/*List<Object[]> list=workoutRepo.findDuration(timeframe);
	
		Map<String,String> map=new HashMap<>();
		for(Object[] obj:list) {
			map.put(obj[0].toString(), (obj[1]).toString());
			
		}
	
		return map;*/
	}

}
