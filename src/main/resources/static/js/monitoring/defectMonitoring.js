function tableInit1() {
	$.ajax({
		method: "GET",
		url: "defectMonitoringRest/unit1",
		success: function(data) {
			datas = data;
			// console.log(datas);

			var table = new Tabulator("#example-table1", {
				layout: "fitColumns",
				data: datas,
				pagination: "local",
				responsiveLayout: "hide",
				paginationSize: 10,
				paginationSizeSelector: [3, 6, 8, 10],
				movableColumns: true,

				columns: [
					{ title: "시간", field: "time", headerHozAlign: "center", hozAlign: "right", minWidth: 60 },
					{ title: "모델명", field: "product_ITEM_NAME", minWidth: 130, headerHozAlign: "center", minWidth: 130 },
					{ title: "생산수량", field: "production_P_Qty", bottomCalc: "sum", headerHozAlign: "center", hozAlign: "right" },
					{ title: "불량수량", field: "production_DEFECT_CODE", bottomCalc: "sum", headerHozAlign: "center", hozAlign: "right" },
					{ title: "불량명", field: "production_DEFECT_NAME", bottomCalc: "sum", headerHozAlign: "center", hozAlign: "left" },
				],
			});
		}
	});
}

function tableInit2() {
	$.ajax({
		method: "GET",
		url: "defectMonitoringRest/unit2",
		success: function(data) {
			datas = data;
			// console.log(datas);

			var table = new Tabulator("#example-table2", {
				layout: "fitColumns",
				data: datas,
				pagination: "local",
				responsiveLayout: "hide",
				paginationSize: 10,
				paginationSizeSelector: [3, 6, 8, 10],
				movableColumns: true,

				columns: [
					{ title: "시간", field: "time", headerHozAlign: "center", hozAlign: "right", minWidth: 60 },
					{ title: "모델명", field: "product_ITEM_NAME", minWidth: 130, headerHozAlign: "center", minWidth: 130 },
					{ title: "생산수량", field: "production_P_Qty", bottomCalc: "sum", headerHozAlign: "center", hozAlign: "right" },
					{ title: "불량수량", field: "production_DEFECT_CODE", bottomCalc: "sum", headerHozAlign: "center", hozAlign: "right" },
					{ title: "불량명", field: "production_DEFECT_NAME", bottomCalc: "sum", headerHozAlign: "center", hozAlign: "left" },
				],
			});
		}
	});
}

window.onload = function() {
	//startDate
	var date = new Date();
	var year = date.getFullYear();
	var month = new String(date.getMonth() + 1);
	var day = new String(date.getDate());

	// 한자리수일 경우 0을 채워준다. 
	if (month.length == 1) {
		month = "0" + month;
	}
	if (day.length == 1) {
		day = "0" + day;
	}

	document.getElementById("today").innerText = year + "-" + month + "-" + day;
	
	document.getElementById("subPages5a").setAttribute('class','active');
	document.getElementById("subPages5a").setAttribute('aria-expanded','true');
	document.getElementById("subPages5").setAttribute('class','collapse in');
	document.getElementById("subPages5").setAttribute('aria-expanded','true');
	document.getElementById("513510").setAttribute('class','active');
}

tableInit1(); tableInit2();

setTimeout(function() {
	location.reload();
}, 300000);