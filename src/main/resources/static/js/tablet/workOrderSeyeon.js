var WorkOrder_tbl = new Tabulator("#WorkOrder_tbl", {
	height:"100%",
	placeholder: "No Data Set",
	resizableColumns: false,
	rowClick: function(e, row) {
	},
	columns: [
		{ title: "작업지시번호", field: "workOrder_ONo", headerHozAlign: "center"},
		{ title: "제품코드", field: "workOrder_ItemCode", headerHozAlign: "center"},
		{ title: "제품이름", field: "workOrder_ItemName", headerHozAlign: "center"},
		{ title: "규격", field: "product_INFO_STND_1", headerHozAlign: "center"},
		{ title: "목표량", field: "workOrder_PQty", headerHozAlign: "center", width: 100, align: "right",
			formatter:function(cell, formatterParams, onRendered){
				return cell.getValue() == null ? cell.getValue() :  cell.getValue().slice(0,-2); //return the contents of the cell;
			},
			visible:false
		},
		{ title: "생산량", field: "workOrder_RQty", headerHozAlign: "center", align: "right",
			formatter:"money", formatterParams: {precision: false}
		},
		{ title: "작업시작일", field: "workOrder_StartTime", align: "right", headerHozAlign: "center"},
		{ title: "작업완료일", field: "workOrder_CompleteTime", align: "right", headerHozAlign: "center"},
		{ title: "특이사항", field: "workOrder_Remark", align: "right", headerHozAlign: "center",visible:false}
	],
});

workOrder_Remark = null;
workOrder_ONo = null;

function workOrderDataInit(){
		data = {
			eqcode : 'M001'
		};
	
		data1 = null;
	
		$.get("/tablet/workOrderSeyeonRest/WorkOrder_ONo_export",data,function(data2){
			data1 = data2;
	
			data = {
				eqcode : 'M002'
			};
	
			$.get("/tablet/workOrderSeyeonRest/WorkOrder_ONo_export",data,function(data2){
				if(data1 == "" && data2 != "")
				{
					document.getElementById("n_len").value = data2.workOrder_ItemName;
					document.getElementById("o_len").value = data2.product_INFO_STND_1;
					document.getElementById("t8").innerHTML = data2.workOrder_ONo;
					workOrder_ONo = data2.workOrder_ONo;
					workOrder_Remark = data2.workOrder_Remark;
				}
				else if(data1 != "" && data2 == "")
				{
					$.get("/tablet/workOrderSeyeonRest/WorkOrder_2rd_AutoInsert",data,function(data2){
						workOrderDataInit();
					});
				}
				else if(data1 == "" && data2 == "")
				{
	
				}
				else if(data1 != "" && data2 != "")
				{
					document.getElementById("n_len").value = data2.workOrder_ItemName;
					document.getElementById("o_len").value = data2.product_INFO_STND_1;
					document.getElementById("t8").innerHTML = data2.workOrder_ONo;
					workOrder_ONo = data2.workOrder_ONo;
					workOrder_Remark = data2.workOrder_Remark;
				}
			});
		});
		
		$.get("../workOrderTABRestXO/MI_Searchd?WorkOrder_EquipCode=M002" + "&startDate=" + $("#startDate").val() + "&endDate=" + $("#endDate").val(), function(data) {
				WorkOrder_tbl.setData(data);
		});
}

window.onload = function(){
	document.getElementById("ko").style.height = window.innerHeight - document.getElementById("ko").offsetTop + "px";
	document.getElementById("WorkOrder_tbl").style.height = window.innerHeight - document.getElementById("ko").offsetTop - 10 + "px";
	workOrderDataInit();
}



function productCom(){
	if(workOrder_Remark != "AUTO")
			{
				$.get("../workOrderTABRestXO/MI_Searche?WorkOrder_WorkStatus=243&WorkOrder_EquipCode=M002"+"&PRODUCTION_SERIAL_NUM="+workOrder_ONo, function(data) {            
					workOrderDataInit();
					workOrder_Remark = "";
				});
			}
			else
			{
				$.get("../workOrderTABRestXO/MI_Searche?WorkOrder_WorkStatus=245&WorkOrder_EquipCode=M002"+"&PRODUCTION_SERIAL_NUM="+workOrder_ONo, function(data) {            
					workOrderDataInit();	
					workOrder_Remark = "";		
				});
			}
			
}