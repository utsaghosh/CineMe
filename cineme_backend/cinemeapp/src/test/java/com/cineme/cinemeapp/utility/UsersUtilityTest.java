package com.cineme.cinemeapp.utility;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

import com.cineme.cinemeapp.entity.Users;
import com.cineme.cinemeapp.exception.InvalidInputException;
import com.cineme.cinemeapp.model.UsersInputModel;
import com.cineme.cinemeapp.model.UsersOutputModel;

@SpringBootTest
public class UsersUtilityTest {

    @InjectMocks
    private UsersUtility usersUtility;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testParseOutputFromEntity() {
    	Users user = new Users();
        // Set up the user with necessary data
    	user.setUserId(1);
        user.setUserName("Test User");
        user.setEmail("testemail@gmail.com");
        user.setPassword("testpassword");
        user.setGender(Gender.MALE);
        user.setDateOfBirth(LocalDate.parse("29/10/2000", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        
        UsersOutputModel userModel = new UsersOutputModel();
        userModel.setUserId(1);
        userModel.setUserName("Test User");
        userModel.setEmail("testemail@gmail.com");
        userModel.setPassword("testpassword");
        userModel.setGender("MALE");
        userModel.setAge(ChronoUnit.YEARS.between(LocalDate.of(2000, 10, 29), LocalDate.now()));
        
        UsersOutputModel userOutput = usersUtility.parseOutputFromEntity(user);
        
        Assertions.assertEquals(userModel,userOutput);
        // Assert other properties based on your expectations
    }

    @Test
    public void testParseOutputFromEntityList() {
    	
    	Users user = new Users();
        // Set up the user with necessary data
    	user.setUserId(1);
        user.setUserName("Test User");
        user.setEmail("testemail@gmail.com");
        user.setPassword("testpassword");
        user.setGender(Gender.MALE);
        user.setDateOfBirth(LocalDate.parse("29/10/2000", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        
        UsersOutputModel userModel = new UsersOutputModel();
        userModel.setUserId(1);
        userModel.setUserName("Test User");
        userModel.setEmail("testemail@gmail.com");
        userModel.setPassword("testpassword");
        userModel.setGender("MALE");
        userModel.setAge(ChronoUnit.YEARS.between(LocalDate.of(2000, 10, 29), LocalDate.now()));
        
        List<Users> userList = List.of(user);
        List<UsersOutputModel> outList = List.of(userModel);
        // Set up the userList with necessary data
        
        List<UsersOutputModel> userOutputList = usersUtility.parseOutputFromEntityList(userList);
        
        Assertions.assertEquals(outList, userOutputList);
        // Assert other properties of the output models based on your expectations
    }

    @Test
    public void testParseEntityFromInput() throws InvalidInputException {
        UsersInputModel userModel = new UsersInputModel();
        // Set up the userInput with necessary data
        userModel.setUserName("Test User");
        userModel.setEmail("testemail@gmail.com");
        userModel.setPassword("testpassword");
        userModel.setGender("MALE");
        userModel.setDateOfBirth("29/10/2000");
        
        Users user = new Users();
        // Set up the user with necessary data
        user.setUserName("Test User");
        user.setEmail("testemail@gmail.com");
        user.setPassword("testpassword");
        user.setGender(Gender.MALE);
        user.setDateOfBirth(LocalDate.parse("29/10/2000", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        
        Users newUser = usersUtility.parseEntityFromInput(userModel);
        
        Assertions.assertEquals(newUser.getUserName(), user.getUserName());
        Assertions.assertEquals(newUser.getEmail(), user.getEmail());
        Assertions.assertEquals(newUser.getPassword(), user.getPassword());
        Assertions.assertEquals(newUser.getGender(), user.getGender());
        Assertions.assertEquals(newUser.getDateOfBirth(), user.getDateOfBirth());
        // Assert other properties of the user based on your expectations
    }

    @Test
    public void testValidateInputModel_ValidInput() throws InvalidInputException {
        UsersInputModel userModel = new UsersInputModel();
        // Set up the userModel with valid input
        userModel.setUserName("Test User");
        userModel.setEmail("testemail@gmail.com");
        userModel.setPassword("testpassword");
        userModel.setGender("MALE");
        userModel.setDateOfBirth("29/10/2000");
        
        boolean result = usersUtility.validateInputModel(userModel);
        
        Assertions.assertTrue(result);
    }

    @Test
    public void testValidateInputModel_InvalidEmail() {
        UsersInputModel userModel = new UsersInputModel();
        userModel.setEmail("invalidemail"); // Set an invalid email format
        
        Assertions.assertThrows(InvalidInputException.class, () -> {
            usersUtility.validateInputModel(userModel);
        });
    }

    @Test
    public void testValidateInputModel_InvalidUserName() {
        UsersInputModel userModel = new UsersInputModel();
        userModel.setUserName("  "); // Set an invalid blank username
        
        Assertions.assertThrows(InvalidInputException.class, () -> {
            usersUtility.validateInputModel(userModel);
        });
    }

    @Test
    public void testValidateInputModel_InvalidPassword() {
        UsersInputModel userModel = new UsersInputModel();
        userModel.setPassword(null); // Set an invalid null password
        
        Assertions.assertThrows(InvalidInputException.class, () -> {
            usersUtility.validateInputModel(userModel);
        });
    }

    @Test
    public void testValidateInputModel_InvalidDateOfBirth() {
        UsersInputModel userModel = new UsersInputModel();
        userModel.setDateOfBirth("2023-07-17"); // Set an invalid date format
        
        Assertions.assertThrows(InvalidInputException.class, () -> {
            usersUtility.validateInputModel(userModel);
        });
    }

    @Test
    public void testValidateInputModel_InvalidGender() {
        UsersInputModel userModel = new UsersInputModel();
        userModel.setGender("Male123"); // Set an invalid gender
        
        Assertions.assertThrows(InvalidInputException.class, () -> {
            usersUtility.validateInputModel(userModel);
        });
    }

    @Test
    public void testValidateEmail_ValidEmail() throws InvalidInputException {
        String email = "test@example.com"; // Set a valid email
        
        boolean result = usersUtility.validateEmail(email);
        
        Assertions.assertTrue(result);
    }

    @Test
    public void testValidateEmail_InvalidEmail_Null() {
        String email = null; // Set an invalid null email
        
        Assertions.assertThrows(InvalidInputException.class, () -> {
            usersUtility.validateEmail(email);
        });
    }

    @Test
    public void testValidateEmail_InvalidEmail_Blank() {
        String email = "  "; // Set an invalid blank email
        
        Assertions.assertThrows(InvalidInputException.class, () -> {
            usersUtility.validateEmail(email);
        });
    }

    @Test
    public void testValidateEmail_InvalidEmail_ExceedsMaxLength() {
        String email = "a".repeat(256) + "@example.com"; // Set an invalid email exceeding max length
        
        Assertions.assertThrows(InvalidInputException.class, () -> {
            usersUtility.validateEmail(email);
        });
    }

    @Test
    public void testValidateEmail_InvalidEmail_InvalidFormat() {
        String email = "test@example"; // Set an invalid email format
        
        Assertions.assertThrows(InvalidInputException.class, () -> {
            usersUtility.validateEmail(email);
        });
    }

    @Test
    public void testValidateUserName_ValidUserName() throws InvalidInputException {
        String userName = "John"; // Set a valid username
        
        boolean result = usersUtility.validateUserName(userName);
        
        Assertions.assertTrue(result);
    }

    @Test
    public void testValidateUserName_InvalidUserName_Null() {
        String userName = null; // Set an invalid null username
        
        Assertions.assertThrows(InvalidInputException.class, () -> {
            usersUtility.validateUserName(userName);
        });
    }

    @Test
    public void testValidateUserName_InvalidUserName_Blank() {
        String userName = "  "; // Set an invalid blank username
        
        Assertions.assertThrows(InvalidInputException.class, () -> {
            usersUtility.validateUserName(userName);
        });
    }

    @Test
    public void testValidateUserName_InvalidUserName_ExceedsMaxLength() {
        String userName = "a".repeat(51); // Set an invalid username exceeding max length
        
        Assertions.assertThrows(InvalidInputException.class, () -> {
            usersUtility.validateUserName(userName);
        });
    }

    @Test
    public void testValidatePassword_ValidPassword() throws InvalidInputException {
        String password = "password123"; // Set a valid password
        
        boolean result = usersUtility.validatePassword(password);
        
        Assertions.assertTrue(result);
    }

    @Test
    public void testValidatePassword_InvalidPassword_Null() {
        String password = null; // Set an invalid null password
        
        Assertions.assertThrows(InvalidInputException.class, () -> {
            usersUtility.validatePassword(password);
        });
    }

    @Test
    public void testValidatePassword_InvalidPassword_Blank() {
        String password = "  "; // Set an invalid blank password
        
        Assertions.assertThrows(InvalidInputException.class, () -> {
            usersUtility.validatePassword(password);
        });
    }

    @Test
    public void testValidatePassword_InvalidPassword_ExceedsMaxLength() {
        String password = "a".repeat(256); // Set an invalid password exceeding max length
        
        Assertions.assertThrows(InvalidInputException.class, () -> {
            usersUtility.validatePassword(password);
        });
    }

    @Test
    public void testValidateGender_ValidGender() throws InvalidInputException {
        String gender = "MALE"; // Set a valid gender
        
        boolean result = usersUtility.validateGender(gender);
        
        Assertions.assertTrue(result);
    }

    @Test
    public void testValidateGender_InvalidGender() {
        String gender = "Unknown"; // Set an invalid gender
        
        Assertions.assertThrows(InvalidInputException.class, () -> {
            usersUtility.validateGender(gender);
        });
    }

    @Test
    public void testValidateDateOfBirth_ValidDateOfBirth() throws InvalidInputException {
        String dateOfBirth = "01/01/2000"; // Set a valid date of birth
        
        boolean result = usersUtility.validateDateOfBirth(dateOfBirth);
        
        Assertions.assertTrue(result);
    }

    @Test
    public void testValidateDateOfBirth_InvalidDateOfBirth() {
        String dateOfBirth = "2000-01-01"; // Set an invalid date format
        
        Assertions.assertThrows(InvalidInputException.class, () -> {
            usersUtility.validateDateOfBirth(dateOfBirth);
        });
    }
}
