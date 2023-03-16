package com.nischitha.spring.fitnesstrackertest.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nischitha.spring.fitnesstrackertest.repos.UserRepository;

@Service
public class FitnessTrackerServiceImpl implements FitnessTrackerService {

	@Autowired
	UserRepository userRepo;
	
	@Override
	public Map<String, String> checkPassword(String password) {

		Map<String, String> response=new HashMap<>();
		boolean isUpperCase=false,isSpecialCharacter=false,isDigit=false,isLowerCase=false;
		boolean passwordLengthOk=(password.length()>=8)?true:false;
		
		for(char c:password.toCharArray()) {
			if(Character.isLetter(c)&&Character.isUpperCase(c)) {
				isUpperCase=true;
			}else if(Character.isDigit(c)) {
				isDigit=true;
			}else if(Character.isLetter(c) && Character.isLowerCase(c)) {
				isLowerCase=true;
			}else if(!Character.isWhitespace(c)) {
				isSpecialCharacter=true;
			}
		}
		
		if(!isUpperCase||!isSpecialCharacter||!isDigit||!passwordLengthOk||!isLowerCase) {
			response.put("msg", "Password should be atleast 8 characters,including 1 uppercase,1 lowercase,1 digit and 1 special character");
		}else {
			response.put("msg", "");
		}
		return response;
	}
	
	

}
