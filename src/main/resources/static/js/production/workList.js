// 출고구분 select를 구성하기위한 ajax
var dtl_arr = dtlSelectList(29);

function MI_searchBtn1()
{
	var jsonData = {
		ItemCode: $("#PRODUCT_ITEM_CODE1").val(),
	}
	
	WorkOrder_tbl.setData("workListRest/workListSearch", jsonData);
}

var WorkOrder_tbl = new Tabulator("#WorkOrder_tbl", {
	height: "calc(100% - 175px)",
	layoutColumnsOnNewData : true,
	groupBy:"workOrder_EquipName",
	columns: [
		{ title: "설비코드", field: "workOrder_EquipCode", headerHozAlign: "center", visible:false},
		{ title: "설비이름", field: "workOrder_EquipName", headerHozAlign: "center", visible:false},	
		{ title: "작업지시No", field: "workOrder_ONo", headerHozAlign: "center"},
		{ title: "제품코드", field: "workOrder_ItemCode", headerHozAlign: "center"},
		{ title: "제품이름", field: "workOrder_ItemName", headerHozAlign: "center"},
		{ title: "규격", field: "workOrder_INFO_STND_1", headerHozAlign: "center"},		
		{ title: "지시수량", field: "workOrder_AllottedQty", headerHozAlign: "center", align:"right",
			formatter:"money", formatterParams: {precision: false}, visible:false},
		{ title: "생산수량", field: "workOrder_ProductionQty", headerHozAlign: "center", align:"right",
			formatter:"money", formatterParams: {precision: false}},
		{ title: "작업등록일", field: "workOrder_RegisterTime", align: "right", headerHozAlign: "center"},
		{ title: "작업시작일", field: "workOrder_StartTime", align: "right", headerHozAlign: "center"},
		{ title: "작업상태", field: "workOrder_WorkStatus_Name", headerHozAlign: "center", align:"center"},
		{ title: "특이사항", field: "workOrder_Remark", headerHozAlign: "center" }
	]
});

window.onload = function(){
  MI_searchBtn1();
}

