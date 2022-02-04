var wipManageTable = new Tabulator("#wipManageTable", {
	layoutColumnsOnNewData : true,
	height:"calc(100% - 175px)",
	ajaxURL:"wipLotManageRest/wipLotTransList",
    ajaxConfig:"get",
    ajaxContentType:"json",
	ajaxParams:{WipStatus : 3,
				startDate : today.toISOString().substring(0, 10),
				endDate : tomorrow.toISOString().substring(0, 10)},
	rowClick:function(e, row){
		row.getTable().deselectRow();
		row.select();
		WD_Search(wipManageTable.getData("selected")[0].wip_LotNo)
	},
 	columns:[
	{title:"공정 중인 재공품", headerHozAlign:"center",
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
		{title:"불량등록일", field:"wip_DateTime", headerHozAlign:"center"}]
	}
 	]
});

function WD_Search(value){
	wipDefectTable.setData("wipLotManageRest/wipDefectSelect", {wip_LotNo : value})
	.then(function(){
	})
}

$("#WD_SearchBtn").click(function(){
	wipManageTable.setData("wipLotManageRest/wipLotTransList",
		{WipStatus : 3,
		startDate : $("#startDate").val(),
		endDate : $("#endDate").val()});
})

var wipDefectTable = new Tabulator("#wipDefectTable", {
	height:"calc(100% - 175px)",
	columns:[
		{ title: "Lot번호", field: "defect_LotNo", headerHozAlign: "center", visible: false},
		{ title: "불량 코드", field: "defect_Code", headerHozAlign: "center" },
		{ title: "불량 명", field: "defect_Name", headerHozAlign: "center" },
		{ title: "불량 내용", field: "defect_Note", headerHozAlign: "center", align:"right", width: 300}
	]
});
