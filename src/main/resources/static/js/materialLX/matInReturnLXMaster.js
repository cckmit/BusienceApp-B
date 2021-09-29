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
				console.log(cell.getValue());
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

var selectedData = null;
var InMat_Order_No = null;
var matInReturnInsertTable = new Tabulator("#matInReturnInsertTable", {
	//페이징
	pagination: "local",
	paginationSize: 20,
	paginationAddRow: "table",
	layoutColumnsOnNewData: true,
	height: "calc(100% - 175px)",
	//복사하여 엑셀 붙여넣기 가능
	clipboard: true,
	rowDblClick: function(e, row) {
		row.toggleSelect();
	},

	columns: [
		{ formatter: "rowSelection", titleFormatter: "rowSelection", headerHozAlign: "center", hozAlign: "center", headerSort: false },
		{ title: "순번", field: "id", headerHozAlign: "center", align: "center" },
		{ title: "발주No", field: "inMat_Order_No", headerHozAlign: "center" },
		{ title: "품목코드", field: "inMat_Code", headerHozAlign: "center" },
		{ title: "품목명", field: "inMat_Name", headerHozAlign: "center" },
		{ title: "입고수량", field: "inMat_Qty", align: "right", headerHozAlign: "center" },
		{ title: "반품수량", field: "inReturn_Qty", align: "right", editor: MIRI_InputEditor, headerHozAlign: "center" },
		{ title: "단가", field: "inMat_Unit_Price", align: "right", headerHozAlign: "center" },
		{ title: "금액", field: "inMat_Price", align: "right", headerHozAlign: "center" },
		{ title: "재고수량", field: "inMat_Stock_Qty", visible: false },
		{ title: "거래처코드", field: "inMat_Client_Code", visible: false },
		{ title: "입고거래처", field: "inMat_Client_Name", headerHozAlign: "center" },
		{ title: "입고구분", field: "inMat_Rcv_Clsfc_Name", headerHozAlign: "center" },
		{ title: "입고일", field: "inMat_Date", headerHozAlign: "center" },
		{ title: "데이터삽입시간", field: "inMat_dInsert_Time", headerHozAlign: "center" },
		{ title: "작업자명", field: "inMat_Modifier", headerHozAlign: "center" }
	]
});

// matInReturn 목록 검색
function MIRI_Search() {
	
	var stockQty = null;
		
	var data = {
		inMat_Code: $('#PRODUCT_ITEM_CODE1').val(),
		inMat_Client_Code: $("#Client_Code1").val(),
	}
	
	if(data.inMat_Code == null || data.inMat_Code == ''){
		alert("제품코드를 입력해 주세요.");
		return false;
	}
	if(data.inMat_Client_Code == null || data.inMat_Client_Code == ''){
		alert("거래처코드를 입력해 주세요.");
		return false;
	}
	
	$.ajax({
		method: "GET",
		dataType: "json",
		async: false,
		url: "matInReturnLXRest/MIRI_Search?data=" + encodeURI(JSON.stringify(data)),
		success: function(datas) {
			console.log(datas);
			matInReturnInsertTable.setData(datas);
			for(i=0; i<datas.length;i++) {
				stockQty = datas[i].inMat_Stock_Qty;
				//console.log(datas[i].inMat_Stock_Qty);
			}
			$('#MIRI_stockQty').val(stockQty);
		}
	});
}

//MIRI_Save
function MIRI_Save(selectedData) {
	//선택된 행만 저장
	//만약 선택된행에서 반품수량이 0 이면 저장 안함
	selectedData = matInReturnInsertTable.getData("selected");

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
		if (selectedData[i].inReturn_Qty > selectedData[i].inMat_Stock_Qty) {
			alert("반품수량이 재고수량보다 많습니다.");
			return;
		}
	}

	$.ajax({
		method: "get",
		url: "matInReturnLXRest/MIRI_Save?data=" + encodeURI(JSON.stringify(selectedData)),
		success: function(result) {
			if (result == "error") {
				alert("중복된 값이 있습니다.");
			} else if (result == "success") {
				MIRI_Search();
				alert("반품 처리 되었습니다.");
			}
		}
	});
}

var matInReturnSearchTable = new Tabulator("#matInReturnSearchTable", {
	//페이징
	pagination: "local",
	paginationSize: 20,
	paginationAddRow: "table",
	height: "calc(100% - 175px)",
	layoutColumnsOnNewData: true,
	//복사하여 엑셀 붙여넣기 가능
	clipboard: true,
	rowDblClick: function(e, row) {
		row.toggleSelect();
	},
	columns: [
		{ title: "순번", field: "id", headerHozAlign: "center", align: "center" },
		{ title: "발주No", field: "inMat_Order_No", headerHozAlign: "center" },
		{ title: "품목코드", field: "inMat_Code", headerHozAlign: "center" },
		{ title: "품목명", field: "inMat_Name", headerHozAlign: "center" },
		{ title: "반품수량", field: "inReturn_Qty", align: "right", headerHozAlign: "center" },
		{ title: "단가", field: "inMat_Unit_Price", align: "right", headerHozAlign: "center" },
		{ title: "금액", field: "inMat_Price", align: "right", headerHozAlign: "center" },
		{ title: "입고거래처", field: "inMat_Client_Name", headerHozAlign: "center" },
		{ title: "입고구분", field: "inMat_Rcv_Clsfc_Name", headerHozAlign: "center" },
		{ title: "입고일", field: "inMat_Date", headerHozAlign: "center" },
		{ title: "반품일", field: "inMat_dInsert_Time", headerHozAlign: "center" },
		{ title: "작업자명", field: "inMat_Modifier", headerHozAlign: "center" }
	]
});


function MIRS_Search() {
	data = {
		startDate: $("#MIRS_startDate").val(),
		endDate: $("#MIRS_endDate").val(),
		inMat_Code: $('#PRODUCT_ITEM_CODE2').val(),
		inMat_Client_Code: $("#Client_Code2").val()
	}

	$.ajax({
		method: "GET",
		dataType: "json",
		url: "matInReturnLXRest/MIRS_Search?data=" + encodeURI(JSON.stringify(data)),
		success: function(data) {
			console.log(data)
			matInReturnSearchTable.setData(data);
		}
	});
}

function MIRS_STOCK() {
	
}


