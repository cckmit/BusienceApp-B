// 출고구분 select를 구성하기위한 ajax
var dtl_arr = new Object();

$.ajax({
   method : "GET",
   async: false,
   url : "dtl_tbl_select?NEW_TBL_CODE=29",
   success : function(datas) {
      for(i=0;i<4;i++){
		 console.log(datas[i].child_TBL_TYPE);
         dtl_arr[datas[i].child_TBL_NO] = datas[i].child_TBL_TYPE;
         console.log(dtl_arr);
      }
   }
});


function MI_searchBtn1()
{
	jsonData = {
		startDate: $("#startDate1").val(),
		endDate: $("#endDate1").val(),
		ItemCode: $("#PRODUCT_ITEM_CODE1").val(),
		MachineCode: $("#Machine_Code1").val(),
		WorkOrder_Check: "Y",
		order_flag : "Y"
	}
	
	$.ajax({
		url : "workListRest/workList_Search?data="+ encodeURI(JSON.stringify(jsonData)),
		method : "get",
		data : jsonData,
		dataType : "json",
		success : function(data) {
			//console.log("MI");
			console.log(data);
			
			WorkOrder_tbl.setData(data);
		}
	});
}

/*
function MI_searchBtn2()
{
		data = {
		PRODUCT_ITEM_CODE: $("#PRODUCT_ITEM_CODE2").val(),
		Machine_Code: $("#Machine_Code2").val(),
		WorkOrder_Check: "C2",
		order_flag : "Y"
	}

	$.ajax({
		method : "GET",
		dataType : "json",
		url : "workOrderListRest/MI_Search1?data="+ encodeURI(JSON.stringify(data)),
		success : function(data) {
			//console.log("MI");
			console.log(data);
			WorkOrder_tbl2.setData(data);
		}
	});
}
*/

var WorkOrder_tbl = new Tabulator("#WorkOrder_tbl", {
	height: "calc(100% - 175px)",
	layout:"fitDataStretch",
	//복사하여 엑셀 붙여넣기 가능
	clipboard: true,
	groupBy:"workOrder_EquipName",
	rowFormatter:function(row){
		console.log(row.getData().workOrder_WorkStatus);
	
		if(row.getData().workOrder_WorkStatus=="293")
			row.getElement().style.color = "blue";
	},
	
	columns: [
		{ title: "설비코드", field: "workOrder_EquipCode", headerHozAlign: "center", width: 100 , visible:false},
		{ title: "설비이름", field: "workOrder_EquipName", headerHozAlign: "center", width: 180 , visible:false},	
		{ title: "작업지시No", field: "workOrder_ONo", headerHozAlign: "center", width: 160 },
		{ title: "제품코드", field: "workOrder_ItemCode", headerHozAlign: "center", width: 100 },
		{ title: "제품이름", field: "workOrder_ItemName", headerHozAlign: "center", width: 150},
		{ title: "규격", field: "product_INFO_STND_1", headerHozAlign: "center"},		
		{ title: "지시수량", field: "workOrder_PQty", headerHozAlign: "center", align:"right", width: 100, formatter:"money", formatterParams: {precision: false}},
		{ title: "생산수량", field: "workOrder_RQty", headerHozAlign: "center", align:"right", width: 100, formatter:"money", formatterParams: {precision: false}},
		{ title: "작업등록일", field: "workOrder_RegisterTime2", align: "right", headerHozAlign: "center", width: 160},
		{ title: "작업시작일", field: "workOrder_StartTime2", align: "right", headerHozAlign: "center", width: 160},
		{ title: "작업완료일", field: "workOrder_CompleteTime2", align: "right", headerHozAlign: "center", width: 160},
		{ title: "작업상태", field: "workOrder_WorkStatusName", headerHozAlign: "center", align:"center", width: 120},
		{ title: "특이사항", field: "workOrder_Remark", headerHozAlign: "center" }
	]
});

window.onload = function(){
  MI_searchBtn1();
}

