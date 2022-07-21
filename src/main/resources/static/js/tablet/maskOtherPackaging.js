$("#machineName").click(function(){
	location.reload();
})

var inputTable = new Tabulator("#inputTable", {
	height: "100%",
	headerVisible: false,
	layout:"fitColumns",
	ajaxURL:"maskInputRest/crateLotListSelect",
	ajaxParams: {machineCode : $("#selectedMachine").val(), itemCode : $("#inputItemCode").val()},
    ajaxConfig:"get",
    ajaxContentType:"json",
	ajaxLoader:false,
	columns:[
		{ title: "코드", field: "cl_CrateCode", headerHozAlign: "center", headerSort:false, widthGrow : 6},
		{ title: "수량", field: "cl_ProductionQty", headerHozAlign: "center", hozAlign:"right", headerSort:false,
			formatter:"money", formatterParams: {precision: false}, widthGrow : 5}
	]
});

$("#barcodeInput").change(function(){
	
	var barcode = inko.ko2en($(this).val()).toUpperCase();
	
	$("#barcodeInput").val("");
	var initial = barcode.substr(0,1);
	if(initial == "N"){
		$.when(CrateStatusCheck(barcode)).
		then(function(data){
			console.log(data)
			//아이템이 설비지정아이템과 맞으면 등록이 되고 맞지않으면 오류 뱉음	
			if(data instanceof Object){
				if(data.c_ItemCode == $("#inputItemCode").val()){
					crateLotUpdate($("#selectedMachine").val(), data.c_CrateCode);
				}else{
					toastr.error("설비와 품목이 맞지 않습니다.")
				}
			}else{
				toastr.error("잘못된 박스 입니다.")
			}
		})
		//maskInputRollback(machineCode, barcode)
	}
});

function CrateStatusCheck(value){
	var ajaxResult = $.ajax({
		method : "get",
		url : "maskProductionRest/CrateStatusCheck",
		data : {itemCode : $("#inputItemCode").val(), crateCode : value, condition : 2},
	})
	return ajaxResult;
}


function crateLotUpdate(machineCode, crateCode){
	$.ajax({
		method : "post",
		url : "maskInputRest/maskInputUpdate",
		data: {CL_MachineCode : machineCode, CL_CrateCode : crateCode},
		beforeSend: function (xhr) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           xhr.setRequestHeader(header, token);
		},
		success : function(data) {
			inputTable.replaceData();
		}
	});
}

var packagingTable = new Tabulator("#packagingTable", {
	layoutColumnsOnNewData : true,
	height: "100%",
	selectable: 1,
	ajaxURL:"/tablet/maskPackagingRest/smallPackagingStandbySelect",
	ajaxParams: {machineCode : $("#machineCode").val(), itemCode : $("#itemCode").val()},
    ajaxConfig:"get",
    ajaxContentType:"json",
	rowSelected: function(row) {
		$("#selectedItem").val(row.getData().small_Packaging_LotNo);
	},
	ajaxResponse:function(url, params, response){
		$("#packaging-Count").val(response.length);
		return response
    },
	columns:[
		{ title: "LotNo", field: "small_Packaging_LotNo", headerHozAlign: "center"},
		{ title: "코드", field: "itemCode", headerHozAlign: "center" },
		{ title: "제품명", field: "itemName", headerHozAlign: "center"},
		{ title: "규격1", field: "itemSTND1", headerHozAlign: "center"},
		{ title: "규격2", field: "itemSTND2", headerHozAlign: "center"},
		{ title: "분류1", field: "itemClsfc1_Name", headerHozAlign: "center"},
		{ title: "분류2", field: "itemClsfc2_Name", headerHozAlign: "center", topCalc:function(){return "합계"}},
		{ title: "수량", field: "qty", headerHozAlign: "center", hozAlign: "right", topCalc:"sum"},
		{ title: "입고일자", field: "create_Date", headerHozAlign: "center", hozAlign: "right",
			formatter: "datetime", formatterParams: { outputFormat: "YYYY-MM-DD HH:mm:ss"}, visible:false
		}
	]
});

$("#fullScreenBtn").click(function(){
	toggleFullScreen();
})

function toggleFullScreen() {
  if (!document.fullscreenElement) {
    document.documentElement.requestFullscreen()
	packagingTable.replaceData();
	packagingTable.redraw();
  } else {
    if (document.exitFullscreen) {
      document.exitFullscreen()
    }
  }
}

//소포장발행
$("#packagingBtn").click(function(){
	if(parseInt($("#waiting-Qty").val())<parseInt($("#packaging-small").val())){
		if(confirm("현재 수량은 "+$("#waiting-Qty").val()+"입니다. 출력하시겠습니까?")){
			
		}else{
			return false;
		}
	}
	$.when(smallPackagingSave($("#machineCode").val(), $("#itemCode").val(), $("#packaging-small").val()))
	.then(function(data){
		productionPrinter(data);
		packagingTable.replaceData();
		inputTable.replaceData();
		waitingQty($("#machineCode").val());
		//return smallPackagingQty($("#machineCode").val(), $("#itemCode").val());
	})
	.then(function(data){
		/*
		if($("#packaging-large").val()/$("#packaging-small").val() == $("#crate-Count").val()){
			$.when(largePackagingSave($("#machineCode").val(), $("#itemCode").val()))
			.then(function(data){
				productionPrinter(data);
				location.reload();
			})
		}*/
	})
})

function smallPackagingSave(machineCode, itemCode, packagingQty){
	var ajaxResult = $.ajax({
		method: "post",
		url: "/tablet/maskPackagingRest/smallPackagingSave",
		data: {machineCode : machineCode, itemCode : itemCode, packagingQty : packagingQty},
		beforeSend: function(xhr) {
			var header = $("meta[name='_csrf_header']").attr("content");
			var token = $("meta[name='_csrf']").attr("content");
			xhr.setRequestHeader(header, token);
		}
	});
	return ajaxResult;
}
function waitingQty(machineCode){
	var ajaxResult = $.ajax({
		method: "get",
		url: "/tablet/maskPackagingRest/packagingWaiting",
		data: {machineCode : machineCode},
		success: function(result) {
			console.log(result)
			$("#waiting-Qty").val(result);			
		}
	});
	return ajaxResult;
}
/*
function smallPackagingQty(machineCode, itemCode){
	var ajaxResult = $.ajax({
		method: "get",
		url: "/tablet/maskPackagingRest/smallPackagingQtySelect",
		data: {machineCode : machineCode, itemCode : itemCode},
		success: function(result) {
			$("#smallPackaging-Qty").val(result);
			
		}
	});
	return ajaxResult;
}

function largePackagingQty(machineCode, itemCode){
	var ajaxResult = $.ajax({
		method: "get",
		url: "/tablet/maskPackagingRest/largePackagingQtySelect",
		data: {machineCode : machineCode, itemCode : itemCode},
		success: function(result) {
			$("#largePackaging-Qty").val(result);
		}
	});
	return ajaxResult;
}*/

$("#rePrintBtn").click(function(){
	//프린트
	productionPrinter(packagingTable.getData("selected")[0])
	$("#selectedItem").val("")
	packagingTable.deselectRow();
})
/*
function largePackagingSave(machineCode, itemCode){
	var ajaxResult = $.ajax({
		method: "post",
		url: "/tablet/maskPackagingRest/largePackagingSave",
		data: {machineCode : machineCode, itemCode : itemCode},
		beforeSend: function(xhr) {
			var header = $("meta[name='_csrf_header']").attr("content");
			var token = $("meta[name='_csrf']").attr("content");
			xhr.setRequestHeader(header, token);
		}
	});
	return ajaxResult;
}

$("#largePackagingBtn").click(function(){
	$.when(largePackagingSave($("#machineCode").val(), $("#itemCode").val()))
	.then(function(data){
		productionPrinter(data);
		location.reload();
	})
})
*/
waitingQty($("#machineCode").val());
setInterval(function(){
	waitingQty($("#machineCode").val());
},10000);
setInterval(function(){
	location.reload();
},1800000);

/*
smallPackagingQty($("#machineCode").val(), $("#itemCode").val());
largePackagingQty($("#machineCode").val(), $("#itemCode").val());
*/