package com.joh.cms.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.joh.cms.model.ProductStepUp;

public interface ProductStepUpDAO extends CrudRepository<ProductStepUp, Integer> {

	@Query(value = "SELECT PS.* FROM PRODUCTS P INNER JOIN  PRODUCT_STEPUPS PS USING(I_PRODUCT)\n"
			+ "WHERE  QUANTITY-SOLD_QUANTITY>0 \n" + "AND PRODUCT_CODE= ?1 "
			+ "ORDER BY EXPIRATION_DATE LIMIT 1;", nativeQuery = true)
	ProductStepUp findProductStepUpForStockDown(String productCode);

	@Modifying
	@Query(value = "UPDATE PRODUCT_STEPUPS SET SOLD_QUANTITY=SOLD_QUANTITY+1 WHERE I_PRODUCT_STEPUP= ?1 ", nativeQuery = true)
	void stockDown(Integer id);

	@Query("SELECT P FROM ProductStepUp P WHERE expirationDate<=?1 AND quantity-soldQuantity>0 ORDER BY expirationDate ")
	List<ProductStepUp> findAllByExpirationDateLessThanEqualOrderByExpirationDate(Date to);

	@Modifying
	@Query("delete from ProductStepUp p where p.id = ?1 ")
	void delete(Integer id);
}
