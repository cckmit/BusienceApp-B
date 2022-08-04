var machineCodeObj = new Object();
var itemCodeObj = new Object();

function tableSetting(value){
	var tableSetting = {
		height: "100%",
		headerVisible: false,
		sortOrderReverse:true,
		layout:"fitColumns",
		ajaxURL:"maskInputRest/crateLotListSelect",
		ajaxParams: {machineCode : value.equip_WorkOrder_Code, itemCode : value.equip_WorkOrder_Old_ItemCode},
	    ajaxConfig:"get",
	    ajaxContentType:"json",
		ajaxLoader:false,
		columns:[
			{ title: "코드", field: "cl_CrateCode", hozAlign:"center", widthGrow : 3},
			{ title: "수량", field: "cl_ProductionQty", hozAlign:"center",
				formatter:"money", formatterParams: {precision: false}, widthGrow : 2}
		]
	}
	return tableSetting
}

$.ajax({
	method : "get",
	url : "maskInputRest/maskInputSelect",
	success : function(data) {
		//설비 8개만 표현
		machineCodeObj = new Object();
		for(let i=0;i<8;i++){
			itemCodeObj[data[i].equip_WorkOrder_Code] = data[i].equip_WorkOrder_Old_ItemCode
			machineCodeObj[data[i].equip_WorkOrder_Code] = "itemTable"+(i)
			let machineCode = data[i].equip_WorkOrder_Code != null ? data[i].equip_WorkOrder_Code : "";
			let machineName = data[i].equip_WorkOrder_Name != null ? data[i].equip_WorkOrder_Name : "";
			let itemCode = data[i].equip_WorkOrder_ItemCode != null ? data[i].equip_WorkOrder_ItemCode : "";
			let itemName = data[i].equip_WorkOrder_ItemName != null ? data[i].equip_WorkOrder_ItemName.substr(0,data[i].equip_WorkOrder_ItemName.length-3) : "작업지시 없음";
			let stnd_1 = data[i].equip_WorkOrder_INFO_STND_1 != null ? data[i].equip_WorkOrder_INFO_STND_1 : "";
			let stnd_2 = data[i].equip_WorkOrder_INFO_STND_2 != null ? data[i].equip_WorkOrder_INFO_STND_2 : "";
			let material_Name = data[i].equip_WorkOrder_Material_Name != null ? data[i].equip_WorkOrder_Material_Name : "";
			let clsfc_1 = data[i].equip_WorkOrder_Item_CLSFC_1_Name != null ? data[i].equip_WorkOrder_Item_CLSFC_1_Name : "";
			let clsfc_2 = data[i].equip_WorkOrder_Item_CLSFC_2_Name != null ? data[i].equip_WorkOrder_Item_CLSFC_2_Name : "";
			
			$("#multiTableAdd").append(
				'<div class="machine-module" id="module-'+machineCode+'">'
						+'<div class="m-header m-font">'
							+'<p><strong>'+machineName+'</strong></p>'
							+'<p><strong>'+itemName+'</strong></p>'
						+'</div>'
						+'<div class="m-main m-font">'
							+'<p><strong>'+itemCode+'</strong></p>'
							+'<p><strong>'+stnd_1+'</strong></p>'
							+'<p><strong>'+stnd_2+'</strong></p>'
							+'<p><strong>'+material_Name+'</strong></p>'
							+'<p><strong>'+clsfc_1+'</strong></p>'
							+'<p><strong>'+clsfc_2+'</strong></p>'
						+'</div>'
					+'<div class="table-container">'
						+'<div id="itemTable'+(i)+'" class="tablet-Table"></div>'
					+'</div>'
				+'</div>'
			)
			new Tabulator("#itemTable"+(i), tableSetting(data[i]));
		}
	}
});

$(document).keydown(function(){
	if(!$("#barcodeInput").is(":focus")){
		$("#barcodeInput").focus();	
	}
});

$("#barcodeInput").change(function(){
	
	var barcode = inko.ko2en($(this).val()).toUpperCase();
	
	$("#barcodeInput").val("");
	var initial = barcode.substr(0,1);
	//코드가 무엇인지 확인
	if(initial == "M"){
		//포장설비코드만 걸러냄
		let result = Object.keys(machineCodeObj).some(x => {
			return x == barcode
		})
		if(result){
			$("#selectedMachine").val(barcode);
			$(".machine-module").removeClass("selected-module");
			$(".machine-module").removeClass("selected-module-Y");
			$("#module-"+barcode).addClass("selected-module");
		}
	//머신코드에 C가 붙으면 수정모드
	}else if(initial == "C"){
		$("#selectedMachine").val(barcode);
		$(".machine-module").removeClass("selected-module");
		$(".machine-module").removeClass("selected-module-Y");
		$("#module-"+barcode.substr(1,barcode.length)).addClass("selected-module-Y");
	}else if(initial == "N"){
		var machine_initial = $("#selectedMachine").val().substr(0,1);
		
		if(machine_initial != "C"){
			$.when(CrateStatusCheck(barcode))
			.then(function(data){
				//아이템이 설비지정아이템과 맞으면 등록이 되고 맞지않으면 오류 뱉음	
				if(data instanceof Object){
					if(data.c_ItemCode == itemCodeObj[$("#selectedMachine").val()]){
						crateLotUpdate($("#selectedMachine").val(), data.c_CrateCode);
						inputMessage(barcode+" 투입")
					}else{
						toastr.error("설비와 품목이 맞지 않습니다.")
					}
				}else{
					toastr.error("잘못된 박스 입니다.")
				}				
			})
		}else{
			var machineCode = $("#selectedMachine").val().substr(1,barcode.length);
			maskInputRollback(machineCode, barcode)
			inputMessage(barcode+" 수정")
		}
	}
});
$("#barcodeInput").keyup(function(e){
	var value = $(this).val().toUpperCase();
	$(this).val(value)
	$("#barcodeInput-mirror").val(value);
})

function CrateStatusCheck(value){
	var ajaxResult = $.ajax({
		method : "get",
		url : "maskProductionRest/CrateStatusCheck",
		data : {itemCode : itemCodeObj[$("#selectedMachine").val()], crateCode : value, condition : 2},
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
			Tabulator.prototype.findTable("#"+machineCodeObj[machineCode])[0].replaceData();
		}
	});
}

function maskInputRollback(machineCode, crateCode){
	$.ajax({
		method : "post",
		url : "maskInputRest/maskInputRollback",
		data: {CL_CrateCode : crateCode},
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

function inputMessage(value){
	$(".message").text(value)
	$(".message").removeClass("none")

	setTimeout(function(){
		if(value.substr(0, 4) == $(".message").text().substr(0, 4)){
			$(".message").addClass("none")	
		}		
	}, 4000)
}

window.onload = function(){	
	setInterval(function(){
		for(var key in machineCodeObj){
			Tabulator.prototype.findTable("#"+machineCodeObj[key])[0].replaceData();	
		}
	},60000);
	
	setInterval(function(){
		location.reload();
	},1800000);
}