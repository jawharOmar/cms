package com.joh.cms.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.joh.cms.model.CustomerOrder;

public interface CustomerOrderDAO extends CrudRepository<CustomerOrder, Integer>, CustomerOrderDAOExt {
	List<CustomerOrder> findAllByOrderTimeBetween(Date from, Date to);

	@Query("SELECT SUM(C.totalPrice-C.totalPayment) FROM CustomerOrder C WHERE C.totalPrice!=C.totalPayment AND C.id= ?1 ")
	Double totalLoan(int id);
}
