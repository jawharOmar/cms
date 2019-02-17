<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<script type="text/javascript">
	var jsonCustomers = '<spring:escapeBody  javaScriptEscape="true">${jsonCustomers}</spring:escapeBody>';
	var jsonProducts = '<spring:escapeBody  javaScriptEscape="true">${jsonProducts}</spring:escapeBody>';
	var jsonCustomerOrder = '<spring:escapeBody  javaScriptEscape="true">${jsonCustomerOrder}</spring:escapeBody>';
	var csrf = '${_csrf.token}';
</script>

<div ng-app="addCustomerOrder" ng-controller="addCustomerOrder"
	ng-init="init()" ng-form name="form">
	<h2>
		<spring:message code="addCustomerOrder.title" />
	</h2>
	<button class="btn btn-lg btn-outline-success"
		onClick="window.location.reload()">
		<i class="fa fa-refresh"></i>
	</button>
	<div id="freeze">
		<table class="table table-sm cus-table-borderless">
			<tbody>
				<tr>
					<td><spring:message code="addCustomerOrder.customerName" /></td>
					<td><input required="required" id="customer-autoselect"
						class="form-control form-control-sm"
						ng-model="cusomerOrder.customer.fullName"></td>
				</tr>
				<tr>
					<td><spring:message code="addCustomerOrder.phone" /></td>
					<td><input required="required" readonly="readonly"
						class="form-control form-control-sm"
						ng-model="customerOrder.customer.phone"></td>
				</tr>
				<tr>
					<td><spring:message code="addCustomerOrder.project" /></td>
					<td><select class="form-control form-control-sm"
						ng-model="customerOrder.customerProject.id"
						ng-options="item.id as item.name for item in customerOrder.customer.customerProjects">
							<option value="">
								<spring:message code="addCustomerOrder.choose" />
							</option>
					</select></td>
				</tr>
			</tbody>
		</table>

		<table class="table table-bordered table-sm">
			<tbody>
				<tr>
					<th><spring:message code="addCustomerOrder.code" /></th>
					<th><spring:message code="addCustomerOrder.name" /></th>
					<th><spring:message code="addCustomerOrder.quantity" /></th>
					<th><spring:message code="addCustomerOrder.price" /></th>
					<th><spring:message code="addCustomerOrder.unitType" /></th>
					<th><spring:message code="addCustomerOrder.function" /></th>
				</tr>

				<tr>

					<th><input id="autoselect"
						class="form-control form-control-sm" ng-model="product.code"
						ng-keypress="getProduct($event)"></th>
					<th><input id="productName"
						class="form-control form-control-sm" ng-model="product.name"
						readonly></th>
					<th><input type="number" class="form-control form-control-sm"
						ng-model="product.quantity" placeholder={{product.stockLevel}}></th>
					<th><input type="number" step="any"
						class="form-control form-control-sm" ng-model="product.price"></th>
					<th><input class="form-control form-control-sm"
						ng-model="product.unitType" readonly></th>
					<th>
						<button
							ng-disabled="!product.quantity||product.quantity<0||!product.name"
							class="btn btn-sm btn-primary"
							ng-click="addCustomerOrderDetail()">
							<i class="fa fa-plus"></i>
						</button>
					</th>

				</tr>
			</tbody>
			<tbody>
				<tr ng-repeat="item in customerOrder.customerOrderDetails">
					<td>{{item.productCode}}</td>
					<td>{{item.productName}}</td>
					<td>{{item.quantity}}</td>
					<td>{{item.price}}</td>
					<td>&nbsp;</td>
					<td>
						<button class="btn btn-sm btn-outline-danger"
							ng-click="removeCustomerOrderDetail($index)">
							<i class="fa fa-times"></i>
						</button>
					</td>
				</tr>
			</tbody>
		</table>
		<hr>
		<div>
			<table style="width: 800px">
				<tr>
					<td><spring:message code="addCustomerOrder.totalPrice" /></td>
					<td><input ng-value="totalPrice()|number : 3"
						class="form-control form-control-sm" readonly></td>
				</tr>
				<tr>
					<td><spring:message code="addCustomerOrder.totalPayment" /></td>
					<td>
						<div ng-if="totalPrice()!=0">
							<span ng-if="totalPrice()==customerOrder.totalPayment"> <spring:message
									code="addCustomerOrder.cash" />
							</span> <span ng-if="customerOrder.totalPayment==0"> <spring:message
									code="addCustomerOrder.debt" />
							</span> <span
								ng-if="customerOrder.totalPayment&&totalPrice()>customerOrder.totalPayment&&totalPrice()!=customerOrder.totalPayment">
								<spring:message code="addCustomerOrder.semidebt" />
							</span>

						</div> <input ng-max="totalPrice()" required="required" step="any"
						type="number" ng-model="customerOrder.totalPayment"
						class="form-control form-control-sm">
					</td>
				</tr>


			</table>
		</div>
		<button
			ng-disabled="!form.$valid || customerOrder.customerOrderDetails.length==0"
			class="btn btn-sm btn-success" ng-click="addCustomerOrder()">
			<i class="fa fa-save"></i>
		</button>
	</div>
</div>