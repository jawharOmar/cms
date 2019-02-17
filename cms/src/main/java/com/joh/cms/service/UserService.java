package com.joh.cms.service;

import com.joh.cms.model.User;

public interface UserService {

	User save(User user);

	User findByUserName(String userName);

	Iterable<User> findAll();

}
