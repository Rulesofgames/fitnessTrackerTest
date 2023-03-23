package com.nischitha.spring.fitnesstrackertest.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nischitha.spring.fitnesstrackertest.entities.User;
import com.nischitha.spring.fitnesstrackertest.repos.UserRepository;
import com.nischitha.spring.fitnesstrackertest.services.FitnessTrackerService;
import com.nischitha.spring.fitnesstrackertest.util.EmailUtil;

@Controller
public class UserController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	UserRepository userRepo;
	
	@Autowired
	EmailUtil emailUtil;

	@Autowired
	FitnessTrackerService fitnessTrackerServiceImpl;

	@Autowired
	public UserController(UserRepository userRepo) {
		this.userRepo = userRepo;
	}

	@RequestMapping("SignUpPage")
	public String displaySignUpPage() {

		return "displaySignUpPage";
	}

	@RequestMapping("SignInPage")
	public String displaySignInPage() {

		return "displaySignInPage";

	}

	@PostMapping("registerUser")
	public String registerUser(@ModelAttribute("user") User user,ModelMap modelmap) {

		LOGGER.info("Inside Register User" + user);
		LOGGER.info("date is" + user.getDOB());
		User savedUser = userRepo.save(user);
		if (savedUser == null) {
			modelmap.addAttribute("msg","User registration failed.Please try again");
			return "displaySignUpPage"; 
		} else {
			modelmap.addAttribute("msg","User registration success.Please log in to continue");
			emailUtil.sendEmail(user.getEmail(),"Start your fitness journey", user.getFirstName()+", Welcome to MYFITNESSBUDYY");
			LOGGER.info("user saved successfully");
			return "displaySignUpPage";
		}
	}

	@GetMapping("checkEmail")
	public @ResponseBody Map<String, Boolean> checkEmailExists(@RequestParam("email") String email) {
		LOGGER.info("Inside checkEmailExists" + email);
		boolean exists = userRepo.existsByEmail(email);
		LOGGER.info("Does email id exists?" + exists);
		Map<String, Boolean> response = new HashMap<>();
		response.put("exists", exists);
		return response;
	}

	@GetMapping("validatePassword")
	public @ResponseBody Map<String, String> validatePassword(@RequestParam("password") String password) {

		return fitnessTrackerServiceImpl.checkPassword(password);

	}

	@PostMapping("checklogIn")
	public String checkLogIn(@RequestParam("email") String email, @RequestParam("password") String password,
			ModelMap modelmap) {
		User user = userRepo.findByEmail(email);
		int id = user.getId();
		modelmap.addAttribute("userId", id);
		if (user == null || !user.getPassword().equals(password)) {
			modelmap.addAttribute("msg",
					"Username or password is incorrect.Please enter correct username and password");
			return "displaySignInPage";
		} else {
			return "displayHomePage";
		}

	}

}
