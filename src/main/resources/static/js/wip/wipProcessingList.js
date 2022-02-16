$("#WPL_SearchBtn").click(function(){
	WPL_Search($("#wip_Process_Type").val())
})

$("#wip_Process_Type").change(function(){
	WPL_Search($(this).val());
	wipProcessingListTable.setColumns(customColumns($(this).val())) 
})

function WPL_Search(value){
	wipProcessingListTable.setData("wipLotManageRest/wipProcessingListSelect",
		{Wip_Process_Type: value});
}

var wipProcessingListTable = new Tabulator("#wipProcessingListTable", {
	placeholder:"창고 내 제공품",
	layoutColumnsOnNewData : true,
	headerFilterPlaceholder: null,
	columnHeaderVertAlign:"bottom",
	height:"calc(100% - 175px)",
	ajaxURL:"wipLotManageRest/wipProcessingListSelect",
	ajaxParams:{Wip_Process_Type:$("#wip_Process_Type").val()},
    ajaxConfig:"get",
    ajaxContentType:"json",
 	columns: customColumns($("#wip_Process_Type").val())
});

function customColumns(value){
	var list = [
		{title:"순번", field:"rownum", formatter:"rownum", hozAlign:"center"},
		{title:"접두사", field:"wip_Prefix", visible:false},
		{title:"Lot번호", field:"wip_LotNo", headerHozAlign:"center", hozAlign:"center", visible:false},
		{title:"Lot번호", field:"wip_FullLotNo", headerHozAlign:"center", hozAlign:"center", headerFilter: "input"}
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
		}
	});
	
	return list
}