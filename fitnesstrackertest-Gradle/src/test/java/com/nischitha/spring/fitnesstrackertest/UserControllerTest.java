package com.nischitha.spring.fitnesstrackertest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.ModelMap;

import com.nischitha.spring.fitnesstrackertest.controller.UserController;
import com.nischitha.spring.fitnesstrackertest.entities.User;
import com.nischitha.spring.fitnesstrackertest.repos.UserRepository;
import com.nischitha.spring.fitnesstrackertest.services.FitnessTrackerServiceImpl;
import com.nischitha.spring.fitnesstrackertest.util.EmailUtil;

import jakarta.servlet.http.HttpSession;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.Simple.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerTest {

	@InjectMocks
	private UserController userController;

	@Mock
	private UserRepository userRepo;

	@Mock
	private EmailUtil emailUtil;

	@Mock
	private FitnessTrackerServiceImpl fitnessTrackerServiceImpl;

	@Mock
	private HttpSession session;

	@Mock
	private ModelMap modelMap;

	@Test
	@Order(1)
	void registerUserTest_Success() {
		// create mock user object for saving
		User user = new User();
		// create mock user object to be returned after saving
		User savedUser = new User();
		user.setEmail("testEmailID");
		user.setFirstName("testUserFirstName");
		when(userRepo.save(user)).thenReturn(savedUser);
		doNothing().when(emailUtil).sendEmail(anyString(), anyString(), anyString());
		String result = userController.registerUser(user, modelMap);
		verify(userRepo, times(1)).save(user);
		verify(emailUtil, times(1)).sendEmail("testEmailID", "Start your fitness journey",
				"testUserFirstName" + ", Welcome to MYFITNESSBUDYY");
		verify(modelMap, times(1)).addAttribute("msg", "User registration success.Please log in to continue");
		assertNotNull(result);
		assertEquals("displaySignUpPage", result);
	}

	@Test
	@Order(2)
	public void registerUserTest_Failure() throws Exception {
		// create mock user object for saving
		User user = new User();
		when(userRepo.save(user)).thenReturn(null);
		String result = userController.registerUser(user, modelMap);
		verify(userRepo, times(1)).save(user);
		verify(modelMap, times(1)).addAttribute("msg", "User registration failed.Please try again");
		assertEquals("displaySignUpPage", result);
	}

	@Test
	@Order(3)
	public void CheckEmailTest() throws Exception {
		String email = "mockedemail@example.com";
		boolean exists = true;
		Map<String, Boolean> response = new HashMap<>();
		response.put("exists", true);
		when(userRepo.existsByEmail(email)).thenReturn(exists);
		Map<String, Boolean> result = userController.checkEmailExists(email);
		verify(userRepo, times(1)).existsByEmail(email);
		assertEquals(result.get("exists"), response.get("exists"));

	}

	@Test
	@Order(4)
	public void ValidatePasswordTest_ValidPassword() throws Exception {
		String password = "testPassword";
		Map<String, String> response = new HashMap<>();
		response.put("msg", "");
		when(fitnessTrackerServiceImpl.checkPassword(password)).thenReturn(response);
		Map<String, String> result = userController.validatePassword(password);
		verify(fitnessTrackerServiceImpl, times(1)).checkPassword(password);
		assertEquals(result.get("msg"), response.get("msg"));
	}

	@Test
	@Order(5)
	public void ValidatePasswordTest_InValidPassword() throws Exception {
		String password = "testPassword";
		Map<String, String> response = new HashMap<>();
		response.put("msg",
				"Password should be atleast 8 characters,including 1 uppercase,1 lowercase,1 digit and 1 special character");
		when(fitnessTrackerServiceImpl.checkPassword(password)).thenReturn(response);
		Map<String, String> result = userController.validatePassword(password);
		verify(fitnessTrackerServiceImpl, times(1)).checkPassword(password);
		assertEquals(result.get("msg"), response.get("msg"));
	}

	@Test
	@Order(6)
	public void checkLogInTest_ValidCredentials() {
		User user = new User();
		user.setPassword("testPassword");
		String email = "testEmail", password = "testPassword";
		when(userRepo.findByEmail(email)).thenReturn(user);
		String result = userController.checkLogIn(email, password, session, modelMap);
		verify(userRepo, times(1)).findByEmail(email);
		assertEquals(result, "redirect:displayHomePage");
	}

	@Test
	@Order(7)
	public void checkLogInTest_UserDoesNotExist() {
		String email = "testEmail", password = "testPassword";
		String response = "displaySignInPage";
		when(userRepo.findByEmail(email)).thenReturn(null);
		String result = userController.checkLogIn(email, password, session, modelMap);
		verify(userRepo, times(1)).findByEmail(email);
		verify(modelMap, times(1)).addAttribute("msg",
				"Username or password is incorrect.Please enter correct username and password");
		assertEquals(result, response);
	}

	@Test
	@Order(8)
	public void checkLogInTest_InvalidCredentials() {
		User user = new User();
		user.setPassword("wrongPassword");
		String email = "testEmail", password = "testPassword";
		when(userRepo.findByEmail(email)).thenReturn(user);
		String result = userController.checkLogIn(email, password, session, modelMap);
		verify(userRepo, times(1)).findByEmail(email);
		verify(modelMap, times(1)).addAttribute("msg",
				"Username or password is incorrect.Please enter correct username and password");
		assertEquals(result, "displaySignInPage");
	}

	@Test
	@Order(9)
	public void checkEmailExistsEditProfileTest_EmailExistsAndNotLoggedInUser() {
		String email = "EmailalreadyExists@example.com";
		User loggedInUser = new User();
		loggedInUser.setEmail("loggedInUser@example.com");
		Optional<User> optionalUser = Optional.ofNullable(loggedInUser);
		doReturn(1).when(session).getAttribute("userId");
		when(userRepo.findById(anyInt())).thenReturn(optionalUser);
		when(userRepo.existsByEmail(anyString())).thenReturn(true);
		Map<String, Boolean> result = userController.checkEmailExistsEditProfile(email, session);
		verify(userRepo, times(1)).findById(anyInt());
		verify(session, times(1)).getAttribute(anyString());
		assertTrue(result.get("exists"));
	}

	@Test
	@Order(10)
	public void checkEmailExistsEditProfileTest_EmailExistsButLoggedInUser() {
		String email = "loggedInUser@example.com";
		User loggedInUser = new User();
		loggedInUser.setEmail("loggedInUser@example.com");
		Optional<User> optionalUser = Optional.ofNullable(loggedInUser);
		doReturn(1).when(session).getAttribute("userId");
		when(userRepo.findById(anyInt())).thenReturn(optionalUser);
		when(userRepo.existsByEmail(anyString())).thenReturn(true);
		Map<String, Boolean> result = userController.checkEmailExistsEditProfile(email, session);
		verify(userRepo, times(1)).findById(anyInt());
		verify(session, times(1)).getAttribute(anyString());
		assertFalse(result.get("exists"));
	}

	@Test
	@Order(11)
	public void checkEmailExistsEditProfileTest_EmailDoesNotExistsAndNotLoggedInUser() {
		String email = "EmailDoesNotExist@example.com";
		User loggedInUser = new User();
		loggedInUser.setEmail("loggedInUser@example.com");
		Optional<User> optionalUser = Optional.ofNullable(loggedInUser);
		doReturn(1).when(session).getAttribute("userId");
		when(userRepo.findById(anyInt())).thenReturn(optionalUser);
		when(userRepo.existsByEmail(anyString())).thenReturn(false);
		Map<String, Boolean> result = userController.checkEmailExistsEditProfile(email, session);
		verify(userRepo, times(1)).findById(anyInt());
		verify(session, times(1)).getAttribute(anyString());
		assertFalse(result.get("exists"));
	}

	@Test
	@Order(12)
	public void saveProfileInfoTest_Success() {
		User user=new User();
		User savedUser = new User();
		int userId = 1;
		when(session.getAttribute("userId")).thenReturn(1);
		when(userRepo.save(user)).thenReturn(savedUser);
		String result = userController.saveProfileInfo(user, modelMap, session);
		verify(userRepo, times(1)).save(user);
		verify(session, times(1)).getAttribute("userId");
		verify(modelMap, times(1)).addAttribute("msg", "Your changes has been successfully saved.");
		verify(modelMap, never()).addAttribute("msg","There was an error saving changes to your profile.Please try again.");
		verify(modelMap,times(1)).addAttribute("user",savedUser);
		assertNotNull(result);
		assertEquals(result, "displayProfileInfo");
	}
	
     @Test
     @Order(13)
	public void saveProfileInfoTest_Failure() {
		User saveUser = new User();
		doReturn(1).when(session).getAttribute("userId");
		when(userRepo.save(any(User.class))).thenReturn(any(User.class));
		String result = userController.saveProfileInfo(saveUser, modelMap, session);
		verify(userRepo, times(1)).save(any(User.class));
		verify(session, times(1)).getAttribute(anyString());
		verify(modelMap, times(1)).addAttribute("msg","There was an error saving changes to your profile.Please try again.");
		verify(modelMap, never()).addAttribute("msg", "Your changes has been successfully saved.");
		assertEquals(result, "displayProfileInfo");
	}

	@Test
	@Order(14)
	public void displayProfileDeatilsTest() {
		User user = new User();
		Optional<User> optionalUser = Optional.ofNullable(user);
		doReturn(1).when(session).getAttribute("userId");
		when(userRepo.findById(1)).thenReturn(optionalUser);
		String result = userController.displayProfileDeatils(session, modelMap);
		verify(userRepo, times(1)).findById(1);
		verify(modelMap, times(1)).addAttribute("user", optionalUser.get());
		assertEquals(result, "displayProfileInfo");
	}

}