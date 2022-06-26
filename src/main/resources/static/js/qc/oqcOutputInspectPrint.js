$('.printBtn').on('click', function() {
	 window.print();
 });


$(document).ready(function() {

	document.getElementById("suboqcDocumentNo").value = opener.document.getElementById("oqcDocumentNo").value;
	document.getElementById("suboqcCusName").value = opener.document.getElementById("oqcCusName").value;
	document.getElementById("suboqcItemName").value = opener.document.getElementById("oqcItemName").value;
	var target =  opener.document.getElementById("oqcWorkerList");
	document.getElementById("suboqcWorkerList").value = target.options[target.selectedIndex].text;
	document.getElementById("suboqcOutMatQty").value = opener.document.getElementById("oqcOutMatQty").value;
	
	document.getElementById("suboqcItemSTND_1").value = opener.document.getElementById("oqcItemSTND_1").value;
	document.getElementById("suboqcDate").value = opener.document.getElementById("oqcDate").value;
	document.getElementById("suboqcQty").value = opener.document.getElementById("oqcQty").value;
	document.getElementById("subprocessRemark").value = opener.document.getElementById("oqcRemark").value;
	
	
	var inspect_val1 = document.querySelectorAll('#subvalue1');
	var inspect_val1_length = inspect_val1.length;
	console.log(inspect_val1_length);

	for (var i = 0; i < inspect_val1_length; i++) {
		inspect_val1[i].value = opener.document.querySelectorAll("#value1")[i].value;
		console.log(opener.document.querySelectorAll("#value1")[i].value);
	}
	
	var inspect_val2 = document.querySelectorAll('#subvalue2');
	var inspect_val2_length = inspect_val2.length;
	console.log(inspect_val2_length);

	for (var i = 0; i < inspect_val2_length; i++) {
		inspect_val2[i].value = opener.document.querySelectorAll("#value2")[i].value;
	}
	
	var inspect_val3 = document.querySelectorAll('#subvalue3');
	var inspect_val3_length = inspect_val3.length;
	console.log(inspect_val3_length);

	for (var i = 0; i < inspect_val3_length; i++) {
		inspect_val3[i].value = opener.document.querySelectorAll("#value3")[i].value;
	}
	
	var inspect_val4 = document.querySelectorAll('#subvalue4');
	var inspect_val4_length = inspect_val4.length;
	console.log(inspect_val4_length);

	for (var i = 0; i < inspect_val4_length; i++) {
		inspect_val4[i].value = opener.document.querySelectorAll("#value4")[i].value;
	}
	
	var inspect_val5 = document.querySelectorAll('#subvalue5');
	var inspect_val5_length = inspect_val5.length;
	console.log(inspect_val5_length);

	for (var i = 0; i < inspect_val5_length; i++) {
		inspect_val5[i].value = opener.document.querySelectorAll("#value5")[i].value;
	}
	
	var inspect_val6 = document.querySelectorAll('#subvalue6');
	var inspect_val6_length = inspect_val6.length;
	console.log(inspect_val6_length);

	for (var i = 0; i < inspect_val6_length; i++) {
		inspect_val6[i].value = opener.document.querySelectorAll("#value6")[i].value;
		console.log(opener.document.querySelectorAll("#value6")[i].value);
	}
	
	var inspect_val7 = document.querySelectorAll('#subvalue7');
	var inspect_val7_length = inspect_val7.length;
	console.log(inspect_val7_length);

	for (var i = 0; i < inspect_val7_length; i++) {
		inspect_val7[i].value = opener.document.querySelectorAll("#value7")[i].value;
	}
	
	var inspect_val8 = document.querySelectorAll('#subvalue8');
	var inspect_val8_length = inspect_val8.length;
	console.log(inspect_val8_length);

	for (var i = 0; i < inspect_val8_length; i++) {
		inspect_val8[i].value = opener.document.querySelectorAll("#value8")[i].value;
	}
	
	var inspect_val9 = document.querySelectorAll('#subvalue9');
	var inspect_val9_length = inspect_val9.length;
	console.log(inspect_val9_length);

	for (var i = 0; i < inspect_val9_length; i++) {
		inspect_val9[i].value = opener.document.querySelectorAll("#value9")[i].value;
	}
	
	var inspect_val10 = document.querySelectorAll('#subvalue10');
	var inspect_val10_length = inspect_val10.length;
	console.log(inspect_val10_length);

	for (var i = 0; i < inspect_val10_length; i++) {
		inspect_val10[i].value = opener.document.querySelectorAll("#value10")[i].value;
	}

	// 규격
	var inspect_stnd1 = document.querySelectorAll('#substnd1');
	var inspect_stnd1_length = inspect_stnd1.length;
	console.log(inspect_stnd1_length);
	for (var i = 0; i < inspect_stnd1_length; i++) {
		inspect_stnd1[i].value = opener.document.querySelectorAll('#stnd1')[i].value;
	}

	var inspect_stnd2 = document.querySelectorAll("#substnd2");
	var inspect_stnd2_length = inspect_stnd2.length;
	console.log(inspect_stnd2_length);
	for (var i = 0; i < inspect_stnd2_length; i++) {
		inspect_stnd2[i].value = opener.document.querySelectorAll('#stnd2')[i].value;
	}
	
	var inspect_unit1 = document.querySelectorAll("#subunit1");
	var inspect_unit1_length = inspect_unit1.length;
	console.log(inspect_unit1_length);
	for (var i = 0; i < inspect_unit1_length; i++) {
		inspect_unit1[i].value = opener.document.querySelectorAll('#unit1')[i].value;
	}

	// 결과
	var inspect_status = document.querySelectorAll("#substatus");
	var inspect_status_length = inspect_status.length;
	console.log(inspect_status_length);
	for (var i = 0; i < inspect_status_length; i++) {
		if (opener.document.querySelectorAll('#status')[i].value == 'true') {
			console.log("적합");
			inspect_status[i].value = "적";
		}

		if (opener.document.querySelectorAll('#status')[i].value == 'false') {
			console.log("부적합");
			inspect_status[i].value = "부";
		}
	}
	
	// 최종결과
	var inspect_finalStatus = document.querySelectorAll("#subresult");
	var inspect_finalStatus_length = inspect_finalStatus.length;
	console.log(inspect_finalStatus_length);
	for (var i = 0; i < inspect_finalStatus_length; i++) {
		if (opener.document.querySelectorAll('#result')[i].value == 'true') {
			console.log("적합");
			inspect_finalStatus[i].value = "적합";
		}

		if (opener.document.querySelectorAll('#result')[i].value == 'false') {
			console.log("부적합");
			inspect_finalStatus[i].value = "부적합";
		}
	}

})
