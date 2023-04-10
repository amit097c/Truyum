package com.truyum.services;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.truyum.model.SecurityUser;
import com.truyum.model.User;
import com.truyum.repository.UserRepository;
@Service
public class JpaUserDetailsService implements UserDetailsService
  {
	@Autowired
	ApplicationContext context;

	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	  {
		UserRepository userRepo=context.getBean(UserRepository.class);
		return (UserDetails) userRepo.findByUsername(username)
				.map(SecurityUser::new)
				.orElseThrow(()->new UsernameNotFoundException("username not found: "+username));
						
	  }

  }
