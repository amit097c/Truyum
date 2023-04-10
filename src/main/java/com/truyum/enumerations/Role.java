package com.truyum.enumerations;
import static  com.truyum.constants.Authority.USER_AUTHORITIES;
import static  com.truyum.constants.Authority.ADMIN_AUTHORITIES;

public enum Role 
  {
    ROLE_USER(USER_AUTHORITIES),
	ROLE_ADMIN(ADMIN_AUTHORITIES);
	private String[] authorities;
	Role(String... authorities)
	  {
		this.authorities=authorities;
	  }
	public String[] getAuthorities() 
	  {
		return authorities;
	  }
  }
