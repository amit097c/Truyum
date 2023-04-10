package com.truyum.authentication;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import com.truyum.services.UserService;

@Component
public class UserAuth 
  {
	/*private AuthenticationManager authenticationManager;
	private UserService service;
	//private JWTTokenProvider jwtTokenProvider;
	
	UserAuth(AuthenticationManager authenticationManager,UserService service,JWTTokenProvider)
	  {
		this.authenticationManager=authenticationManager;
		this.service=service;
		//this.jwtTokenProvider=jwtTokenProvider;
	  }
	//login()
    void authenticate(String username,String password) 
      {
    	this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
      }*/
  }
