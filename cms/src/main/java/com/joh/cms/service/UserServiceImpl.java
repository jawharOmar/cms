package com.joh.cms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joh.cms.dao.UserDAO;
import com.joh.cms.model.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDAO;
	
	@Override
	public Iterable<User> findAll() {
		return userDAO.findAll();
	}

	@Override
	public User save(User user) {
		return userDAO.save(user);
	}

	@Override
	public User findByUserName(String userName) {
		return userDAO.findByUserName(userName);
	}

}
