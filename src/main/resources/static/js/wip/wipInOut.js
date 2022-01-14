$("#wipLotNo").keypress(function(e){
	if(e.keyCode == 13){
		LotNoCheck($(this).val())
		$(this).val("");
	}
})

function LotNoCheck(LotNo){
	if(LotNo.length == 9){
		var thisValue = processCheck(LotNo)
		console.log(thisValue);
		if(thisValue){
			
			var tableData = wipManageTable.getData();
			
			var resultCheck = tableData.some(function(currentValue, index, array){
				return (thisValue.wip_LotNo == currentValue.wip_LotNo);
			})
			if(resultCheck){
				if(confirm("Lot번호 "+thisValue.wip_LotNo+"의 현재 공정은 '"+thisValue.wip_Process_Name+"' 입니다.\r\n출고 하시겠습니까?")){
					wipInOutInsert(thisValue.wip_LotNo)
				}
			}else{
				if(confirm("Lot번호 "+thisValue.wip_LotNo+"의 다음 공정은 '"+thisValue.wip_Process_Name+"' 입니다.\r\n입고 하시겠습니까?")){
					wipInOutInsert(thisValue.wip_LotNo)
				}
			}
							
		}else{
			alert( "Lot번호 "+LotNo+"는 이미 공정이 완료되었거나, 존재하지 않는 LotNo입니다.");
		}
	}else{
		alert("Lot번호 "+LotNo+"는 잘못된 Lot번호 입니다.");
	}
}

//공정과정중인 재공품인지 확인
function processCheck(value){
	var result
	var resultCheck
	$.ajax({
		method : "get",
		url : "wipLotManageRest/wipProcessingListSelect",
		async: false,
		success : function(data) {
			console.log(data)
			resultCheck = data.some(function(currentValue, index, array){
				result = currentValue
				return (value == currentValue.wip_LotNo);
			})
		}
	});
	if(resultCheck){
		return result;
	}
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
		alert("출고내역이 있는 행은 취소 할 수 없습니다.")
	}else{
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
}

function wipOutputRollback(row){
	var datas = row.getData();

	var checkData
	
	$.ajax({
		method : "get",
		url : "wipLotManageRest/wipOutputRollbackCheck",
		async : false,
		data : {LotNo : datas.wip_LotNo},
		success : function(data) {
			checkData = data
		}
	});
	
	if(checkData == null){
		alert("입고내역이 있는 행은 취소 할 수 없습니다.")
	}else{
		if(confirm("Lot번호 "+checkData.wip_LotNo+"의 '"+checkData.wip_Process_Name+"' 출고를 취소하시겠습니까?")){
		
			$.ajax({
				method : "POST",
				url : "wipLotManageRest/wipOutputRollback",
				data : checkData,
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
		{title:"Lot번호", field:"wip_LotNo", headerHozAlign:"center", hozAlign:"center"},
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
		{title:"Lot번호", field:"wip_LotNo", headerHozAlign:"center", hozAlign:"center"},
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
		{title:"Lot번호", field:"wip_LotNo", headerHozAlign:"center", hozAlign:"center"},
		{title:"공정단계", field:"wip_Process_Name", headerHozAlign:"center", hozAlign:"center"},
		{title:"출고시간", field:"wip_OutputDate", headerHozAlign:"center"}
		]
	}	
 	]
});

function WI_Search(){
	console.log(today);
	console.log(tomorrow);
	wipInputTable.setData("wipLotManageRest/wipInputListSelect",
	{startDate:today.toISOString().substring(0, 10), endDate:tomorrow.toISOString().substring(0, 10)});
}

function WO_Search(){
	wipOutputTable.setData("wipLotManageRest/wipOutputListSelect",
	{startDate:today.toISOString().substring(0, 10), endDate:tomorrow.toISOString().substring(0, 10)});
}

$(document).ready(function(){
	WI_Search();
	WO_Search();
})
