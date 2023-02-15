package com.li.delivery.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.li.delivery.common.contants.ResultCode;
import com.li.delivery.common.model.ResponseModel;
import com.li.delivery.order.model.OrderModel;
import com.li.delivery.user.model.UserModel;
import com.li.delivery.user.service.UserService;


@RestController
@RequestMapping("/users")
public class UserController {
	
	private final UserService userService;
	
	 public UserController(UserService userService) {
	        this.userService = userService;
	}
	@PostMapping("/register")
    public ResponseModel<?> saveUser(@Validated @RequestBody UserModel user){
    	userService.createUserDetails(user);
    	return new ResponseModel<>(ResultCode.OK);
    }
	
}
