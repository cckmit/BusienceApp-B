var wipManageTable = new Tabulator("#wipManageTable", {
	layoutColumnsOnNewData : true,
	height:"calc(100% - 175px)",
	ajaxURL:"wipLotManageRest/wipLotTransList",
    ajaxConfig:"get",
    ajaxContentType:"json",
	ajaxParams:{WipStatus : 321},
	rowClick:function(e, row){
		row.getTable().deselectRow();
		row.select();
		WD_Search(wipManageTable.getData("selected")[0].wip_LotNo)
	},
 	columns:[
	{title:"곻정 중인 재공품", headerHozAlign:"center",
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
		{title:"출고시간", field:"wip_OutputDate", headerHozAlign:"center", hozAlign:"center"}]
	}
 	]
});

function WD_Search(value){
	wipDefectTable.setData("wipLotManageRest/wipDefectSelect", {wip_LotNo : value})
	.then(function(){
		wipDefectTable.getRows()[0].getCell("defect_Note").edit();
	})
}

$("#WD_SaveBtn").click(function(){
	if(confirm("저장하시겠습니까?")){
		WD_Insert();
	}
})

function WD_Insert(){
	$.ajax({
        method : "post",
        url : "wipLotManageRest/wipDefectInsert",
		data : JSON.stringify(wipDefectTable.getData()),
		contentType:'application/json',
		beforeSend: function (xhr) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           xhr.setRequestHeader(header, token);
		},
        success : function(result) {
            if (result == 0) {
				alert("잘못 입력하였습니다.");
			} else if (result == 1) {
				alert("저장되었습니다.");
				wipManageTable.replaceData();
				wipDefectTable.clearData();
			}
        }
    })
}

var wipDefectTable = new Tabulator("#wipDefectTable", {
	height:"calc(100% - 175px)",
	columns:[
		{ title: "Lot번호", field: "defect_LotNo", headerHozAlign: "center", visible: false},
		{ title: "불량 코드", field: "defect_Code", headerHozAlign: "center" },
		{ title: "불량 명", field: "defect_Name", headerHozAlign: "center" },
		{ title: "불량 내용", field: "defect_Note", headerHozAlign: "center", align:"right", editor: "input", width: 300}
	]
});
