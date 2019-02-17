function getAddingCustomer() {
	console.log("getAddingCustomer->fired");
	$.get($$ContextURL + "/customers/add", function(response) {
		$("#modal-body").html(response);
		$("#modal").modal("show");
	});

}

function deleteCustomer(id) {
	console.log("id=", id);

	$.when(cusConfirm()).done(function() {

		$.ajax({
			type : "POST",
			url : $$ContextURL + "/customers/delete/" + id,
			contentType : "application/json",
			success : function(data) {
				if (data == "success") {
					location.reload();
				}
			},
			error : function(request, status, error) {
				$("#modal-body").html(request.responseText);
				$("#modal").modal("show");
			}
		});

	});

}

function editingCustomer(id) {
	console.log("editingCustomer->fired");

	$.ajax({
		type : "GET",
		url : $$ContextURL + "/customers/edit/" + id,
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
