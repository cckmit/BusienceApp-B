var LotList = new Array();

$("#machineName").click(function(){
	location.reload();
})

var itemTable = new Tabulator("#itemTable", {
	layoutColumnsOnNewData : true,
	height: "100%",
	ajaxURL:"../itemManageRest/itemCodeInfo",
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
	crateTable.replaceData();
	crateTable.redraw();
  } else {
    if (document.exitFullscreen) {
      document.exitFullscreen()
    }
  }
}

function workOrderSet(){
	$.when(CrateSelect($("#machineCode").val()))
	.then(function(data){
		return RawSelect(data.c_Production_LotNo);
	}).then(function(data1){
		if(data1.length == 0){
			
			BOM_Check(nowItemCode($("#machineCode").val()))
		}
	})
}

$(document).keydown(function(){
	$("#barcodeInput").focus();
});


$("#barcodeInput").keyup(function(){
	$(this).val($(this).val().toUpperCase());
})

$("#barcodeInput").change(function(){
	//바코드를 읽었을때
	//이니셜값 비교하여 맞는 칸에 값을 넣는다.
	var barcode = $(this).val();
	$("#barcodeInput").val("");
	var initial = barcode.substring(0,1);
	
	if(initial == 'R'){
		//상자가 있으면 저장하고 없으면 저장하지 않는다.
		//코드가 안맞을경우 안맞는다고 알림
		
		rawMaterialLotInput(barcode);
		/*
		$.when(rawMaterialCheck(value))
			.then(function(data){
			if(data.length>0){
				
			}else{
				console.log("창고에 없는 원자재 입니다.")
			}			
		})*/	
		
		if(inputCheck($("#crate-LotNo").val())){
			rawMaterialSave($("#crate-LotNo").val());	
		}
		
		$("#barcodeInput").val("");
	}else if(initial == 'N'){
		$.when(CrateStatusCheck(barcode))
		.then(function(data){
			
			//있다 or 없다.
			if(data instanceof Object){
				// 상자를 바꿀때 설비- 아이템을 확인하여 그정보로 저장
				//기존 생산랏 상태변경 새로운 생산랑 생성 
				return CrateSave($("#crateCode").val(), data.c_CrateCode)
			}
		})
		.then(function(data){
			$("#crateCode").val(data.c_CrateCode);
			$("#crate-LotNo").val(data.c_Production_LotNo);
			return BOM_Check(value)
		})
		.then(function(data){
			console.log(data);
			if(inputCheck($("#crate-LotNo").val())){
				rawMaterialSave($("#crate-LotNo").val());
			}
		})	
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

//원자재 리스트 입력
function rawMaterialLotInput(value){
	//lotNo와 기존 원자재와 비교 한 뒤에 맞는게 없으면 다음 작업의 봄과 비교한다.
	// 없으면 에러발생, 있으면 작업종료후 원자재 등록
	var itemCode = value.substr(7,6);
	var result = LotList.every(function(currentValue, i){
		//품목코드와 동일한 값을 찾은 후, 랏번호가 같지 않다면 변경됨
		if(currentValue.rms_ItemCode == itemCode){
			if(currentValue.rms_LotNo != value){
				//반복문 실행하여 LotList 에 정보만 넣고
				//LotList의 정보를 화면에 보여주는 함수 따로 만들면 좋을듯
				$(".main-c .item:nth-of-type("+(i+2)+") .LotNo").val(value);
				currentValue.rms_LotNo = value
							
			}
			return false;
		}
		return true;
	})
	if(result){
		console.log("제품과 맞지않는 원자재 입니다.")
	}
	return 
}
function rawMaterialCheck(value){
	var ajaxResult = $.ajax({
		method : "get",
		url : "/matOutReturnRest/MORI_Search",
		data : {lotNo : value, warehouse : "51"},
		success : function(data) {
		}
	})
	return ajaxResult;
}

function inputCheck(LotNo){
	var result = LotList.every(x => {
		return x.rms_LotNo != null
	})
	if(LotNo.length > 0 && result){
		return true;
	}
	return false;
}

function rawMaterialSave(value){
	var masterData = {
			production_LotNo : value,
			qty : $("#crate-Qty").val()
		}
		
	$.ajax({
		method : "post",
		url : "maskProductionRest/rawMaterialSave",
		data: {masterData : JSON.stringify(masterData), subData: JSON.stringify(LotList)},
		beforeSend: function (xhr) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           xhr.setRequestHeader(header, token);
		},
		success: function (data){
			//console.log(data);
		}
	});
}

function BOM_Check(value){
	var result = LotList.every(x => {
		return x.rms_LotNo == null
	})
	if(result){
		var ajaxResult = $.ajax({
			method : "get",
			url : "maskProductionRest/BOMBOMList",
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
}

function RawSelect(value){
	var ajaxResult = $.ajax({
	method : "get",
		url : "maskProductionRest/rawMaterialSelect",
		data : {lotNo : value},
		success : function(data) {
			LotList = new Array();
			
			for(let i=0;i<data.length;i++){
				LotList.push({
					rms_LotNo : data[i].material_LotNo,
					rms_ItemCode : data[i].material_ItemCode
				})
				$(".main-c .item:nth-of-type("+(i+2)+") .LotNo_Code").val(data[i].material_ItemCode);
				$(".main-c .item:nth-of-type("+(i+2)+") .LotNo_Name").val(data[i].material_ItemName);
				$(".main-c .item:nth-of-type("+(i+2)+") .LotNo").val(data[i].material_LotNo);
			}
		}
	})
	return ajaxResult;
}

function CrateStatusCheck(value){
	var ajaxResult = $.ajax({
		method : "get",
		url : "maskProductionRest/CrateStatusCheck",
		data : {crateCode : value, condition : 0},
		success : function() {
		}
	})
	return ajaxResult;
}

function CrateSelect(value){
	var ajaxResult = $.ajax({
		method : "get",
		url : "maskProductionRest/crateSelect",
		data : {machineCode : value, condition : 1},
		success : function(data) {
			if(data instanceof Object){
				$("#crate-LotNo").val(data.c_Production_LotNo);
				$("#crateCode").val(data.c_CrateCode);
				$("#crate-Qty").val(data.c_Qty);
			}
		}
	})
	return ajaxResult;
}

function CrateSave(before, after){
	var ajaxResult = $.ajax({
		method : "post",
		url : "maskProductionRest/crateSave",
		data : {
			C_Before_CrateCode : before,
			C_CrateCode : after,
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
			}
		}
	});
	return ajaxResult;
}
function CrateProductionSave(lotNo, production_ID){
	var ajaxResult = $.ajax({
		method : "post",
		url : "maskProductionRest/crateProductionSave",
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

var crateTable = new Tabulator("#crateTable", {
	layoutColumnsOnNewData : true,
	ajaxLoader:false,
	height: "100%",
	columns:[
		{title:"생산 LotNo 이력", headerHozAlign:"center",
			columns: [	
				{ title: "생산 LotNo", field: "cl_LotNo", headerHozAlign: "center"},
				{ title: "상자코드", field: "cl_CrateCode", headerHozAlign: "center"},
				{ title: "생산수량", field: "cl_Qty", headerHozAlign: "center", hozAlign:"right",
					formatter:"money", formatterParams: {precision: false}}
			]
		}
	]
});

function crateTableSelect(value){
	crateTable.setData("maskProductionRest/crateLotRecordSelect", {machineCode : value})
}

$("#completeBtn").click(function(){
	if(confirm("완료버튼을 누르면 현재 작업이 완료 되고 화면이 초기화 됩니다. 하시겠습니까?")){
		crateUpdate($("#crateCode").val())
	}
})

function crateUpdate(crateCode){
	var ajaxResult = $.ajax({
		method : "post",
		url : "/crateRest/crateUpdate",
		data : { c_CrateCode : crateCode, c_Condition : '2'},
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

window.onload = function(){
	workOrderSet();
	setInterval(function(){
		itemTable.replaceData();
		CrateSelect($("#machineCode").val());
	},5000);
}