<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<table class="table table-bordered">
	<tr>
		<td>Customer name</td>
		<td>${customerPayment.customer.fullName}</td>
	</tr>

	<tr>
		<td>Payment Time</td>
		<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"
				value="${customerPayment.time}" /></td>
	</tr>
	<tr>
		<td>Total Payment</td>
		<td><fmt:formatNumber maxFractionDigits="3"
				value="${customerPayment.totalPayment}" /></td>
	</tr>
	<tr>
		<td>Total Loan</td>
		<td><fmt:formatNumber maxFractionDigits="3"
				value="${totalLoan}" /></td>
	</tr>

</table>
