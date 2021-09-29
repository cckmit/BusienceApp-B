// 출고구분 select를 구성하기위한 ajax
var dtl_arr = new Object();

$.ajax({
	method: "GET",
	async: false,
	url: "dtl_tbl_select?NEW_TBL_CODE=29",
	success: function(datas) {
		for (i = 0; i < 2; i++) {
			dtl_arr[datas[i].child_TBL_NO] = datas[i].child_TBL_TYPE;
		}
	}
});



var WorkOrder_tbl = new Tabulator("#WorkOrder_tbl", {
	height: "calc(100% - 175px)",
	layout: "fitData",
	//복사하여 엑셀 붙여넣기 가능
	clipboard: true,

	columns: [
		{ title: "작업지시No", field: "workOrder_ONo", headerHozAlign: "center", width: 160 },
		{ title: "제품코드", field: "workOrder_ItemCode", headerHozAlign: "center", width: 100 },
		{ title: "제품이름", field: "workOrder_ItemName", headerHozAlign: "center", width: 180 },
		{ title: "설비코드", field: "workOrder_EquipCode", headerHozAlign: "center", width: 100 },
		{ title: "설비이름", field: "workOrder_EquipName", headerHozAlign: "center", width: 180 },
		{ title: "규격", field: "product_INFO_STND_1", headerHozAlign: "center" },
		{ title: "지시수량", field: "workOrder_PQty", headerHozAlign: "center", align: "right" },
		{ title: "작업지시일", field: "workOrder_OrderTime", align: "right", headerHozAlign: "center", width: 160, formatter: "datetime", formatterParams: { outputFormat: "YYYY-MM-DD HH:mm:ss" } },
		{ title: "작업예정완료일", field: "workOrder_CompleteOrderTime", align: "right", headerHozAlign: "center", width: 160, formatter: "datetime", formatterParams: { outputFormat: "YYYY-MM-DD HH:mm:ss" } },
		{ title: "접수일", field: "workOrder_ReceiptTime", align: "right", headerHozAlign: "center", width: 160, formatter: "datetime", formatterParams: { outputFormat: "YYYY-MM-DD HH:mm:ss" } },
		{ title: "작업시작일", field: "workOrder_StartTime", align: "right", headerHozAlign: "center", width: 160, formatter: "datetime", formatterParams: { outputFormat: "YYYY-MM-DD HH:mm:ss" } },
		{ title: "작업완료일", field: "workOrder_CompleteTime", align: "right", headerHozAlign: "center", width: 160, formatter: "datetime", formatterParams: { outputFormat: "YYYY-MM-DD HH:mm:ss" } }
	]
});

$('#MI_searchBtn1').click(function() {
	MI_searchBtn1();
})


function MI_searchBtn1() {
	data = {
		startDate: $("#startDate").val(),
		endDate: $("#endDate").val(),
		PRODUCT_ITEM_CODE: $("#PRODUCT_ITEM_CODE1").val(),
		Machine_Code: $("#Machine_Code1").val(),
		WorkOrder_Check: "N"
	}

	$.ajax({
		method: "GET",
		dataType: "json",
		url: "workdListRest/MI_Search?data=" + encodeURI(JSON.stringify(data)),
		success: function(data) {
			console.log("MI");
			console.log(data);

			for (i = 0; i < data.length; i++) {
				workOrder_OrderTime = data[i].workOrder_OrderTime;
				data[i].workOrder_OrderTime = workOrder_OrderTime.substr(0, workOrder_OrderTime.length - 2);

			}

			WorkOrder_tbl.setData(data);
		}
	});
}

$('#MI_restartSearch').click(function() {
	restartPage();
})

function restartPage() {
	data = {
		startDate: null,
		endDate: $("#endDate").val(),
		PRODUCT_ITEM_CODE: $("#PRODUCT_ITEM_CODE1").val(),
		Machine_Code: $("#Machine_Code1").val(),
		WorkOrder_Check: "N"
	}

	$.ajax({
		method: "GET",
		dataType: "json",
		url: "workdListRest/MI_Search?data=" + encodeURI(JSON.stringify(data)),
		success: function(data) {
			//console.log("MI");
			console.log(data);

			for (i = 0; i < data.length; i++) {
				workOrder_OrderTime = data[i].workOrder_OrderTime;
				data[i].workOrder_OrderTime = workOrder_OrderTime.substr(0, workOrder_OrderTime.length - 2);


			}

			WorkOrder_tbl.setData(data);
		}
	});
}


$(document).ready(function() {
	restartPage();
});