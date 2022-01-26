var wipOutputListTable = new Tabulator("#wipOutputListTable", {
	layoutColumnsOnNewData : true,
	height:"calc(100% - 175px)",
 	columns:[
	{title:"순번", field:"rownum", formatter:"rownum", hozAlign:"center"},
	{title:"접두사", field:"wip_Prefix", visible:false},
	{title:"Lot번호", field:"wip_LotNo", headerHozAlign:"center", hozAlign:"center",
		formatter: function(cell){
			var prefix = cell.getRow().getData().wip_Prefix
			return prefix + cell.getValue();
		}
	},
	{title:"공정단계", field:"wip_Process_Name", headerHozAlign:"center", hozAlign:"center"},
 	{title:"입고시간", field:"wip_InputDate", headerHozAlign:"center"},
	{title:"출고시간", field:"wip_OutputDate", headerHozAlign:"center"}
 	]
});

$("#WOL_SearchBtn").click(function(){
	WOL_Search();
})

function WOL_Search(){
	wipOutputListTable.setData("wipLotManageRest/wipOutputListSelect",
		{startDate:$("#startDate").val(), endDate:$("#endDate").val()})
}