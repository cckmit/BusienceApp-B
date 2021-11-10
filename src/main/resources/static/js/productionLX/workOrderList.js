// 출고구분 select를 구성하기위한 ajax
var dtl_arr = new Object();

$.ajax({
   method : "GET",
   async: false,
   url : "dtl_tbl_select?NEW_TBL_CODE=29",
   success : function(datas) {
      for(i=0;i<2;i++){
         dtl_arr[datas[i].child_TBL_NO] = datas[i].child_TBL_TYPE;
      }
   }
});

var WorkOrder_tbl = new Tabulator("#WorkOrder_tbl", {
	height: "calc(50% - 100px)",
	layout:"fitDataStretch",
	//복사하여 엑셀 붙여넣기 가능
	clipboard: true,
	
	columns: [
		{ title: "작업지시No", field: "workOrder_ONo", headerHozAlign: "center", width: 160 },
		{ title: "제품코드", field: "workOrder_ItemCode", headerHozAlign: "center", width: 100 },
		{ title: "제품이름", field: "workOrder_ItemName", headerHozAlign: "center", width: 180},
		{ title: "설비코드", field: "workOrder_EquipCode", headerHozAlign: "center", width: 100 },
		{ title: "설비이름", field: "workOrder_EquipName", headerHozAlign: "center", width: 180 },
		{ title: "규격", field: "product_INFO_STND_1", headerHozAlign: "center"},	
		{ title: "영업재고", field: "qty", headerHozAlign: "center",align:"right"},
		{ title: "지시수량", field: "workOrder_PQty", headerHozAlign: "center", align:"right", formatter:"money", formatterParams: {precision: false}},
		{ title: "작업지시일", field: "workOrder_OrderTime", align: "right", headerHozAlign: "center", width: 160},
		{ title: "작업지시종료일", field: "workOrder_CompleteOrderTime", align: "right", headerHozAlign: "center", width: 160},
		{ title: "등록일", field: "workOrder_RegisterTime", align: "right", headerHozAlign: "center", width: 160},
		{ title: "접수여부", field: "workOrder_WorkStatus", headerHozAlign: "center",editor:"select",align:"center"
			,formatter:function(cell, formatterParams){
		         var value = cell.getValue();
		         if(dtl_arr[value] != null){
		               value = dtl_arr[value];   
		            }else{
		               value = "";
		            }
		          return value;
		      },
      		editorParams:{values:dtl_arr}
			,cellEdited: function(cell)
			{
				//Right_Move(cell,"right");
				console.log(cell.getValue());
				if(cell.getValue()=="243")
				{
					$.ajax({
							method : "GET",
							dataType : "json",
							url : "workOrderListRest/OrderUpdate?workOrder_ONo="+ cell.getRow().getData().workOrder_ONo,
							complete : function(data) {
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

$('#FI_SearchBtn1').click(function(){
	FI_SearchBtn1();
})

$('#FI_SearchBtn2').click(function(){
	FI_SearchBtn2();
})

function FI_SearchBtn1()
{
	data = {
		startDate: $("#startDate").val(),
		endDate: $("#endDate").val(),
		PRODUCT_ITEM_CODE: $("#PRODUCT_ITEM_CODE1").val(),
		Machine_Code: $("#Machine_Code1").val(),
		WorkOrder_Check: "N"
	}

	$.ajax({
		method : "GET",
		dataType : "json",
		url : "workOrderListRest/workorderList_top?data="+ encodeURI(JSON.stringify(data)),
		success : function(data) {
			//console.log("MI");
			console.log(data);
			
			/*
			for(i=0;i<data.length;i++)
			{
				workOrder_OrderTime = data[i].workOrder_OrderTime;
				data[i].workOrder_OrderTime =  workOrder_OrderTime.substr(0,workOrder_OrderTime.length-2);
				
				workOrder_CompleteOrderTime = data[i].workOrder_CompleteOrderTime;
				data[i].workOrder_CompleteOrderTime =  workOrder_CompleteOrderTime.substr(0,workOrder_CompleteOrderTime.length-11);
				
				workOrder_RegisterTime = data[i].workOrder_RegisterTime;
				data[i].workOrder_RegisterTime =  workOrder_RegisterTime.substr(0,workOrder_RegisterTime.length-2);
			}
			*/
			
			WorkOrder_tbl.setData(data);
		}
	});
}

function FI_SearchBtn2()
{
	data = {
		startDate: $("#startDate2").val(),
		endDate: $("#endDate2").val(),
		PRODUCT_ITEM_CODE: $("#PRODUCT_ITEM_CODE2").val(),
		Machine_Code: $("#Machine_Code2").val(),
		WorkOrder_Check: "Y"
	}

	$.ajax({
		method : "GET",
		dataType : "json",
		url : "workOrderListRest/workorderList_down?data="+ encodeURI(JSON.stringify(data)),
		success : function(data) {
			//console.log("MI");
			console.log(data);
			
			/*
			for(i=0;i<data.length;i++)
			{
				workOrder_OrderTime = data[i].workOrder_OrderTime;
				data[i].workOrder_OrderTime =  workOrder_OrderTime.substr(0,workOrder_OrderTime.length-2);
				
				workOrder_CompleteOrderTime = data[i].workOrder_CompleteOrderTime;
				data[i].workOrder_CompleteOrderTime =  workOrder_CompleteOrderTime.substr(0,workOrder_CompleteOrderTime.length-11);
				
				workOrder_ReceiptTime = data[i].workOrder_ReceiptTime;
				data[i].workOrder_ReceiptTime =  workOrder_ReceiptTime.substr(0,workOrder_ReceiptTime.length-2);
			}
			*/
			
			WorkOrder_tbl2.setData(data);
		}
	});
}

var WorkOrder_tbl2 = new Tabulator("#WorkOrder_tbl2", {
	height: "calc(50% - 175px)",
	layout:"fitDataStretch",
	//복사하여 엑셀 붙여넣기 가능
	clipboard: true,
	
	columns: [
		{ title: "작업지시No", field: "workOrder_ONo", headerHozAlign: "center", width: 160 },
		{ title: "제품코드", field: "workOrder_ItemCode", headerHozAlign: "center", width: 100 },
		{ title: "제품이름", field: "workOrder_ItemName", headerHozAlign: "center", width: 180},
		{ title: "설비코드", field: "workOrder_EquipCode", headerHozAlign: "center", width: 100 },
		{ title: "설비이름", field: "workOrder_EquipName", headerHozAlign: "center", width: 180 },
		{ title: "규격", field: "product_INFO_STND_1", headerHozAlign: "center"},	
		{ title: "영업재고", field: "qty", headerHozAlign: "center",align:"right"},	
		{ title: "지시수량", field: "workOrder_PQty", headerHozAlign: "center", align:"right", formatter:"money", formatterParams: {precision: false}},
		{ title: "작업지시일", field: "workOrder_OrderTime", align: "right", headerHozAlign: "center", width: 160},
		{ title: "작업지시종료일", field: "workOrder_CompleteOrderTime", align: "right", headerHozAlign: "center", width: 160},
		{ title: "접수일", field: "workOrder_ReceiptTime", align: "right", headerHozAlign: "center", width: 160},
		{ title: "접수여부", field: "workOrder_WorkStatus", headerHozAlign: "center",editor:"select",align:"center"
			,formatter:function(cell, formatterParams){
		         var value = cell.getValue();
		         if(dtl_arr[value] != null){
		               value = dtl_arr[value];   
		            }else{
		               value = "";
		            }
		          return value;
		      },
      		editorParams:{values:dtl_arr}
			,cellEdited: function(cell)
			{
				//Right_Move(cell,"right");
				console.log(cell.getValue());
				if(cell.getValue()=="242")
				{
					$.ajax({
							method : "GET",
							dataType : "json",
							url : "workOrderListRest/OrderUpdate2?workOrder_ONo="+ cell.getRow().getData().workOrder_ONo,
							complete : function(data) {
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

$(document).ready(function(){
	FI_SearchBtn1();
	FI_SearchBtn2();
})