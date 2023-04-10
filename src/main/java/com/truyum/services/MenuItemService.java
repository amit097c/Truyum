package com.truyum.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Component;

import com.truyum.dao.MenuItemDao;

import com.truyum.model.MenuItem;
import com.truyum.repository.MenuItemRepository;
@Component
public class MenuItemService implements MenuItemDao
  {
	@Autowired
	ApplicationContext context;
	public List<MenuItem> getMenuItemListAdmin()
	 {
		MenuItemRepository menuItemRepo=context.getBean(MenuItemRepository.class);
		List<MenuItem> itemList=new ArrayList<MenuItem>();
		for(MenuItem item:menuItemRepo.findAll()) 
		  {
			itemList.add(item);
		  }
		return itemList;
	 }

	public List<MenuItem> getMenuItemListCustomer() 
	  {
		//some of the attributes will be hidden from frontend only.
		MenuItemRepository menuItemRepo=context.getBean(MenuItemRepository.class);
		List<MenuItem> itemList=new ArrayList<MenuItem>();
		for(MenuItem item:menuItemRepo.findAll()) 
		  {
			itemList.add(item);
		  }
		return itemList;
	  }

	public void modifyMenuItem(MenuItem menuItem) 
	  {
		MenuItemRepository menuItemRepo=context.getBean(MenuItemRepository.class);
		MenuItem item= menuItemRepo.findById(menuItem.getId()).get();
		item.setName(menuItem.getName());
		item.setActive(menuItem.getActive());
		item.setPrice(menuItem.getPrice());
		item.setFreeDelivery(menuItem.getFreeDelivery());
		item.setCategory(menuItem.getCategory());
		item.setDateOfLaunch(menuItem.getDateOfLaunch());		
		menuItemRepo.save(item);
	  }
	public MenuItem getMenuItem(long menuItemId)
	  {
		MenuItemRepository menuItemRepo=context.getBean(MenuItemRepository.class);
		Optional<MenuItem> item= menuItemRepo.findById(menuItemId);
		System.out.println(item);
		return item.isPresent()?item.get():null;	
	  }
	public void saveMenuItem(MenuItem menuItem) 
	  {
		MenuItemRepository menuItemRepo=context.getBean(MenuItemRepository.class);
		menuItemRepo.save(menuItem);
		
	  }


}
