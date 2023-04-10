package com.truyum.dao;

import java.util.List;



import com.truyum.model.User;

import com.truyum.exception.domain.UsernameExistException;

public interface UserDao {
	User register(String username, String password)throws UsernameExistException;
    List<User> getUsers();
    User findUserByUsername(String username);


}
