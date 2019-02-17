$(document)
		.ready(
				function() {

					// S-DataTable
					$('#customerPaymentsTable tfoot th:not(.cus-not-search)')
							.each(
									function() {
										var title = $(this).text();
										$(this)
												.html(
														'<input class="form-control fomt-control-sm cus-inline" type="text" />');
									});

					var table = $('#customerPaymentsTable').DataTable({
						paginate : false,
						dom : 'Bfrtip',
						buttons : [ {
							extend : "excel",
							className : "btn btn-sm  btn-outline-info",
							exportOptions : {
								columns : ':not(.cus-not-export)'
							}
						}, {
							extend : "csv",
							className : "btn btn-sm btn-outline-info",
							exportOptions : {
								columns : ':not(.cus-not-export)'
							}
						}, {
							extend : "pdf",
							className : "btn btn-sm btn-outline-info",
							exportOptions : {
								columns : ':not(.cus-not-export)'
							}
						} ],
						bInfo : false,
					});

					// Apply the search
					table.columns().every(
							function() {
								var that = this;
								console.log("that=", that);
								console.log("that.search()=", that.search());

								$('input', this.footer()).on('keyup change',
										function() {
											if (that.search() !== this.value) {
												that.search(this.value).draw();
											}
										});
							});
					// E-DataTable

				});

function getAddingCustomerPayment(id) {
	console.log("getAddingCustomerPayment->fired");
	$.get($$ContextURL + "/customerPayments/add/customer/" + id, function(
			response) {
		$("#modal-body").html(response);
		$("#modal").modal("show");
	});

}

function deleteCustomerPayment(id) {
	console.log("id=", id);

	$.when(cusConfirm()).done(function() {

		$.ajax({
			type : "POST",
			url : $$ContextURL + "/customerPayments/delete/" + id,
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

	});

}

function editingCustomerPayment(id, customerId) {
	console.log("editingCustomerPayment->fired");
	console.log("id=", id);
	console.log("customerId=", customerId);

	$.ajax({
		type : "GET",
		url : $$ContextURL + "/customerPayments/edit/" + id + "/customer/"
				+ customerId,
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
