$(document).ready()
{
	console.log("csrf=",csrf);
}

// Angular

appAddCusotmerOrder = angular.module("addCustomerOrder", []);


appAddCusotmerOrder.factory('httpRequestInterceptor', function () {
	  return {
	    request: function (config) {
	      config.headers['X-CSRF-TOKEN'] = csrf;
	      return config;
	    }
	  };
});

appAddCusotmerOrder.config(function ($httpProvider) {
	  $httpProvider.interceptors.push('httpRequestInterceptor');
});

appAddCusotmerOrder.controller('addCustomerOrder', function($scope, $http) {
	
    $scope.customerOrder;

	$scope.customers;
		
	$scope.totalPrice=function(){
		var totalPrice=0;
		for(var i = 0; i < $scope.customerOrder.customerOrderDetails.length; i++){
			var quantity= $scope.customerOrder.customerOrderDetails[i].quantity;
			var price= $scope.customerOrder.customerOrderDetails[i].price;
			totalPrice+=quantity*price;
		}

        return Number((totalPrice).toFixed(3));
	};

	$scope.init = function() {
		console.log("init->fired");
		console.log("jsonCustomers=", jsonCustomers);
		
		$scope.products = JSON.parse(jsonProducts);

		
		console.log("jsonProducts=", jsonProducts);
		$scope.products = JSON.parse(jsonProducts);
		console.log("$scope.products=", $scope.products);
		
		$scope.customerOrder=JSON.parse(jsonCustomerOrder);
		console.log("$scope.customerOrder=", $scope.customerOrder);
		
		$scope.customers=JSON.parse(jsonCustomers);
		console.log("$scope.customerOrder=", $scope.customerOrder);
		
		

		// S-Product AutoCompletion
		var productAuto = [];

		angular.forEach($scope.products, function(value, key) {
			var obj = {
				label : value.name + " " + value.code,
				value : value.code,
				data : value
			}
			productAuto.push(obj);
		});

		$("#autoselect")
				.autocomplete(
						{
							source : productAuto,
							select : function(event, ui) {
								var item = ui.item.data;
								console.log("selected item =", item);
								
								$scope.product.code = item.code;
								
								$scope.$digest();
							}
						});
		
		// E-Product AutoCompletion
		
		
		// S-Customer AutoCompletion
		var customerAuto = [];

		angular.forEach($scope.customers, function(value, key) {
			var obj = {
				label : value.fullName + " " + value.phone,
				value : value.fullName,
				data : value
			}
			customerAuto.push(obj);
		});
		console.log("customerAuto=",customerAuto);

		$("#customer-autoselect")
				.autocomplete(
						{
							source : customerAuto,
							select : function(event, ui) {
								var item = ui.item.data;
								console.log("selected item =", item);
								
								$scope.customerOrder.customer=item;
								
								$scope.$digest();
							}
						});
		
		// E-Customer AutoCompletion
	};

	$scope.product = {
		productId : "",
		code : "",
		name : "",
		scientifiName : "",
		unitType : "",
		stockLevel : "",
		cost : "",
		profit : "",
		price : "",
		quantity : "",
		country:"",
	};
	$scope.addedProduct = [];

	$scope.resetProduct =angular.copy($scope.product);

	$scope.getProduct = function(event) {
		console.log("getProduct->fired");
		if (event.which == 13) {

			console.log($scope.product.code);
			$http
					.get(
							$$ContextURL + "/products/find/code/"
									+ $scope.product.code).then(
							function(response) {
								console.log("success");
								console.log("response=",response);
							
								if(response.data.stockLevel==0){
									$("#modal-body").html("Out of the stock or you may have expiration");
									$("#modal").modal("show");
								}
								else{
									$scope.product = response.data;
									$( "#productName" ).focus();
								    if($scope.customerOrder.customer&&$scope.customerOrder.customer.priceCategory&&
								    		$scope.product.productPriceCategories){
								    	console.log("spaial price for customer");
								    	var spaialPrice =$scope.product.productPriceCategories.find(function(e) {
								    		  return e.priceCategory.id==$scope.customerOrder.customer.priceCategory.id;
								    		});
								    	$scope.product.price=spaialPrice.price;
								    }
								}
							}, function(response) {
								console.error("failed");
								console.error("error occured");
								$("#modal-body").html(response.data);
								$("#modal").modal("show");
							});

		}
		
	}

	$scope.addCustomerOrderDetail = function() {
		console.log("addCustomerOrderDetail->fired");

		var customerOrderDetail = {
			productId : $scope.product.productId,
			productCode : $scope.product.code,
			productName : $scope.product.name,
			scientificName : $scope.product.scientificName,
			quantity : $scope.product.quantity,
			price : $scope.product.price,
			country:$scope.product.country
		};
		$scope.customerOrder.customerOrderDetails.push(customerOrderDetail);
		console.log($scope.customerOrder.customerOrderDetails);
		$scope.product = $scope.resetProduct;
	}

	$scope.addCustomerOrder = function() {
		console.log("addCustomerOrder->fired");
		console.log("$scope.customerOrder=", $scope.customerOrder);

		$http({
			method : 'POST',
			data : $scope.customerOrder,
			url : $$ContextURL + '/customerOrders/update'
		}).then(function(response) {
			console.log(response);
			history.pushState(null, '',  $$ContextURL+"/customerOrders/edit/"+response.data.etc);
			var outPut = `
			
			<div>${response.data.message}
			</div>
			<div>
			<a class="btn btn-info" target="_blank" href="${$$ContextURL}/customerOrders/${response.data.etc}"><i class="fa fa-print"></i></a></div>
			`;
			$("#freeze").addClass("cus-freeze");
			console.log("outPut=",outPut);
			
			$("#modal-body").html(outPut);
			$("#modal").modal("show");
		}, function(response) {
			console.error("error occured");
			if (response.data.fieldErrors) {
				console.log(response.data.fieldErrors);
				var outPut = `<table><tbody>`;
				response.data.fieldErrors.forEach(function(element) {
					console.log("element=", element);
					outPut += `<tr><td>${element.message}</td></tr>`;
				});
				outPut += `</tbody></table>`;
				console.log("outPut=", outPut);
				$("#modal-body").html(outPut);
				$("#modal").modal("show");
			} else {
				if(response.data.message){
					$("#modal-body").html(response.data.message);
					$("#modal").modal("show");
				}
				else{
					$("#modal-body").html(response.data);
					$("#modal").modal("show");
				}
				
			}
		});

	}

	$scope.removeCustomerOrderDetail = function(index) {
		console.log("removeCustomerOrderDetail->fired");
		console.log("index=", index);
		$scope.customerOrder.customerOrderDetails.splice(index, 1);
		console.log("customerOrderDetails=",
				$scope.customerOrder.customerOrderDetails);
	}

});