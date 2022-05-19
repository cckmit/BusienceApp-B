$('.printBtn').on('click', function() {
	window.print();
});


$(document).ready(function() {

	document.getElementById("submatInspectItemName").value = opener.document.getElementById("matInspectItemName").value;
	document.getElementById("submatInspectDate").value = opener.document.getElementById("matInspectDate").value;
	document.getElementById("submatInspectQty").value = opener.document.getElementById("matInspectQty").value;
	document.getElementById("submatInspectWorker").value = opener.document.getElementById("matInspectWorker").value;
	document.getElementById("submatInspectCustomer").value = opener.document.getElementById("matInspectCustomer").value;
	document.getElementById("subinspectionText").value = opener.document.getElementById("inspectionText").value;
	document.getElementById("subinspectionRemark").value = opener.document.getElementById("inspectionRemark").value;
	
	var inspect_val1 = document.getElementsByName("subInspect_Value_1[]");
	var inspect_val1_length = inspect_val1.length;
	console.log(inspect_val1_length);
	for (var i=0; i<inspect_val1_length; i++) {
		inspect_val1[i].value = opener.document.getElementsByName("Inspect_Value_1[]")[i].value;
	}
	
	var inspect_val2 = document.getElementsByName("subInspect_Value_2[]");
	var inspect_val2_length = inspect_val2.length;
	console.log(inspect_val2_length);
	for (var i=0; i<inspect_val2_length; i++) {
		inspect_val2[i].value = opener.document.getElementsByName("Inspect_Value_2[]")[i].value;
	}
	
	var inspect_val3 = document.getElementsByName("subInspect_Value_3[]");
	var inspect_val3_length = inspect_val3.length;
	console.log(inspect_val3_length);
	for (var i=0; i<inspect_val3_length; i++) {
		inspect_val3[i].value = opener.document.getElementsByName("Inspect_Value_3[]")[i].value;
	}
	
	var inspect_val4 = document.getElementsByName("subInspect_Value_4[]");
	var inspect_val4_length = inspect_val4.length;
	console.log(inspect_val4_length);
	for (var i=0; i<inspect_val4_length; i++) {
		inspect_val4[i].value = opener.document.getElementsByName("Inspect_Value_4[]")[i].value;
	}
	
	var inspect_val5 = document.getElementsByName("subInspect_Value_5[]");
	var inspect_val5_length = inspect_val5.length;
	console.log(inspect_val5_length);
	for (var i=0; i<inspect_val5_length; i++) {
		inspect_val5[i].value = opener.document.getElementsByName("Inspect_Value_5[]")[i].value;
	}
	
	// 규격
	var inspect_stnd1 = document.getElementsByName("subInspect_STND_1[]");
	var inspect_stnd1_length = inspect_stnd1.length;
	console.log(inspect_stnd1_length);
	for (var i=0; i<inspect_stnd1_length; i++) {
		inspect_stnd1[i].value = opener.document.getElementsByName("Inspect_STND_1[]")[i].value;
	}
	
	var inspect_stnd2 = document.getElementsByName("subInspect_STND_2[]");
	var inspect_stnd2_length = inspect_stnd2.length;
	console.log(inspect_stnd2_length);
	for (var i=0; i<inspect_stnd2_length; i++) {
		inspect_stnd2[i].value = opener.document.getElementsByName("Inspect_STND_2[]")[i].value;
	}
	
	// 팜정
	var inspect_status = document.getElementsByName("substatus[]");
	var inspect_status_length = inspect_status.length;
	console.log(inspect_status_length);
	for (var i=0; i<inspect_status_length; i++) {
		if(opener.document.getElementsByName("status[]")[i].value == 'true') {
			console.log("적합");
			inspect_status[i].value = "적";
		}
		
		if(opener.document.getElementsByName("status[]")[i].value == 'false') {
			console.log("부적합");
			inspect_status[i].value = "부";
		}
	}
	
})
