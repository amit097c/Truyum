package com.truyum.filter;


import java.io.IOException;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.truyum.constants.SecurityConstant;
import com.truyum.jwtUtility.JWTTokenProvider;

//import ch.qos.logback.classic.Logger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter
  {

	@Autowired
	private UserDetailsService userService;
	@Autowired
	JWTTokenProvider jwtTokenProvider;
	
	private Logger LOGGER=LoggerFactory.getLogger(getClass());
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException
	  {
		
		//System.out.println("****request.getMethod()**"+request.getMethod());
		LOGGER.info("Inside doFilter Internal method of JwtAuthenticationFilter class");
		if(request.getMethod().equalsIgnoreCase(SecurityConstant.OPTIONS_HTTP_METHOD)) 
		  {
			response.setStatus(HttpStatus.OK.value());
		  }
		else 
			
		  {
			
			String authorizationHeader =request.getHeader(HttpHeaders.AUTHORIZATION);
			if(authorizationHeader==null||!authorizationHeader.startsWith(SecurityConstant.TOKEN_PREFIX)) 
			 {
				 LOGGER.info("User is logging for the first time as authorizationHeader: "+authorizationHeader);
				 System.out.println("authorizationHeader: "+authorizationHeader);
				 filterChain.doFilter(request,response);
				 return;
			 }	
	    LOGGER.info("User request: "+request);

		System.out.println(request);
		//1. Get token
		String requestToken =request.getHeader(HttpHeaders.AUTHORIZATION);
		LOGGER.info("request header (requestToken): "+requestToken);
		// Bearer t3495ujaa
		System.out.println("request Token is :"+requestToken);
		String username=null;
		String token=null;
		if(request!=null && requestToken.startsWith(SecurityConstant.TOKEN_PREFIX)) 
		  {
			token=requestToken.substring(SecurityConstant.TOKEN_PREFIX.length());
			try 
			  {
			    username=jwtTokenProvider.getUsernameFromToken(token);
			    LOGGER.info("Get username from token: "+username);
			  }
			catch(IllegalArgumentException e) 
			  {
				System.out.println("Unable to get Jwt token");
			  }
			catch(Exception  e) 
			  {
				System.out.println("Exception is : "+ e);
			  }
		  }
		else 
		  {
			System.out.println("Token does not start with bearer");
		  }
		//Once we got the token, now validate
		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) 
		  {
			LOGGER.info("Once we got the token now validate it: "+token);
			List<GrantedAuthority> authorities=jwtTokenProvider.getAuthorities(token);
			LOGGER.info("Authorities: "+authorities);
			Authentication authentication=jwtTokenProvider.getAuthentication(username, authorities, request);
			LOGGER.info("Authentication: "+authentication);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			LOGGER.info("set the authentication in SecurityContextHolder: "+authentication);
		  }
		else 
		  {
			LOGGER.info("clearing SecurityContextHolder");
			SecurityContextHolder.clearContext();
		  }
		LOGGER.info("call filterChain.doFilter(request,response) method");
		filterChain.doFilter(request,response);
	  }
		LOGGER.info("call filterChain.doFilter(request,response) method");
		filterChain.doFilter(request,response);
     }
  }
