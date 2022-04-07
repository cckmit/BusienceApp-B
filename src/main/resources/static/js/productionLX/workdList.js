var WorkOrder_tbl = new Tabulator("#WorkOrder_tbl", {
	height: "calc(100% - 175px)",
	layout: "fitData",
	//복사하여 엑셀 붙여넣기 가능
	clipboard: true,

	columns: [
		{ title: "작업지시No", field: "workOrder_ONo", headerHozAlign: "center", width: 160 },
		{ title: "제품코드", field: "workOrder_ItemCode", headerHozAlign: "center", width: 100 },
		{ title: "제품이름", field: "workOrder_ItemName", headerHozAlign: "center", width: 150 },
		{ title: "설비코드", field: "workOrder_EquipCode", headerHozAlign: "center", width: 100 },
		{ title: "설비이름", field: "workOrder_EquipName", headerHozAlign: "center", width: 150 },
		{ title: "규격", field: "product_INFO_STND_1", headerHozAlign: "center", width: 100 },
		{ title: "품목분류1", field: "product_Item_CLSFC_NAME_1", headerHozAlign: "center", width: 100},
		{ title: "품목분류2", field: "product_Item_CLSFC_NAME_2", headerHozAlign: "center", width: 100},	
		{ title: "지시수량", field: "workOrder_PQty", headerHozAlign: "center", align: "right", formatter:"money", formatterParams: {precision: false} },
		{ title: "작업등록일", field: "workOrder_RegisterTime", align: "right", headerHozAlign: "center", width: 160, formatter: "datetime", formatterParams: { outputFormat: "YYYY-MM-DD HH:mm:ss" }, width: 170 },
		{ title: "작업시작일", field: "workOrder_StartTime", align: "right", headerHozAlign: "center", width: 160, formatter: "datetime", formatterParams: { outputFormat: "YYYY-MM-DD HH:mm:ss" }, width: 170 },
		{ title: "작업완료일", field: "workOrder_CompleteTime", align: "right", headerHozAlign: "center", width: 160, formatter: "datetime", formatterParams: { outputFormat: "YYYY-MM-DD HH:mm:ss" }, width: 170 }
	]
});

$('#MI_searchBtn1').click(function() {
	MI_searchBtn1();
})


function MI_searchBtn1() {
	datas = {
		StartDate: $("#startDate").val(),
		EndDate: $("#endDate").val(),
		ItemCode: $("#PRODUCT_ITEM_CODE1").val(),
		MachineCode: $("#Machine_Code1").val(),
		WorkOrder_Check: "N"
	}
	
	console.log(datas);

	$.ajax({
		method: "GET",
		dataType: "json",
		url: "workdListRest/MI_Search",
		data: datas,
		success: function(data) {
			console.log(data);

			WorkOrder_tbl.setData(data);
		}
	});
}

$('#MI_restartSearch').click(function() {
	restartPage();
})

function restartPage() {
	datas = {
		StartDate: null,
		EndDate: $("#endDate").val(),
		ItemCode: $("#PRODUCT_ITEM_CODE1").val(),
		MachineCode: $("#Machine_Code1").val(),
		WorkOrder_Check: "N"
	}

	$.ajax({
		method: "GET",
		dataType: "json",
		url: "workdListRest/MI_Search",
		data: datas,
		success: function(data) {
			//console.log("MI");
			console.log(data);

			WorkOrder_tbl.setData(data);
		}
	});
}


$(document).ready(function() {
	restartPage();
});