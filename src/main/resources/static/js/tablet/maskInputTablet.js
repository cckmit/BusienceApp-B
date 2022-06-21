var machineCodeObj = new Object();
var inputMode = '';

function tableSetting(value){
	var tableSetting = {
		height: "100%",
		ajaxURL:"maskInputRest/crateLotListSelect",
		ajaxParams: {machineCode : value.workOrder_EquipCode},
	    ajaxConfig:"get",
	    ajaxContentType:"json",
		ajaxLoader:false,
		rowFormatter: function(row) {
			if (row.getData().color == '1')
				row.getElement().style.backgroundColor = "yellow";
		},
		columns:[
			{title: value.workOrder_EquipName+" ("+value.workOrder_ItemCode+")", field: "machineCode", headerHozAlign:"center",
				columns: [
					{ title: "상 자 코 드", field: "cl_CrateCode", headerHozAlign: "center", headerSort:false},
					{ title: "제 품 수 량", field: "cl_Qty", headerHozAlign: "center", hozAlign:"right", headerSort:false,
						formatter:"money", formatterParams: {precision: false}},
					{ title: "색상", field: "color", visible: false}
				]
			}
		]
	}
	return tableSetting
}

$.ajax({
	method : "get",
	url : "maskInputRest/maskInputSelect",
	success : function(data) {
		machineCodeObj = new Object();
		for(let i=0;i<data.length;i++){
			machineCodeObj[data[i].workOrder_EquipCode] = "itemTable"+(i)
			$("#multiTableAdd").append(
				'<div class="table-container">'
					+'<div id="itemTable'+(i)+'" class="tablet-Table"></div>'
				+'</div>'
			)
			new Tabulator("#itemTable"+(i), tableSetting(data[i]));
		}
	}
});

$(document).keydown(function(){
	$("#barcodeInput").focus();
});

$("#barcodeInput").keypress(function(e){
	if(e.keyCode == 13){
			
		var barcode = $(this).val();
		var initial = barcode.substr(0,1);
		//코드가 무엇인지 확인
		if(initial == "M"){
			//포장설비코드만 걸러냄
			let result = Object.keys(machineCodeObj).some(x => {
				return x == barcode
			})
			if(result){
				$("#selectedMachine").val(barcode);
				$(".tablet-Table").removeClass("border-color")
				$("#"+machineCodeObj[barcode]).addClass("border-color")
				$(".border-color").css("border-color","red");
			}
		}else if(initial == "N"){
			$.when(CrateStatusCheck(barcode)).
			then(function(data){
				//아이템이 설비지정아이템과 맞으면 등록이 되고 맞지않으면 오류 뱉음				
				console.log(data)
				
				crateLotUpdate($("#selectedMachine").val(), data.c_CrateCode);
			})
		}
		$(this).val("");
	}
});

$("#barcodeInput").keyup(function(){
	$(this).val($(this).val().toUpperCase());
	$("#barcodeInput-mirror").val($(this).val());
})

function CrateStatusCheck(value){
	var ajaxResult = $.ajax({
		method : "get",
		url : "maskProductionRest/CrateStatusCheck",
		data : {crateCode : value, condition : 2},
		success : function(data) {
			console.log(data)
		}
	})
	return ajaxResult;
}

function crateLotUpdate(machineCode, CrateCode){
	$.ajax({
		method : "post",
		url : "maskInputRest/maskInputUpdate",
		data: {CL_MachineCode : machineCode, CL_CrateCode : CrateCode},
		beforeSend: function (xhr) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           xhr.setRequestHeader(header, token);
		},
		success : function(data) {
			Tabulator.prototype.findTable("#"+machineCodeObj[machineCode])[0].replaceData();
		}
	});
}

window.onload = function(){
	
	setInterval(function(){
		for(var key in machineCodeObj){
			//Tabulator.prototype.findTable("#"+machineCodeObj[key])[0].replaceData();	
		}
	},60000);
}