var PrcsDate = null;

var matInputListTable = new Tabulator("#matInputListTable", {
	//페이징
	pagination: "local",
	paginationSize: 20,
	headerFilterPlaceholder: null,
	height: "calc(100% - 175px)",
	columns: [ //Define Table Columns
		{ title: "순번", field: "id", headerHozAlign: "center", headerFilter: true, hozAlign: "center" },
		{ title: "입고일자", field: "inMat_Date", headerHozAlign: "center", headerFilter: true, hozAlign: "left", width: 150, formatter: "datetime", formatterParams: { outputFormat: "YYYY-MM-DD HH:mm:ss" } },
		{ title: "입고구분", field: "inMat_Rcv_Clsfc", headerHozAlign: "center", headerFilter: true, hozAlign: "left" },
		{ title: "거래처명", field: "inMat_Client_Name", headerHozAlign: "center", headerFilter: true, hozAlign: "left", width: 100 },
		{ title: "품목코드", field: "inMat_Code", headerHozAlign: "center", headerFilter: true, hozAlign: "left" },
		{ title: "품명", field: "inMat_Name", headerHozAlign: "center", headerFilter: true, hozAlign: "left", width: 170 },
		{ title: "규격", field: "inMat_STND_1", headerHozAlign: "center", headerFilter: true, hozAlign: "left", width: 120 },
		{ title: "단위", field: "inMat_UNIT", headerHozAlign: "center", headerFilter: true, hozAlign: "left" },
		{ title: "수량", field: "inMat_Qty", headerHozAlign: "center", headerFilter: true, hozAlign: "right" },
		{ title: "입고단가", field: "inMat_Unit_Price", headerHozAlign: "center", headerFilter: true, hozAlign: "right", formatter: "money", formatterParams: { precision: false } },
		{ title: "입고금액", field: "inMat_Price", headerHozAlign: "center", headerFilter: true, hozAlign: "right", formatter: "money", formatterParams: { precision: false } },
		{ title: "비고", field: "inMat_Info_Remark", headerHozAlign: "center", headerFilter: true, hozAlign: "right" },
		{ title: "발주번호", field: "inMat_Order_No", headerHozAlign: "center", headerFilter: true, hozAlign: "left", width: 120 },
		{ title: "등록자", field: "inMat_Modifier", headerHozAlign: "center", headerFilter: true, hozAlign: "left", width: 80 },
		{ title: "등록일자", field: "inMat_dInsert_Time", headerHozAlign: "center", headerFilter: true, hozAlign: "left", width: 140, formatter: "datetime", formatterParams: { outputFormat: "YYYY-MM-DD HH:mm:ss" } },
	],
});

function MI_ListViewSearchBtn() {
	data = {
		startDate: $("#matInputList_startDate").val(),
		endDate: $("#matInputList_endDate").val(),
		inMat_Code: $("#PRODUCT_ITEM_CODE1").val(),
		inMat_Client_Name: $("#InMat_Client_Name1").val(),
		inMat_Rcv_Clsfc: $("#inMatTypeListSelectBox option:selected").val(),
		inMat_Lot_No: $("#InMat_Lot_No").val()
	}

	$.ajax({
		method: "GET",
		dataType: "json",
		url: "matInputReportLXRest/MI_ListView?data=" + encodeURI(JSON.stringify(data)),
		success: function(data) {
			console.log(data);
			TableSetData(matInputListTable, data);
		}
	});
}

var matInputItemViewTable = new Tabulator("#matInputItemViewTable", {
	//페이징
	pagination: "local",
	paginationSize: 20,
	//Sub Total 색상
	rowFormatter: function(row) {
		if (row.getData().inMat_Rcv_Clsfc == "Sub Total") {
			row.getElement().style.backgroundColor = "#c0c0c0";
		}
	},
	height: "calc(100% - 175px)",
	columns: [ //Define Table Columns
		{ title: "입고일자", field: "inMat_Date", headerHozAlign: "center", hozAlign: "left", formatter: "datetime", formatterParams: { outputFormat: "YYYY-MM-DD HH:mm:ss" } },
		{ title: "입고구분", field: "inMat_Rcv_Clsfc", headerHozAlign: "center", hozAlign: "center" },
		{ title: "거래처명", field: "inMat_Client_Name", headerHozAlign: "center", hozAlign: "left", width: 100 },
		{ title: "품목코드", field: "inMat_Code", headerHozAlign: "center", hozAlign: "left" },
		{ title: "품명", field: "inMat_Name", headerHozAlign: "center", hozAlign: "left", width: 170 },
		{ title: "규격", field: "inMat_STND_1", headerHozAlign: "center", hozAlign: "left", width: 120 },
		{ title: "단위", field: "inMat_UNIT", headerHozAlign: "center", hozAlign: "left", width: 60 },
		{ title: "수량", field: "inMat_Qty", headerHozAlign: "center", hozAlign: "right" },
		{ title: "입고단가", field: "inMat_Unit_Price", headerHozAlign: "center", hozAlign: "right", formatter: "money", formatterParams: { precision: false }, width: 100 },
		{ title: "입고금액", field: "inMat_Price", headerHozAlign: "center", hozAlign: "right", formatter: "money", formatterParams: { precision: false }, width: 100 },
		{ title: "발주번호", field: "inMat_Order_No", headerHozAlign: "center", hozAlign: "left", width: 130 }
	],
});

function MI_ItemViewSearchBtn() {
	data = {
		startDate: $("#matInputItemView_startDate").val(),
		endDate: $("#matInputItemView_endDate").val(),
		inMat_Code: $("#PRODUCT_ITEM_CODE2").val(),
		inMat_Rcv_Clsfc: $("#inMatTypeItemViewSelectBox option:selected").val()
	}

	$.ajax({
		method: "GET",
		dataType: "json",
		url: "matInputReportLXRest/MI_ItemView?data=" + encodeURI(JSON.stringify(data)),
		success: function(data) {
			console.log(data);
			TableSetData(matInputItemViewTable, data);
		}
	});
}

var matInputCustomerViewTable = new Tabulator("#matInputCustomerViewTable", {
	//페이징
	pagination: "local",
	paginationSize: 20,
	//Sub Total 색상
	rowFormatter: function(row) {
		if (row.getData().inMat_Rcv_Clsfc == "Sub Total") {
			row.getElement().style.backgroundColor = "#c0c0c0";
		}
	},
	height: "calc(100% - 175px)",
	columns: [ //Define Table Columns
		{ title: "입고일자", field: "inMat_Date", headerHozAlign: "center", hozAlign: "left", formatter: "datetime", formatterParams: { outputFormat: "YYYY-MM-DD HH:mm:ss" } },
		{ title: "입고구분", field: "inMat_Rcv_Clsfc", headerHozAlign: "center", hozAlign: "center" },
		{ title: "거래처코드", field: "inMat_Client_Code", headerHozAlign: "center", hozAlign: "left" },
		{ title: "거래처명", field: "inMat_Client_Name", headerHozAlign: "center", hozAlign: "left", width: 100 },
		{ title: "품명", field: "inMat_Name", headerHozAlign: "center", hozAlign: "left", width: 170 },
		{ title: "규격", field: "inMat_STND_1", headerHozAlign: "center", hozAlign: "left", width: 120 },
		{ title: "단위", field: "inMat_UNIT", headerHozAlign: "center", hozAlign: "left", width: 60 },
		{ title: "수량", field: "inMat_Qty", headerHozAlign: "center", hozAlign: "right" },
		{ title: "입고단가", field: "inMat_Unit_Price", headerHozAlign: "center", hozAlign: "right", formatter: "money", formatterParams: { precision: false }, width: 100 },
		{ title: "입고금액", field: "inMat_Price", headerHozAlign: "center", hozAlign: "right", formatter: "money", formatterParams: { precision: false }, width: 100 },
		{ title: "발주번호", field: "inMat_Order_No", headerHozAlign: "center", hozAlign: "left", width: 130 }
	],
});

function MI_CustomerViewSearchBtn() {
	data = {
		startDate: $("#matInputCustomerView_startDate").val(),
		endDate: $("#matInputCustomerView_endDate").val(),
		inMat_Client_Code: $("#InMat_Client_Code2").val(),
		inMat_Client_Name: $("#InMat_Client_Name2").val(),
		inMat_Rcv_Clsfc: $("#inMatTypeCustomerViewSelectBox option:selected").val()
	}

	$.ajax({
		method: "GET",
		dataType: "json",
		url: "matInputReportLXRest/MI_CustomerView?data=" + encodeURI(JSON.stringify(data)),
		success: function(data) {
			console.log(data);
			TableSetData(matInputCustomerViewTable, data);
		}
	});
}

var matInputDeliveryListTable = new Tabulator("#matInputDeliveryListTable", {
	//페이징
	pagination: "local",
	paginationSize: 20,
	//Sub Total 색상
	rowFormatter: function(row) {
		if (row.getData().inMat_Client_Code == "Sub Total") {
			row.getElement().style.backgroundColor = "#c0c0c0";
		}
	},
	height: "calc(100% - 175px)",
	//행클릭 이벤트
	rowClick: function(e, row) {
		matInputDeliveryListTable.deselectRow();
		row.select();
		MI_DeliveryItem(row.getData().inMat_Client_Code);
	},
	columns: [ //Define Table Columns
		{ title: "순번", field: "id", headerHozAlign: "center", hozAlign: "center" },
		{ title: "거래처코드", field: "inMat_Client_Code", headerHozAlign: "center", hozAlign: "center" },
		{ title: "거래처명", field: "inMat_Client_Name", headerHozAlign: "center", hozAlign: "left", width: 100 },
		{ title: "입고금액", field: "inMat_Price", headerHozAlign: "center", hozAlign: "right", formatter: "money", formatterParams: { precision: false } }
	],
});



function MI_DeliveryListSearchBtn() {

	// 처리연월
	var date = new Date();
	var year = date.getFullYear();
	var rawDate = $("#PrcsDatest").val();
	var years = year.toString().substring(0, 2);
	var PrcsYear = rawDate.substring(0, 2);
	var PrcsMonth = rawDate.substring(2, 4);
	var LastDay = document.getElementById('LastDay').value;
	alert("LastDay = " + LastDay);
	alert("years : " + years + " PrcsYear : " + PrcsYear + " PrcsMonth : " + PrcsMonth + '-' + LastDay);
	PrcsDate = years + PrcsYear + '-' + PrcsMonth + '-';

	datas = {
		PrcsDate: PrcsDate,
		RawDate: rawDate,
		LastDay: LastDay
	}

	$.ajax({
		method: "GET",
		url: "matInputReportLXRest/MI_DeliverySearch?data=" + encodeURI(JSON.stringify(datas)),
		success: function(data) {
			console.log(datas);

			if (data == "DateFormat") {
				alert("해당하는 데이터 정보가 없습니다.");

			} else if (data == "Success") {
				$.ajax({
					method: "GET",
					url: "matInputReportLXRest/MI_DeliveryView?data=" + encodeURI(JSON.stringify(datas)),
					success: function(data) {
						console.log(data);
						TableSetData(matInputDeliveryListTable, data);
						matInputDeliveryItemTable.clearData();
					}
				});
			}
		}, error: function(request, status, error) {
			alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
		}

	});
}

var matInputDeliveryItemTable = new Tabulator("#matInputDeliveryItemTable", {
	//페이징
	pagination: "local",
	paginationSize: 20,
	//Sub Total 색상
	rowFormatter: function(row) {
		if (row.getData().inMat_Code == "Sub Total") {

			row.getElement().style.backgroundColor = "#c0c0c0";
		}
	},
	height: "calc(100% - 175px)",
	columns: [ //Define Table Columns
		{ title: "순번", field: "inMat_No", headerHozAlign: "center", hozAlign: "center" },
		{ title: "입고일자", field: "inMat_Date", headerHozAlign: "center", hozAlign: "center", formatter: "datetime", formatterParams: { outputFormat: "YYYY-MM-DD HH:mm:ss" } },
		{ title: "입고구분", field: "inMat_Rcv_Clsfc", headerHozAlign: "center", hozAlign: "left" },
		{ title: "품목코드", field: "inMat_Code", headerHozAlign: "center", hozAlign: "left" },
		{ title: "품명", field: "inMat_Name", headerHozAlign: "center", hozAlign: "left", width: 155 },
		{ title: "규격", field: "inMat_STND_1", headerHozAlign: "center", hozAlign: "left", width: 120 },
		{ title: "단위", field: "inMat_UNIT", headerHozAlign: "center", hozAlign: "left" },
		{ title: "수량", field: "inMat_Qty", headerHozAlign: "center", hozAlign: "right" },
		{ title: "단가", field: "inMat_Unit_Price", headerHozAlign: "center", hozAlign: "right", formatter: "money", formatterParams: { precision: false }, width: 70 },
		{ title: "금액", field: "inMat_Price", headerHozAlign: "center", hozAlign: "right", formatter: "money", formatterParams: { precision: false }, width: 70 }
	],
});

function MI_DeliveryItem(inMat_Client_Code) {
	var date = new Date();
	var year = date.getFullYear();
	var rawDate = $("#PrcsDatest").val();
	var years = year.toString().substring(0, 2);
	var PrcsYear = rawDate.substring(0, 2);
	var PrcsMonth = rawDate.substring(2, 4);
	var LastDay = document.getElementById('LastDay').value;
	
	PrcsDate = years + PrcsYear + '-' + PrcsMonth + '-';
	data = {
		PrcsDate: PrcsDate,
		LastDay: LastDay,
		inMat_Client_Code: inMat_Client_Code
	}
	$.ajax({
		method: "GET",
		dataType: "json",
		url: "matInputReportLXRest/MI_DeliveryItem?data=" + encodeURI(JSON.stringify(data)),
		success: function(data) {
			TableSetData(matInputDeliveryItemTable, data);
		}
	});
}

var matInputDeliveryLastListTable = new Tabulator("#matInputDeliveryLastListTable", {
	//페이징
	pagination: "local",
	paginationSize: 20,
	//Sub Total 색상
	rowFormatter: function(row) {
		if (row.getData().inMat_Client_Code == "Sub Total") {
			row.getElement().style.backgroundColor = "#c0c0c0";
		}
	},
	height: "calc(100% - 175px)",
	//행클릭 이벤트
	rowClick: function(e, row) {
		matInputDeliveryLastListTable.deselectRow();
		row.select();
		MI_DeliveryLastItem(row.getData().inMat_Client_Code);
	},
	columns: [ //Define Table Columns
		{ title: "순번", field: "id", headerHozAlign: "center", hozAlign: "center" },
		{ title: "거래처코드", field: "inMat_Client_Code", headerHozAlign: "center", hozAlign: "center" },
		{ title: "거래처명", field: "inMat_Client_Name", headerHozAlign: "center", hozAlign: "left", width: 100 },
		{ title: "입고금액", field: "inMat_Price", headerHozAlign: "center", hozAlign: "right", formatter: "money", formatterParams: { precision: false } }
	],
});



function MI_DeliveryLastListSearchBtn() {

	// 처리연월
	var date = new Date();
	var year = date.getFullYear();
	var rawDate = $("#LastMonthst").val();
	var years = year.toString().substring(0, 2);
	var PrcsYear = rawDate.substring(0, 2);
	var LastMonth = rawDate.substring(2, 4);
	var LastDay = document.getElementById('LastDay').value;
	//alert("years : " + years + " PrcsYear : " + PrcsYear + " LastMonth : " + LastMonth + '-' + LastDay);
	PrcsDate = years + PrcsYear + '-' + LastMonth + '-';

	datas = {
		PrcsDate: PrcsDate,
		RawDate: rawDate,
		LastDay: LastDay
	}

	$.ajax({
		method: "GET",
		url: "matInputReportLXRest/MI_LastMonthSearch?data=" + encodeURI(JSON.stringify(datas)),
		success: function(data) {
			console.log(datas);

			if (data == "DateFormat") {
				alert("해당하는 데이터 정보가 없습니다.");

			} else if (data == "Success") {
				$.ajax({
					method: "GET",
					url: "matInputReportLXRest/MI_DeliveryView?data=" + encodeURI(JSON.stringify(datas)),
					success: function(data) {
						console.log(data);
						TableSetData(matInputDeliveryLastListTable, data);
						matInputDeliveryLastItemTable.clearData();
					}
				});
			}
		}, error: function(request, status, error) {
			alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
		}

	});
}

var matInputDeliveryLastItemTable = new Tabulator("#matInputDeliveryLastItemTable", {
	//페이징
	pagination: "local",
	paginationSize: 20,
	//Sub Total 색상
	rowFormatter: function(row) {
		if (row.getData().inMat_Code == "Sub Total") {

			row.getElement().style.backgroundColor = "#c0c0c0";
		}
	},
	height: "calc(100% - 175px)",
	columns: [ //Define Table Columns
		{ title: "순번", field: "inMat_No", headerHozAlign: "center", hozAlign: "center" },
		{ title: "입고일자", field: "inMat_Date", headerHozAlign: "center", hozAlign: "center", formatter: "datetime", formatterParams: { outputFormat: "YYYY-MM-DD HH:mm:ss" } },
		{ title: "입고구분", field: "inMat_Rcv_Clsfc", headerHozAlign: "center", hozAlign: "left" },
		{ title: "품목코드", field: "inMat_Code", headerHozAlign: "center", hozAlign: "left" },
		{ title: "품명", field: "inMat_Name", headerHozAlign: "center", hozAlign: "left", width: 155 },
		{ title: "규격", field: "inMat_STND_1", headerHozAlign: "center", hozAlign: "left", width: 120 },
		{ title: "단위", field: "inMat_UNIT", headerHozAlign: "center", hozAlign: "left" },
		{ title: "수량", field: "inMat_Qty", headerHozAlign: "center", hozAlign: "right" },
		{ title: "단가", field: "inMat_Unit_Price", headerHozAlign: "center", hozAlign: "right", formatter: "money", formatterParams: { precision: false }, width: 70 },
		{ title: "금액", field: "inMat_Price", headerHozAlign: "center", hozAlign: "right", formatter: "money", formatterParams: { precision: false }, width: 70 }
	],
});

function MI_DeliveryLastItem(inMat_Client_Code) {

	// 처리연월
	var date = new Date();
	var year = date.getFullYear();
	var rawDate = $("#LastMonthst").val();
	var years = year.toString().substring(0, 2);
	var PrcsYear = rawDate.substring(0, 2);
	var LastMonth = rawDate.substring(2, 4);
	var LastDay = document.getElementById('LastDay').value;
	//alert("years : " + years + " PrcsYear : " + PrcsYear + " LastMonth : " + LastMonth + '-' + LastDay);
	PrcsDate = years + PrcsYear + '-' + LastMonth + '-';

	data = {
		PrcsDate: PrcsDate,
		LastDay: LastDay,
		inMat_Client_Code: inMat_Client_Code
	}
	$.ajax({
		method: "GET",
		dataType: "json",
		url: "matInputReportLXRest/MI_DeliveryLastItem?data=" + encodeURI(JSON.stringify(data)),
		success: function(data) {
			TableSetData(matInputDeliveryLastItemTable, data);
		}
	});
}