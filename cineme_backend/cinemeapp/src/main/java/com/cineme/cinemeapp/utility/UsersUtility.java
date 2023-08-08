package com.cineme.cinemeapp.utility;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.cineme.cinemeapp.entity.Users;
import com.cineme.cinemeapp.exception.InvalidInputException;
import com.cineme.cinemeapp.model.UsersInputModel;
import com.cineme.cinemeapp.model.UsersOutputModel;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UsersUtility {
	
	public UsersOutputModel parseOutputFromEntity(Users user) {
		
		log.info("Parsing UsersOutputModel from user entity with id={}", user.getUserId());
		UsersOutputModel userOutput = new UsersOutputModel();
		userOutput.setUserId(user.getUserId());
		userOutput.setUserName(user.getUserName());
		userOutput.setEmail(user.getEmail());
		userOutput.setPassword(user.getPassword());
		userOutput.setAge(user.getAge());
		userOutput.setGender(user.getGender().toString());
		log.info("Parsed UsersOutputModel= {}", userOutput);
		return userOutput;
	}
	
	public List<UsersOutputModel> parseOutputFromEntityList(List<Users> userList){
		
		log.info("Parsing UsersOutputModel from entity list");
		List<UsersOutputModel> userOutputList = new ArrayList<UsersOutputModel>();
		for(Users u : userList) {
			userOutputList.add(this.parseOutputFromEntity(u));
		}
		log.info("UsersOutputModel list parsed successfully");
		return userOutputList;
	}
	
	public Users parseEntityFromInput(UsersInputModel userInput) throws InvalidInputException {
		
		log.info("Parsing Users entity from UsersInputModel= {}", userInput);
		
		if( ! this.validateInputModel(userInput)) {
			return null;
		}
		
		Users user = new Users();
		user.setDateOfBirth(LocalDate.parse(userInput.getDateOfBirth(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		user.setEmail(userInput.getEmail());
		user.setGender(Gender.valueOf(userInput.getGender().toUpperCase()));
		user.setPassword(userInput.getPassword());
		user.setUserName(userInput.getUserName());
		log.info("User entity parsed successfully id={}", user.getUserId());
		return user;
	}
	
	public Boolean validateInputModel(UsersInputModel userModel) throws InvalidInputException{
		
		log.info("Validating UsersInputModel= {}", userModel);
		
		if( ! this.validateEmail( userModel.getEmail() ) ) {
			log.info("Invalid email");
			return false;
		}
		if( ! this.validateUserName( userModel.getUserName() ) ) {
			log.info("Invalid username");
			return false;
		}
		if( ! this.validatePassword( userModel.getPassword() ) ) {
			log.info("Invalid password");
			return false;
		}
		if( ! this.validateDateOfBirth( userModel.getDateOfBirth() )) {
			log.info("Invalid DateOfBirth");
			return false;
		}
		if( ! this.validateGender( userModel.getGender() )) {
			log.info("Invalid Gender");
			return false;
		}
		log.info("UsersInputModel is valid");
		return true;
	}
	
	public Boolean validateEmail(String email) throws InvalidInputException {
		
		String emailregex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
		
		if( email == null ) {
			throw new InvalidInputException("Email cannot be null");
		}
		if( email.isBlank() ) {
			throw new InvalidInputException("Email cannot be whitespace");
		}
		if( email.length() > 255 ) {
			throw new InvalidInputException("Email cannot exceed 255 characters");
		}
		if( ! email.matches(emailregex) ) {
			throw new InvalidInputException("Email is invalid");
		}
		
		return true;
	}
	
	public Boolean validateUserName(String userName) throws InvalidInputException {
		
		if( userName == null ) {
			throw new InvalidInputException("UserName cannot be null");
		}
		if( userName.isBlank() ) {
			throw new InvalidInputException("UserName cannot be whitespace");
		}
		if( userName.length() > 50 ) {
			throw new InvalidInputException("UserName cannot exceed 50 characters");
		}
		return true;
	}
	
	public Boolean validatePassword(String password) throws InvalidInputException {
		
		if( password == null ) {
			throw new InvalidInputException("Password cannot be null");
		}
		if( password.isBlank() ) {
			throw new InvalidInputException("Password cannot be whitespace");
		}
		if( password.length() > 255 ) {
			throw new InvalidInputException("Password cannot exceed 255 characters");
		}
		return true;
	}
	
	public Boolean validateGender(String gender) throws InvalidInputException{
		
		try {
			Gender.valueOf(gender.toUpperCase());
			return true;
		} catch (IllegalArgumentException e) {
			throw new InvalidInputException("Gender can be MALE/FEMALE/OTHER only");
		}
	}
	
	public Boolean validateDateOfBirth(String dateOfBirth) throws InvalidInputException {
		
		DateTimeFormatter myDateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		try {
			LocalDate.parse(dateOfBirth, myDateFormat);
			return true;
		} catch (DateTimeParseException e) {
			throw new InvalidInputException("Enter valid date in dd/MM/yyyy format");
		}
	}
}
