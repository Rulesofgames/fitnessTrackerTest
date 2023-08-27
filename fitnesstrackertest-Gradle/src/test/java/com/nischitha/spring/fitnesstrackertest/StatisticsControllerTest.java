package com.nischitha.spring.fitnesstrackertest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.ModelMap;

import com.nischitha.spring.fitnesstrackertest.controller.StatisticsController;
import com.nischitha.spring.fitnesstrackertest.services.FitnessTrackerServiceImpl;

import jakarta.servlet.http.HttpSession;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.Simple.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StatisticsControllerTest {

	@Mock
	private FitnessTrackerServiceImpl fitnessTrackerServiceImpl;

	@Mock
	private HttpSession session;
	
	@Mock
	private ModelMap modelMap;
	
	@InjectMocks
	private StatisticsController statisticsController;
	
	@Test
	@Order(1)
	void getDataTest() {
		int userId = 1;
		Map<String,String> mockedMap=new HashMap<>();
		mockedMap.put("category_1", "data_1");
		mockedMap.put("category_2", "data_2");
		when(session.getAttribute("userId")).thenReturn(userId);
		when(fitnessTrackerServiceImpl.generateGraphData("category","subCategory","metric",30,1)).thenReturn(mockedMap);
		Map<String,String> result=statisticsController.getData(modelMap,"category","subCategory","metric",30,session);
		verify(session,times(1)).getAttribute("userId");
		assertNotNull(result);
		assertEquals(2,result.size());
		
	}

}
