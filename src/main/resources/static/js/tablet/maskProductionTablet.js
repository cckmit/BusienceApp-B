var LotList = new Array();

$("#machineName").click(function(){
	location.reload();
})

var itemTable = new Tabulator("#itemTable", {
	layoutColumnsOnNewData : true,
	height: "100%",
	ajaxURL:"maskProductionRest/workingByMachine",
	ajaxParams: {machineCode : $("#machineCode").val(), condition: 1},
    ajaxConfig:"get",
    ajaxContentType:"json",
	ajaxResponse:function(url, params, response){
		//작업지시번호로 생산랏을 검색해서 내용이 있는지 여부를 검색
		//있으면 생산랏에 대한 정보를 검색하고
		//없으면 봄만 가져옴
		$.when(RawMaterialSelect(response))
		.then(function(data){
			// 자재식별코드가 있을경우
			if(data.length>0){
				$("#production-ID").val(data[0].rmm_Production_ID)
				$("#production-Qty").val(data[0].rmm_Qty)
				
				RawSubSelect(data[0].rmm_Production_ID)
				CrateSelect(response)				
			}else{
				//생산랏이 없을경우
				BOM_Check(response)
			}
			crateTableSelect(response[0].workOrder_ONo)
			rawMaterialTableSelect(response[0].workOrder_ONo)
		})
		
   		return response;
    },
	columns:[
		{title:"현재 생산중인 제품", headerHozAlign:"center",
			columns: [	
				{ title: "작업지시No", field: "workOrder_ONo", headerHozAlign: "center"},
				{ title: "제품코드", field: "workOrder_ItemCode", headerHozAlign: "center"},
				{ title: "제품이름", field: "workOrder_ItemName", headerHozAlign: "center"},
				{ title: "규격1", field: "workOrder_Item_STND_1", headerHozAlign: "center"},
				{ title: "규격2", field: "workOrder_Item_STND_2", headerHozAlign: "center"},
				{ title: "생산수량", field: "workOrder_ProductionQty", headerHozAlign: "center", hozAlign:"right",
					formatter:"money", formatterParams: {precision: false}},
				{ title: "작업등록일", field: "workOrder_RegisterTime", hozAlign: "right", headerHozAlign: "center"},
				{ title: "작업시작일", field: "workOrder_StartTime", hozAlign: "right", headerHozAlign: "center"},
				{ title: "작업완료일", field: "workOrder_CompleteTime", hozAlign: "right", headerHozAlign: "center"}
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
	itemTable.redraw();
	itemTable.replaceData();
	crateTable.redraw();
	itemTable.replaceData();
	rawMaterialTable.redraw();
	rawMaterialTable.replaceData();
  } else {
    if (document.exitFullscreen) {
      document.exitFullscreen()
    }
  }
}

$(document).keydown(function(){
	$("#barcodeInput").focus();
});

$("#barcodeInput").keypress(function(e){
	if(e.keyCode == 13){
		//바코드를 읽었을때
		//이니셜값 비교하여 맞는 칸에 값을 넣는다.
		var barcode = $(this).val();
		var initial = barcode.substring(0,1);
		if(initial == 'R'){
			//원자재 object 판단하여 다찼으면 저장 쿼리를 실행한다.
			rawMaterialLotInput(barcode);
		}else if(initial == 'C'){
			if($("#production-ID").val().length > 0){
				//이미 작업중인 상자가 있을경우
				//작업중인 상자와 같은 랏인경우 이무일도 일어나면 안됨
				//다른 랏인경우
				//기존 상자를 상태를 바꿔 준 뒤 새로 등록
				//작업중인 상자가 없을경우
				//새로 등록
				if($("#crateCode").val() != barcode){
					//상자를 등록했을때 해당 작업지시의 첫 상자면 작업지시를 작업시작으로 변경함
					workOrderStart($("#machineCode").val())
					CrateSave($("#crate-LotNo").val(), barcode)
					CrateSelect(itemTable.getData())
				}
			}else{
				alert("원자재를 등록해 주세요.")
			}
			
		}
		itemTable.redraw();
		crateTable.redraw();
		rawMaterialTable.redraw();
	}
});

//원자재 리스트 입력
function rawMaterialLotInput(value){
	var itemCode = value.substr(7,6);
	var result = LotList.every(function(currentValue, i){
		//품목코드와 동일한 값을 찾은 후, 랏번호가 같지 않다면 변경됨
		if(currentValue.rms_ItemCode == itemCode){
			if(currentValue.rms_LotNo != value){
				//반복문 실행하여 LotList 에 정보만 넣고
				//LotList의 정보를 화면에 보여주는 함수 따로 만들면 좋을듯
				$(".main-c .item:nth-of-type("+(i+2)+") .LotNo").val(value);
				$("#barcodeInput").val("");
				currentValue.rms_LotNo = value
			}
			return false;
		}
		return true;
	})
	
	if(result){
		alert("제품과 맞지않는 원자재 입니다.");
	}else{
		//다 찼을경우 저장 실행
		rawMaterialSave();		
	}
}

function rawMaterialSave(){
	var result = LotList.every(x => {
		return x.rms_LotNo != null
	})
	if(result){
		var masterData = {
			rmm_OrderNo : itemTable.getData()[0].workOrder_ONo,
			rmm_ItemCode : itemTable.getData()[0].workOrder_ItemCode,
			rmm_Before_Production_ID : $("#production-ID").val()
		}
		
		$.ajax({
			method : "post",
			url : "maskProductionRest/rawMaterialSave",
			data: {masterData : JSON.stringify(masterData), subData: JSON.stringify(LotList)},
			beforeSend: function (xhr) {
	           var header = $("meta[name='_csrf_header']").attr("content");
	           var token = $("meta[name='_csrf']").attr("content");
	           xhr.setRequestHeader(header, token);
			}
		});
	}
}

function BOM_Check(values){
	var ajaxResult = $.ajax({
		method : "get",
		url : "maskProductionRest/BOMBOMList",
		data : {itemCode : values[0].workOrder_ItemCode},
		success : function(data) {
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
	});
	return ajaxResult
}

function RawMaterialSelect(values){
	var ajaxResult = $.ajax({
	method : "get",
		url : "maskProductionRest/rawMaterialMasterSelect",
		data : {orderNo : values[0].workOrder_ONo}
	})
	return ajaxResult;
}

function RawSubSelect(value){
	var ajaxResult = $.ajax({
	method : "get",
		url : "maskProductionRest/rawMaterialSubSelect",
		data : {lotNo : value},
		success : function(data) {
			for(let i=0;i<data.length;i++){
				LotList.push({
					rms_LotNo : data[i].rms_LotNo,
					rms_ItemCode : data[i].rms_ItemCode
				})
				$(".main-c .item:nth-of-type("+(i+2)+") .LotNo_Code").val(data[i].rms_ItemCode);
				$(".main-c .item:nth-of-type("+(i+2)+") .LotNo_Name").val(data[i].rms_ItemName);
				$(".main-c .item:nth-of-type("+(i+2)+") .LotNo").val(data[i].rms_LotNo);
			}
		}
	})
	return ajaxResult;
}

function CrateSelect(values){
	var ajaxResult = $.ajax({
		method : "get",
		url : "maskProductionRest/crateSelect",
		data : {orderNo : values[0].workOrder_ONo},
		success : function(data) {
			if(data.length>0){
				$("#crate-LotNo").val(data[0].cl_LotNo);
				$("#crateCode").val(data[0].cl_CrateCode);
				$("#crate-Qty").val(data[0].cl_Qty);
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
			CL_Before_LotNo : before,
			CL_CrateCode : after,
			CL_OrderNo : itemTable.getData()[0].workOrder_ONo,
			CL_ItemCode : itemTable.getData()[0].workOrder_ItemCode,
			CL_Production_ID : $("#production-ID").val()
		},
		beforeSend: function (xhr) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           xhr.setRequestHeader(header, token);
		}
	});
	return ajaxResult;
}

function workOrderStart(machineCode){
	var ajaxResult = $.ajax({
		method : "post",
		url : "maskProductionRest/workOrderStart",
		data : {
			workOrder_EquipCode : machineCode,
			workOrder_WorkStatus_Name : "S"
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
		{title:"상자 LotNo 이력", headerHozAlign:"center",
			columns: [	
				{ title: "상자 LotNo", field: "cl_LotNo", headerHozAlign: "center"},
				{ title: "상자코드", field: "cl_CrateCode", headerHozAlign: "center"},
				{ title: "생산수량", field: "cl_Qty", headerHozAlign: "center", hozAlign:"right",
					formatter:"money", formatterParams: {precision: false}}
			]
		}
	]
});

function crateTableSelect(value){
	crateTable.setData("maskProductionRest/crateLotRecordSelect", {orderNo : value})
}

var rawMaterialTable = new Tabulator("#rawMaterialTable", {
	layoutColumnsOnNewData : true,
	ajaxLoader:false,
	height: "100%",
	columns:[
		{title:"자재 식별 코드 이력", headerHozAlign:"center",
			columns: [	
				{ title: "작업지시No", field: "rmm_OrderNo", headerHozAlign: "center"},
				{ title: "자재식별코드", field: "rmm_Production_ID", headerHozAlign: "center"},
				{ title: "생산수량", field: "rmm_Qty", headerHozAlign: "center", hozAlign:"right",
					formatter:"money", formatterParams: {precision: false}}
			]
		}
	]
});

function rawMaterialTableSelect(value){
	rawMaterialTable.setData("maskProductionRest/rawMaterialRecordSelect", {orderNo : value})
}

window.onload = function(){
	setInterval(function(){
		itemTable.replaceData();
	},2000);
}