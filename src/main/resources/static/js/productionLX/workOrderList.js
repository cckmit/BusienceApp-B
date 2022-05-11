// 출고구분 select를 구성하기위한 ajax
var dtl_arr = new Object();

$.ajax({
	method: "GET",
	async: false,
	url: "dtl_tbl_select?NEW_TBL_CODE=29",
	success: function(datas) {
		console.log(datas);
		for (i = 0; i < 6; i++) {
			if (datas[i].child_TBL_NUM == '1' || datas[i].child_TBL_NUM == '5') {
				dtl_arr[datas[i].child_TBL_NO] = datas[i].child_TBL_TYPE;
			}
		}
	}
});

var WorkOrder_tbl = new Tabulator("#WorkOrder_tbl", {
	height: "calc(50% - 100px)",
	layout: "fitDataStretch",
	//복사하여 엑셀 붙여넣기 가능
	clipboard: true,

	columns: [
		{ title: "작업지시No", field: "workOrder_ONo", headerHozAlign: "center", width: 160 },
		{ title: "제품코드", field: "workOrder_ItemCode", headerHozAlign: "center", width: 100 },
		{ title: "제품이름", field: "workOrder_ItemName", headerHozAlign: "center", width: 180 },
		{ title: "설비코드", field: "workOrder_EquipCode", headerHozAlign: "center", width: 100 },
		{ title: "설비이름", field: "workOrder_EquipName", headerHozAlign: "center", width: 180 },
		{ title: "규격", field: "workOrder_INFO_STND_1", headerHozAlign: "center", width: 120 },
		{ title: "분류", field: "workOrder_Item_STND_1", headerHozAlign: "center", width: 120 },
		{ title: "재고", field: "workOrder_SQty", headerHozAlign: "center", align: "right", width: 120 },
		{ title: "등록일", field: "workOrder_RegisterTime", align: "right", headerHozAlign: "center", width: 160 },
		{
			title: "접수여부", field: "workOrder_WorkStatus", headerHozAlign: "center", editor: "select", align: "center", width: 130
			, formatter: function(cell, formatterParams) {
				var value = cell.getValue();
				console.log(value);
				if (dtl_arr[value] != null) {
					value = dtl_arr[value];
				} else {
					value = "";
				}
				return value;
			},
			editorParams: { values: dtl_arr }
			, cellEdited: function(cell) {
				//Right_Move(cell,"right");
				console.log(cell.getValue());
				if (cell.getValue() == "243") {

					var datas = {
						WorkOrder_ONo: cell.getRow().getData().workOrder_ONo,
						WorkOrder_WorkStatus: '242'
					}

					$.ajax({
						method: "POST",
						dataType: "json",
						url: "workOrderListRest/OrderUpdate",
						data: datas,
						beforeSend: function(xhr) {
							var header = $("meta[name='_csrf_header']").attr("content");
							var token = $("meta[name='_csrf']").attr("content");
							xhr.setRequestHeader(header, token)
						},
						complete: function(data) {
							console.log("접수완료");
							FI_SearchBtn1();
							console.log("접수완료중");
							FI_SearchBtn2();
						}
					});
				}
			}
		},
		{ title: "특이사항", field: "workOrder_Remark", headerHozAlign: "center" }
	]
});

$('#FI_SearchBtn1').click(function() {
	FI_SearchBtn1();
})

$('#FI_SearchBtn2').click(function() {
	FI_SearchBtn2();
})

function FI_SearchBtn1() {
	var datas = {
		startDate: $("#startDate").val(),
		endDate: $("#endDate").val(),
		ItemCode: $("#PRODUCT_ITEM_CODE1").val(),
		MachineCode: $("#Machine_Code1").val(),
		Condition: '242'
	}

	WorkOrder_tbl.setData("workOrderListRest/workorderList", datas);

}

function FI_SearchBtn2() {
	var datas = {
		startDate: $("#startDate2").val(),
		endDate: $("#endDate2").val(),
		ItemCode: $("#PRODUCT_ITEM_CODE2").val(),
		MachineCode: $("#Machine_Code2").val(),
		Condition: '243'
	}

	Sub_WorkOrder_tbl.setData("workOrderListRest/workorderList", datas);

	console.log(Sub_WorkOrder_tbl);

}

var Sub_WorkOrder_tbl = new Tabulator("#Sub_WorkOrder_tbl", {
	height: "calc(50% - 175px)",
	layout: "fitDataStretch",
	//복사하여 엑셀 붙여넣기 가능
	clipboard: true,

	columns: [
		{ title: "작업지시No", field: "workOrder_ONo", headerHozAlign: "center", width: 160 },
		{ title: "제품코드", field: "workOrder_ItemCode", headerHozAlign: "center", width: 100 },
		{ title: "제품이름", field: "workOrder_ItemName", headerHozAlign: "center", width: 180 },
		{ title: "설비코드", field: "workOrder_EquipCode", headerHozAlign: "center", width: 100 },
		{ title: "설비이름", field: "workOrder_EquipName", headerHozAlign: "center", width: 180 },
		{ title: "규격", field: "workOrder_INFO_STND_1", headerHozAlign: "center", width: 120 },
		{ title: "분류", field: "workOrder_Item_STND_1", headerHozAlign: "center", width: 120 },
		{ title: "재고", field: "workOrder_SQty", headerHozAlign: "center", align: "right", width: 120 },
		{ title: "접수일", field: "workOrder_ReceiptTime", align: "right", headerHozAlign: "center", width: 160 },
		{
			title: "접수여부", field: "workOrder_WorkStatus", headerHozAlign: "center", editor: "select", align: "center"
			, formatter: function(cell, formatterParams) {
				var value = cell.getValue();
				if (dtl_arr[value] != null) {
					value = dtl_arr[value];
				} else {
					value = "";
				}
				return value;
			},
			editorParams: { values: dtl_arr }
			, cellEdited: function(cell) {
				//Right_Move(cell,"right");
				console.log(cell.getValue());
				if (cell.getValue() == "242") {

					var datas = {
						WorkOrder_ONo: cell.getRow().getData().workOrder_ONo,
						WorkOrder_WorkStatus: '243'
					}

					$.ajax({
						method: "POST",
						dataType: "json",
						url: "workOrderListRest/OrderUpdate",
						data: datas,
						beforeSend: function(xhr) {
							var header = $("meta[name='_csrf_header']").attr("content");
							var token = $("meta[name='_csrf']").attr("content");
							xhr.setRequestHeader(header, token)
						},
						complete: function(data) {
							console.log("미접수");
							FI_SearchBtn1();
							console.log("미접수중");
							FI_SearchBtn2();
						}
					});
				}
			}
		},
		{ title: "특이사항", field: "workOrder_Remark", headerHozAlign: "center" }
	]
});

$(document).ready(function() {
	FI_SearchBtn1();
	FI_SearchBtn2();
})