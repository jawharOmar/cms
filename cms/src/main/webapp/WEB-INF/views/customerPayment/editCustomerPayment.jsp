<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div>

	<sf:form id="edit-customer-payment-form" method="POST"
		commandName="customerPayment" onsubmit="editCustomerPayment(event)">
		<sf:input type="hidden" path="id" />
		<table>
			<tbody>

				<tr>
					<td class="text-left"><spring:message
							code="addCustomerPayment.totalPayment" /></td>
					<td><sf:input type="number" step="any" class="form-control"
							path="totalPayment" /></td>
					<td><sf:errors path="totalPayment" /></td>
				</tr>

				<tr>
					<td class="text-left"><spring:message
							code="addCustomerPayment.project" /></td>
					<td><select id="productUnit" class="form-control"
						name="customerProject[id]">
							<option value=""><spring:message
									code="addCustomerPayment.choose" /></option>
							<c:forEach items="${customer.customerProjects}" var="item">

								<c:choose>
									<c:when test="${item.id==customerPayment.customerProject.id}">
										<option selected="selected" value="${item.id}">${item.name}</option>
									</c:when>
									<c:otherwise>
										<option value="${item.id}">${item.name}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
					</select></td>

					<td><sf:errors path="customerProject" /></td>
				</tr>

				<tr>
					<td><button class="btn btn-warning">
							<i class="fa fa-edit"></i>
						</button></td>
				</tr>

			</tbody>

		</table>

	</sf:form>

</div>


<script>
	var csrf = '${_csrf.token}';
	function editCustomerPayment(event) {
		event.preventDefault();
		console.log("editCustomerPayment->fired");

		var data = $("#edit-customer-payment-form").serializeJSON();
		console.log("data=", data);
		$
				.ajax({
					type : "POST",
					url : "<c:url value="/customerPayments/update/customer/"/>${customer.id}",
					headers : {
						'X-CSRF-TOKEN' : csrf
					},
					data : JSON.stringify(data),
					contentType : "application/json",
					success : function(response) {
						$("#modal-body").html(response);
						$("#modal").modal("show");
					},
					error : function(response) {
						$("#modal-body").html(response.responseText);
						$("#modal").modal("show");
					}
				});
	}
</script>