var WorkOrder_tbl = new Tabulator("#WorkOrder_tbl", {
	height:"100%",
	placeholder: "No Data Set",
	resizableColumns: false,
	ajaxLoader:false,
	columns: [
		{ title: "작업지시번호", field: "workOrder_ONo", headerHozAlign: "center"},
		{ title: "제품코드", field: "workOrder_ItemCode", headerHozAlign: "center"},
		{ title: "제품이름", field: "workOrder_ItemName", headerHozAlign: "center"},
		{ title: "규격", field: "workOrder_Item_STND_1", headerHozAlign: "center"},
		{ title: "생산량", field: "workOrder_RQty", headerHozAlign: "center", align: "right",
			formatter:"money", formatterParams: {precision: false}},
		{ title: "작업시작일", field: "workOrder_StartTime", align: "right", headerHozAlign: "center"},
		{ title: "작업완료일", field: "workOrder_CompleteTime", align: "right", headerHozAlign: "center"}
	],
});

//1이 작업중인 데이터가 있는경우
//2에 있는경우
//비교하여 같으면 이상 없음, 다르면 완료경고떠야됨

//2에 없는경우
//1의 정보로 2의 작업지시 생성

function WOS_Choice(){
	//M001의 작업지시, M002의 작업지시 가져옴
	$.when(WOT_Choice_Ajax("M001"),WOT_Choice_Ajax("M002"))
	.then(function(data1, data2){
		if(data2[0].length > 0){
		{
			$("#n_len_code").val(data2[0][0].workOrder_ItemCode);
			$("#n_len").val(data2[0][0].workOrder_ItemName);
			$("#o_len").val(data2[0][0].workOrder_Item_STND_1);
			$("#t8").text(data2[0][0].workOrder_ONo)
			qtyCalc()
		}			
			
			
		}else{
			//2에 존재하지 않는 경우, M001의 데이터를 바탕으로 작업지시 새로 생성			
			if(data1[0].length > 0){
				
				//1에 존재
				$.when(WOT_Save(data1[0][0]))
				.then(function(){
					WOS_NewUpdate(data1[0][0])
				})
					
			}else{
				$("#n_len_code").val("");
				$("#n_len").val("");
				$("#o_len").val("");
				$("#t8").text("NONE")
			}
		}
	})
}

//작업완료 버튼을 눌렀을때
//설비2 작업은 완료로 변경 해주고 나서 설비1 작업을 확인하여 상태를 적용시킨다.
$("#WOS_CompleteBtn").click(function(){
	if(confirm("작업지시를 완료하시겠습니까?")){
		//M001의 데이터를 가져옴, 진행중인 작업 완료
		$.when(WOT_Choice_Ajax("M001"),WOS_Complete())
	}
})

function WOS_Complete(){
	var datas = {
		workOrder_ONo : $("#t8").text(),
		workOrder_ItemCode : $("#n_len_code").val(),
		workOrder_EquipCode : $("#eqselect").val(),
		workOrder_PQty : $("#M001_count").val(), //목표수량
		workOrder_RQty : $("#M002_count").val(), //생산수량
		workOrder_DQty : $("#gap_count").val(),
		workOrder_WorkStatus_Name : "E"
	} 
	var ajaxResult = $.ajax({
		method : "get",
		url : "../workOrderSeiyonRest/WOS_Complete",
		data : datas,
		async : false,
		success : function(data) {
			if (data) {
			}else{
				
			}
		}
	});
	return ajaxResult
}

function WOT_Choice_Ajax(machineCodeValue){
	var ajaxResult = $.ajax({
		method : "get",
		url : "../workOrderTABRestXO/WOT_Choice",
		data : {
			machineCode : machineCodeValue,
		},
		success : function(data) {						
		}
	});
	return ajaxResult;	
}

//해당설비 오늘작업 완료리스트
function WOT_Search(){
	var datas = {
		machineCode : $("#eqselect").val(),
		startDate : today.toISOString().substring(0, 10),
		endDate : tomorrow.toISOString().substring(0, 10),
		statusCodeArr : ["245"]
	}
	WorkOrder_tbl.setData("../workOrderTABRestXO/WOT_Search",datas)
}

function WOT_Save(jsonDatas){
	var datas = {
		//새로 만들 작업지시의 제품명
		workOrder_ONo : jsonDatas.workOrder_ONo,
		workOrder_ItemCode : jsonDatas.workOrder_ItemCode,
		workOrder_EquipCode : $("#eqselect").val(),
		workOrder_OrderTime : jsonDatas.workOrder_OrderTime,
		workOrder_CompleteOrderTime : jsonDatas.workOrder_CompleteOrderTime,
		workOrder_Remark : "AUTO"
	}
	
	var ajaxResult = $.ajax({
		method : "get",
		url : "../workOrderTABRestXO/WOT_Save",
		data : datas,
		success : function(data) {
			if(data){
				
				WOT_Search();
			}else{
			}
		}
	});
	return ajaxResult;
}
function qtyCalc(){
	$.when(sumQtyAjax("M001"),sumQtyAjax("M002"))
	.done(function(resp1, resp2){
		$("#gap_count").val(resp1[0] - resp2[0]);
		//2에 존재
		if(resp2[0] != 0 && resp2[0] - resp1[0] >= 0){
			if($("#complete_message").hasClass("none")){
				$("#complete_message").removeClass("none")
			}					
		}else{
			if(!$("#complete_message").hasClass("none")){
				$("#complete_message").addClass("none")
			}
		}
	})
}

function sumQtyAjax(machineCodeValue){
	var ajaxResult = $.ajax({
		method : "get",
		url : "../workOrderTABRestXO/workOrderSumQty",
		data : {
			machineCode : machineCodeValue,
			workOrderONo : $("#t8").text()
		},
		success : function(data) {
			$("#"+machineCodeValue+"_count").val(data);
		},
		error : function(){
			alert("설비코드 오류")
		}
	})
	return ajaxResult
}

function WOS_NewUpdate(jsonDatas){
	var datas = {
		//새로 만들 작업지시의 제품명
		workOrder_ONo : $("#t8").text(),
		NewWorkOrder_ONo : jsonDatas.workOrder_ONo
	}
	$.ajax({
		method : "get",
		url : "../workOrderSeiyonRest/WOS_NewUpdate",
		data : datas,
		success : function(data) {
			
		}
	});
}

window.onload = function(){
	document.getElementById("ko").style.height = window.innerHeight - document.getElementById("ko").offsetTop + "px";
	document.getElementById("WorkOrder_tbl").style.height = window.innerHeight - document.getElementById("ko").offsetTop - 10 + "px";
	WOS_Choice();
	
	setInterval(function(){
		WOS_Choice();
	},1000);
}
