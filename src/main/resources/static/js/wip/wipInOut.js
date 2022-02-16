$("#wipLotNo").keypress(function(e){
	if(e.keyCode == 13){
		LotNoCheck($(this).val())
		$(this).val("");
	}
})

function LotNoCheck(LotNo){
	if(LotNo.length != 9){
		alert("Lot번호 "+LotNo+"는 잘못된 Lot번호 입니다.");
		return false;
	}
	$.when(processCheck(LotNo))
	.then(function(data){
		if(data.length == 0){
			alert( "Lot번호 "+LotNo+"는 존재하지 않는 LotNo입니다.");
			return false;
		}
		//출고시간이 비어있으면 출고, 아니면 입고
		if(data[0].wip_OutputDate == null){
			if(confirm("Lot번호 "+data[0].wip_LotNo+"의 현재 공정은 '"+data[0].wip_Process_Name+"' 입니다.\r\n출고 하시겠습니까?")){
				wipInOutInsert(data[0].wip_LotNo)
			}
		}else{
			//입고하는데 만약 순번이 5번째면 입고하면 안됨
			if(data[0].wip_Process_No != 5){
				nextProcess(data[0])
				if(confirm("Lot번호 "+data[0].wip_LotNo+"의 다음 공정은 '"+data[0].wip_Process_Name+"' 입니다.\r\n입고 하시겠습니까?")){
					wipInOutInsert(data[0].wip_LotNo)
				}	
			}else{
				alert( "Lot번호 "+LotNo+"는 이미 공정이 완료되었습니다.");
			}
		}
	})
}

//공정과정중인 재공품인지 확인
function processCheck(value){
	var ajaxResult = $.ajax({
		method : "get",
		data : {lotNo : value},
		url : "wipLotManageRest/wipLastDataSelect",
		success : function(data) {
		}
	});
	return ajaxResult
}

//다음 공정을 가져옴
function nextProcess(values){
	var beforData = values;
	$.ajax({
		method : "get",
		data : {Item_Clsfc_1 : values.wip_Process_Type},
		url : "routingManageRest/routingManageDetail",
		async : false,
		success : function(data) {
			for(let i=0;i<data.length-1;i++){
				if(data[i].routing_No == beforData.wip_Process_No){
					beforData.wip_Process_Name = data[i+1].routing
				}
			}
		}
	});
	return beforData
}

$("#wipLotNo").blur(function(){
	$("#wipLotNo").focus();
})

function wipInOutInsert(datas){
	$.ajax({
		method : "POST",
		url : "wipLotManageRest/wipInOutInsert",
		data : {wip_LotNo : datas},
		beforeSend: function (xhr) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           xhr.setRequestHeader(header, token);
		},
		success : function(data) {
			if (data == "in") {
			}else if(data == "out"){
			}else if(data == "fail"){
			}
			wipManageTable.replaceData();
			WI_Search();
			WO_Search();
		}
	});
}

function wipInputRollback(row){
	var datas = row.getData();
	
	if(datas.wip_OutputDate){
		alert("출고상태인 품목은 입고취소 할 수 없습니다.")
		return false;
	}
	if(confirm("Lot번호 "+datas.wip_LotNo+"의 '"+datas.wip_Process_Name+"' 입고를 취소하시겠습니까?")){
		
		$.ajax({
			method : "POST",
			url : "wipLotManageRest/wipInputRollback",
			data : datas,
			beforeSend: function (xhr) {
	           var header = $("meta[name='_csrf_header']").attr("content");
	           var token = $("meta[name='_csrf']").attr("content");
	           xhr.setRequestHeader(header, token);
			},
			success : function(data) {
				wipManageTable.replaceData();
				WI_Search();
				WO_Search();
			}
		});		
	}
}

function wipOutputRollback(row){
	$.when(processCheck(row.getData().wip_LotNo))
	.then(function(data){
		if(data[0].wip_OutputDate == null){
			alert("입고상태인 품목은 출고취소 할 수 없습니다.");
			return false;
		}
		if(confirm("Lot번호 "+data[0].wip_LotNo+"의 '"+data[0].wip_Process_Name+"' 출고를 취소하시겠습니까?")){
			$.ajax({
				method : "POST",
				url : "wipLotManageRest/wipOutputRollback",
				data : data[0],
				beforeSend: function (xhr) {
			       var header = $("meta[name='_csrf_header']").attr("content");
			       var token = $("meta[name='_csrf']").attr("content");
			       xhr.setRequestHeader(header, token);
				},
				success : function(data) {
					wipManageTable.replaceData();
					WI_Search();
					WO_Search();
				}
			});
		}
	})
}

var customInOutReceiver = function(fromRow, toRow, toTable){
    LotNoCheck(fromRow.getData().wip_LotNo);
}

var customRollbackReceiver = function(fromRow, toRow, toTable){
	var table = fromRow.getTable().element.id
	
	if(table == "wipInputTable"){
		wipInputRollback(fromRow);
	}else if(table == "wipOutputTable"){
		wipOutputRollback(fromRow);
	}
}

var wipManageTable = new Tabulator("#wipManageTable", {
	placeholder:"창고 내 재공품",
	layoutColumnsOnNewData : true,
	height:"calc(100% - 175px)",
	ajaxURL:"wipLotManageRest/wipInOutListSelect",
    ajaxConfig:"get",
    ajaxContentType:"json",
	movableRows: true,
    movableRowsConnectedTables: "#wipOutputTable",
	movableRowsReceiver: customRollbackReceiver,
 	columns:[
	{title:"창고 내 재공품", headerHozAlign:"center",
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
		{title:"보관기간", field:"wip_SaveTime", headerHozAlign:"center", hozAlign:"center"}]
	}
 	]
});

var wipInputTable = new Tabulator("#wipInputTable", {
	placeholder:"오늘 입고 된 재공품",
	layoutColumnsOnNewData : true,
	height:"50%",
	movableRows: true,
    movableRowsConnectedTables: "#wipManageTable",
	movableRowsReceiver: customInOutReceiver,
 	columns:[
	{title:"오늘 입고 된 재공품", headerHozAlign:"center",
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
	 	{title:"입고시간", field:"wip_InputDate", headerHozAlign:"center"}
		]
	}
 	]
});

var wipOutputTable = new Tabulator("#wipOutputTable", {
	placeholder:"오늘 출고 된 재공품",
	layoutColumnsOnNewData : true,
	height:"50%",
	movableRows: true,
    movableRowsConnectedTables: ["#wipManageTable","#wipInputTable"],
	movableRowsReceiver: customInOutReceiver,
 	columns:[
	{title:"오늘 출고 된 재공품", headerHozAlign:"center",
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
		{title:"출고시간", field:"wip_OutputDate", headerHozAlign:"center"}
		]
	}	
 	]
});

function WI_Search(){
	wipInputTable.setData("wipLotManageRest/wipInputListSelect",
		{startDate : today.toISOString().substring(0, 10),
		endDate : tomorrow.toISOString().substring(0, 10),
		wipStatus : 321}
	);
}

function WO_Search(){
	wipOutputTable.setData("wipLotManageRest/wipOutputListSelect",
		{startDate : today.toISOString().substring(0, 10),
		endDate : tomorrow.toISOString().substring(0, 10),
		wipStatus : 321}
	);
}

$(document).ready(function(){
	WI_Search();
	WO_Search();
})
