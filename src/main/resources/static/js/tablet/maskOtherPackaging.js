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
	//숫자 3자리인경우 N추가
	const myRe = /^[0-9]{3}$/;
	if(myRe.test(barcode)){
		barcode = "N"+barcode;
	}
	console.log(barcode)
	var initial = barcode.substr(0,1);
	if(initial == "N"){
		$.when(CrateStatusCheck(barcode)).
		then(function(data){
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
$("#packagingBtn").click(function(){
	setTimeout(function() {
		inputTable.replaceData();
	}, 500);
})
