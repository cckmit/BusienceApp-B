var machineCodeObj = new Object();
var itemCodeObj = new Object();
var inputMode = '';

function tableSetting(value){
	var tableSetting = {
		height: "100%",
		headerVisible: false,
		layout:"fitColumns",
		ajaxURL:"maskInputRest/crateLotListSelect",
		ajaxParams: {machineCode : value.equip_WorkOrder_Code},
	    ajaxConfig:"get",
	    ajaxContentType:"json",
		ajaxLoader:false,
		columns:[
			{ title: "코드", field: "cl_CrateCode", headerHozAlign: "center", headerSort:false, widthGrow : 6},
			{ title: "수량", field: "cl_ProductionQty", headerHozAlign: "center", hozAlign:"right", headerSort:false,
				formatter:"money", formatterParams: {precision: false}, widthGrow : 5}
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
			if(data[i].equip_WorkOrder_Equipment_Type == '325'){
				itemCodeObj[data[i].equip_WorkOrder_Code] = data[i].equip_WorkOrder_Old_ItemCode
				machineCodeObj[data[i].equip_WorkOrder_Code] = "itemTable"+(i)
				$("#multiTableAdd").append(
					'<div class="machine-module" id="module-'+data[i].equip_workOrder_Code+'">'
							+'<div class="m-header m-font">'
								+'<p><strong>'+data[i].equip_WorkOrder_Name+'</strong></p>'
								+'<p><strong>'+data[i].equip_WorkOrder_ItemName.substr(0,data[i].equip_WorkOrder_ItemName.length-3)+'</strong></p>'
							+'</div>'
							+'<div class="m-main m-font">'
								+'<p><strong>'+data[i].equip_WorkOrder_ItemCode+'</strong></p>'
								+'<p><strong>'+data[i].equip_WorkOrder_INFO_STND_1+'</strong></p>'
								+'<p><strong>'+data[i].equip_WorkOrder_INFO_STND_2+'</strong></p>'
								+'<p><strong>'+data[i].equip_WorkOrder_Material_Name+'</strong></p>'
								+'<p><strong>'+data[i].equip_WorkOrder_Item_CLSFC_1_Name+'</strong></p>'
								+'<p><strong>'+data[i].equip_WorkOrder_Item_CLSFC_2_Name+'</strong></p>'
							+'</div>'
						+'<div class="table-container">'
							+'<div id="itemTable'+(i)+'" class="tablet-Table"></div>'
						+'</div>'
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
				$(".machine-module").removeClass("selected-module");
				$("#module-"+barcode).addClass("selected-module");
			}
		}else if(initial == "C"){
			barcode = barcode.substr(1,-1)
			//console.log("수정모드")
			$("#selectedMachine").val(barcode);
			$(".machine-module").removeClass("selected-module");
			$("#module-"+barcode).addClass("selected-module");
		}else if(initial == "N"){
			$.when(CrateStatusCheck(barcode)).
			then(function(data){
				//아이템이 설비지정아이템과 맞으면 등록이 되고 맞지않으면 오류 뱉음	
				if(data instanceof Object){
					//console.log(itemCodeObj[$("#selectedMachine").val()]);
					//console.log(data.c_ItemCode);
					if(data.c_ItemCode == itemCodeObj[$("#selectedMachine").val()]){
						crateLotUpdate($("#selectedMachine").val(), data.c_CrateCode);
						//console.log("통과");
					}else{
						console.log("설비와 품목이 맞지 않습니다.")
					}
				}else{
					console.log("잘못된 박스입니다.")
				}
				
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
		data : {itemCode : itemCodeObj[$("#selectedMachine").val()], crateCode : value, condition : 2},
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
			Tabulator.prototype.findTable("#"+machineCodeObj[key])[0].replaceData();	
		}
	},60000);
}