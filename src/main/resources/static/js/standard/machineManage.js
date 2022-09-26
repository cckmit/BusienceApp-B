function nextFocus(next) {
	if (event.keyCode == 13) {
		$('#' + next).focus();
	}
}

//입력 및 업데이트 할 리스트
var pickValue = ["equipment_BUSINESS_PLACE", "equipment_INFO_CODE", "equipment_INFO_NAME",
					"equipment_INFO_ABR", "equipment_HEIGHT", "equipment_WIDTH",
					"equipment_DEPTH", "equipment_SERIAL_NUM", "equipment_RECEIVED_D",
					"equipment_TYPE", "equipment_MODEL_YEAR", "equipment_MANUFACTURER",
					"equipment_STATUS_NAME", "equipment_INFO_RMARK", "equipment_USE_STATUS",
					"equipment_MODIFY_D", "equipment_MODIFIER"];


var machineManageTable = new Tabulator("#machineManageTable", {
	//페이징
	layoutColumnsOnNewData : true,
	headerFilterPlaceholder: null,
	height: "calc(100% - 175px)",
	ajaxURL:"machineManageRest/machineManageSelect",
    ajaxConfig:"get",
    ajaxContentType:"json",
	rowClick: function(e, row) {
		row.getTable().deselectRow();
		row.select();
	},
	rowDblClick: function(e, row) {
		//모달창 띄움
		modifyModalShow();
	},
	rowSelected:function(row){
    	var jsonData = fromRowToJson(row, pickValue);
		modalInputBox(jsonData);
    },
	columns: [
		{ title: "번호", field: "rownum", hozAlign: "center", headerHozAlign: "center", formatter: "rownum"},
		{ title: "사업장", field: "equipment_BUSINESS_PLACE_NAME", headerHozAlign: "center", headerFilter: "input"},
		{ title: "설비코드", field: "equipment_INFO_CODE", headerHozAlign: "center", headerFilter: "input"},
		{ title: "설비명", field: "equipment_INFO_NAME", headerHozAlign: "center", headerFilter: "input"},
		{ title: "설비호기", field: "equipment_INFO_ABR",	headerHozAlign: "center", headerFilter: "input"},
		{ title: "설비(높이)", field: "equipment_HEIGHT", headerHozAlign: "center", headerFilter: "input"},
		{ title: "설비(폭)", field: "equipment_WIDTH", headerHozAlign: "center", headerFilter: "input"},
		{ title: "설비(깊이)", field: "equipment_DEPTH", headerHozAlign: "center", headerFilter: "input"},
		{ title: "설비(S/N)", field: "equipment_SERIAL_NUM", headerHozAlign: "center", headerFilter: "input"},
		{ title: "설비종류", field: "equipment_TYPE_NAME", headerHozAlign: "center", headerFilter: "input"},
		{ title: "구입일자", field: "equipment_RECEIVED_D", headerHozAlign: "center",	headerFilter: "input"},
		{ title: "설비연식", field: "equipment_MODEL_YEAR", headerHozAlign: "center",	headerFilter: "input"},
		{ title: "제작업체", field: "equipment_MANUFACTURER", headerHozAlign: "center", headerFilter: "input"},
		{ title: "설비상태", field: "equipment_STATUS_NAME", headerHozAlign: "center", headerFilter: "input"},
		{ title: "비고", field: "equipment_INFO_RMARK", headerHozAlign: "center", headerFilter: "input"},
		{ title: "사용유무", field: "equipment_USE_STATUS", headerHozAlign: "center",	hozAlign: "center",
			formatter: "tickCross", headerFilter: true, headerFilterParams: { values: { true: "사용", false: "미사용" }}},
		{ title: "수정일자", field: "equipment_MODIFY_D", headerHozAlign: "center", formatter: "datetime",
			formatterParams: { outputFormat: "YYYY-MM-DD HH:mm:ss" }, headerFilter: "input"},
		{ title: "수정자", field: "equipment_MODIFIER", headerHozAlign: "center", headerFilter: "input"}
	]
});

// ADD버튼을 클릭할때 모달창을 여는 이벤트
$("#machineADDBtn").click(function() {
	machineManageTable.deselectRow();
	registerModalShow()
});


function registerModalShow(){
	
	$('.modify').addClass('none');
	
	if ($('.insert').hasClass('none')) {
		$('.insert').removeClass('none');
	}
	$("#machineManageModal").find('form')[0].reset();
	$("#equipment_INFO_CODE").removeAttr('readonly');
	
	$("#machineManageModal").modal("show").on("shown.bs.modal", function () {
		$("#equipment_INFO_CODE").focus();
	});
}

//모달창내 등록버튼
$("#machineRegisterBtn").click(function(){
	if(confirm("등록 하시겠습니까?")){
		machineRegister();
	}
})

function machineRegister() {
	
	var datas = {
		EQUIPMENT_BUSINESS_PLACE: $("#equipment_BUSINESS_PLACE").val(),
		EQUIPMENT_INFO_CODE: $("#equipment_INFO_CODE").val(),
		EQUIPMENT_INFO_NAME: $("#equipment_INFO_NAME").val(),
		EQUIPMENT_INFO_ABR: $("#equipment_INFO_ABR").val(),
		EQUIPMENT_HEIGHT: $("#equipment_HEIGHT").val(),
		EQUIPMENT_WIDTH: $("#equipment_WIDTH").val(),
		EQUIPMENT_DEPTH: $("#equipment_DEPTH").val(),
		EQUIPMENT_SERIAL_NUM: $("#equipment_SERIAL_NUM").val(),
		EQUIPMENT_RECEIVED_D: $("#equipment_RECEIVED_D").val(),
		EQUIPMENT_TYPE: $("#equipment_TYPE").val(),
		EQUIPMENT_MODEL_YEAR: $("#E#equipment_DEL_YEAR").val(),
		EQUIPMENT_MANUFACTURER: $("#equipment_MANUFACTURER").val(),
		EQUIPMENT_STATUS: $("#equipment_STATUS").val(),
		EQUIPMENT_INFO_RMARK: $("#equipment_INFO_RMARK").val(),
		EQUIPMENT_USE_STATUS: $("#equipment_USE_STATUS").is(":checked")
	}
	
	// 설비코드
	if (datas.EQUIPMENT_INFO_CODE.length != 4) {
		alert("품목코드는 4글자로 입력해야 합니다.");
		return $("#equipment_INFO_CODE").focus();
	}
	
	$.ajax({
		method : "post",
		url : "machineManageRest/machineManageInsert",
		data : datas,
		beforeSend: function (xhr) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           xhr.setRequestHeader(header, token);
		},
		success : function(data) {
			if (data) {
				alert("저장 되었습니다.");
				
				machineManageTable.replaceData();
				
				$("#machineManageModal").modal("hide");
			}else{
				alert("중복된 코드 입니다.");
			}
		}
	});
}

// update버튼을 클릭을 할때 모달창을 여는 이벤트
$("#machineUpdateBtn").click(function() {
	var selectedRow = machineManageTable.getData("selected");
	
	if(selectedRow.length == 0){
		alert("수정할 행을 선택하세요.");
	} else {
		modifyModalShow()
	}
});

function modifyModalShow(){
	
	$('.insert').addClass('none');
	
	if ($('.modify').hasClass('none')) {
		$('.modify').removeClass('none');
	}
	$("#equipment_INFO_CODE").attr('readonly', 'readonly');
	
	$("#machineManageModal").modal("show").on("shown.bs.modal", function () {
		$("#equipment_INFO_NAME").focus();
	});
}

//모달창내 수정버튼
$("#machineModifyBtn").click(function(){
	if(confirm("수정 하시겠습니까?")){
		machineModify();
	}
})

function machineModify() {
	var datas = {
		EQUIPMENT_BUSINESS_PLACE: $("#equipment_BUSINESS_PLACE").val(),
		EQUIPMENT_INFO_CODE: $("#equipment_INFO_CODE").val(),
		EQUIPMENT_INFO_NAME: $("#equipment_INFO_NAME").val(),
		EQUIPMENT_INFO_ABR: $("#equipment_INFO_ABR").val(),
		EQUIPMENT_HEIGHT: $("#equipment_HEIGHT").val(),
		EQUIPMENT_WIDTH: $("#equipment_WIDTH").val(),
		EQUIPMENT_DEPTH: $("#equipment_DEPTH").val(),
		EQUIPMENT_SERIAL_NUM: $("#equipment_SERIAL_NUM").val(),
		EQUIPMENT_RECEIVED_D: $("#equipment_RECEIVED_D").val(),
		EQUIPMENT_TYPE: $("#equipment_TYPE").val(),
		EQUIPMENT_MODEL_YEAR: $("#equipment_MODEL_YEAR").val(),
		EQUIPMENT_MANUFACTURER: $("#equipment_MANUFACTURER").val(),
		EQUIPMENT_STATUS: $("#equipment_STATUS").val(),
		EQUIPMENT_INFO_RMARK: $("#equipment_INFO_RMARK").val(),
		EQUIPMENT_USE_STATUS: $("#equipment_USE_STATUS").is(":checked")
	}

	$.ajax({
		method: "put",
		data: datas,
		url: "machineManageRest/machineManageUpdate",
		beforeSend: function (xhr) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           xhr.setRequestHeader(header, token);
		},
		success : function(data) {
			if (data) {
				alert("저장 되었습니다.");
				machineManageTable.replaceData();
				
				$("#machineManageModal").modal("hide");
			}else{
				alert("오류가 발생했습니다.");
			}
		}
	});
}


// delete버튼을 클릭을 할때 모달창을 여는 이벤트
$("#machineDeleteBtn").click(function() {
	var selectedRow = machineManageTable.getData("selected");
	
	if(selectedRow.length == 0){
		alert("삭제할 행을 선택하세요.");
	}else{
		modifyModalShow();		
	}
});

//모달창내 삭제버튼
$("#machineRemoveBtn").click(function(){
	if(confirm("삭제 하시겠습니까?")){
		machineRemove();	
	}
})

function machineRemove() {
	
	var datas = {
		EQUIPMENT_INFO_CODE: $("#equipment_INFO_CODE").val()
	};

	$.ajax({
		method: "delete",
		data: datas,
		url: "machineManageRest/machineManageDelete",
		beforeSend: function (xhr) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           xhr.setRequestHeader(header, token);
		},
		success : function(data) {
			if (data) {
				alert("삭제 되었습니다.");
				machineManageTable.replaceData();
				
				$("#machineManageModal").modal("hide");
			}else{
				alert("사용중인 코드는 삭제할 수 없습니다.");
				location.reload();
			}
		},
		error:function(request,status,error){
        alert("사용 중인 코드는 삭제할 수 없습니다.");
       }
	});
}
