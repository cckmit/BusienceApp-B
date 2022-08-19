//커스텀 기능설정
var MIRI_InputEditor = function(cell, onRendered, success, cancel, editorParams) {
	//cell - 편집 가능한 셀의 셀 구성 요소
	//onRendered - 에디터가 렌더링 되었을 때 호출 할 함수
	//success - 성공적을 업데이트 된 값을 tabulator에 전달하기 위해 호출되는 함수
	//cancel - 편집을 중단하고 일반 셀로 돌아 가기 위해 호출하는 함수
	//editorParams - editorParams 열 정의 속성에 전달 된 params 객체

	//create 및 style editor
	var input = document.createElement("input");

	input.setAttribute("type", "text");

	//입력 생성 및 스타일 지정
	input.style.padding = "3px";
	input.style.width = "100%";
	input.style.boxSizing = "border-box";

	//편집기의 값을 셀의 현재 값으로 설정
	if (cell.getValue() == undefined) {
		input.value = "";
	} else {
		input.value = cell.getValue();
	}

	//에디터가 선택되면 선택 상자에 포커스 설정 (타임 아웃은 편집기를 DOM에 추가 할 수 있음)
	onRendered(function() {
		input.focus();
		input.select();
		input.style.css = "100%";
	});

	//값이 설정되면 업데이트 할 셀 트리거
	function onChange() {
		success(input.value);
	}

	//바꼈을때 블러됫을때 함수 발동
	input.addEventListener("change", onChange);
	input.addEventListener("blur", onChange);

	//키버튼 이벤트
	input.addEventListener("keydown", function(e) {
		//엔터쳤을떄
		if (e.keyCode == 13) {
			//반품수량셀 채크
			if (cell.getField() == "inReturn_Qty") {
				//입력값이 양의 정수가 아니거나 입고수량보다 높으면 안내문
				if (!(input.value >= 0) || cell.getRow().getData().InMat_Qty < input.value) {
					alert("반품 수량을 잘못입력하였습니다.");
					input.value = 0;
				}
				//입력값이 0이 아닌경우 (반품수량을 입력했을경우) 셀 선택
				if (input.value != 0) {
					cell.getRow().select();
				}
				//다음셀로
				cell.nav().next();
			}
		}
	});
	//반환
	return input;
};

var matInReturnInsertTable = new Tabulator("#matInReturnInsertTable", {
	layoutColumnsOnNewData: true,
	selectable : true,
	selectableRangeMode:"click",
	height: "calc(100% - 175px)",
	rowDblClick: function(e, row) {
		row.toggleSelect();
	},
	ajaxResponse:function(url, params, response){
		if(response.length == 0){
			toastr.info("목록이 없습니다.");	
		}
		return response;
    },
	columns: [
		{ formatter: "rowSelection", titleFormatter: "rowSelection", headerHozAlign: "center", hozAlign: "center", headerSort: false },
		{ title: "순번", field: "rownum", headerHozAlign: "center", align: "center", formatter: "rownum"},
		{ title: "발주No", field: "inMat_Order_No", headerHozAlign: "center" },
		{ title: "품목코드", field: "inMat_Code", headerHozAlign: "center" },
		{ title: "품목명", field: "inMat_Name", headerHozAlign: "center" },
		{ title: "입고수량", field: "inMat_Qty", align: "right", headerHozAlign: "center"},
		{ title: "단가", field: "inMat_Unit_Price", align: "right", headerHozAlign: "center", formatter : "money", formatterParams: { precision: false }},
		{ title: "반품수량", field: "inReturn_Qty", align: "right", editor: MIRI_InputEditor, headerHozAlign: "center"},
		{ title: "거래처코드", field: "inMat_Client_Code", visible: false },
		{ title: "거래처명", field: "inMat_Client_Name", headerHozAlign: "center" },
		{ title: "입고구분", field: "inMat_Rcv_Clsfc_Name", headerHozAlign: "center" },
		{ title: "입고일", field: "inMat_Date", headerHozAlign: "center" }
	]
});

$("#MIRI_SearchBtn").click(function(){
	MIRI_Search()
})

// matInReturn 목록 검색
function MIRI_Search() {
	
	var data = {
		itemCode: $('#PRODUCT_ITEM_CODE1').val(),
		ClientCode: $("#Client_Code1").val(),
	}
	
	matInReturnInsertTable.setData("matInReturnRest/MIRI_Search", data);
}

$("#MIRI_SaveBtn").click(function(){
	MIRI_Save()
})

//MIRI_Save
function MIRI_Save() {
	//선택된 행만 저장
	//만약 선택된행에서 반품수량이 0 이면 저장 안함
	var selectedData = matInReturnInsertTable.getData("selected");

	// 선택한 행이 있을경우에 저장이 가능하다.
	if (selectedData.length == 0) {
		alert("저장할 목록이 없습니다. 행을 선택해 주세요.");
		return;
	}

	//반품수량이 0 인경우
	for (var i = 0; i < selectedData.length; i++) {
		if (selectedData[i].inReturn_Qty == "0") {
			alert("반품수량이 입력되지 않은 행이 존재합니다.");
			return;
		}
	}
	
	//반품수량이 재고수량보다 클 경우
	for (var i = 0; i < selectedData.length; i++) {
		if (selectedData[i].inReturn_Qty > selectedData[i].inMat_Qty) {
			alert("반품수량이 재고수량보다 많습니다.");
			return;
		}
	}

	$.ajax({
		method: "post",
		url: "matInReturnRest/MIRI_Save",
		data: JSON.stringify(selectedData),
		contentType:'application/json',
		beforeSend: function (xhr) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           xhr.setRequestHeader(header, token);
		},
		success: function(result) {
			if (result) {
				MIRI_Search();
				alert("반품 처리 되었습니다.");
			} else {
				alert("중복된 값이 있습니다.");
			}
		}
	});
}

var matInReturnSearchTable = new Tabulator("#matInReturnSearchTable", {
	height: "calc(100% - 175px)",
	layoutColumnsOnNewData: true,
	rowDblClick: function(e, row) {
		row.toggleSelect();
	},
	ajaxResponse:function(url, params, response){
		if(response.length == 0){
			toastr.info("목록이 없습니다.");	
		}
		return response;
    },
	columns: [
		{ title: "순번", field: "rownum", headerHozAlign: "center", align: "center", formatter: "rownum"},
		{ title: "발주번호", field: "inMat_Order_No", headerHozAlign: "center" },
		{ title: "Lot번호", field: "inMat_Lot_No", headerHozAlign: "center", hozAlign: "left"},
		{ title: "품목코드", field: "inMat_Code", headerHozAlign: "center" },
		{ title: "품목명", field: "inMat_Name", headerHozAlign: "center"},
		{ title: "규격1", field: "inMat_STND_1", headerHozAlign: "center", hozAlign: "left"},
		{ title: "규격2", field: "inMat_STND_2", headerHozAlign: "center", hozAlign: "left"},
		{ title: "단위", field: "inMat_UNIT", headerHozAlign: "center", hozAlign: "left" },
		{ title: "반품수량", field: "inMat_Qty", align: "right", headerHozAlign: "center"},
		{ title: "단가", field: "inMat_Unit_Price", align: "right", headerHozAlign: "center", formatter : "money", formatterParams: { precision: false } },
		{ title: "금액", field: "inMat_Price", align: "right", headerHozAlign: "center", formatter : "money", formatterParams: { precision: false } },
		{ title: "거래처명", field: "inMat_Client_Name", headerHozAlign: "center"},
		{ title: "입고구분", field: "inMat_Rcv_Clsfc_Name", headerHozAlign: "center" },
		{ title: "반품일", field: "inMat_Date", headerHozAlign: "center" },
		{ title: "작업자명", field: "inMat_Modifier", headerHozAlign: "center" }	
	]
});


function MIRS_Search() {
	var datas = {
		startDate: $("#MIRS_startDate").val(),
		endDate: $("#MIRS_endDate").val(),
		itemCode: $("#PRODUCT_ITEM_CODE2").val(),
		clientCode: $("#Client_Code2").val(),
		ItemSendClsfc: "207"
	}
	matInReturnSearchTable.setData("matInputRest/MIL_Search", datas)
}
