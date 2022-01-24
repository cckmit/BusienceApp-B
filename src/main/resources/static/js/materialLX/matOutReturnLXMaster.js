//커스텀 기능설정
var MORI_InputEditor = function(cell, onRendered, success, cancel, editorParams){
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
	if(cell.getValue() == undefined){
		input.value = "";
	}else{
		input.value = cell.getValue();
	}

    //에디터가 선택되면 선택 상자에 포커스 설정 (타임 아웃은 편집기를 DOM에 추가 할 수 있음)
    onRendered(function(){
        input.focus();
		input.select();
        input.style.css = "100%";
    });

    //값이 설정되면 업데이트 할 셀 트리거
    function onChange(){
		success(input.value);   
    }
    
	//바꼈을때 블러됫을때 함수 발동
    input.addEventListener("change", onChange);
	input.addEventListener("blur", onChange);

    //키버튼 이벤트
    input.addEventListener("keydown", function (e) {
		//엔터쳤을떄
		if(e.keyCode == 13){
			//반품수량셀 채크
			if (cell.getField() == "inReturn_Qty") {
				console.log(cell.getValue());
				//입력값이 양의 정수가 아니거나 입고수량보다 높으면 안내문
				if(!(input.value >= 0) || cell.getRow().getData().InMat_Qty < input.value){
					alert("반품 수량을 잘못입력하였습니다.");
					input.value = 0;
				}
				//입력값이 0이 아닌경우 (반품수량을 입력했을경우) 셀 선택
				if(input.value != 0){
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

var matOutReturnInsertTable = new Tabulator("#matOutReturnInsertTable", {
	//페이징
	pagination: "local",
	paginationSize: 20,
	paginationAddRow: "table",
	height: "calc(100% - 175px)",
	//복사하여 엑셀 붙여넣기 가능
	clipboard: true,
	rowDblClick: function(e, row) {
		row.toggleSelect();
	},

	columns: [
		{ formatter: "rowSelection", align: "center"},
		{ title: "순번", field: "outMat_No", headerHozAlign: "center", align: "center" },
		{ title: "품목코드", field: "outMat_Code", headerHozAlign: "center" },
		{ title: "품목명", field: "outMat_Name", headerHozAlign: "center" },
		{ title: "출고수량", field: "outMat_Qty", align: "right", headerHozAlign: "center" },
		{ title: "반품수량", field: "outReturn_Qty", align: "right", editor: MORI_InputEditor, headerHozAlign: "center"},
		{ title: "출고구분", field: "outMat_Send_Clsfc_Name", headerHozAlign: "center" },
		{ title: "수취인코드", field: "outMat_Consignee", headerHozAlign: "center", visible:false},
		{ title: "수취인", field: "outMat_Consignee_Name", headerHozAlign: "center" },
		{ title: "부서코드", field: "outMat_Dept_Code", headerHozAlign: "center", visible:false},
		{ title: "부서명", field: "outMat_Dept_Name", headerHozAlign: "center" },
		{ title: "출고일", field: "outMat_Date", headerHozAlign: "center", formatter: "datetime", formatterParams : {outputFormat : "YYYY-MM-DD HH:mm:ss"}},
		{ title: "데이터삽입시간", field: "outMat_dInsert_Time", headerHozAlign: "center",
			formatter: "datetime", formatterParams : {outputFormat : "YYYY-MM-DD HH:mm:ss"}},
		{ title: "작업자명", field: "outMat_Modifier", headerHozAlign: "center" }
	]
});

// matOutReturn 목록 검색
function MORI_Search() {
	data = {
		startDate: $("#MORI_startDate").val(),
		endDate: $("#MORI_endDate").val(),
		outMat_Code: $("#PRODUCT_ITEM_CODE1").val()
	}

	$.ajax({
		method: "GET",
		dataType: "json",
		url: "matOutReturnLXRest/MORI_Search?data=" + encodeURI(JSON.stringify(data)),
		success: function(data) {
			console.log(data)
			matOutReturnInsertTable.setData(data);
		}
	});
}

function MORI_Save() {
	//선택된 행만 저장
	//만약 선택된행에서 반품수량이 0 이면 저장 안함
	selectedData = matOutReturnInsertTable.getData("selected");
	console.log("selectedData = " + selectedData);

	if (selectedData.length == 0) {
		alert("저장할 목록이 없습니다. 행을 선택해 주세요.");
		return;
	}

	for (var i = 0; i < selectedData.length; i++) {
		if (selectedData[i].outReturn_Qty == "0") {
			alert("반품수량이 입력되지 않은 행이 존재합니다.");
			return;
		}
	}

	$.ajax({
		method: "GET",
		url: "matOutReturnLXRest/MORI_Save?data=" + encodeURI(JSON.stringify(selectedData)),
		success: function(result) {
			if(result == "error"){
				alert("중복된 값이 있습니다.");
			}else if(result == "success"){
				MORI_Search();
				alert("반품 처리 되었습니다.");
			}
		}
	});

}

var matOutReturnSearchTable = new Tabulator("#matOutReturnSearchTable", {
	//페이징
	pagination: "local",
	paginationSize: 20,
	paginationAddRow: "table",
	height: "calc(100% - 175px)",
	//복사하여 엑셀 붙여넣기 가능
	clipboard: true,
	rowDblClick: function(e, row) {
		row.toggleSelect();
	},
	columns: [
		{ title: "순번", field: "outMat_No", headerHozAlign: "center", align: "center" },
		{ title: "품목코드", field: "outMat_Code", headerHozAlign: "center" },
		{ title: "품목명", field: "outMat_Name", headerHozAlign: "center" },
		{ title: "반품수량", field: "outReturn_Qty", align: "right", headerHozAlign: "center" },
		{ title: "출고구분", field: "outMat_Send_Clsfc_Name", headerHozAlign: "center" },
		{ title: "수취인코드", field: "outMat_Consignee", headerHozAlign: "center", visible:false},
		{ title: "수취인", field: "outMat_Consignee_Name", headerHozAlign: "center" },
		{ title: "부서코드", field: "outMat_Dept_Code", headerHozAlign: "center", visible:false},
		{ title: "부서명", field: "outMat_Dept_Name", headerHozAlign: "center" },
		{ title: "출고일", field: "outMat_Date", headerHozAlign: "center",
			formatter: "datetime", formatterParams : {outputFormat : "YYYY-MM-DD HH:mm:ss"}},
		{ title: "반품일", field: "outMat_dInsert_Time", headerHozAlign: "center",
			formatter: "datetime", formatterParams : {outputFormat : "YYYY-MM-DD HH:mm:ss"}},
		{ title: "작업자명", field: "outMat_Modifier", headerHozAlign: "center" }
	]
});

// 출고반품조회 검색
function MORS_Search() {
	data = {
		startDate: $("#MORS_startDate").val(),
		endDate: $("#MORS_endDate").val(),
		outMat_Code: $("#PRODUCT_ITEM_CODE2").val()
	}


	$.ajax({
		method: "GET",
		dataType: "json",
		url: "matOutReturnLXRest/MORS_Search?data=" + encodeURI(JSON.stringify(data)),
		success: function(data) {
			console.log(data)
			matOutReturnSearchTable.setData(data);
		}
	});
}

var matOutReturnSalesListTable = new Tabulator("#matOutReturnSalesListTable", {
	//페이징
	pagination: "local",
	paginationSize: 20,
	paginationAddRow: "table",
	height: "calc(50% - 100px)",
	//복사하여 엑셀 붙여넣기 가능
	clipboard: true,
	rowDblClick: function(e, row) {
		for(i=0;i<matOutReturnSalesDeliveryTable.getData().length;i++){
			if(matOutReturnSalesDeliveryTable.getData()[i].sales_Lot_No == row.getData().inMat_Lot_No){
				return false;
			}
		}
		row.toggleSelect();
		matOutReturnSalesDeliveryTable.addRow({sales_Lot_No : row.getData().inMat_Lot_No,
												sales_Code : row.getData().inMat_Code,
												sales_Name : row.getData().inMat_Name,
												sales_Client_Code : row.getData().inMat_Client_Code,
												sales_Client_Name : row.getData().inMat_Client_Name,
												outMat_Consignee : 1,
												outMat_Dept_Code : 12,
												inMat_Qty : row.getData().inMat_Qty,
												sales_Qty : 0,
												sales_Unit_Price : row.getData().inMat_Unit_Price,
												sales_Price : 0,
												inMat_Date : row.getData().inMat_Date})
	},
	columns: [
		{ title: "순번", field: "inMat_No", headerHozAlign: "center", align: "center" },
		{ title: "발주No", field: "inMat_Order_No", headerHozAlign: "center" },
		{ title: "품목코드", field: "inMat_Code", headerHozAlign: "center" },
		{ title: "품목명", field: "inMat_Name", headerHozAlign: "center" },
		{ title: "재고수량", field: "inMat_Qty", align: "right", headerHozAlign: "center" },
		{ title: "단가", field: "inMat_Unit_Price", align: "right", headerHozAlign: "center", formatter: "money", formatterParams: { precision: false } },
		{ title: "금액", field: "inMat_Price", align: "right", headerHozAlign: "center", formatter: "money", formatterParams: { precision: false } },
		{ title: "입고거래처코드", field: "inMat_Client_Code", visible:false},
		{ title: "입고거래처", field: "inMat_Client_Name", headerHozAlign: "center" },
		{ title: "입고구분", field: "inMat_Rcv_Clsfc_Name", headerHozAlign: "center" },
		{ title: "입고일", field: "inMat_Date", headerHozAlign: "center",	
			formatter: "datetime", formatterParams : {outputFormat : "YYYY-MM-DD HH:mm:ss"}},
		{ title: "데이터삽입시간", field: "inMat_dInsert_Time", headerHozAlign: "center",
			formatter: "datetime", formatterParams : {outputFormat : "YYYY-MM-DD HH:mm:ss"}},
		{ title: "작업자명", field: "inMat_Modifier", headerHozAlign: "center" }
	]
});