var machineCodeObj = new Object();

function tableSetting(value){
	var tableSetting = {
		height: "100%",
		ajaxURL:"maskInputRest/crateLotListSelect",
		ajaxParams: {machineCode : value.equipment_INFO_CODE},
	    ajaxConfig:"get",
	    ajaxContentType:"json",
		ajaxLoader:false,
		columns:[
			{title: value.equipment_INFO_NAME, field: "machineCode", headerHozAlign:"center",
				columns: [	
					{ title: "Lot번호", field: "cl_LotNo", headerHozAlign: "center", headerSort:false},
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
		var barcode = $(this).val()
		
		let result = Object.keys(machineCodeObj).some(x => {
			return x == barcode
		})
		
		if(result){
			$("#selectedMachine").val(barcode);
			$(".tablet-Table").removeClass("redTest")
			$("#"+machineCodeObj[barcode]).addClass("redTest")
		}else if($("#selectedMachine").val().length >0){
			//상자코드가 찍힐 것
			//해당설비로 해당 상자가 등록되면 된다
			crateLotUpdate($("#selectedMachine").val(), barcode)
		}
		$(this).val("");
	}
});

$("#barcodeInput").keyup(function(){
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

window.onload = function(){
	
	setInterval(function(){
		for(var key in machineCodeObj){
			Tabulator.prototype.findTable("#"+machineCodeObj[key])[0].replaceData();	
		}
	},5000);
}