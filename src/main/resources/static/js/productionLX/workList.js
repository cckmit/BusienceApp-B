// 출고구분 select를 구성하기위한 ajax
var dtl_arr = new Object();

$.ajax({
   method : "GET",
   async: false,
   url : "dtl_tbl_select?NEW_TBL_CODE=29",
   success : function(datas) {
      for(i=1;i<4;i++){
         dtl_arr[datas[i].child_TBL_NO] = datas[i].child_TBL_TYPE;
      }
   }
});


function MI_searchBtn1()
{
	jsonData = {
		startDate: $("#startDate1").val(),
		endDate: $("#endDate1").val(),
		PRODUCT_ITEM_CODE: $("#PRODUCT_ITEM_CODE1").val(),
		Machine_Code: $("#Machine_Code1").val(),
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
		{ title: "작업지시일", field: "workOrder_OrderTime", align: "right", headerHozAlign: "center", width: 150},
		{ title: "작업지시종료일", field: "workOrder_CompleteOrderTime", align: "right", headerHozAlign: "center", width: 150},
		{ title: "작업시작일", field: "workOrder_StartTime", align: "right", headerHozAlign: "center", width: 150},
		{ title: "작업완료일", field: "workOrder_CompleteTime", align: "right", headerHozAlign: "center", width: 150},
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
				debugger;

				//Right_Move(cell,"right");
				console.log(cell.getValue());
				console.log(cell.getInitialValue());
				
				if(cell.getValue()=="243")
				{
					var cellInitialValue = cell.getInitialValue();
					//if(cellInitialValue=="293" || cellInitialValue=="294")
					if(cellInitialValue=="244" || cellInitialValue=="245")
					{
						alert("해당 데이터는 작업이 진행되고 있는 건으로써 수정이 불가능합니다.");
						cell.restoreOldValue();
						return;
					}
				}
				if(cell.getValue()=="244")
				{
					/*
					$.ajax({
							method : "GET",
							dataType : "json",
							url : "workOrderListRest/OrderUpdate2?workOrder_ONo="+ cell.getRow().getData().workOrder_ONo+"&workOrder_EquipCode="+cell.getRow().getData().workOrder_EquipCode,
							complete : function(data) {
								//FI_SearchBtn1();
								//FI_SearchBtn2();
							}
						});
					*/
					
					var cellInitialValue = cell.getInitialValue();
					if(cellInitialValue=="245")
					{
						alert("작업이 완료되었던 건으로써 수정이 불가능합니다.");
						cell.restoreOldValue();
						return;
					}
					
					if(confirm("작업을 시작하시겠습니까?"))
					{
						$.ajax({
							method : "GET",
							dataType : "json",
							url : "workListRest/OrderUpdate?workOrder_ONo="+ cell.getRow().getData().workOrder_ONo+"&workOrder_EquipCode="+cell.getRow().getData().workOrder_EquipCode,
							complete : function(data) {
								console.log(data.responseText);
								
								if(data.responseText=="OK")
								{
									alert("해당 호기에 이미 작업시작이 된 데이터가 존재합니다.");
									cell.restoreOldValue();
									return;
								}
								else
									MI_searchBtn1();
							}
						});
					}
					else
						cell.restoreOldValue();
				}
				else if(cell.getValue()=="245")
				{
					var cellInitialValue = cell.getInitialValue();
					if(cellInitialValue=="242")
					{
						alert("해당 데이터는 작업이 진행되지 않았던 건으로써 작업 시작후 접수하여 주십시오.");
						cell.restoreOldValue();
						return;
					}
				
					if(confirm("작업을 완료하시겠습니까?"))
					{
						$.ajax({
							method : "GET",
							dataType : "json",
							url : "workListRest/OrderUpdate2?workOrder_ONo="+ cell.getRow().getData().workOrder_ONo+"&workOrder_EquipCode="+cell.getRow().getData().workOrder_EquipCode,
							complete : function(data) {
								console.log(data.responseText);
								
								MI_searchBtn1();
							}
						});
					}
					else
						cell.restoreOldValue();
				}
			}
		},
		{ title: "특이사항", field: "workOrder_Remark", headerHozAlign: "center" }
	]
});

window.onload = function(){
  MI_searchBtn1();
}

