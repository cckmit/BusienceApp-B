var matOutputTable = null;
var itemCode = null;
var outputTable = new Tabulator("#outputTable", {
	//페이징
	pagination: "local",
	paginationSize: 20,
	paginationAddRow : "table",
	height: "calc(100% - 175px)",
	layoutColumnsOnNewData: true,
	headerFilterPlaceholder: null,
	//복사하여 엑셀 붙여넣기 가능
	clipboard: true,
	rowFormatter: function(row) {
		//입고수량이 수량이상이면 글자색을 빨간색으로 바꿔준다
		if (row.getData().sm_Last_Qty == 0) {
			row.getElement().style.color = "red";
		}
	},
	//행클릭 이벤트
	rowClick: function(e, row) {
		outputTable.deselectRow();
		row.select();
		
	},
	rowSelected: function(row) {

		MOM_Search(row.getData().sm_Code);
		UseBtn();


	},
	rowDeselected: function(row) {

		dataCount = matOutputTable.getDataCount();
		console.log("dataCount = " + dataCount);
		console.log("deselected");
		//클릭한 행과 같은 랏번호를 찾아서 삭제해줌
		for (i = 0; i < matOutputTable.getDataCount(); i++) {
			if (matOutputTable.getData()[i].outMat_Code == row.getData().sm_Code) {
				matOutputTable.getRows()[i].delete()
					.then(function() {
						// 삭제후 순번 정리matOutputTable
						rowCount = matOutputTable.getDataCount("active");
						for (j = 0; j < rowCount; j++) {
							matOutputTable.getRows()[j].update({ outMat_No: j + 1 })
						}
					});
				break;
			}
		}
	},
	columns: [
		{ title: "순번", field: "id", headerHozAlign: "center", hozAlign: "center"},
		{ title: "코드", field: "sm_Code", headerHozAlign: "center", headerFilter:true},
		{ title: "품목명", field: "sm_Name", headerHozAlign: "center", hozAlign: "right", headerFilter:true },
		{ title: "출고수량", field: "sm_Out_Qty", headerHozAlign: "center", hozAlign: "right", headerFilter:true },
		{ title: "재고", field: "sm_Last_Qty", headerHozAlign: "center", headerFilter:true }]
});


//orderMaster 목록검색 matOrder와 동일하지만 테이브이름이 다르고, 미입고 컬럼이 추가됨
function MOS_Search() {

	data = {
		SM_Code : outputTable.getData("selected").sm_Code,
		itemCode : $("#outmatLX_itemCode").val()
	}
	
	$.ajax({
		method: "GET",
		dataType: "json",
		async: false,
		url: "matOutputLXRest/MOS_Search?data=" + encodeURI(JSON.stringify(data)),
		success: function(data) {
			console.log(data);
			outputTable.setData(data);
			SM_Code = outputTable.getData("selected").sm_Code;
			//console.log("sm_code val = " + SM_Code);
			MOM_Search(SM_Code);
			//matOutputTable.clearData();
		}
	});
}

$('#MR_SearchBtn').click(function() {
	MOS_Search();
})



// 입고구분 select를 구성하기위한 ajax
var dtl_arr = new Object();

// 매니저명 select를 구성하기 위한 ajax
var manager_arr = new Object();

$.ajax({
	method: "GET",
	async: false,
	url: "dtl_tbl_select?NEW_TBL_CODE=18",
	success: function(datas) {
		for (i = 0; i < datas.length; i++) {
			dtl_arr[datas[i].child_TBL_NO] = datas[i].child_TBL_TYPE;
		}
	}
});

$.ajax({
	method: "GET",
	async: false,
	url: "manager_select?NEW_TBL_CODE=1",
	success: function(datas) {
		console.log("매니저 조회 완료");
		for (i = 0; i < datas.length; i++) {
			manager_arr[datas[i].child_TBL_NO] = datas[i].child_TBL_TYPE;
		}
	}
});

//matInputSub 커스텀 기능설정
var MOM_InputEditor = function(cell, onRendered, success, cancel, editorParams) {
	//cell - 편집 가능한 셀의 셀 구성 요소
	//onRendered - 에디터가 렌더링 되었을 때 호출 할 함수
	//success - 성공적을 업데이트 된 값을 tabulator에 전달하기 위해 호출되는 함수
	//cancel - 편집을 중단하고 일반 셀로 돌아 가기 위해 호출하는 함수
	//editorParams - editorParams 열 정의 속성에 전달 된 params 객체

	//create 및 style editor
	var MOM_input = document.createElement("input");

	MOM_input.setAttribute("type", "text");

	//입력 생성 및 스타일 지정
	MOM_input.style.padding = "3px";
	MOM_input.style.width = "100%";
	MOM_input.style.boxSizing = "border-box";

	//편집기의 값을 셀의 현재 값으로 설정
	if (cell.getValue() == undefined) {
		MOM_input.value = "";
	} else {
		MOM_input.value = cell.getValue();
	}

	//에디터가 선택되면 선택 상자에 포커스 설정 (타임 아웃은 편집기를 DOM에 추가 할 수 있음)
	onRendered(function() {
		MOM_input.focus();
		MOM_input.select();
		MOM_input.style.css = "100%";
	});

	//값이 설정되면 업데이트 할 셀 트리거
	function onChange() {
		success(MOM_input.value);
	}

	//바꼈을때 블러됫을때 함수 발동
	MOM_input.addEventListener("change", onChange);
	MOM_input.addEventListener("blur", onChange);

	//키버튼 이벤트
	MOM_input.addEventListener("keydown", function(e) {
		//품목코드 팝업창
		if (e.keyCode == 13) {
			//단가셀 체크
			if (cell.getField() == "inMat_Unit_Price") {
				//선택되있는 행중 마지막행의 순번 == 선택할 다음행의 인덱스값
				nextRow_No = matInputSubTable.getData("selected")[matInputSubTable.getDataCount("selected") - 1].order_lNo;
				nextRow = matInputSubTable.getRows()[nextRow_No];
				if (nextRow) {
					matInputSubTable.selectRow(nextRow);
				}
			}
		}
	});
	//반환
	return MOM_input;
};


//inMatTable 이미 저장되있는 데이터는 편집 불가능 하게 하는 확인 기능
var editCheck = function(cell) {
	//cell - the cell component for the editable cell
	//get row data
	var data = cell.getRow().getData();
	return data.outMat_Date == null;
}

var matOutputTable = new Tabulator("#matOutputTable", {
	height: "calc(100% - 175px)",
	layoutColumnsOnNewData: true,
	//복사하여 엑셀 붙여넣기 가능
	clipboard: true,
	//커스텀 키 설정
	keybindings: {
		"navNext": "13"
	},
	rowAdded: function(row) {
		//console.log("추가됨");
		row.select();

		//console.log("outputTable 추가 = " + outputTable.getData("selected"));
		
		row.update({
			"id": matOutputTable.getDataCount(),
			"outMat_Code": outputTable.getData("selected")[0].sm_Code,
			"outMat_Qty": 0,
			"outMat_Consignee": '',
			"outMat_Send_Clsfc": ''
		});
		
		
		//행이 추가되면 첫셀에 포커스
		do {
			setTimeout(function() {
				row.getCell("outMat_Qty").edit();
				matOutputTable.deselectRow();
				row.select();
			}, 100);
		}
		while (row.getData().outMat_Qty === "0");

	},
	rowClick: function(e, row) {
		matOutputTable.deselectRow();
		row.select();
	},
	//order_lNo를 인덱스로 설정
	index: "inMat_No",
	columns: [
		{ title: "순번", field: "id", headerHozAlign: "center", hozAlign: "center" },
		{ title: "코드", field: "outMat_Code", headerHozAlign: "center" },
		{ title: "수량", field: "outMat_Qty", headerHozAlign: "center", hozAlign: "right", editor: MOM_InputEditor, editable: editCheck },
		{
			title: "수취인", field: "outMat_Consignee", headerHozAlign: "center", hozAlign: "left", editor: "select",  editable: editCheck,
			formatter: function(cell, formatterParams) {
				var value = cell.getValue();
				if (manager_arr[value] != null) {
					value = manager_arr[value];
				} else {
					value = "";
				}
				return value;
			}, editorParams: { values: manager_arr }
		},
		{
			title: "구분", field: "outMat_Send_Clsfc", headerHozAlign: "center", editor: "select", editable: editCheck, width: 65,
			formatter: function(cell, formatterParams) {
				var value = cell.getValue();
				if (dtl_arr[value] != null) {
					value = dtl_arr[value];
				} else {
					value = "";
				}
				return value;
			},
			editorParams: { values: dtl_arr }
		},
		{ title: "출고일자", field: "outMat_Date", headerHozAlign: "center", hozAlign: "right", width: 125, formatter: "datetime", formatterParams : {outputFormat : "YYYY-MM-DD HH:mm:ss" }}]
});

//inMatTable에 있던 데이터의 갯수
var MIM_preData = 0;

//outMat 목록검색
function MOM_Search(sm_Code) {
	console.log("sm_Code = " + sm_Code);
	$("#outmatLX_itemCode").val(sm_Code);

	$.ajax({
		method: "GET",
		url: "matOutputLXRest/MOM_Search?sm_Code=" + sm_Code,
		success: function(MIM_datas, row) {
			matOutputTable.setData(MIM_datas);
			console.log(MIM_datas);
			matOutputTable.addRow();
		}
	});
}

//inMatTable 저장
function MOM_Save() {
	rowData = matOutputTable.getData("selected");
	console.log("rowData =" + rowData);
	stockData = outputTable.getData("selected");

	realData = []

	// 재고수량보다 출고수량이 더 많을 경우 저장안됨

	if (stockData[0].sm_Code == rowData[0].outMat_Code && stockData[0].sm_Last_Qty < rowData[0].outMat_Qty) {
		alert("재고 수량보다 출고 수럄이 더 많습니다.");
		return false;
	}
	
	if (rowData[0].outMat_Consignee == "") {
		alert("수취인을 입력하세요.");
		return false;
	}

	if (rowData[0].outMat_Send_Clsfc == undefined) {
		alert("출고 유형을 선택하세요.");
		return false;
	}
	
	if (rowData[0].outMat_Qty != 0 && rowData[0].outMat_Consignee != null && rowData[0].outMat_Send_Clsfc != null) {
		realData.push(rowData[0]);
	}

	if (realData.length == 0) {
		alert("저장할 목록이 없습니다.");
		return false;
	}

	//InputSub 저장부분
	$.ajax({
		method: "get",
		contentType: 'application/json',
		url: "matOutputLXRest/MOM_Save?data=" + encodeURI(JSON.stringify(realData)),
		success: function(result) {
			//console.log(result);
			if (result == "error") {
				alert("중복된 값이 있습니다..");
			} else if (result == "success") {
				MOS_Search();
				//Cus_No_select();
				alert("저장되었습니다.");
			}
		}
	});
}


$('#MOM_SaveBtn').click(function() {
	MOM_Save();
})

//발주번호로 OrderMaster 선택하는 코드
function Cus_No_select() {
	rowCount = outputTable.getDataCount("active");
	//컬럼값을 검색해서 입력값을 포함하는 값이 있으면 선택한다. 
	for (var i = 0; i < rowCount; i++) {
		Cus_No = outputTable.getColumn("sm_Code").getCells();
		//발주번호가 입력내용을 포함하면 코드 실행
		if (Cus_No[i].getValue() == $('#Order_lCus_No').val()) {
			//발주번호가 같은 행 선택
			Cus_No[i].getRow().select();
			break;
		}
	}
}