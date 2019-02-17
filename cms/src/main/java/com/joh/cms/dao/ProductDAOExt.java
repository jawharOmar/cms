package com.joh.cms.dao;

import java.util.List;

import com.joh.cms.domain.model.ProductD;

public interface ProductDAOExt {

	List<ProductD> findStock();


	ProductD findProductByCode(String productCode);


	ProductD findProductByProductStepUpId(int productStepUpId);
}
