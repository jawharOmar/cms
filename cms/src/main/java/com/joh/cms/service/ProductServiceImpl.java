package com.joh.cms.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.joh.cms.dao.PriceCategoryDAO;
import com.joh.cms.dao.ProductDAO;
import com.joh.cms.domain.model.ProductD;
import com.joh.cms.exception.CusDataIntegrityViolationException;
import com.joh.cms.exception.ItemExistsException;
import com.joh.cms.model.PriceCategory;
import com.joh.cms.model.Product;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDAO productDAO;

	@Override
	public Product findOne(int id) {
		return productDAO.findOne(id);
	}

	@Override
	public Iterable<Product> findAll() {
		return productDAO.findAll();
	}

	@Override
	@Transactional
	public Product save(Product product) {

		System.err.println(product.getProductUnitType() != null && product.getProductUnitType().getId() == 1);
		System.out.println((product.getPacketSize() == null || product.getPacketSize() <= 0));
		System.err.println(product.getProductUnitType() != null && product.getProductUnitType().getId() == 1
				&& (product.getPacketSize() == null || product.getPacketSize() <= 0));

		if (product.getProductUnitType() != null && product.getProductUnitType().getId() == 1
				&& (product.getPacketSize() == null || product.getPacketSize() <= 0)) {
			throw new CusDataIntegrityViolationException("Packet size is not set");
		}

		if (product.getProductUnitType() == null || product.getProductUnitType().getId() != 1) {
			product.setPacketSize(null);
		}

		try {
			product.getProductPriceCategories().removeIf(e -> e.getPrice() == null || e.getPrice() == 0);
			return productDAO.save(product);
		} catch (DataIntegrityViolationException e) {
			throw new ItemExistsException(e.getMessage());
		}
	}

	@Override
	@Transactional
	public List<ProductD> findStock() {
		List<ProductD> productDs = productDAO.findStock();

		productDs.forEach(e -> {
			e.setProductPriceCategories(productDAO.findOne(e.getProductId()).getProductPriceCategories());
		});

		return productDs;
	}

	@Override
	@Transactional
	public void delete(int id) {
		productDAO.delete(id);
	}

	@Override
	@Transactional
	public Product update(Product product) {
		productDAO.findOne(product.getId());
		// pack id=1
		if (product.getProductUnitType() != null && product.getProductUnitType().getId() == 1
				&& (product.getPacketSize() == null || product.getPacketSize() <= 0)) {
			throw new CusDataIntegrityViolationException("Packet size is not set");
		}

		if (product.getProductUnitType() == null || product.getProductUnitType().getId() != 1) {
			product.setPacketSize(null);
		}

		return productDAO.save(product);
	}

	@Override
	public ProductD findProductByCode(String code) {
		try {
			ProductD findProductByCode = productDAO.findProductByCode(code);
			findProductByCode.setProductPriceCategories(
					productDAO.findOne(findProductByCode.getProductId()).getProductPriceCategories());
			return findProductByCode;
		} catch (EmptyResultDataAccessException e) {
			throw new EntityNotFoundException();
		}

	}

	@Override
	public ProductD findProductByProductStepUpId(int productStepUpId) {
		try {
			return productDAO.findProductByProductStepUpId(productStepUpId);
		} catch (EmptyResultDataAccessException e) {
			throw new EntityNotFoundException();
		}

	}

}
