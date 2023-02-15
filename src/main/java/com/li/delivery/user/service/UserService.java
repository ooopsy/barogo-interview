package com.li.delivery.user.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.li.delivery.common.contants.ResultCode;
import com.li.delivery.common.exception.RuntimeExceptionBase;
import com.li.delivery.user.model.UserModel;
import com.li.delivery.user.repository.UserRepository;

@Service
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	public UserModel getUser(String userName) {
		UserModel user = userRepository.findUserByUserName(userName);
		return user;
	}

	public void createUserDetails(UserModel user) {
		UserModel existingUser = getUser(user.getUserName());
		if (existingUser != null) {
			throw new RuntimeExceptionBase(ResultCode.DUPLICATE, "사용자가 존재합니다.");
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		List<GrantedAuthority> authorityList = new ArrayList<>();
		authorityList.add(new SimpleGrantedAuthority("USER_ROLE"));
	    userRepository.save(user);
	}
}
