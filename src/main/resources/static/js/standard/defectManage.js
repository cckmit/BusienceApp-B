var table = null;

// DB의 데이터를 변수에 담아서 화면에 뿌여줄 용도
var datas = "";

// 선택한 행을 변수에 담아서 더블클릭한 행에 색깔을 칠하는 변수
var element = null;

// 행을 더블클릭하여서 해당 행의 인덱스를 저장하는 변수
var rowindex = 0;

// 행을 더블클릭하여서 해당 행의 데이터를 저장했다가 화면에서 뿌려주는 변수
var defect_CODE = null;
var defect_NAME = null;
var defect_ABR = null;
var defect_MODIFIER = null;
var defect_MODIFY_D = null;
var defect_RMRKS = null;
var defect_USE_STATUS = null;

// 행을 더블클릭했는지 안했는지 아는 변수
var defect_FLAG = false;

function updatedeleteView() {
	if (!defect_FLAG) {
		alert("행을 선택한 후에 수정 및 삭제를 시도하여 주십시오.");
		return;
	}

	$("#defectModifyModal").modal("show");
	modviewBtn();
}

window.onload = function() {
	$.ajax({
		method: "GET",
		url: "defectManageRest/view",
		success: function(data) {
			datas = data;

			table = new Tabulator(
				"#example-table",
				{
					//페이징
					layout: "fitColumns",
					pagination: "local",
					paginationSize: 20,
					height: "calc(100% - 175px)",
					headerFilterPlaceholder: null,
					selectable: 1, //make rows selectable
					rowClick: function(e, row) {
						// 클릭한 행의 색상을 칠해준다.
						if (element == null) {
							element = row.getElement();
							element.style.color = "red";
							element.style.fontWeight = "bold";
						}
						else {
							element.style.color = "black";
							element.style.fontWeight = "normal";
							row.getElement().style.color = "red";
							row.getElement().style.fontWeight = "bold";
							element = row.getElement();
						}

						rowindex = row.getIndex();

						// 더블클릭할때 데이터를 저장
						defect_CODE = row.getData().defect_CODE;
						defect_NAME = row.getData().defect_NAME;
						defect_ABR = row.getData().defect_ABR;
						defect_MODIFIER = row.getData().defect_MODIFIER;
						defect_MODIFY_D = row.getData().defect_MODIFY_D;
						defect_RMRKS = row.getData().defect_RMRKS;
						defect_USE_STATUS = row.getData().defect_USE_STATUS;

						defect_FLAG = true;
					},
					rowDblClick: function(e, row) {
						row.select();

						// 클릭한 행의 색상을 칠해준다.
						if (element == null) {
							element = row.getElement();
							element.style.color = "red";
							element.style.fontWeight = "bold";
						}
						else {
							element.style.color = "black";
							element.style.fontWeight = "normal";
							row.getElement().style.color = "red";
							row.getElement().style.fontWeight = "bold";
							element = row.getElement();
						}

						rowindex = row.getIndex();

						// 더블클릭할때 데이터를 저장
						defect_CODE = row.getData().defect_CODE;
						defect_NAME = row.getData().defect_NAME;
						defect_ABR = row.getData().defect_ABR;
						defect_MODIFIER = row.getData().defect_MODIFIER;
						defect_MODIFY_D = row.getData().defect_MODIFY_D;
						defect_RMRKS = row.getData().defect_RMRKS;
						defect_USE_STATUS = row.getData().defect_USE_STATUS;

						defect_FLAG = true;

						$("#defectModifyModal").modal("show");
						modviewBtn();
					},
					data: datas, //assign data to table
					columns: [ //Define Table Columns
						{
							title: "번호",
							field: "id",
							hozAlign: "center",
							headerHozAlign: "center",
							width: 60
						}, {
							title: "불량코드",
							field: "defect_CODE",
							width: 100,
							headerHozAlign: "center",
							headerFilter: "input"
						}, {
							title: "불량명",
							field: "defect_NAME",
							width: 200,
							headerHozAlign: "center",
							headerFilter: "input"
						}, {
							title: "약자",
							field: "defect_ABR",
							width: 100,
							headerHozAlign: "center",
							headerFilter: "input"
						}, {
							title: "비고",
							field: "defect_RMRKS",
							width: 200,
							headerHozAlign: "center",
							headerFilter: "input"
						}, {
							title: "사용유무",
							field: "defect_USE_STATUS",
							width: 100,
							headerHozAlign: "center",
							hozAlign: "center",
							formatter: "tickCross",
							editor: "text",
							editorParams: {
								values: {
									"true": "사용",
									"false": "미사용"
								}
							},
							headerFilter: true,
							headerFilterParams: {
								values: {
									"true": "사용",
									"false": "미사용"
								}
							}
						}, {
							title: "수정일자",
							field: "defect_MODIFY_D",
							width: 150,
							headerHozAlign: "center",
							hozAlign: "right",
							sorter: "date",
							formatter: "datetime", formatterParams: { outputFormat: "YYYY-MM-DD HH:mm:ss" },
							headerFilter: "input"
						}, {
							title: "수정자",
							field: "defect_MODIFIER",
							width: 150,
							headerHozAlign: "center",
							headerFilter: "input"
						}],
				});
		}
	});

	//SubmenuSelector("2", "13251");
}

// 삭제 기능을 수행하는 함수
function delBtn() {
	// 화면에서 선택한 행의 인덱스를 지우는 로직
	table.deleteRow(rowindex);

	// 실제로 DB에서 선택한 행의 데이터를 지운다.
	$.ajax({
		method: "POST",
		url: "defectManageRest/delete?DEFECT_CODE="
			+ defect_CODE,
		beforeSend: function (xhr) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           xhr.setRequestHeader(header, token);
		},
		success: function(data, testStatus) {
		}
	});

	// DB까지 지우고 나면 화면을 새로고침
	location.reload();
}

function modviewBtn() {
	// 수정,삭제 모달창에 더블클릭한 데이터를 렌더링함
	document.getElementById("update_defect_CODE").value = defect_CODE;
	document.getElementById("update_defect_NAME").value = defect_NAME;
	document.getElementById("update_defect_ABR").value = defect_ABR;
	document.getElementById("update_defect_RMRKS").value = defect_RMRKS;

	if (defect_USE_STATUS == "true")
		document.getElementById("update_defect_USE_STATUS").checked = true;
	else
		document.getElementById("update_defect_USE_STATUS").checked = false;
}

function modBtn() {
	datas = {
		DEFECT_CODE: defect_CODE,
		DEFECT_NAME: document.getElementById("update_defect_NAME").value,
		DEFECT_ABR: document.getElementById("update_defect_ABR").value,
		DEFECT_USE_STATUS: document.getElementById("update_defect_USE_STATUS").checked,
		DEFECT_RMRKS: document.getElementById("update_defect_RMRKS").value
	};

	$.ajax({
		method: "POST",
		data: datas,
		url: "defectManageRest/update?data="
			+ encodeURI(JSON.stringify(datas)),
		beforeSend: function (xhr) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           xhr.setRequestHeader(header, token);
		},
		success: function(data, testStatus) {
		}
	});

	location.reload();
}

// 수정 모달창에서 취소버튼을 누를때 수정 모달창 데이터를 초기화
function modResetBtn() {
	document.getElementById("update_defect_NAME").value = "";
	document.getElementById("update_defect_ABR").value = "";
	document.getElementById("update_defect_USE_STATUS").checked = false;
	document.getElementById("update_defect_RMRKS").value = "";
}

// 입력버튼을 클릭을 할때 모달창을 여는 이벤트
$("#registerModal").click(function() {
	$("#defectRegisterModal").modal("show");
});

// 수정버튼을 클릭을 할때 모달창을 여는 이벤트
if (defect_FLAG) {
	$("#modifyModal").click(function() {
		$("#defectModifyModal").modal("show");
	});
}

// 입력모달창이 열릴때 가장 상단의 input에 포커스를 주는 이벤트
$("#defectRegisterModal").on("shown.bs.modal", function() {
	$("#insert_defect_CODE").focus();
});

// 수정모달창이 열릴때 가장 상단의 input에 포커스를 주는 이벤트
$("#defectModifyModal").on("shown.bs.modal", function() {
	$("#update_defect_NAME").focus();
});

// 저장할지 말지 여부를 묻는 모달창 열기
function insertModal() {
	$("#insertYesNo").modal("show");
}

function insBtn() {
	var dcode = document.getElementById("insert_defect_CODE").value;

	if (dcode == "") {
		alert("불량코드는 반드시 입력하셔야 합니다.");
		document.getElementById("insert_defect_CODE").focus();
		return;
	}

	if (dcode.length > 4) {
		alert("불량코드는 4글자 이하로 입력하여주세요.");
		document.getElementById("insert_defect_CODE").focus();
		return;
	}

	datas = {
		DEFECT_CODE: dcode,
		DEFECT_NAME: document.getElementById("insert_defect_NAME").value,
		DEFECT_ABR: document.getElementById("insert_defect_ABR").value,
		DEFECT_USE_STATUS: document.getElementById("insert_defect_USE_STATUS").checked,
		DEFECT_RMRKS: document.getElementById("insert_defect_RMRKS").value
	};

	$.ajax({
		method: "POST",
		data: datas,
		url: "defectManageRest/insert?data="
			+ encodeURI(JSON.stringify(datas)),
		beforeSend: function (xhr) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           xhr.setRequestHeader(header, token);
		},
		success: function(data) {
			if (data == "Overlap")
				alert("중복코드를 입력하셨습니다. 다른 코드를 입력해주세요.");
			else if (data == "Success") {
				location.reload();
			}
		}
	});
}

// 입력 모달창에서 취소버튼을 누를때 입력 모달창 데이터를 초기화
function insResetBtn() {
	document.getElementById("insert_defect_CODE").value = "";
	document.getElementById("insert_defect_NAME").value = "";
	document.getElementById("insert_defect_ABR").value = "";
	document.getElementById("insert_defect_USE_STATUS").checked = false;
	document.getElementById("insert_defect_RMRKS").value = "";
}