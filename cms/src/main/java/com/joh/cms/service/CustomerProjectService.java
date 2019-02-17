package com.joh.cms.service;

import com.joh.cms.model.CustomerProject;

public interface CustomerProjectService {

	void delete(int id);

	CustomerProject save(CustomerProject customerPoroject);

	CustomerProject update(CustomerProject customerPoroject);

	CustomerProject findOne(int id);

}
