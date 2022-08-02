var editCheck = function(cell) {
	//cell - the cell component for the editable cell

	//get row data
	var data = cell.getRow().getData();

	return data.c_Create_Date == undefined;
}

var editCheckStatus = function(cell) {
	//cell - the cell component for the editable cell

	//get row data
	var data = cell.getRow().getData();

	return data.c_Condition != 0;
}

//입력 및 업데이트 할 리스트
var MO_inputEditor = function(cell, onRendered, success, cancel, editorParams) {
	//cell - 편집 가능한 셀의 셀 구성 요소
	//onRendered - 에디터가 렌더링 되었을 때 호출 할 함수
	//success - 성공적을 업데이트 된 값을 tabulator에 전달하기 위해 호출되는 함수
	//cancel - 편집을 중단하고 일반 셀로 돌아 가기 위해 호출하는 함수
	//editorParams - editorParams 열 정의 속성에 전달 된 params 객체

	//create 및 style editor
	var MO_input = document.createElement("input");

	MO_input.setAttribute("type", "text");

	//입력 생성 및 스타일 지정
	MO_input.style.padding = "3px";
	MO_input.style.width = "100%";
	MO_input.style.boxSizing = "border-box";

	//에디터가 선택되면 선택 상자에 포커스 설정 (타임 아웃은 편집기를 DOM에 추가 할 수 있음)
	onRendered(function() {
		MO_input.focus();
		MO_input.select();
		MO_input.style.css = "100%";
	});

	//값이 설정되면 업데이트 할 셀 트리거
	function onChange() {
		success(MO_input.value);
	}

	//바꼈을때 블러됫을때 함수 발동
	MO_input.addEventListener("change", onChange);
	MO_input.addEventListener("blur", onChange);

	//키버튼 이벤트
	MO_input.addEventListener("keyup", function(e) {
		//거래처코드 셀에서 백스페이스를 눌렀을경우 거래처명이 사라지게함
		//거래처코드셀 체크
		if (cell.getField() == "c_CrateCode") {
			//내용이 있을경우 검색해서 값이 하나일경우 생략, 아닐경우 팝업창
			$(this).val($(this).val().toUpperCase());
		}
	});
	//반환
	return MO_input;
};

var crateManageTable = new Tabulator("#crateManageTable", {
	headerFilterPlaceholder: null,
	layoutColumnsOnNewData: true,
	selectable: true,
	height: "calc(100% - 175px)",
	ajaxURL:"crateRest/crateSelect",
    ajaxConfig:"get",
    ajaxContentType:"json",
	rowSelected: function(row) {
		UseBtn();
	},
	//행추가시 기능
	rowAdded: function(row) {
		row.getTable().deselectRow();
		row.select();
		crateManageTable.scrollToRow(row, "nearest", false)
		.then(function(){
			//행이 추가되면 첫셀에 포커스
			do {
				setTimeout(function() {
					row.getCell("c_CrateCode").edit();
				}, 100);
			}
			while (row.getData().c_Create_Date === "undefined");
		})
	},
	columns: [
		{ formatter: "rowSelection", titleFormatter: "rowSelection", headerHozAlign: "center", hozAlign: "center", headerSort: false },
		{ title: "순번", field: "rownum", hozAlign: "center", headerHozAlign: "center", formatter: "rownum" },
		{ title: "상자코드", field: "c_CrateCode", headerHozAlign: "center", editor: MO_inputEditor, headerFilter: "input", editable: editCheck},
		{ title: "생성일자", field: "c_Create_Date", headerHozAlign: "center" },
		{ title: "상태", field: "c_Condition", headerHozAlign: "center", headerFilter: "input", editable: editCheckStatus, editor: "select",
			editorParams: { values: { "0": "미사용", "1": "마스크 투입", "2": "마스크 생산 완료", "3": "포장 투입" } },
			cellEdited: function(cell) {
				cell.getRow().select();
			}
		},
		{ title: "LotNo", field: "c_Production_LotNo", headerHozAlign: "center", headerFilter: "input"},
		{ title: "수량", field: "c_ProductionQty", headerHozAlign: "center", hozAlign: "right" },
		{ title: "생산설비", field: "c_MachineName", headerHozAlign: "center", headerFilter: "input"},
		{ title: "생산일자", field: "cl_Create_Date", headerHozAlign: "center", headerFilter: "input"},
		{ title: "투입설비", field: "c_MachineName2", headerHozAlign: "center", headerFilter: "input"},
		{ title: "투입일자", field: "cl_Update_Date", headerHozAlign: "center", headerFilter: "input"}		
	]
});

// ADD버튼을 클릭할때 모달창을 여는 이벤트
$("#crateADDBtn").click(function() {
	crateManageTable.addRow({c_Condition : 0});
});

function crateSave(values) {
	var ajaxResult = $.ajax({
		method: "POST",
		url: "crateRest/crateSave",
		data: JSON.stringify(values),
		contentType:'application/json',
		beforeSend: function(xhr) {
			var header = $("meta[name='_csrf_header']").attr("content");
			var token = $("meta[name='_csrf']").attr("content");
			xhr.setRequestHeader(header, token);
		},
		success: function(result) {
			alert("저장되었습니다.");
		}, error: function() {
			alert("저장 중 오류가 발생했습니다. 다시 시도해주세요.");
		}
	});
	return ajaxResult;
}

$("#crateSaveBtn").click(function() {
	var selectedData = crateManageTable.getData("selected");
	var saveData = new Array();
	
	for(let i=0;i<selectedData.length;i++){
		if(selectedData[i].c_CrateCode.length > 0){
			saveData.push(selectedData[i])
		}
	}
	
	$.when(crateSave(saveData))
	.then(function(data){
		CratePrinter(saveData)
		crateManageTable.replaceData();
	})
});

$("#cratePrintBtn").click(function() {
	CratePrinter(crateManageTable.getData("selected"))
})
