//입력 및 업데이트 할 리스트
var pickValue = ["cus_Code", "cus_Name", "cus_Status_Name",
					"cus_Clsfc_Name", "cus_Rprsn", "cus_Mng",
					"cus_Co", "cus_Co_EstYr", "cus_Rprsn_PhNr",
					"cus_Mng_PhNr", "cus_Mng_Email", "cus_Adr",
					"cus_Pymn_Date", "cus_Rgstr_Nr"];

var customerManageTable = new Tabulator("#customerManageTable", {
	//페이징
	pagination: "local",
	paginationSize: 20,
	layoutColumnsOnNewData : true,
	headerFilterPlaceholder: null,
	height: "calc(100% - 175px)",
	ajaxURL:"customerManageRest/customerManageSelect",
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
		{ title: "거래처코드", field: "cus_Code", headerHozAlign: "center", headerFilter: "input"},
		{ title: "거래처명", field: "cus_Name", headerHozAlign: "center", headerFilter: "input"},
		{ title: "납품조건", field: "cus_Status_Name", headerHozAlign: "center", headerFilter: "input"},
		{ title: "분류",	field: "cus_Clsfc_Name", headerHozAlign: "center", headerFilter: "input"},
		{ title: "대표자명", field: "cus_Rprsn", headerHozAlign: "center", headerFilter: "input"},
		{ title: "담당자명", field: "cus_Mng", headerHozAlign: "center", headerFilter: "input"},
		{ title: "회사명", field: "cus_Co", headerHozAlign: "center", headerFilter: "input"},
		{ title: "회사설립연도", field: "cus_Co_EstYr", headerHozAlign: "center", headerFilter: "input"},
		{ title: "대표전화번호", field: "cus_Rprsn_PhNr", headerHozAlign: "center", headerFilter: "input"},
		{ title: "담당자 전화번호", field: "cus_Mng_PhNr", headerHozAlign: "center", headerFilter: "input"},
		{ title: "담당자 이메일", field: "cus_Mng_Email", headerHozAlign: "center", headerFilter: "input"},
		{ title: "주소", field: "cus_Adr", headerHozAlign: "center", headerFilter: "input"},
		{ title: "결제일", field: "cus_Pymn_Date_Name", headerHozAlign: "center", headerFilter: "input"},
		{ title: "사업자등록번호", field: "cus_Rgstr_Nr", headerHozAlign: "center", headerFilter: "input"}
	]
});

// ADD버튼을 클릭할때 모달창을 여는 이벤트
$("#customerADDBtn").click(function() {
	customerManageTable.deselectRow();
	registerModalShow()
});

function registerModalShow(){
	
	$('.modify').addClass('none');
	
	if ($('.insert').hasClass('none')) {
		$('.insert').removeClass('none');
	}
	$("#customerManageModal").find('form')[0].reset();
	$("#cus_Code").removeAttr('readonly');
	
	$("#customerManageModal").modal("show").on("shown.bs.modal", function () {
		$("#cus_Code").focus();
	});
}

//모달창내 등록버튼
$("#customerRegisterBtn").click(function(){
	if(confirm("등록 하시겠습니까?")){
		customerRegister();
	}
})

function customerRegister() {
	
	var datas = {
		cus_Code: $("#cus_Code").val(),
		cus_Name: $("#cus_Name").val(),
		cus_Status: $("#cus_Status").val(),
		cus_Clsfc: $("#cus_Clsfc").val(),
		cus_Rprsn: $("#cus_Rprsn").val(),
		cus_Mng: $("#cus_Mng").val(),
		cus_Co: $("#cus_Co").val(),
		cus_Co_EstYr: $("#cus_Co_EstYr").val(),
		cus_Rprsn_PhNr: $("#cus_Rprsn_PhNr").val(),
		cus_Mng_PhNr: $("#cus_Mng_PhNr").val(),
		cus_Mng_Email: $("#cus_Mng_Email").val(),
		cus_Adr: $("#cus_Adr").val(),
		cus_Pymn_Date: $("#cus_Pymn_Date").val(),
		cus_Rgstr_Nr: $("#cus_Rgstr_Nr").val()
	};
	
	if (datas.cus_Code.length != 6) {
		alert("거래처코드는 6글자로 입력해야 합니다.");
		return $("#cus_Code").focus();
	}
	
	$.ajax({
		method : "post",
		url : "customerManageRest/customerManageInsert",
		data : datas,
		beforeSend: function (xhr) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           xhr.setRequestHeader(header, token);
		},
		success : function(data) {
			if (data) {
				alert("저장 되었습니다.");
				
				customerManageTable.replaceData();
				
				$("#customerManageModal").modal("hide");
			}else{
				alert("중복된 코드 입니다.");
			}
		}
	});
}


// update버튼을 클릭을 할때 모달창을 여는 이벤트
$("#customerUpdateBtn").click(function() {
	var selectedRow = customerManageTable.getData("selected");
	
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
	$("#cus_Code").attr('readonly', 'readonly');
	
	$("#customerManageModal").modal("show").on("shown.bs.modal", function () {
		$("#cus_Code").focus();
	});
}

//모달창내 수정버튼
$("#customerModifyBtn").click(function(){
	if(confirm("수정 하시겠습니까?")){
		customerModify();
	}
})

function customerModify() {
	var datas = {
		cus_Code: $("#cus_Code").val(),
		cus_Name: $("#cus_Name").val(),
		cus_Status: $("#cus_Status").val(),
		cus_Clsfc: $("#cus_Clsfc").val(),
		cus_Rprsn: $("#cus_Rprsn").val(),
		cus_Mng: $("#cus_Mng").val(),
		cus_Co: $("#cus_Co").val(),
		cus_Co_EstYr: $("#cus_Co_EstYr").val(),
		cus_Rprsn_PhNr: $("#cus_Rprsn_PhNr").val(),
		cus_Mng_PhNr: $("#cus_Mng_PhNr").val(),
		cus_Mng_Email: $("#cus_Mng_Email").val(),
		cus_Adr: $("#cus_Adr").val(),
		cus_Pymn_Date: $("#cus_Pymn_Date").val(),
		cus_Rgstr_Nr: $("#cus_Rgstr_Nr").val()
	};

	$.ajax({
		method: "put",
		data: datas,
		url: "customerManageRest/customerManageUpdate",
		beforeSend: function (xhr) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           xhr.setRequestHeader(header, token);
		},
		success : function(data) {
			if (data) {
				alert("저장 되었습니다.");
				customerManageTable.replaceData();
				
				$("#customerManageModal").modal("hide");
			}else{
				alert("오류가 발생했습니다.");
			}
		}
	});
}

// delete버튼을 클릭을 할때 모달창을 여는 이벤트
$("#customerDeleteBtn").click(function() {
	var selectedRow = customerManageTable.getData("selected");
	
	if(selectedRow.length == 0){
		alert("삭제할 행을 선택하세요.");
	}else{
		modifyModalShow();		
	}
});

//모달창내 삭제버튼
$("#customerRemoveBtn").click(function(){
	if(confirm("삭제 하시겠습니까?")){
		customerRemove();	
	}
})

function customerRemove() {
	
	var datas = {
		cus_Code: $("#cus_Code").val()
	};

	$.ajax({
		method: "delete",
		data: datas,
		url: "customerManageRest/customerManageDelete",
		beforeSend: function (xhr) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           xhr.setRequestHeader(header, token);
		},
		success : function(data) {
			if (data) {
				alert("삭제 되었습니다.");
				customerManageTable.replaceData();
				
				$("#customerManageModal").modal("hide");
			}else{
				alert("오류가 발생했습니다.");
			}
		}
	});
}