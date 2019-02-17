<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>

<head>

<style type="text/css">
* {
	direction: rtl;
}
td,th{
text-align: right;
}
</style>


</head>

<body>


	<div class="cus-print-container">


		<h3 class="cus-center">
			<b>Rosh</b>
		</h3>

		<p>
			# [${customerOrder.id} ]
			<fmt:formatDate pattern="yyyy-MM-dd  hh:mm:ss"
				value="${customerOrder.orderTime}" />
		</p>

		<table style="width: 100%; font-size: 14px;">
			<tr>
				<td><spring:message code="getCustomerOrder.customerName" /></td>
				<td>${customerOrder.customer.fullName}</td>
			</tr>
			<tr>
				<td><spring:message code="getCustomerOrder.totalPrice" /></td>
				<td><fmt:formatNumber type="number" maxFractionDigits="3"
						value="${customerOrder.totalPrice}" /></td>
			</tr>
		</table>
		<hr>

		<table class="cus-center" style="width: 100%">

			<thead>
				<tr>
					<th><spring:message code="getCustomerOrder.name" /></th>
					<th><spring:message code="getCustomerOrder.quantity" /></th>
					<th><spring:message code="getCustomerOrder.price" /></th>
					<th><spring:message code="getCustomerOrder.total" /></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${customerOrder.customerOrderDetails}" var="item">
					<tr>
						<td>${item.productName}</td>
						<td>${item.quantity}</td>
						<td><fmt:formatNumber type="number" maxFractionDigits="3"
								value="${item.price}" /></td>

						<td><fmt:formatNumber type="number" maxFractionDigits="3"
								value="${item.price*item.quantity}" /></td>

					</tr>
				</c:forEach>
			</tbody>

		</table>

	</div>
	<hr>

	<script type="text/javascript">
		function printing() {
			console.log("print fired");
			window.print();
		}
		printing();
	</script>


</body>
</html>




