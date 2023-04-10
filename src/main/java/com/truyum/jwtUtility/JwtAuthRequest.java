package com.truyum.jwtUtility;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
//@Data
@NoArgsConstructor
@Setter @Getter
@AllArgsConstructor
@ToString@Entity
public class JwtAuthRequest 
{
	@Id
	int id;
	String username;
	String password;
	

}
