package com.li.delivery.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BindException;
import org.springframework.validation.Validator;

import com.li.delivery.user.model.UserModel;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class PasswordRuleTests {

	@Autowired
    private Validator validator;
	
    @Test
    void lenfthSmallThan12() {
    	UserModel userModel = getUserWithUserNameAndName();
    	userModel.setPassword("aA1@");
    	BindException errors = new BindException(userModel, "user");
        validator.validate(userModel, errors);
        assertTrue(errors.hasErrors());
    }
    
    
    @Test
    void onlyLetter() {
    	UserModel userModel = getUserWithUserNameAndName();
    	userModel.setPassword("aaaaaaAAAAAAAAAA");
    	BindException errors = new BindException(userModel, "user");
        validator.validate(userModel, errors);
        assertTrue(errors.hasErrors());
    }
    
    @Test
    void onlySpecialDigit() {
    	UserModel userModel = getUserWithUserNameAndName();
    	userModel.setPassword("@@@@@@@@@123123");
    	BindException errors = new BindException(userModel, "user");
        validator.validate(userModel, errors);
        assertTrue(errors.hasErrors());
    }

    
    @Test
    void lettersAndDigit() {
    	UserModel userModel = getUserWithUserNameAndName();
    	userModel.setPassword("AAAAAaaaaaa123123");
    	BindException errors = new BindException(userModel, "user");
        validator.validate(userModel, errors);
        assertFalse(errors.hasErrors());
    }


	private UserModel getUserWithUserNameAndName() {
		UserModel userModel = new UserModel();
    	userModel.setUserName("oopsy");
    	userModel.setName("이성");
		return userModel;
	}
}
