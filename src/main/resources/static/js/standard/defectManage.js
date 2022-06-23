var nowModal

//입력 및 업데이트 할 리스트
var pickValue = ["defect_Code", "defect_Name", "defect_Abr", "defect_Rmrks",
					"defect_Use_Status", "defect_Modify_D",	"defect_Modifier"];
					
var defectManageTable = new Tabulator("#defectManageTable", {
	//페이징
	layoutColumnsOnNewData : true,
	headerFilterPlaceholder: null,
	height: "calc(100% - 175px)",
	ajaxURL:"defectManageRest/defectManageSelect",
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
	columns: [ //Define Table Columns
		{ title: "번호", field: "rownum", formatter: "rownum", hozAlign: "center", headerHozAlign: "center"},
		{ title: "불량코드", field: "defect_Code", headerHozAlign: "center", headerFilter: "input"},
		{ title: "불량명", field: "defect_Name", width: 150, headerHozAlign: "center", headerFilter: "input"},
		{ title: "약자", field: "defect_Abr", headerHozAlign: "center", headerFilter: "input"},
		{ title: "비고", field: "defect_Rmrks", width: 200, headerHozAlign: "center", headerFilter: "input"},
		{ title: "사용유무", field: "defect_Use_Status", headerHozAlign: "center", hozAlign: "center", 
			formatter: "tickCross", editorParams: { values: {"true": "사용","false": "미사용"}}, headerFilter: true,
			headerFilterParams: {values: {"true": "사용","false": "미사용"	}}},
		{ title: "수정일자", field: "defect_Modify_D", headerHozAlign: "center", hozAlign: "right", sorter: "date", formatter: "datetime", formatterParams: { outputFormat: "YYYY-MM-DD HH:mm:ss" },
			headerFilter: "input"},
		{ title: "수정자", field: "defect_Modifier", headerHozAlign: "center", headerFilter: "input"}
	]
});

// ADD버튼을 클릭할때 모달창을 여는 이벤트
$("#defectADDBtn").click(function() {
	defectManageTable.deselectRow();
	registerModalShow()
});

function registerModalShow(){
	
	$('.modify').addClass('none');
	
	if ($('.insert').hasClass('none')) {
		$('.insert').removeClass('none');
	}
	$("#defectManageModal").find('form')[0].reset();
	$("#defect_Code").removeAttr('readonly');
	
	$("#defectManageModal").modal("show").on("shown.bs.modal", function () {
		$("#defect_Code").focus();
	});
}

//모달창내 등록버튼
$("#defectRegisterBtn").click(function(){
	if(confirm("등록 하시겠습니까?")){
		defectRegister();
	}
})

function defectRegister() {
	
	var datas = fromInputToJson(pickValue)
		
	if (datas.defect_Code.length != 4) {
		alert("불량코드는 4글자로 입력해야 합니다.");
		return $("#defect_Code").focus();
	}
	console.log(datas);
	$.ajax({
		method : "post",
		url : "defectManageRest/defectManageInsert",
		data : datas,
		beforeSend: function (xhr) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           xhr.setRequestHeader(header, token);
		},
		success : function(data) {
			if (data) {
				alert("저장 되었습니다.");
				
				defectManageTable.replaceData();
				
				$("#defectManageModal").modal("hide");
			}else{
				alert("중복된 코드 입니다.");
			}
		}
	});
}

// update버튼을 클릭을 할때 모달창을 여는 이벤트
$("#defectUpdateBtn").click(function() {
	var selectedRow = defectManageTable.getData("selected");
	
	if(selectedRow.length == 0){
		alert("수정할 행을 선택하세요.");
	} else {
		modifyModalShow()
	}
});

function modifyModalShow(){
	nowModal = "modify"
	
	$('.insert').addClass('none');
	
	if ($('.modify').hasClass('none')) {
		$('.modify').removeClass('none');
	}
	$("#defect_Code").attr('readonly', 'readonly');
	
	$("#defectManageModal").modal("show").on("shown.bs.modal", function () {
		$("#defect_Name").focus();
	});
}

//모달창내 수정버튼
$("#defectModifyBtn").click(function(){
	if(confirm("수정 하시겠습니까?")){
		defectModify();
	}
})

function defectModify() {
	
	var datas = fromInputToJson(pickValue)

	$.ajax({
		method: "put",
		data: datas,
		url: "defectManageRest/defectManageUpdate",
		beforeSend: function (xhr) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           xhr.setRequestHeader(header, token);
		},
		success : function(data) {
			if (data) {
				alert("저장 되었습니다.");
				defectManageTable.replaceData();
				
				$("#defectManageModal").modal("hide");
			}else{
				alert("오류가 발생했습니다.");
			}
		}
	});
}

// delete버튼을 클릭을 할때 모달창을 여는 이벤트
$("#defectDeleteBtn").click(function() {
	var selectedRow = defectManageTable.getData("selected");
	
	if(selectedRow.length == 0){
		alert("삭제할 행을 선택하세요.");
	}else{
		modifyModalShow();		
	}
});

//모달창내 삭제버튼
$("#defectRemoveBtn").click(function(){
	if(confirm("삭제 하시겠습니까?")){
		defectRemove();	
	}
})

function defectRemove() {
	
	var datas = fromInputToJson(pickValue)

	$.ajax({
		method: "delete",
		data: datas,
		url: "defectManageRest/defectManageDelete",
		beforeSend: function (xhr) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           xhr.setRequestHeader(header, token);
		},
		success : function(data) {
			if (data) {
				alert("삭제 되었습니다.");
				defectManageTable.replaceData();
				
				$("#defectManageModal").modal("hide");
			}else{
				alert("오류가 발생했습니다.");
			}
		},
		error:function(request,status,error){
        alert("사용 중인 코드는 삭제할 수 없습니다.");
       }
	});
}