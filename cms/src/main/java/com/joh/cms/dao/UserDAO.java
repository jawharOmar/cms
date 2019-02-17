package com.joh.cms.dao;

import org.springframework.data.repository.CrudRepository;

import com.joh.cms.model.User;

public interface UserDAO extends CrudRepository<User, Integer> {

	User findByUserName(String userName);
}
