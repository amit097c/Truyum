package com.truyum.jwtUtility;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Setter @Getter
@AllArgsConstructor
@ToString@Entity
public class JwtAuthResponse 
{
	
	@Id
	int id;
	String username;
	String jwtToken;
	long expiration_time;
	

}
