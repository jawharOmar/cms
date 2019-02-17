package com.joh.cms.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name = "CUSTOMER_PAYMENTS")
public class CustomerPayment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "I_CUSTOMER_PAYMENT")
	private int id;

	@Column(name = "TOTAL_PAYMENT")
	private Double totalPayment;

	@ManyToOne()
	@JoinColumn(name = "I_CUSTOMER_PROJECT")
	private CustomerProject customerProject;

	@Column(name = "PAYMENT_TIME", insertable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp()
	@ColumnDefault("CURRENT_TIMESTAMP")
	private Date time;

	@JsonProperty(access = Access.READ_ONLY)
	@ManyToOne()
	@JoinColumn(name = "I_CUSTOMER")
	private Customer customer;

	public CustomerProject getCustomerProject() {
		return customerProject;
	}

	public void setCustomerProject(CustomerProject customerProject) {
		this.customerProject = customerProject;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Double getTotalPayment() {
		return totalPayment;
	}

	public void setTotalPayment(Double totalPayment) {
		this.totalPayment = totalPayment;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public String toString() {
		return "CustomerPayment [id=" + id + ", totalPayment=" + totalPayment + ", customerProject=" + customerProject
				+ ", time=" + time + "]";
	}

}
