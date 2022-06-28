var editCheck = function(cell) {
	//cell - the cell component for the editable cell

	//get row data
	var data = cell.getRow().getData();

	return data.packaging_ModifyDate == undefined || data.packaging_ModifyDate == ""; // only allow the name cell to be edited if the age is over 18
}

let checkNo;

//커스텀 기능설정
var PM_InputEditor = function(cell, onRendered, success, cancel, editorParams) {
	//cell - 편집 가능한 셀의 셀 구성 요소
	//onRendered - 에디터가 렌더링 되었을 때 호출 할 함수
	//success - 성공적을 업데이트 된 값을 tabulator에 전달하기 위해 호출되는 함수
	//cancel - 편집을 중단하고 일반 셀로 돌아 가기 위해 호출하는 함수
	//editorParams - editorParams 열 정의 속성에 전달 된 params 객체

	//create 및 style editor
	var PM_Input = document.createElement("input");

	PM_Input.setAttribute("type", "text");

	//입력 생성 및 스타일 지정
	PM_Input.style.padding = "3px";
	PM_Input.style.width = "100%";
	PM_Input.style.boxSizing = "border-box";

	//편집기의 값을 셀의 현재 값으로 설정
	if (cell.getValue() == undefined) {
		PM_Input.value = "";
	} else {
		PM_Input.value = cell.getValue();
	}

	//에디터가 선택되면 선택 상자에 포커스 설정 (타임 아웃은 편집기를 DOM에 추가 할 수 있음)
	onRendered(function() {
		PM_Input.focus();
		PM_Input.select();
		PM_Input.style.css = "100%";
	});

	//값이 설정되면 업데이트 할 셀 트리거
	function onChange() {
		success(PM_Input.value);
	}

	//바꼈을때 블러됫을때 함수 발동
	PM_Input.addEventListener("change", onChange);
	PM_Input.addEventListener("blur", onChange);

	//키버튼 이벤트
	PM_Input.addEventListener("keydown", function(e, row) {
		//코드 셀에서 백스페이스를 눌렀을경우 명이 사라지게함
	
		if (e.keyCode == 13) {
		
		}
	});
	//반환
	return PM_Input;
};



//팝업창으로부터 특정 파라미터 값으로 데이터를 받는다 
function customer_gridInit(CCode, CName) {
	cellPos.getRow().update({
		"packaging_Cus_Code": CCode,
		"packaging_Cus_Name": CName
	});
	//선택 후 포커스 이동
	cellPos.getElement().focus();
}

//팝업창으로부터 특정 파라미터 값으로 데이터를 받는다 
function item_gridInit(PCode, PName, PSTND_1, PPrice) {
	cellPos.getRow().update({
		"packaging_ItemCode": PCode,
		"packaging_ItemName": PName
	})
	cellPos.getElement().focus();
}

var dtl_arr = dtlSelectList(6);

var dtl_arr_material = dtlSelectList(8);

//셀 위치 저장
var cellPos = null;

var paldangPackagingManageTable = new Tabulator("#paldangPackagingManageTable", {
	//페이징
	paginationAddRow: "table",
	layoutColumnsOnNewData: true,
	headerFilterPlaceholder: null,
	ajaxConfig: "get",
	ajaxContentType: "json",
	ajaxURL: "paldangPackagingRest/paldangPackagingList",
	height: "calc(100% - 175px)",
	selectable: true,
	tabEndNewRow: true,
	keybindings: {
		"navNext": "13"
	},
	rowClick: function(e, row) {
		//console.log(row.getElement());
	},
	rowAdded: function(row) {
		//행이 추가되면 첫셀에 포커스
		do {
			setTimeout(function() {
				row.getCell("packaging_No").edit();
			}, 100);
		}
		while (row.getData().packaging_No === "undefined");
	},
	cellEditing: function(cell) {
		//셀위치 저장하여 포커싱부여
		cellPos = cell;
	},
	columns: [
		{ formatter: "rowSelection", titleFormatter: "rowSelection", headerHozAlign: "center", hozAlign: "center", headerSort: false },
		{ title: "순번", field: "rownum", formatter: "rownum", hozAlign: "center", headerHozAlign: "center" },
		{
			title: "코드", field: "packaging_No", hozAlign: "left", headerHozAlign: "center", headerFilter: "input", editor:  PM_InputEditor, editable: editCheck,
			cellEdited: function(cell) {
				//행추가할때 첫번째행을 복사하므로 첫번째행과
				checkNo = cell.getValue();
				let resultData = item_Code_Check(checkNo);
			}
		},
		{ title: "구분", field: "packaging_Clsfc", headerHozAlign: "center", headerFilter: "input", editor: PM_InputEditor, width: 100 },
		{
			title: "타입", field: "packaging_Type", headerHozAlign: "center", headerFilter: "input", headerFilterParams: dtl_arr_material, width: 100, editor: "select",
			formatter: function(cell, formatterParams) {
				var value = cell.getValue();
				if (dtl_arr_material[value] != null) {
					value = dtl_arr_material[value];
				} else {
					value = value;
				}
				return value;
			},
			editorParams: { values: dtl_arr_material }

		},
		{
			title: "규격", field: "packaging_Size", headerHozAlign: "center", headerFilter: "input", hozAlign: "left", headerFilterParams: dtl_arr, width: 120, editor: "select",
			formatter: function(cell, formatterParams) {
				var value = cell.getValue();
				if (dtl_arr[value] != null) {
					value = dtl_arr[value];
				} else {
					value = value;
				}
				return value;
			},
			editorParams: { values: dtl_arr }
		},
		{ title: "품목", field: "packaging_Item", headerHozAlign: "center", headerFilter: "input", editor: PM_InputEditor, width: 300 },
		{ title: "소포장", field: "packaging_Small", headerHozAlign: "center", editor: PM_InputEditor, hozAlign: "right", width: 80 },
		{ title: "대포장", field: "packaging_Large", headerHozAlign: "center", editor: PM_InputEditor, hozAlign: "right", width: 80 },
		{ title: "저장시간", field: "packaging_ModifyDate", headerHozAlign: "center", hozAlign: "left", visible: false },
	]
});

//BIL_Search
function PM_Search() {
	$.ajax({
		method: "GET",
		dataType: "json",
		url: "paldangPackagingRest/paldangPackagingList",
		success: function(result) {
			console.log(result)
			paldangPackagingManageTable.setData(result);
			ResetBtn();
		}
	});
}

function Right_Move(cell, flag) {
	var cellElement = cell.getElement();

	cellElement.addEventListener('keydown', function(e) {
		if (e.keyCode == 13) {

			if (flag == "right")
				cell.nav().right();
			else
				newRow_Add();
		}
	});
}

$('#PM_SearchBtn').click(function() {
	PM_Search();
})

function PM_New() {
	paldangPackagingManageTable.addRow();
	UseBtn();
}

$('#PM_NewBtn').click(function() {
	PM_New();
})

function PM_Add() {
	//목록의 구분, 거래처명, 제품명을 검사하여 입력하지 않았을 경우 추가 안됨
	for (i = 0; i < paldangPackagingManageTable.getDataCount("active"); i++) {
		rowData = paldangPackagingManageTable.getData()[i];

		if (rowData.packaging_Cus_Name == '' || rowData.packaging_ItemName == '') {
			alert("작성중인 행이 있습니다.");
			return false;
		}
	}
	paldangPackagingManageTable.selectRow();
	paldangPackagingManageTable.addRow();
}

$('#PM_AddBtn').click(function() {
	PM_Add();
})

function PM_Save() {
	selectedRow = paldangPackagingManageTable.getData("selected");
	rowCount = paldangPackagingManageTable.getDataCount("selected");

	console.log(selectedRow);
	if (rowCount == 0) {
		alert("저장할 선택한 데이터가 없습니다.");
		return false;
	}
	//목록의 구분, 거래처명, 제품명을 검사하여 입력하지 않았을 경우 저장 안됨
	for (i = 0; i < rowCount; i++) {
		rowData = selectedRow[i];

		if (rowData.packaging_Item == '' || rowData.packaging_Small == '' || rowData.packaging_Large == '') {
			alert("작성중인 행이 있습니다.");
			return false;
		}
	}
	if (confirm("선택한 행이 저장됩니다. 저장하시겠습니까?")) {
		$.ajax({
			method: "post",
			url: "paldangPackagingRest/paldangPackaginInsert",
			data: JSON.stringify(selectedRow),
			contentType: 'application/json',
			beforeSend: function(xhr) {
				var header = $("meta[name='_csrf_header']").attr("content");
				var token = $("meta[name='_csrf']").attr("content");
				xhr.setRequestHeader(header, token);
			},
			success: function(result) {
				console.log(result);
				if (result == "error") {
					alert("저장 중 오류가 발생했습니다. 다시 시도해주세요.")
				} else {
					PM_Search();
				}
			}
		});
	}
}

$('#PM_SaveBtn').click(function() {
	PM_Save();
})

function PM_Delete() {
	//테이블에서 선택한 데이터
	selectedData = paldangPackagingManageTable.getSelectedData();
	realData = []

	if (selectedData.length == 0) {
		alert("삭제할 선택한 데이터가 없습니다.");
		return false;
	}

	// 기존 조회한 데이터는 순번이 1이상, 새로 추가된데이터는 순번이 0
	if (confirm("선택한 행이 삭제됩니다. 삭제하시겠습니까?")) {
		//순번이 1인 데이터만 모아서 쿼리를 실행한다.
		for (i = 0; i < selectedData.length; i++) {
			if (selectedData[i].id != 0) {
				realData.push(selectedData[i]);
			}
		}
		//배열에 담은 데이터가 있을경우 쿼리 실행
		if (realData.length != 0) {
			$.ajax({
				method: "post",
				url: "paldangPackagingRest/paldangPackaginDelete",
				data: JSON.stringify(realData),
				contentType: 'application/json',
				beforeSend: function(xhr) {
					var header = $("meta[name='_csrf_header']").attr("content");
					var token = $("meta[name='_csrf']").attr("content");
					xhr.setRequestHeader(header, token);
				},
				success: function(result) {
					console.log(result);
					if (result == "error") {
						alert("사용중인 코드는 삭제할 수 없습니다.")
						return;
					} else {
						alert("삭제되었습니다.");
						PM_Search();
					}
				}
			})
		}
	}
}

$('#PM_DeleteBtn').click(function() {
	PM_Delete();
})

//list에서 같은 품목을 추가할때 경고 알리고 추가안됨
function item_Code_Check(checkNo) {
	datas = {
		packaging_No: checkNo
	}

	$.ajax({
		method: "get",
		url: "paldangPackagingRest/paldangPackagingCheck",
		data: datas,
		success: function(result) {
			console.log(result);
			if (result.length > 0) {
				alert("사용중인 코드입니다.")
			}
		}
	});

}