package com.li.delivery.user;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.li.delivery.common.contants.ResultCode;
import com.li.delivery.common.exception.RuntimeExceptionBase;
import com.li.delivery.user.model.UserModel;
import com.li.delivery.user.repository.UserRepository;
import com.li.delivery.user.service.UserService;

@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest
public class UserTests {

	@Autowired
	private UserRepository userRepository;
	
	
	@Autowired
	private UserService userService;
	
	
	@BeforeAll
	public void ensureUser() {
		UserModel userModel= userRepository.findUserByUserName("oopsy");
		
		if (userModel == null) {
			UserModel newUser = makeUser();
			userRepository.save(newUser);
		}
	}
	
	@Test
    void insertDuplicate() {
		RuntimeExceptionBase re = Assertions.assertThrows(RuntimeExceptionBase.class, () -> {
			 userService.createUserDetails(makeUser());
	    });
		assertEquals(ResultCode.DUPLICATE, re.getErrorCode()); 
    }
	
	private UserModel makeUser() {
		UserModel newUser = new UserModel();
		newUser.setUserName("oopsy");
		newUser.setName("이성");
		newUser.setPassword("Aaaaaaaaaaa1@#");
		return newUser;
	}
}
