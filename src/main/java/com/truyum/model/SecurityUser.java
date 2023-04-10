package com.truyum.model;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import static java.util.Arrays.stream;
@Component
public class SecurityUser implements UserDetails
 {
	private User user;
	public SecurityUser(User user) 
	  {
		this.user=user;
	  }
	@Override
	public String getUsername()
	  {
		return user.getUsername();
	  }

	@Override
	public String getPassword()
	  {
		return user.getPassword();
	  }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() 
	  {		
		return stream(user.getAuthorities()).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	  }

	@Override
	public boolean isAccountNonExpired() 
	  {
		return true;
	  }

	@Override
	public boolean isAccountNonLocked()
	  {
		return user.isNotLocked();
	  }

	@Override
	public boolean isCredentialsNonExpired()
	  {
		return true;
	  }

	@Override
	public boolean isEnabled()
	  {
		return user.isActive();
	  }

 }
