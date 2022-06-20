var machineCodeObj = new Object();
var inputMode = "save"
var refreshData = { machineCode : "", crateCode : ""};

function tableSetting(value){
	var tableSetting = {
		height: "100%",layout:"fitDataFill",
		ajaxURL:"maskInputRest/crateLotListSelect",
		ajaxParams: {machineCode : value.equipment_INFO_CODE},
	    ajaxConfig:"get",
	    ajaxContentType:"json",
		ajaxLoader:false,
		columns:[
			{title: value.equipment_INFO_NAME, field: "machineCode", headerHozAlign:"center",
				columns: [	
					{ title: "코드", field: "cl_CrateCode", headerHozAlign: "center", headerSort:false},
					{ title: "수량", field: "cl_Qty", headerHozAlign: "center", hozAlign:"right", headerSort:false,
						formatter:"money", formatterParams: {precision: false}}
				]
			}
		]
	}
	return tableSetting
}

$.ajax({
	method : "get",
	url : "../machineManageRest/machineManageSelect",
	success : function(data) {
		machineCodeObj = new Object();
		for(let i=0;i<data.length;i++){
			if(data[i].equipment_TYPE_NAME == "포장"){
				machineCodeObj[data[i].equipment_INFO_CODE] = "itemTable"+(i)
				$("#multiTableAdd").append(
					'<div class="table-container">'
						+'<div id="itemTable'+(i)+'" class="tablet-Table"></div>'
					+'</div>'
				)
				new Tabulator("#itemTable"+(i), tableSetting(data[i]));
			}
		}
	}
});




$(document).keydown(function(){
	$("#barcodeInput").focus();
});

$("#barcodeInput").keypress(function(e){
	if(e.keyCode == 13){
		// m인지 파악
		// r인지 y인지 파악
		
		// c인지 파악
		// r이면 바로 저장
		// y면 배열에 저장		
		var barcode = $(this).val();
		var colorStatus = 'R'
		var initial = barcode.substr(0,1);
		//코드가 무엇인지 확인
		if(initial == "M"){
			//R은 빨간색 Y는 노란색
			if(barcode.substr(-1) == "Y"){
				colorStatus = barcode.substr(-1);
				barcode = barcode.substr(0,4);
			}
			//포장설비코드만 걸러냄
			let result = Object.keys(machineCodeObj).some(x => {
				return x == barcode
			})
			if(result){
				$("#selectedMachine").val(barcode);
				if(colorStatus == 'R'){
					$(".tablet-Table").removeClass("border-color")
					$("#"+machineCodeObj[barcode]).addClass("border-color")
					$(".border-color").css("border-color","red");
					inputMode = "save"
				}else if(colorStatus == 'Y'){
					if(inputMode == "refresh"){
						//최신화 실행
						refreshData = { machineCode : "", crateCode : ""};
						inputMode = "save";
						
						$(".tablet-Table").removeClass("border-color")
					}else{
						$(".tablet-Table").removeClass("border-color")
						$("#"+machineCodeObj[barcode]).addClass("border-color")
						$(".border-color").css("border-color","yellow");
						inputMode = "refresh"
						refreshData.machineCode = barcode;
					}			
				}
			}
		}else if(initial == "N"){
			if(inputMode == "save"){
				//상자코드가 찍힐 것
				//해당설비로 해당 상자가 등록되면 된다
				crateLotUpdate($("#selectedMachine").val(), barcode);
				$(".tablet-Table").removeClass("border-color");
			}else if(inputMode == "refresh"){
				refreshData.crateCode = barcode;
			}
		}
		$(this).val("");
	}
});

$("#barcodeInput").keyup(function(){
	$(this).val($(this).val().toUpperCase());
	$("#barcodeInput-mirror").val($(this).val());
})

function crateLotUpdate(machineCode, CrateCode){
	$.ajax({
		method : "get",
		url : "maskInputRest/crateLotUpdate",
		data: {CL_MachineCode : machineCode, CL_CrateCode : CrateCode},
		success : function() {
			Tabulator.prototype.findTable("#"+machineCodeObj[machineCode])[0].replaceData();
		}
	});
}

function crateLotRefresh(machineCode, CrateCode){
	$.ajax({
		method : "get",
		url : "maskInputRest/crateLotRefresh",
		data: {CL_MachineCode : machineCode, CL_CrateCode : CrateCode},
		success : function() {
			Tabulator.prototype.findTable("#"+machineCodeObj[machineCode])[0].replaceData();
		}
	});
}

window.onload = function(){
	
	setInterval(function(){
		for(var key in machineCodeObj){
			Tabulator.prototype.findTable("#"+machineCodeObj[key])[0].replaceData();	
		}
	},5000);
}