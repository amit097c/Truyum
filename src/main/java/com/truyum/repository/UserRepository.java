package com.truyum.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.truyum.model.User;

public interface UserRepository extends CrudRepository<User,Integer>
 {
	Optional<User>findByUsername(String username);
 }
