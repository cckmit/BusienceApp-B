var WorkOrderTable = new Tabulator("#WorkOrderTable", {
	//페이징
	pagination: "local",
	paginationSize: 20,
	height:"calc(100% - 175px)",
	layoutColumnsOnNewData : true,
	rowClick:function(e, row){
		row.getTable().deselectRow();
		row.select();
		DLS_Search(row.getData().workOrder_ItemCode, row.getData().workOrder_EquipCode);
	},
	columns:[
		{ title: "작업지시No", field: "workOrder_ONo", headerHozAlign: "center"},
		{ title: "제품코드", field: "workOrder_ItemCode", headerHozAlign: "center"},
		{ title: "제품명", field: "workOrder_ItemName", headerHozAlign: "center"},
		{ title: "규격", field: "workOrder_Item_STND_1", headerHozAlign: "center"},
		{ title: "설비코드", field: "workOrder_EquipCode", headerHozAlign: "center"},
		{ title: "설비명", field: "workOrder_EquipName", headerHozAlign: "center"},
		{ title: "작업완료일", field: "workOrder_CompleteTime", headerHozAlign: "center"},
		{ title: "생산수량", field: "workOrder_RQty", headerHozAlign: "center", align:"right"},
		{ title: "불량수량", field: "workOrder_DQty", headerHozAlign: "center", align:"right"},
		{ title: "불량률", field: "workOrder_Defect_Rate", headerHozAlign: "center", align:"right",
			formatter : function(cell, formatterParams, onRendered){
				var denomin = cell.getRow().getData().workOrder_RQty
				var numer = cell.getRow().getData().workOrder_DQty
				var result = Math.round(numer/denomin*10000)/100
				
				return  result+"%";
			}
		}
	]
});

$("#DL_SearchBtn").click(function(){
	DL_Search();
})

function DL_Search(){
	var datas = {
		startDate : $("#startDate").val(),
		endDate : $("#endDate").val()
	}
	WorkOrderTable.setData("defectListRest/DL_Search", datas);
}

var defectTable = new Tabulator("#defectTable", {
	height:"calc(100% - 175px)",
	columns:[
		{ title: "작업지시 번호", field: "defect_ONo", headerHozAlign: "center", visible: false },
		{ title: "불량 코드", field: "defect_Code", headerHozAlign: "center" },
		{ title: "불량 명", field: "defect_Name", headerHozAlign: "center" },
		{ title: "불량 수량", field: "defect_Qty", headerHozAlign: "center", align:"right", topCalc:"sum"},
		{ title: "비율", field: "defect_Rate", headerHozAlign: "center", align:"right",
			formatter : function(cell, formatterParams, onRendered){
				var tableData = cell.getTable().getData()
				var denomin = 0;
				for(let i=0;i<tableData.length;i++){
					denomin += tableData[i].defect_Qty
				}
				var numer = cell.getRow().getData().defect_Qty
				var result = Math.round(numer/denomin*10000)/100
				if(isNaN(result)){
					result = 0;
				}
				
				return  result+"%";
			}
		}
	]
});

function DLS_Search(value, evalue){
	var datas = {
		startDate : $("#startDate").val(),
		endDate : $("#endDate").val(),
		itemCode : value,
		machineCode : evalue
	}
	defectTable.setData("defectListRest/DLS_Search", datas)
}

$(document).ready(function(){
	DL_Search();
})