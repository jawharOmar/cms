package com.joh.cms.service;

import java.util.Date;
import java.util.List;

import com.joh.cms.model.OrderProductStepUp;

public interface OrderProductServiceService {

	OrderProductStepUp save(OrderProductStepUp orderProductStepUp);

	List<OrderProductStepUp> findAllByOrderTimeBetween(Date from, Date to);

	OrderProductStepUp findOne(int id);

	OrderProductStepUp update(OrderProductStepUp orderProductStepUp);

	void delete(int id);

	List<OrderProductStepUp> findAllByProductStepUpsProductCode(String code);

}
