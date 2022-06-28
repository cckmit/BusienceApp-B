$("#machineName").click(function(){
	location.reload();
})

var packagingTable = new Tabulator("#packagingTable", {
	layoutColumnsOnNewData : true,
	height: "100%",
	selectable: 1,
	ajaxURL:"maskPackagingRest/smallPackagingStandbySelect",
	ajaxParams: {machineCode : $("#machineCode").val(), itemCode : $("#itemCode").val()},
    ajaxConfig:"get",
    ajaxContentType:"json",
	rowSelected: function(row) {
		$("#selectedItem").val(row.getData().small_Packaging_LotNo);
	},
	ajaxResponse:function(url, params, response){
		$("#crate-Count").val(response.length);
		return response
    },
	columns:[
		{ title: "", field: "rownum", headerHozAlign: "center", hozAlign: "right", formatter: "rownum"},
		{ title: "LotNo", field: "small_Packaging_LotNo", headerHozAlign: "center"},
		{ title: "제품코드", field: "itemCode", headerHozAlign: "center" },
		{ title: "제품명", field: "itemName", headerHozAlign: "center"},
		{ title: "규격1", field: "itemSTND1", headerHozAlign: "center"},
		{ title: "규격2", field: "itemSTND2", headerHozAlign: "center"},
		{ title: "재질", field: "itemMaterial_Name", headerHozAlign: "center"},
		{ title: "분류1", field: "itemClsfc1_Name", headerHozAlign: "center"},
		{ title: "분류2", field: "itemClsfc2_Name", headerHozAlign: "center", topCalc:function(){return "합계"}},
		{ title: "수량", field: "qty", headerHozAlign: "center", hozAlign: "right", topCalc:"sum"},
		{ title: "입고일자", field: "create_Date", headerHozAlign: "center", hozAlign: "right",
			formatter: "datetime", formatterParams: { outputFormat: "YYYY-MM-DD HH:mm:ss" }
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
	$.when(smallPackagingSave($("#machineCode").val(), $("#itemCode").val()))
	.then(function(data){
		productionPrinter(data);
		packagingTable.replaceData();
		smallPackagingQty($("#machineCode").val(), $("#itemCode").val())
	})
})

function smallPackagingSave(machineCode, itemCode){
	var ajaxResult = $.ajax({
		method: "post",
		url: "maskPackagingRest/smallPackagingSave",
		data: {machineCode : machineCode, itemCode : itemCode},
		beforeSend: function(xhr) {
			var header = $("meta[name='_csrf_header']").attr("content");
			var token = $("meta[name='_csrf']").attr("content");
			xhr.setRequestHeader(header, token);
		}
	});
	return ajaxResult;
}

function packagingLineListSelect(itemCode){
	var ajaxResult = $.ajax({
		method: "get",
		url: "maskPackagingRest/packagingLineListSelect",
		data: {itemCode : itemCode},
		success: function(result) {
			for(let j=0;j<result.length;j++){
				$("#bundle-list").append('<li>'+result[j].equip_WorkOrder_Name+'</li>')
			}
		}
	});
	return ajaxResult;
}

function smallPackagingQty(machineCode, itemCode){
	var ajaxResult = $.ajax({
		method: "get",
		url: "maskPackagingRest/smallPackagingQtySelect",
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
		url: "maskPackagingRest/largePackagingQtySelect",
		data: {machineCode : machineCode, itemCode : itemCode},
		success: function(result) {
			$("#largePackaging-Qty").val(result);
		}
	});
	return ajaxResult;
}

$("#rePrintBtn").click(function(){
	//프린트
	productionPrinter(packagingTable.getData("selected")[0])
	$("#selectedItem").val("")
	packagingTable.deselectRow();
})

function largePackagingSave(machineCode, itemCode){
	var ajaxResult = $.ajax({
		method: "post",
		url: "maskPackagingRest/largePackagingSave",
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
		packagingTable.replaceData();
		largePackagingQty($("#machineCode").val(), $("#itemCode").val());
	})
})


window.onload = function(){
	packagingLineListSelect($("#itemCode").val())
	setup();
	smallPackagingQty($("#machineCode").val(), $("#itemCode").val());
	largePackagingQty($("#machineCode").val(), $("#itemCode").val());
}