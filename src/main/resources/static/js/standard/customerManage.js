var nowModal

//입력 및 업데이트 할 리스트
var pickValue = ["cus_Code", "cus_Name", "cus_Clsfc",
					"cus_Rprsn", "cus_Rprsn_PhNr", "cus_Rprsn_Email",
					"cus_Mng", "cus_Mng_PhNr", "cus_Mng_Email",
					"cus_Co", "cus_Co_EstYr", "cus_Adr",
					"cus_Pymn_Date", "cus_Status", "cus_Bank", "cus_Account_No",
					"cus_Rgstr_Nr"];


if(pickValue != null){
	for(let i=0;i<pickValue.length;i++){
		$("#"+pickValue[i]).keydown(function(e){
			if(e.keyCode == 13){
				if(i != pickValue.length-1){
					$("#"+pickValue[i+1]).focus();
				}else{
					$(".focus"+nowModal).focus();
				}
			}
		})
	}
}

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
		{ title: "코드", field: "cus_Code", headerHozAlign: "center", headerFilter: "input"},
		{ title: "거래처명", field: "cus_Name", headerHozAlign: "center", headerFilter: "input"},
		{ title: "분류",	field: "cus_Clsfc_Name", headerHozAlign: "center", headerFilter: "input"},
		{ title: "대표자", field: "cus_Rprsn", headerHozAlign: "center", headerFilter: "input"},
		{ title: "대표전화번호", field: "cus_Rprsn_PhNr", headerHozAlign: "center", headerFilter: "input"},
		{ title: "대표 이메일", field: "cus_Rprsn_Email", headerHozAlign: "center", headerFilter: "input"},
		{ title: "담당자", field: "cus_Mng", headerHozAlign: "center", headerFilter: "input"},
		{ title: "담당전화번호", field: "cus_Mng_PhNr", headerHozAlign: "center", headerFilter: "input"},
		{ title: "담당 이메일", field: "cus_Mng_Email", headerHozAlign: "center", headerFilter: "input"},
		{ title: "간략업체명", field: "cus_Co", headerHozAlign: "center", headerFilter: "input"},
		{ title: "주소", field: "cus_Adr", headerHozAlign: "center", headerFilter: "input"},
		{ title: "설립연도", field: "cus_Co_EstYr", headerHozAlign: "center", headerFilter: "input"},
		{ title: "결제일", field: "cus_Pymn_Date_Name", headerHozAlign: "center", headerFilter: "input"},
		{ title: "결제방법", field: "cus_Status_Name", headerHozAlign: "center", headerFilter: "input"},
		{ title: "결제은행", field: "cus_Bank", headerHozAlign: "center", headerFilter: "input"},
		{ title: "계좌번호", field: "cus_Account_No", headerHozAlign: "center", headerFilter: "input"},
		{ title: "사업자등록번호", field: "cus_Rgstr_Nr", headerHozAlign: "center", headerFilter: "input"},
		{ title: "수정일자", field: "cus_Modify_D", headerHozAlign: "center", hozAlign: "right", headerFilter: "input",
				formatter: "datetime", formatterParams: { outputFormat: "YYYY-MM-DD HH:mm:ss" }},
		{ title: "수정자", field: "cus_Modifier", headerHozAlign: "center", headerFilter: "input"}
	]
});

// ADD버튼을 클릭할때 모달창을 여는 이벤트
$("#customerADDBtn").click(function() {
	customerManageTable.deselectRow();
	registerModalShow()
});

function registerModalShow(){
	nowModal = "insert"
	
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
	
	var datas = fromInputToJson(pickValue)
		
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
	nowModal = "modify"
	
	$('.insert').addClass('none');
	
	if ($('.modify').hasClass('none')) {
		$('.modify').removeClass('none');
	}
	$("#cus_Code").attr('readonly', 'readonly');
	
	$("#customerManageModal").modal("show").on("shown.bs.modal", function () {
		$("#cus_Name").focus();
	});
}

//모달창내 수정버튼
$("#customerModifyBtn").click(function(){
	if(confirm("수정 하시겠습니까?")){
		customerModify();
	}
})

function customerModify() {
	
	var datas = fromInputToJson(pickValue)

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
		},
		error:function(request,status,error){
        alert("사용 중인 코드는 삭제할 수 없습니다.");
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
	
	var datas = fromInputToJson(pickValue)

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