package com.joh.cms.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "CUSTOMERS")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "I_CUSTOMER")
	private int id;

	@NotBlank(message = "Full Name is blank")
	@Column(name = "FULL_NAME")
	private String fullName;

	@NotBlank(message = "Phone is blank")
	@Column(name = "PHONE")
	private String phone;

	@NotBlank(message = "Address is blank")
	@Column(name = "ADDRESS")
	private String address;

	@Column(name = "NOTE")
	private String note;

	@ManyToOne()
	@JoinColumn(name = "I_PRICE_CATEGORY")
	private PriceCategory priceCategory;

	@OneToMany
	@JoinColumn(name = "I_CUSTOMER")
	private List<CustomerProject> customerProjects = new ArrayList<>();

	@OneToMany(mappedBy = "customer")
	private List<CustomerPayment> customerPayments = new ArrayList<>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public PriceCategory getPriceCategory() {
		return priceCategory;
	}

	public void setPriceCategory(PriceCategory priceCategory) {
		this.priceCategory = priceCategory;
	}

	public List<CustomerProject> getCustomerProjects() {
		return customerProjects;
	}

	public void setCustomerProjects(List<CustomerProject> customerProjects) {
		this.customerProjects = customerProjects;
	}

	public List<CustomerPayment> getCustomerPayments() {
		return customerPayments;
	}

	public void setCustomerPayments(List<CustomerPayment> customerPayments) {
		this.customerPayments = customerPayments;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", fullName=" + fullName + ", phone=" + phone + ", address=" + address + ", note="
				+ note + ", priceCategory=" + priceCategory + ", customerProjects=" + customerProjects
				+ ", customerPayments=" + customerPayments + "]";
	}

}
