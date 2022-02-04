var wipInputListTable = new Tabulator("#wipInputListTable", {
	layoutColumnsOnNewData : true,
	headerFilterPlaceholder: null,
	height:"calc(100% - 175px)",
 	columns:[
	{title:"순번", field:"rownum", formatter:"rownum", hozAlign:"center"},
	{title:"접두사", field:"wip_Prefix", visible:false},
	{title:"Lot번호", field:"wip_LotNo", headerHozAlign:"center", hozAlign:"center", headerFilter: "input",
		formatter: function(cell){
			var prefix = cell.getRow().getData().wip_Prefix
			return prefix + cell.getValue();
		}
	},
	{title:"공정단계", field:"wip_Process_Name", headerHozAlign:"center", hozAlign:"center"},
 	{title:"입고시간", field:"wip_InputDate", headerHozAlign:"center"},
	{title:"출고시간", field:"wip_OutputDate", headerHozAlign:"center"},
	{title:"작업자", field:"wip_Worker_Name", headerHozAlign:"center", headerFilter: "input"}
 	]
});

$("#WIL_SearchBtn").click(function(){
	WIL_Search();
})

function WIL_Search(){
	wipInputListTable.setData("wipLotManageRest/wipInputListSelect",
		{startDate:$("#startDate").val(), endDate:$("#endDate").val()})
}