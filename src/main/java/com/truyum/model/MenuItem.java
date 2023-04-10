package com.truyum.model;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
@Entity
@Table(name="menuitem")
public class MenuItem 
  {
		@Id
		long id;
		@Column(name="fname")
		String name;
		float price;
		String active;
		@Column(name="dateoflaunch")
		String dateOfLaunch;
		String category;
		@Column(name="freedelivery")
		String freeDelivery;
  }
