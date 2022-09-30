var workOrderTable = new Tabulator("#workOrderTable", {
	height: "calc(100% - 175px)",
	layoutColumnsOnNewData: true,
	columns: [
		{ title: "작업지시No", field: "workOrder_ONo", headerHozAlign: "center"},
		{ title: "제품코드", field: "workOrder_ItemCode", headerHozAlign: "center"},
		{ title: "제품이름", field: "workOrder_ItemName", headerHozAlign: "center"},
		{ title: "설비코드", field: "workOrder_EquipCode", headerHozAlign: "center"},
		{ title: "설비이름", field: "workOrder_EquipName", headerHozAlign: "center"},
		{ title: "규격1", field: "workOrder_INFO_STND_1", headerHozAlign: "center"},
		{ title: "규격2", field: "workOrder_INFO_STND_2", headerHozAlign: "center"},
		{ title: "품목분류1", field: "workOrder_Item_CLSFC_1_Name", headerHozAlign: "center"},
		{ title: "품목분류2", field: "workOrder_Item_CLSFC_2_Name", headerHozAlign: "center"},	
		{ title: "지시수량", field: "workOrder_AllottedQty", headerHozAlign: "center", hozAlign: "right",
			formatter:"money", formatterParams: {precision: false} },
		{ title: "작업등록일", field: "workOrder_RegisterTime", hozAlign: "right", headerHozAlign: "center",
			formatter: "datetime", formatterParams: { outputFormat: "YYYY-MM-DD HH:mm:ss" }},
		{ title: "작업시작일", field: "workOrder_StartTime", hozAlign: "right", headerHozAlign: "center",
			formatter: "datetime", formatterParams: { outputFormat: "YYYY-MM-DD HH:mm:ss" }},
		{ title: "작업완료일", field: "workOrder_CompleteTime", hozAlign: "right", headerHozAlign: "center",
			formatter: "datetime", formatterParams: { outputFormat: "YYYY-MM-DD HH:mm:ss" }}
	]
});

$('#searchBtn').click(function() {
	workDetailListSearch();
})

$("#now_SearchBtn").click(function(){
	workDetailListSearch("now");
})

function workDetailListSearch(condition) {
	var datas = {
		startDate: $("#startDate").val(),
		endDate: $("#endDate").val(),
		itemCode: $(".itemCode").val(),
		machineCode: $(".machineCode").val(),
		condition: condition
	}
	workOrderTable.setData("workDetailListRest/workDetailListSearch", datas);
}