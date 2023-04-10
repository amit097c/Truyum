package com.truyum.services;

import static com.truyum.constants.UserConstants.USERNAME_ALREADY_EXISTS;
import static com.truyum.enumerations.Role.ROLE_USER;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.truyum.dao.UserDao;
import com.truyum.exception.domain.UsernameExistException;
import com.truyum.model.SecurityUser;
import com.truyum.model.User;
import com.truyum.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService implements UserDao,UserDetailsService
  {
	@Autowired
	ApplicationContext context;
	private Logger LOGGER=LoggerFactory.getLogger(getClass());
	
	public User register(String username, String password) throws UsernameExistException
	  {
		LOGGER.info("Regiter Method executing");
		UserRepository userRepo=context.getBean(UserRepository.class);
		try 
		  {
			validateUsername(username);
		  }
		catch(UsernameExistException e) 
		  {
			System.out.println("Validation failed!!");
			e.printStackTrace();
			throw new UsernameExistException(USERNAME_ALREADY_EXISTS);
		  }
		catch(UsernameNotFoundException e) 
		  {
			LOGGER.info(e.getMessage());
		  }
		String encodedPassword=encodePassword(password);
		User user=new User();
		user.setId( Integer.parseInt(generateUserId()));
		user.setUsername(username);
		user.setPassword(encodedPassword);
		user.setRole(ROLE_USER.name());
		user.setJoinDate(new Date().toString());
		user.setNotLocked(true);
		user.setActive(true);
		user.setAuthorities(ROLE_USER.getAuthorities());
		userRepo.save(user);
		System.out.println("New customer Registered Successfully: " + user);
		LOGGER.info("New customer Registered Successfully: " + user);
		return user;
	  }
	private User validateUsername(String username) throws UsernameExistException
	  {
			LOGGER.info("Validating username: "+username);
			User user=findUserByUsername(username);
			
			if(user!=null) 
			  {
				 System.out.println("user is "+user);
				 LOGGER.info("username already exists: "+username);
				 throw new UsernameExistException(USERNAME_ALREADY_EXISTS);
			  }
			LOGGER.info("username doen't exists: "+username);
			return user;
		 
	  }
	@Override
	public List<User> getUsers() 
	  {
		UserRepository userRepo=context.getBean(UserRepository.class);
		Iterable<User> iterable=userRepo.findAll();
		return StreamSupport.stream(iterable.spliterator(),false).collect(Collectors.toList());
		
	  }

	@Override
	public User findUserByUsername(String username) throws UsernameNotFoundException 
	  {
		UserRepository userRepo=context.getBean(UserRepository.class);
		 for(User user:userRepo.findAll()) 
		   {
			 System.out.println(user);
			 if(user.getUsername().equals(username)) 
			   {
				 return user;
			   }
		   }
		 throw new UsernameNotFoundException(username +"does not exist in the server database");
		 
		//return null;
	  }
	
	private String encodePassword(String password) 
	  {
		return getBCryptPasswordEncoder().encode(password);
	  }
	@Bean
	private BCryptPasswordEncoder getBCryptPasswordEncoder() 
	  {
		return new BCryptPasswordEncoder();
	  }
	private String generateUserId() 
	  {
		return RandomStringUtils.randomNumeric(5);
	  }
	@Override
	public UserDetails loadUserByUsername(String username) 
	 {
		LOGGER.info("LoadUserbyUsername method : "+username);
		UserRepository userRepo=context.getBean(UserRepository.class);
		User user=null;
		try 
	       {
			   Optional<User> userOpt=userRepo.findByUsername(username);
			   user=userOpt.get();
			   
	       }
		catch(NoSuchElementException usernameNotFound) 
		  {
			  LOGGER.info(username +" does not exist in the server database");
			  LOGGER.info("Exception: "+usernameNotFound.getClass());
			  throw new NoSuchElementException( username+" does not exist in the server database"); 
		  }
		SecurityUser securityUser=new SecurityUser(user);
		LOGGER.info("UserLoaded : "+username);
		return securityUser;
	}
  }
