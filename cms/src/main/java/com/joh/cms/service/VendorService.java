package com.joh.cms.service;

import com.joh.cms.model.Vendor;

public interface VendorService {

	Vendor save(Vendor vendor);

	Iterable<Vendor> findAll();

	void delete(int id);

	Vendor findOne(int id);

	Vendor update(Vendor vendor);

}
