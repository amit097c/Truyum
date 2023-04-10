package com.truyum.dao;

import java.util.List;

import com.truyum.model.MenuItem;

public interface MenuItemDao
  {
    List<MenuItem> getMenuItemListAdmin();
    List<MenuItem> getMenuItemListCustomer();
    void modifyMenuItem(MenuItem menuItem);
    MenuItem getMenuItem(long menuItemId);
    void saveMenuItem(MenuItem menuItem);
	
  }
