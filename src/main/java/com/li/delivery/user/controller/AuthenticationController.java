package com.li.delivery.user.controller;

import com.li.delivery.common.contants.ResultCode;
import com.li.delivery.common.model.ResponseModel;
import com.li.delivery.user.model.UserModel;
import com.li.delivery.user.service.UserService;
import com.li.delivery.util.JwtTokenUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final Log logger = LogFactory.getLog(getClass());

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;

    public AuthenticationController(AuthenticationManager authenticationManager,
    		UserService userService, 
    		JwtTokenUtil jwtTokenUtil) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/login")
    public ResponseModel<?> loginUser(@RequestParam("userName") String username,
                                       @RequestParam("password") String password) {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        if (auth.isAuthenticated()) {
            logger.info("Logged In");
            UserModel user = userService.getUser(username);
            String token = jwtTokenUtil.generateToken(user);
            return new ResponseModel<>(ResultCode.OK, token);
        } else {
            return new ResponseModel<>(ResultCode.NO_AUTHRORIZATION);
        }
    
    }

    
}
