$("#WIOL_SearchBtn").click(function(){
	WIOL_Search($("#wip_Process_Type").val());
})

$("#wip_Process_Type").change(function(){
	WIOL_Search($(this).val());
	wipInOutListTable.setColumns(customColumns($(this).val()))
	
})

function WIOL_Search(value){
	wipInOutListTable.setData("wipLotManageRest/wipLotMasterListSelect",
		{Wip_Process_Type: value})
	.then(function(){
		console.log(wipInOutListTable.getData());
	});
}

var wipInOutListTable = new Tabulator("#wipInOutListTable", {
	placeholder:"총 입출고 내역",
	layoutColumnsOnNewData : true,
	headerFilterPlaceholder: null,
	columnHeaderVertAlign:"bottom",
	height:"calc(100% - 175px)",
	ajaxURL:"wipLotManageRest/wipLotMasterListSelect",
	ajaxParams:{Wip_Process_Type:$("#wip_Process_Type").val()},
    ajaxConfig:"get",
    ajaxContentType:"json",
 	columns: customColumns($("#wip_Process_Type").val())
});

function customColumns(value){
	var list = [
		{title:"순번", field:"rownum", formatter:"rownum", hozAlign:"center"},
		{title:"접두사", field:"wip_Prefix", visible:false},
		{title:"Lot번호", field:"wip_LotNo", headerHozAlign:"center", hozAlign:"center", headerFilter: "input",
			formatter: function(cell){
				var prefix = cell.getRow().getData().wip_Prefix
				return prefix + cell.getValue();
			}
		}
 	]
	$.ajax({
		method: "GET",
		async: false,
		url: "routingManageRest/routingManageDetail",
		data: {"Item_Clsfc_1" : value},
		success: function(datas) {
			for (i = 0; i < 5; i++) {
				list.push({title:datas[i].routing, field:"wip_Process_P"+(i+1), headerHozAlign:"center",	
					columns:[
					{title:"입고시간", field:"wip_InputDate_P"+(i+1), headerHozAlign:"center",
						formatter:"datetime", formatterParams :{outputFormat :"YYYY-MM-DD HH:mm"}},
					{title:"출고시간", field:"wip_OutputDate_P"+(i+1), headerHozAlign:"center",
						formatter:"datetime", formatterParams :{outputFormat :"YYYY-MM-DD HH:mm"}},
					{title:"보관시간", field:"wip_SaveDate_P"+(i+1), headerHozAlign:"center"}
					]
				})
			}
			list.push({title:"상태", field:"wip_Status", headerHozAlign:"center", 
						formatter: function(cell){
							var status = "공정중"
							if(cell.getValue() == 1){
								status = "공정중"
							}else if(cell.getValue() == 2){
								status = "완료"
							}else if(cell.getValue() == 3){
								status = "불량"
							}
							return status;
						}})
		}
	});
	
	return list
}