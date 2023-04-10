package com.truyum.model;

import java.util.Date;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Component
@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity
@Table(name="users_info")
public class User 
  {
	  @Id
	  private int id;
	  private String username;
	  private String password;
	  private String lastLoginDate;
	  private String lastLoginDateDisplay;
	  private String joinDate;
	  private String role;
	  private String[] authorities;

	  private boolean isActive;
	  private boolean isNotLocked;

  }
