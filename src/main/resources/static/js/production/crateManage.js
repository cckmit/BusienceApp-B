//셀위치저장
var cellPos = null;

var editCheck = function(cell) {
	//cell - the cell component for the editable cell

	//get row data
	var data = cell.getRow().getData();

	return data.c_Create_Date == undefined; // only allow the name cell to be edited if the age is over 18
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
	//페이징
	pagination: "local",
	paginationSize: 20,
	headerFilterPlaceholder: null,
	layoutColumnsOnNewData: true,
	selectable: true,
	height: "calc(100% - 175px)",
	rowClick: function(e, row) {
		row.select();
	},
	rowSelected: function(row) {
		UseBtn();
	},
	//행추가시 기능
	rowAdded: function(row) {
		row.getTable().deselectRow();
		row.select();

		//MO_Add버튼 비활성화 (작성중인 행이 있다면 추가못하게함)
		if (!$('#crateSaveBtn').hasClass('unUseBtn')) {
			$('#crateSaveBtn').addClass('unUseBtn');
		}

		row.update({
			"c_Use_Status": "true"
		});

		UseBtn();

		//행이 추가되면 첫셀에 포커스
		do {
			setTimeout(function() {
				row.getCell("c_CrateCode").edit();
			}, 100);
		}
		while (row.getData().c_Create_Date === "undefined");
	},
	cellEditing: function(cell) {
		//셀위치 저장하여 포커싱부여
		cellPos = cell;
	},
	columns: [
		{ formatter: "rowSelection", titleFormatter: "rowSelection", headerHozAlign: "center", hozAlign: "center", headerSort: false },
		{ title: "순번", field: "rownum", hozAlign: "center", headerHozAlign: "center", formatter: "rownum" },
		{
			title: "상자코드", field: "c_CrateCode", headerHozAlign: "center", editor: MO_inputEditor, headerFilter: "input", editable: editCheck,
			cellEdited: function(cell) {

			}
		},
		{ title: "상태", field: "c_Condition", headerHozAlign: "center", headerFilter: "input", visible: false },
		{ title: "생성일자", field: "c_Create_Date", headerHozAlign: "center" },
		{
			title: "사용유무", field: "c_Use_Status", headerHozAlign: "center", hozAlign: "center",
			formatter: "tickCross", editor: 'select', editorParams: { values: { "true": "사용", "false": "미사용" } }
		}
	]
});

// ADD버튼을 클릭할때 모달창을 여는 이벤트
$("#crateADDBtn").click(function() {
	crateManageTable.addRow();
});

function crateSave() {

	$.when(saveChk())
		.then(function(data) {
			console.log(data);
			if (data.length > 0) {
				for (var i = 0; i < data.length; i++) {

					if (data[i].c_Use_Status == 'true') {
						status = 1;
					} else if (data[i].c_Use_Status == 'false') {
						status = 0;
					}
					var datas = {
						C_CrateCode: data[i].c_CrateCode,
						C_Use_Status: status
					}

					$.ajax({
						method: "POST",
						url: "crateRest/crateSave",
						data: datas,
						beforeSend: function(xhr) {
							var header = $("meta[name='_csrf_header']").attr("content");
							var token = $("meta[name='_csrf']").attr("content");
							xhr.setRequestHeader(header, token);
						},
						success: function(result) {
							if (result == 1) {
								$(function() {
									alert("저장되었씁니다.");
									CratePrinter(crateManageTable.getData("selected"))
									$(this).off();
								})
								location.reload();
							} else {
								alert("저장 중 오류가 발생했습니다. 다시 시도해주세요.");
								location.reload();
							}

						}
					});
				}
			}
		})
}



function saveChk() {

	var selectedRow = crateManageTable.getData("selected");

	for (var i = 0; i < selectedRow.length; i++) {
		tempCode = selectedRow[i].c_CrateCode;
		index = cellPos.getRow().getPosition(true);
		var k = 0;
		for (var j = 0; j < crateManageTable.getDataCount(); j++) {
			if (crateManageTable.getData()[j].c_CrateCode == tempCode) {

				// 공백 삭제
				if (crateManageTable.getData()[j].c_CrateCode == "") {
					console.log(crateManageTable.getData()[j].c_CrateCode);
					crateManageTable.deleteRow(crateManageTable.getRows()[j]);
					alert("작성중인 행이 있습니다.");
					return;
				}
				
				k += 1;
				if (k > 1) {
					if (crateManageTable.getRows()[j].getPosition() == index) {
						alert("동일한 코드가 존재합니다.");
						return;
					}
				}
				k++;
			}
		}
	}

	return selectedRow;
}


// update버튼을 클릭을 할때 모달창을 여는 이벤트
$("#crateSaveBtn").click(function() {

	crateSave();

});

$("#cratePrintBtn").click(function(){
	CratePrinter(crateManageTable.getData("selected"))
})

$(document).ready(function() {

	crateManageTable.setData("crateRest/crateSelect");

});

