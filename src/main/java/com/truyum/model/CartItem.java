package com.truyum.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
public class CartItem 
{
	
	List<MenuItem> menuItemList;
	double total;

}
