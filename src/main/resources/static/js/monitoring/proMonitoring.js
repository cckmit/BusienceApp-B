function tableInit1() {
	$.ajax({
		method: "GET",
		url: "proMonitoringRest/unit1",
		success: function(data) {
			datas = data;
			//console.log(datas);

			var table = new Tabulator("#example-table1", {
				layout: "fitColumns",
				data: datas,
				pagination: "local",
				responsiveLayout: "hide",
				paginationSize: 6,
				paginationSizeSelector: [3, 6, 8, 10],
				movableColumns: true,
				rowFormatter: function(row) {
					//row - row component

					var data = row.getData();
					// console.log(data);
					// console.log(data.time);
					
					if (data.time == undefined){}
					else
					{
						var timeset = data.time;
						var starttime = timeset.substring(0, 2);
						starttime += ":";
						var endtime = timeset.substring(2, 4);
						starttime += endtime;

						data.time = starttime;
					}
					
					/*
					// undefined가 조건문에 걸리질 않음
					if (data.time != undefined) {
						var timeset = data.time;
						var starttime = timeset.substring(0, 2);
						starttime += ":";
						var endtime = timeset.substring(2, 4);
						starttime += endtime;

						data.time = starttime;
					}
					*/
				},

				columns: [
					{ title: "시간", field: "time", headerHozAlign: "center", hozAlign: "right", minWidth: 60 },
					{ title: "모델명", field: "product_ITEM_NAME", headerHozAlign: "center", minWidth: 130 },
					{ title: "생산수량", field: "production_P_Qty", bottomCalc: "sum", headerHozAlign: "center", hozAlign: "right" },
					{ title: "불량수량", field: "production_DEFECT_CODE", bottomCalc: "sum", headerHozAlign: "center", hozAlign: "right" },
					{ title: "양품수량", field: "production_PRODUCTS_VOLUM", bottomCalc: "sum", headerHozAlign: "center", hozAlign: "right" },
				],
			});
		}
	});
}

function out1() {
	$.ajax({
		method: "GET",
		url: "proMonitoringRest/out1",
		success: function(data) {
			datas = data;
			//console.log(datas);

			document.getElementById("product_ITEM_NAME").value = datas.product_ITEM_NAME;
			document.getElementById("production_MODIFY_D").value = datas.production_MODIFY_D;
			document.getElementById("production_VOLUME").value = datas.production_P_Qty;
			document.getElementById("production_DEFECT_CODE").value = datas.production_DEFECT_CODE;
		}
	});
}

function out2() {
	$.ajax({
		method: "GET",
		url: "proMonitoringRest/out2",
		success: function(data) {
			datas = data;
			//console.log(datas);
			//console.log(datas.product_ITEM_NAME);
			document.getElementById("product_ITEM_NAME2").value = datas.product_ITEM_NAME;
			document.getElementById("production_MODIFY_D2").value = datas.production_MODIFY_D;
			//document.getElementById("production_VOLUME2").value = datas.production_VOLUME;
			//document.getElementById("production_DEFECT_CODE2").value = datas.production_DEFECT_CODE;
			
			document.getElementById("production_VOLUME2").value = datas.production_P_Qty;
			document.getElementById("production_DEFECT_CODE2").value = datas.production_DEFECT_CODE;
		}
	});
}

function tableInit2() {
	$.ajax({
		method: "GET",
		url: "proMonitoringRest/unit2",
		success: function(data) {
			datas = data;
			// console.log(datas);

			var table = new Tabulator("#example-table2", {
				layout: "fitColumns",
				data: datas,
				pagination: "local",
				paginationSize: 6,
				paginationSizeSelector: [3, 6, 8, 10],
				movableColumns: true,
				responsiveLayout: "hide",
				rowFormatter: function(row) {
					//row - row component

					var data = row.getData();
					//console.log(data);
					//console.log(data.time);
					
					if (data.time == undefined){}
					else
					{
						var timeset = data.time;
						var starttime = timeset.substring(0, 2);
						starttime += ":";
						var endtime = timeset.substring(2, 4);
						starttime += endtime;

						data.time = starttime;
					}
					
					/*
					// undefined가 조건문에 걸리질 않음
					if (data.time != undefined) {
						var timeset = data.time;
						var starttime = timeset.substring(0, 2);
						starttime += ":";
						var endtime = timeset.substring(2, 4);
						starttime += endtime;

						data.time = starttime;
					}
					*/
				},

				columns: [
					{ title: "시간", field: "time", headerHozAlign: "center", hozAlign: "right", minWidth: 60 },
					{ title: "모델명", field: "product_ITEM_NAME", minWidth: 130, headerHozAlign: "center", minWidth: 130 },
					{ title: "생산수량", field: "production_P_Qty", bottomCalc: "sum", headerHozAlign: "center", hozAlign: "right" },
					{ title: "불량수량", field: "production_DEFECT_CODE", bottomCalc: "sum", headerHozAlign: "center", hozAlign: "right" },
					{ title: "양품수량", field: "production_PRODUCTS_VOLUM", bottomCalc: "sum", headerHozAlign: "center", hozAlign: "right" },
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
	document.getElementById("513501").setAttribute('class','active');
}

tableInit1(); 
tableInit2(); 
out1(); 
out2();

setTimeout(function() {
	location.reload();
}, 300000);