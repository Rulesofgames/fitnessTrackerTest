package com.nischitha.spring.fitnesstrackertest;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import com.nischitha.spring.fitnesstrackertest.controller.UserController;
import com.nischitha.spring.fitnesstrackertest.entities.User;
import com.nischitha.spring.fitnesstrackertest.repos.SetsRepository;
import com.nischitha.spring.fitnesstrackertest.repos.UserRepository;
import com.nischitha.spring.fitnesstrackertest.repos.WorkoutReopository;
import com.nischitha.spring.fitnesstrackertest.util.EmailUtil;

@SpringBootTest
class FitnesstrackertestGradleApplicationTests {

	@Mock
	private UserRepository userRepo;

	@Mock
	private EmailUtil emailUtil;

	@InjectMocks
	private UserController userController;
	
	 @Mock
		private ModelMap modelMap;

	@Test
	void contextLoads() {

	}

	@Test
	void testUserRegistrationSuccess() {
		// create mock user object for saving
		User user = new User();
		user.setEmail("johnanthony@gmail.com");
		user.setFirstName("john");
		user.setLastName("anthony");
		user.setDOB(LocalDate.of(2023, 5, 13));
		user.setAddress("Tonawanda,NewYork");
		user.setPincode(14150);
		user.setCountry("USA");
		user.setGender("male");

		// create mock user object to be returned after saving
		User savedUser = new User();
		savedUser.setEmail("johnanthony@gmail.com");
		savedUser.setFirstName("john");
		savedUser.setLastName("anthony");
		savedUser.setDOB(LocalDate.of(2023, 5, 13));
		savedUser.setAddress("Tonawanda,NewYork");
		savedUser.setPincode(14150);
		savedUser.setCountry("USA");
		savedUser.setGender("male");

		ModelMap modelmap = new ModelMap();
		/*modelmap.addAttribute("msg", "User registration failed.Please try again");
		when(modelmap.addAttribute(eq("msg"), eq("User registration success.Please log in to continue"))).thenReturn(modelmap);*/
		when(userRepo.save(user)).thenReturn(savedUser);
		//doNothing().when(emailUtil).sendEmail(anyString(), anyString(), anyString());
		String result = userController.registerUser(user, modelmap);
		verify(userRepo, times(1)).save(user);
		verify(emailUtil, times(1)).sendEmail(eq(user.getEmail()),eq("Start your fitness journey"), eq(user.getFirstName() + ", Welcome to MYFITNESSBUDYY"));
        //verify(modelmap, times(1)).addAttribute("msg", "User registration success.Please log in to continue");

		
		assertEquals("displaySignUpPage", result);
		//assertEquals("User registration success.Please log in to continue", modelmap.get("msg"));
    
	}
	
	 @Test
	    public void testRegisterUserFailure() throws Exception {
		// create mock user object for saving
			User user = new User();
			user.setEmail("johnanthony@gmail.com");
			user.setFirstName("john");
			user.setLastName("anthony");
			user.setDOB(LocalDate.of(2023, 5, 13));
			user.setAddress("Tonawanda,NewYork");
			user.setPincode(14150);
			user.setCountry("USA");
			user.setGender("male");
	        when(userRepo.save(user)).thenReturn(null);
	        String result = userController.registerUser(user, modelMap);
	        verify(userRepo, times(1)).save(user);
	        verify(modelMap, times(1)).addAttribute("msg", "User registration failed.Please try again");
	        assertEquals("displaySignUpPage", result);
	    }

}
