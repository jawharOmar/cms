<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jsp:useBean id="now" class="java.util.Date" />
<fmt:formatDate var="currentDate" value="${now}" pattern="yyyy-MM-dd" />
<script>
	var csrf = '${_csrf.token}';
</script>


<div>

	<h2>
		<spring:message code="users.title" />
	</h2>

	<div class="add-new-doctor-div">
		<button class="btn btn-success" onclick="getAddingCustomer()">
			<i class="fa fa-plus"></i>
		</button>
	</div>

	<table class="table table-bordered">
		<thead>
			<tr>
				<td><spring:message code="users.userName" /></td>
				<td><spring:message code="users.enabled" /></td>
				<td><spring:message code="users.function" /></td>
			</tr>
		</thead>
		<tbody>


			<c:forEach items="${users}" var="item">
				<tr>
					<td>${item.userName}</td>
					<td>${item.enabled}</td>
					<td>
						<div>

							<a class="btn btn-info" target="_blank"
								href="<c:url value="/customerPayments/customer/" />${item.id}">
								<i class="fa fa-money"></i>
							</a> <a class="btn btn-info" target="_blank"
								href="<c:url value="/customerProjects/customer/" />${item.id}">
								<i class="fa fa-tasks "></i>
							</a>
							<button class="btn btn-danger"
								onclick="deleteCustomer(${item.id})">
								<i class="fa fa-times"></i>
							</button>
							<button class="btn btn-warning"
								onclick="editingCustomer(${item.id})">
								<i class="fa fa-edit"></i>
							</button>
						</div>
					</td>


				</tr>
			</c:forEach>

		</tbody>

	</table>

</div>





