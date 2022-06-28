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
		{ title: "분류2", field: "itemClsfc2_Name", headerHozAlign: "center"},
		{ title: "수량", field: "qty", headerHozAlign: "center", hozAlign: "right"},
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
	$.when(smallPackagingSave())
	.then(function(data){
		//productionPrinter(data);
		packagingTable.replaceData();
		smallPackagingQty()
	})
})

function smallPackagingSave(){
	var ajaxResult = $.ajax({
		method: "post",
		url: "maskPackagingRest/smallPackagingSave",
		data: {machineCode : $("#machineCode").val(), itemCode : $("#itemCode").val()},
		beforeSend: function(xhr) {
			var header = $("meta[name='_csrf_header']").attr("content");
			var token = $("meta[name='_csrf']").attr("content");
			xhr.setRequestHeader(header, token);
		}
	});
	return ajaxResult;
}

function packagingLineListSelect(){
	var ajaxResult = $.ajax({
		method: "get",
		url: "maskPackagingRest/packagingLineListSelect",
		data: {itemCode : $("#itemCode").val()},
		success: function(result) {
			for(let j=0;j<result.length;j++){
				$("#bundle-list").append('<li>'+result[j].equip_WorkOrder_Name+'</li>')
			}
		}
	});
	return ajaxResult;
}

function smallPackagingQty(){
	var ajaxResult = $.ajax({
		method: "get",
		url: "maskPackagingRest/smallPackagingQtySelect",
		data: {machineCode : $("#machineCode").val(), itemCode : $("#itemCode").val()},
		success: function(result) {
			$("#smallPackaging-Qty").val(result);
		}
	});
	return ajaxResult;
}

function largePackagingQty(){
	var ajaxResult = $.ajax({
		method: "get",
		url: "maskPackagingRest/largePackagingQtySelect",
		data: {machineCode : $("#machineCode").val(), itemCode : $("#itemCode").val()},
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

function largePackagingSave(){
	var ajaxResult = $.ajax({
		method: "post",
		url: "maskPackagingRest/largePackagingSave",
		data: {machineCode : $("#machineCode").val(), itemCode : $("#itemCode").val()},
		beforeSend: function(xhr) {
			var header = $("meta[name='_csrf_header']").attr("content");
			var token = $("meta[name='_csrf']").attr("content");
			xhr.setRequestHeader(header, token);
		}
	});
	return ajaxResult;
}

$("#largePackagingBtn").click(function(){
	$.when(largePackagingSave())
	.then(function(data){
		productionPrinter(data);
		packagingTable.replaceData();
		largePackagingQty();
	})
})


window.onload = function(){
	packagingLineListSelect()
	setup();
	smallPackagingQty();
	largePackagingQty();
	setInterval(function(){
		packagingTable.replaceData();
	},10000);
}