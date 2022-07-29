var LotList = new Array();
var tempRawMaterial = new Object();

$("#machineName").click(function(){
	location.reload();
})

var itemTable = new Tabulator("#itemTable", {
	layoutColumnsOnNewData : true,
	height: "100%",
	ajaxURL:"/itemManageRest/itemCodeInfo",
	ajaxParams: {itemCode : nowItemCode($("#machineCode").val())},
    ajaxConfig:"get",
    ajaxContentType:"json",
	ajaxResponse:function(url, params, response){
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
		
        //return rawMaterialUpdate(data.c_Production_LotNo, $("#itemCode").val());
        //그값에 따라 원자재 검색
        return RawSelect(data.c_Production_LotNo, $("#itemCode").val());
    }).then(function(data1){
		tempRawMaterial
        //없으면 봄검색
		if(data1.length == 0){			
			//BOM_Check(nowItemCode($("#machineCode").val()))
		}
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
	console.log(tempRawMaterial);
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
				lotInput($("#crate-LotNo").val(), barcode, itemCode)
			}
		}
		if(!check){
			toastr.error("제품과 맞지않는 원자재 입니다.")
		}
		$("#barcodeInput").val("");
	}else if(initial == 'N'){
		crateChange($("#crateCode").val(), barcode)
		/*
		$.when(CrateStatusCheck(barcode))
		.then(function(data){
			if(data instanceof Object){
				// 상자를 바꿀때 설비- 아이템을 확인하여 그정보로 저장
				//기존 생산랏 상태변경 새로운 생산랏 생성 
				return CrateSave($("#crateCode").val(), data.c_CrateCode)
			}
		})
		.then(function(data){
			$("#crateCode").val(data.c_CrateCode);
			$("#crate-LotNo").val(data.c_Production_LotNo);
			//return rawMaterialUpdate(data.c_Production_LotNo, $("#itemCode").val())
			return BOM_Check(data.c_ItemCode)
		})
		.then(function(data){
			if(inputCheck($("#crate-LotNo").val())){
				rawMaterialSave($("#crate-LotNo").val());
			}
		})*/
	}
});
/*
function barcodeBranch(barcode){
	var initial = barcode.substring(0,1);
	
	if(initial == 'R'){
		//상자가 있으면 저장하고 없으면 저장하지 않는다.
		//코드가 안맞을경우 안맞는다고 알림
		rawMaterialLotInput(barcode);
		
		return {cl_LotNo : $("#crate-LotNo").val()}; 
	}else if(initial == 'N'){
		$.when(CrateStatusCheck(values))
		.then(function(data){
			//있다 or 없다.
			console.log(data)
			
			// 상자를 바꿀때 설비- 아이템을 확인하여 그정보로 저장
			$("#crateCode").val(barcode);
			return CrateSave($("#crate-LotNo").val(), barcode)
		})
		
		//아이템이 바뀌는데 원자재가 일부만 바뀌어 기존 원자재를 일부 사용할때 문제가 된다.
	}
}*/

function lotInput(production_LotNo, material_LotNo, material_ItemCode){
	var ajaxResult = $.ajax({
		method : "post",
		url : "/tablet/maskProductionRest/lotInput",
		data : {
			Production_LotNo : production_LotNo,
			Material_LotNo : material_LotNo,
			Material_ItemCode : material_ItemCode
		},
		beforeSend: function (xhr) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           xhr.setRequestHeader(header, token);
		},
		success : function(result) {
			if(result.material_ItemCode != null){
				for(let i=0, len = $(".main-c .item").length-1;i<len;i++){
					if($(".main-c .item:nth-of-type("+(i+2)+") .LotNo_Code").val() == result.material_ItemCode){
						$(".main-c .item:nth-of-type("+(i+2)+") .LotNo").val(result.material_LotNo)
						$(".main-c .item:nth-of-type("+(i+2)+") .removeBtn").removeClass("hiddenBtn");
					}		
				}
			}else{
				toastr.error("생산창고에 존재하지 않습니다.")
			}			
		}
	})
	return ajaxResult;
	/*
	if(rawMaterialChange(value, itemCode, 0)){
		
		var result = LotList.every(function(currentValue, i){
			if($(".main-c .item:nth-of-type("+(i+2)+") .LotNo_Code").val() == itemCode){
				$(".main-c .item:nth-of-type("+(i+2)+") .LotNo").val(value)
			}
		})
		if(result){
			toastr.error("제품과 맞지않는 원자재 입니다.")
		}
	}else{
		toastr.error("생산창고에 수량이 없습니다.")
	}*/
}

//원자재 리스트 입력
/*
function rawMaterialLotInput(value){
	//lotNo와 기존 원자재와 비교 한 뒤에 맞는게 없으면 다음 작업의 봄과 비교한다.
	// 없으면 에러발생, 있으면 작업종료후 원자재 등록
	var itemCode = value.substr(7,6);
	var result = LotList.every(function(currentValue, i){
		//품목코드와 동일한 값을 찾은 후, 랏번호가 같지 않다면 변경됨
		if(currentValue.rms_ItemCode == itemCode){
			if(currentValue.rms_LotNo != value){
				if(rawMaterialChange(value, itemCode, 0)){
					//반복문 실행하여 LotList 에 정보만 넣고
					//LotList의 정보를 화면에 보여주는 함수 따로 만들면 좋을듯
					$(".main-c .item:nth-of-type("+(i+2)+") .LotNo").val(value);
					currentValue.rms_LotNo = value
					
					$(".main-c .item:nth-of-type("+(i+2)+") .removeBtn").removeClass("hiddenBtn");
				}				
			}
			return false;
		}
		return true;
	})
	if(result){
		toastr.error("제품과 맞지않는 원자재 입니다.")
	}
	return 
}*/

/*function rawMaterialUpdate(lotNo, itemCode){
	var ajaxResult = $.ajax({
		method : "get",
		url : "/tablet/maskProductionRest/RawMaterialBOMList",
        data : {lotNo : lotNo, itemCode : itemCode},
		success : function(data) {			
			LotList = new Array();
			
			console.log(data);
			for(let i=0;i<data.length;i++){
				LotList.push({
					rms_LotNo : data[i].bom_Material_LotNo,
					rms_ItemCode : data[i].bom_ItemCode
				})
				$(".main-c .item:nth-of-type("+(i+2)+") .LotNo_Code").val(data[i].bom_ItemCode);
				$(".main-c .item:nth-of-type("+(i+2)+") .LotNo_Name").val(data[i].bom_ItemName);
				$(".main-c .item:nth-of-type("+(i+2)+") .LotNo").val(data[i].bom_Material_LotNo);
			}
			//가져온 bom과 lotlist랑 비교해서 변동이 있는지 확인
			let z=0;
			for(let j=0;j<data.length;j++){
				for(let k=0;k<LotList.length;k++){
					if(data[i].bom_ItemCode = LotList.rms_ItemCode){
						z++
					}
				}
			}
			if(z != data.length){
				//변동이 있으면 초기화하여 뿌려줌
				LotList = new Array();
				//BOM을 가져와서 그수만큼 리스트에 담는다			
				for(let i=0;i<data.length;i++){
					
					LotList.push({
						rms_LotNo : null,
						rms_ItemCode : data[i].bom_ItemCode
					})
					
					$(".main-c .item:nth-of-type("+(i+2)+") .LotNo_Code").val(data[i].bom_ItemCode);
					$(".main-c .item:nth-of-type("+(i+2)+") .LotNo_Name").val(data[i].bom_ItemName);
				}
			}
			//변동이 없으면 아무것도 안해도됨	
		}
	})
	return ajaxResult;
}*/
/*
function inputCheck(LotNo){
	var result = LotList.every(x => {
		return x.rms_LotNo != null
	})
	if(LotNo.length > 0 && result){
		return true;
	}
	return false;
}*/
/*
function rawMaterialSave(value){
	var masterData = {
			production_LotNo : value,
			qty : $("#crate-Qty").val()
		}
		
	$.ajax({
		method : "post",
		url : "/tablet/maskProductionRest/rawMaterialSave",
		data: {masterData : JSON.stringify(masterData), subData: JSON.stringify(LotList)},
		beforeSend: function (xhr) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           xhr.setRequestHeader(header, token);
		},
		success: function (data){
			toastr.success("원자재가 투입되었습니다.")
		}
	});
}*/
/*
function BOM_Check(value){
	var result = LotList.every(x => {
		return x.rms_LotNo == null
	})
	if(result){
		var ajaxResult = $.ajax({
			method : "get",
			url : "/tablet/maskProductionRest/BOMBOMList",
			data : {itemCode : value},
			success : function(data) {
				//가져온 bom과 lotlist랑 비교해서 변동이 있는지 확인
				let z=0;
				for(let j=0;j<data.length;j++){
					for(let k=0;k<LotList.length;k++){
						if(data[i].bom_ItemCode = LotList.rms_ItemCode){
							z++
						}
					}
				}
				if(z != data.length){
					//변동이 있으면 초기화하여 뿌려줌
					LotList = new Array();
					//BOM을 가져와서 그수만큼 리스트에 담는다			
					for(let i=0;i<data.length;i++){
						
						LotList.push({
							rms_LotNo : null,
							rms_ItemCode : data[i].bom_ItemCode
						})
						
						$(".main-c .item:nth-of-type("+(i+2)+") .LotNo_Code").val(data[i].bom_ItemCode);
						$(".main-c .item:nth-of-type("+(i+2)+") .LotNo_Name").val(data[i].bom_ItemName);
					}
				}
				//변동이 없으면 아무것도 안해도됨				
			}
		});
	}	
	return ajaxResult
}*/

function RawSelect(lotNo, itemCode){
	var ajaxResult = $.ajax({
		method : "get",
		url : "/tablet/maskProductionRest/rawMaterialBOMList",
		data : {lotNo : lotNo, itemCode : itemCode},
		success : function(data) {
			console.log(data);
			//LotList = new Array();
			
			for(let i=0;i<data.length;i++){
				/*
				LotList.push({
					rms_LotNo : data[i].material_LotNo,
					rms_ItemCode : data[i].material_ItemCode
				})*/
				tempRawMaterial[data[i].bom_ItemCode] = data[i].bom_Material_LotNo;
				$(".main-c .item:nth-of-type("+(i+2)+") .LotNo_Code").val(data[i].bom_ItemCode);
				$(".main-c .item:nth-of-type("+(i+2)+") .LotNo_Name").val(data[i].bom_ItemName);
				$(".main-c .item:nth-of-type("+(i+2)+") .LotNo").val(data[i].bom_Material_LotNo);
			}
		}
	})
	return ajaxResult;
}

/*function CrateStatusCheck(value){
	var ajaxResult = $.ajax({
		method : "get",
		url : "/tablet/maskProductionRest/CrateStatusCheck",
		data : {crateCode : value, condition : 0},
		success : function() {
		}
	})
	return ajaxResult;
}*/
function rawLotList(){
	const rawLotList = new Array();
	for(let i=0, len = $(".main-c .item").length-1;i<len;i++){
		if($(".main-c .item:nth-of-type("+(i+2)+") .LotNo_Code").val().length > 0){
			rawLotList[i] = {
				material_ItemCode : $(".main-c .item:nth-of-type("+(i+2)+") .LotNo_Code").val(),
				material_LotNo : $(".main-c .item:nth-of-type("+(i+2)+") .LotNo").val()
			}
		}		
	}
	return rawLotList;
}

function crateChange(beforeCrate, afterCrate){
	var beforeQty = $("#crate-Qty").val();
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
		},
		success : function(data) {
			if(data instanceof Object){
				if(beforeQty != data.c_Qty){
					$(".removeBtn").addClass("hiddenBtn");
				}
				$("#crate-LotNo").val(data.c_Production_LotNo);
				$("#crateCode").val(data.c_CrateCode);
				$("#crate-Qty").val(data.c_Qty);
				toastr.success("상자코드 "+data.c_CrateCode+"로 교체되었습니다.")
			}
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
			if(data instanceof Object){
				if(beforeQty != data.c_Qty){
					$(".removeBtn").addClass("hiddenBtn");
				}
				$("#crate-LotNo").val(data.c_Production_LotNo);
				$("#crateCode").val(data.c_CrateCode);
				$("#crate-Qty").val(data.c_Qty);
			}
		}
	})
	return ajaxResult;
}

/*function CrateSave(before, after){
	var ajaxResult = $.ajax({
		method : "post",
		url : "/tablet/maskProductionRest/crateSave",
		data : {
			C_Before_CrateCode : before,
			C_CrateCode : after,
			C_Qty : $("#crate-Qty").val(),
			C_ItemCode : nowItemCode($("#machineCode").val()),
			C_MachineCode : $("#machineCode").val()
		},
		beforeSend: function (xhr) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           xhr.setRequestHeader(header, token);
		},
		success: function (data){
			if(data instanceof Object){
				$("#crate-LotNo").val(data.c_LotNo);
				$("#crateCode").val(data.c_CrateCode);
				$("#crate-Qty").val(data.c_Qty);
				toastr.success("상자코드 "+data.c_CrateCode+"로 교체되었습니다.")
			}
		}
	});
	return ajaxResult;
}*/
/*
function CrateProductionSave(lotNo, production_ID){
	var ajaxResult = $.ajax({
		method : "post",
		url : "/tablet/maskProductionRest/crateProductionSave",
		data : {
			CP_LotNo : lotNo,
			CP_Production_ID : production_ID
		},
		beforeSend: function (xhr) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           xhr.setRequestHeader(header, token);
		}
	});
	return ajaxResult;
}
*/
$("#completeBtn").click(function(){
	if(confirm("완료버튼을 누르면 현재 작업이 완료 되고 화면이 초기화 됩니다. 하시겠습니까?")){
		crateUpdate($("#crateCode").val())
	}
})

function crateUpdate(crateCode){
	var condition = 0;
	if($("#crate-Qty").val()>0){
		condition = 2
	}
	var ajaxResult = $.ajax({
		method : "post",
		url : "/crateRest/crateUpdate",
		data : { c_CrateCode : crateCode, c_Condition : condition},
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

function nowItemCode(machineCode){
	var result;
	
	$.ajax({
		method : "get",
		url : "/equipWorkOrderRest/equipOrderList",
		data : {machineCode : machineCode},
		async: false,
		beforeSend: function (xhr) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           xhr.setRequestHeader(header, token);
		},
		success: function (data){
			result = data[0].equip_WorkOrder_ItemCode;
		}
	});
	
	return result
}

function rawMaterialChange(production_LotNo, material_LotNo, material_ItemCode, check){
	var ajaxresult = $.ajax({
		method : "post",
		url : "/tablet/maskProductionRest/rawMaterialChange",
		data : {
			Production_LotNo : production_LotNo,
			Material_LotNo : material_LotNo,
			Material_ItemCode : material_ItemCode,
			check : check
		},
		async: false,
		beforeSend: function (xhr) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           xhr.setRequestHeader(header, token);
		},
		success: function (data){
			console.log(data)
		}
	});
	
	return ajaxresult
}

$(".removeBtn").click(function(){
	rawMaterialChange($("#crate-LotNo").val() ,$(this).siblings(".LotNo").val(), $(this).siblings(".LotNo_Code").val(), 1)
	console.log($(this).siblings(".LotNo").val());
	console.log(tempRawMaterial[$(this).siblings(".LotNo_Code").val()]);
	console.log(tempRawMaterial)
	$(".removeBtn").addClass("hiddenBtn");
	$(this).siblings(".LotNo").val(tempRawMaterial[$(this).siblings(".LotNo_Code").val()])
	
	/*
	for(var i=0;i<LotList.length;i++){
		if(LotList[i].rms_ItemCode = $(this).siblings(".LotNo_Code").val()){
			
		}
	}
	$(this).siblings(".LotNo").val("")
	$($(this)).addClass("hiddenBtn");*/
})

window.onload = function(){
	workOrderSet();
	setInterval(function(){
		itemTable.replaceData();
		CrateSelect($("#machineCode").val());
	},5000);
	setInterval(function(){
		location.reload();
	},1800000);
}