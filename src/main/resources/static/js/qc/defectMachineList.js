var WorkOrderTable = new Tabulator("#WorkOrderTable", {
	//페이징
	pagination: "local",
	paginationSize: 20,
	height:"calc(100% - 175px)",
	layoutColumnsOnNewData : true,
	rowClick:function(e, row){
		row.getTable().deselectRow();
		row.select();
		DILS_Search(row.getData().workOrder_EquipCode);
	},
	columns:[
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

$("#DIL_SearchBtn").click(function(){
	DIL_Search();
})

function DIL_Search(){
	var datas = {
		startDate : $("#startDate").val(),
		endDate : $("#endDate").val()
	}
	WorkOrderTable.setData("defectMachineListRest/DML_Search", datas);
}

var defectTable = new Tabulator("#defectTable", {
	height:"calc(100% - 175px)",
	columns:[
		{ title: "불량 코드", field: "defect_Code", headerHozAlign: "center" },
		{ title: "불량 명", field: "defect_Name", headerHozAlign: "center" },
		{ title: "불량 수량", field: "defect_Qty", headerHozAlign: "center", align:"right", topCalc:"sum"},
		{ title: "불량률", field: "defect_Rate", headerHozAlign: "center", align:"right",
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

function DILS_Search(value){
	var datas = {
		startDate : $("#startDate").val(),
		endDate : $("#endDate").val(),
		MachineCode : value
	}
	defectTable.setData("defectMachineListRest/DMLS_Search", datas)
}

$(document).ready(function(){
	DIL_Search();
})