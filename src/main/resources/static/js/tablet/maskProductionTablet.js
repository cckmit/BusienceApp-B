$("#machineName").click(function(){
	location.reload();
})

var itemTable = new Tabulator("#itemTable", {
	layoutColumnsOnNewData : true,
	height: "100%",
	ajaxURL:"/itemManageRest/itemCodeInfo",
	ajaxParams: {itemCode : $("#itemCode").val()},
    ajaxConfig:"get",
    ajaxContentType:"json",
	ajaxResponse:function(url, params, response){
		console.log(response)
		return [response]
    },
	columns:[
		{title:"현재 생산중인 제품", headerHozAlign:"center",
			columns: [
				{ title: "제품코드", field: "product_ITEM_CODE", headerHozAlign: "center"},
				{ title: "제품이름", field: "product_ITEM_NAME", headerHozAlign: "center"},
				{ title: "규격1", field: "product_INFO_STND_1", headerHozAlign: "center"},
				{ title: "규격2", field: "product_INFO_STND_2", headerHozAlign: "center"},
				{ title: "재질", field: "product_MATERIAL_NAME", headerHozAlign: "center"},
				{ title: "분류1", field: "product_ITEM_CLSFC_1_NAME", headerHozAlign: "center"},
				{ title: "분류2", field: "product_ITEM_CLSFC_2_NAME", headerHozAlign: "center"}				
			]
		}
	]
});

$("#fullScreenBtn").click(function(){
	toggleFullScreen();
})

function toggleFullScreen() {
  if (!document.fullscreenElement) {
    document.documentElement.requestFullscreen()
	itemTable.replaceData();
	itemTable.redraw();
  } else {
    if (document.exitFullscreen) {
      document.exitFullscreen()
    }
  }
}

function workOrderSet(){
	//상자가 있는지 체크
    $.when(CrateSelect($("#machineCode").val()))
    .then(function(data){
        //그값에 따라 원자재 검색
        rawSelect(data.c_Production_LotNo, $("#itemCode").val());
    })
}

$(document).keydown(function(){
	if(!$("#barcodeInput").is(":focus")){
		$("#barcodeInput").focus();	
	}
});

$("#barcodeInput").keyup(function(){
	$(this).val($(this).val().toUpperCase());
})

$("#barcodeInput").change(function(){
	//바코드를 읽었을때
	//이니셜값 비교하여 맞는 칸에 값을 넣는다.
	var barcode = inko.ko2en($(this).val()).toUpperCase();
	$("#barcodeInput").val("");
	var initial = barcode.substring(0,1);
	
	if(initial == 'R'){
		let itemCode = barcode.substr(7,6)
		//상자가 있으면 저장하고 없으면 저장하지 않는다.
		//코드가 안맞을경우 안맞는다고 알림
		let check = false;
		for(let i=0, len = $(".main-c .item").length-1;i<len;i++){
			if($(".main-c .item:nth-of-type("+(i+2)+") .LotNo_Code").val() == itemCode){
				check = true
				if($("#crate-LotNo").val().length > 0){
					console.log("실행?")
					lotInput($("#crate-LotNo").val(), barcode, itemCode, 0)
				}else{
					$(".main-c .item:nth-of-type("+(i+2)+") .LotNo").val(barcode)
					$(".main-c .item:nth-of-type("+(i+2)+") .removeBtn").removeClass("hiddenBtn");
				}
			}
		}
		if(!check){
			toastr.error("제품과 맞지않는 원자재 입니다.")
		}
		$("#barcodeInput").val("");
	}else if(initial == 'N'){
		$.when(crateChange($("#crateCode").val(), barcode))
		.then(function(data){
			if(data instanceof Object){
				if(data.c_MachineCode == $("#machineCode").val() && data.c_Condition == '1'){
					if($("#crateCode").val() == data.c_CrateCode){
						toastr.success("동일한 상자코드 "+data.c_CrateCode+" 입니다.")
					}else{
						$(".removeBtn").addClass("hiddenBtn");
						$("#crate-LotNo").val(data.c_Production_LotNo);
						$("#crateCode").val(data.c_CrateCode);
						$("#crate-Qty").val(data.c_Qty);
						rawSelect(data.c_Production_LotNo, data.c_ItemCode);
						itemTable.setData("/itemManageRest/itemCodeInfo",{itemCode : data.c_ItemCode})	
						toastr.success("상자코드 "+data.c_CrateCode+"로 교체되었습니다.")						
					}				
				}else {
					toastr.error("상자코드 "+data.c_CrateCode+"는 상태값이 "+data.c_Condition+" ("+data.c_Condition_Name+") 입니다.")
				}				
			}else{
				toastr.error("잘못된 코드를 입력하였습니다.")
			}
		})
	}
});

function lotInput(production_LotNo, material_LotNo, material_ItemCode, check){
	//check = 0이면 투입, 1이면 롤백
	$.when(rawMaterialChange(production_LotNo, material_LotNo, material_ItemCode, check))
	.then(function(result){
		if(check){
			for(let i=0, len = $(".main-c .item").length-1;i<len;i++){
				if($(".main-c .item:nth-of-type("+(i+2)+") .LotNo_Code").val() == material_ItemCode){
					$(".main-c .item:nth-of-type("+(i+2)+") .LotNo").val(result)
					$(".main-c .item:nth-of-type("+(i+2)+") .removeBtn").addClass("hiddenBtn");	
				}		
			}
		}else{
			if(result != ''){
				for(let i=0, len = $(".main-c .item").length-1;i<len;i++){
					if($(".main-c .item:nth-of-type("+(i+2)+") .LotNo_Code").val() == material_ItemCode){
						$(".main-c .item:nth-of-type("+(i+2)+") .LotNo").val(result)
						$(".main-c .item:nth-of-type("+(i+2)+") .removeBtn").removeClass("hiddenBtn");
					}		
				}
			}else{
				toastr.error("생산창고에 존재하지 않습니다.")
			}
		}
	})
}

function rawSelect(lotNo, itemCode){
	var ajaxResult = $.ajax({
		method : "get",
		url : "/tablet/maskProductionRest/rawMaterialBOMList",
		data : {lotNo : lotNo, itemCode : itemCode},
		success : function(data) {	
			for(let i=0;i<data.length;i++){
				$(".main-c .item:nth-of-type("+(i+2)+") .LotNo_Code").val(data[i].bom_ItemCode);
				$(".main-c .item:nth-of-type("+(i+2)+") .LotNo_Name").val(data[i].bom_ItemName);
				$(".main-c .item:nth-of-type("+(i+2)+") .LotNo").val(data[i].bom_Material_LotNo);
			}
		}
	})
	return ajaxResult;
}

function rawLotList(){
	const rawLotList = new Array();
	for(let i=0, len = $(".main-c .item").length-1;i<len;i++){
		if($(".main-c .item:nth-of-type("+(i+2)+") .LotNo").val().length > 0){
			rawLotList.push({
				material_ItemCode : $(".main-c .item:nth-of-type("+(i+2)+") .LotNo_Code").val(),
				material_LotNo : $(".main-c .item:nth-of-type("+(i+2)+") .LotNo").val()
			})
		}		
	}
	return rawLotList;
}

function crateChange(beforeCrate, afterCrate){
	var ajaxResult = $.ajax({
		method : "post",
		url : "/tablet/maskProductionRest/crateChange",
		data : {changeInfo : JSON.stringify({
								c_Before_CrateCode : beforeCrate,
								c_CrateCode : afterCrate,
								c_Qty : $("#crate-Qty").val(),
								c_MachineCode : $("#machineCode").val()
							}),
				rawLotList : JSON.stringify(rawLotList())},
		beforeSend: function (xhr) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           xhr.setRequestHeader(header, token);
		}
	})
	return ajaxResult;
}

function CrateSelect(value){
	var beforeQty = $("#crate-Qty").val();
	var ajaxResult = $.ajax({
		method : "get",
		url : "/tablet/maskProductionRest/crateSelectByMachine",
		data : {machineCode : value, condition : 1},
		success : function(data) {
			if(data.length > 0){
				if(beforeQty != data[0].c_Qty){
					$(".removeBtn").addClass("hiddenBtn");
				}
				$("#crate-LotNo").val(data[0].c_Production_LotNo);
				$("#crateCode").val(data[0].c_CrateCode);
				$("#crate-Qty").val(data[0].c_Qty);
				
				production_Alarm(data[0].c_Qty)
			}
		}
	})
	return ajaxResult;
}

$("#completeBtn").click(function(){
	if(confirm("완료버튼을 누르면 현재 작업이 완료 되고 화면이 초기화 됩니다. 하시겠습니까?")){
		workComplete($("#crateCode").val())
	}
})

function workComplete(crateCode){
	var condition = 0;
	if($("#crate-Qty").val()>0){
		condition = 2
	}
	var ajaxResult = $.ajax({
		method : "post",
		url : "/tablet/maskProductionRest/workComplete",
		data : { c_CrateCode : crateCode, c_Condition : condition, C_Production_LotNo : $("#crate-LotNo").val()},
		beforeSend: function (xhr) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           xhr.setRequestHeader(header, token);
		},
		success: function (){
			location.reload();
		}
	});
	return ajaxResult;
}

function rawMaterialChange(production_LotNo, material_LotNo, material_ItemCode, check){
	console.log(production_LotNo)
	var ajaxresult = $.ajax({
		method : "post",
		url : "/tablet/maskProductionRest/rawMaterialChange",
		data : {
			Production_LotNo : production_LotNo,
			Material_LotNo : material_LotNo,
			Material_ItemCode : material_ItemCode,
			check : check
		},
		beforeSend: function (xhr) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           xhr.setRequestHeader(header, token);
		}
	});	
	return ajaxresult
}

$(".removeBtn").click(function(){
	lotInput($("#crate-LotNo").val(), $(this).siblings(".LotNo").val(), $(this).siblings(".LotNo_Code").val(), 1);
})

function production_Alarm(value){
	var ajaxresult = $.ajax({
		method : "get",
		url : "/tablet/dtlTrueSelect",
		data : {"NEW_TBL_CODE" : 31},
		success: function (result){
			var resultValue
			for(let i=0;i<result.length;i++){
				if(result[i].child_TBL_NUM == "2"){
					resultValue = result[i].child_TBL_RMARK	
				}
			}
			if(value >= resultValue){
				$("#crate-Qty").css('background','white');
				$("#crate-Qty").addClass("red_light");
				new Audio('/audio/Alarm_4sec.mp3').play();
			}else{
				$("#crate-Qty").css('background', null);
				$("#crate-Qty").removeClass("red_light");
			}
		}
	});	
	return ajaxresult
}

window.onload = function(){
	workOrderSet();
	setInterval(function(){
		CrateSelect($("#machineCode").val());
	},5000);
	setInterval(function(){
		location.reload();
	},1800000);
}