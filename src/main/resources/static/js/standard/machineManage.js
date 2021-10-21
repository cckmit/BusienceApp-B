// reset
function resetBtn() {
	location.reload();
}

// 설비연식 자동 입력
function date_change(today) {
	var date = today.value;
	var substr = date.substring(0, 4);
	document.getElementById('EQUIPMENT_MODEL_YEAR').value = substr;
}
// 삭제
function delBtn() {
	$.ajax({
		method: "get",
		data: null,
		url: "machineManageRest/machineManageDelete?EQUIPMENT_INFO_CODE=" + document.getElementById("EQUIPMENT_INFO_CODE").value,
		success: function(data) {
			console.log(data);
			alert("삭제 성공 하였습니다.");
			resetBtn();
		}
	});
}
function insBtn() {
	// 사용유무 true / false
	var check_count1 = document.getElementsByName("EQUIPMENT_USE_STATUS").length;
	var EQUIPMENT_USE_STATUS = "";

	for (var i = 0; i < check_count1; i++) {
		if (document.getElementsByName("EQUIPMENT_USE_STATUS")[i].checked == true) {
			EQUIPMENT_USE_STATUS = document.getElementsByName("EQUIPMENT_USE_STATUS")[i].value;
		}
	}

	// 설비코드
	if (document.getElementById("EQUIPMENT_INFO_CODE").value == "") {
		alert("설비 코드는 반드시 입력하셔야 합니다.");
		return $("#EQUIPMENT_INFO_CODE").focus();;
	}
	if (document.getElementById("EQUIPMENT_INFO_CODE").value.length > 10) {
		alert("설비 코드는 10글자 이하로 입력해주세요.");
		return $("#EQUIPMENT_INFO_CODE").focus();;
	}
	
	if (document.getElementById("EQUIPMENT_RECEIVED_D").value == "") {
		alert("구입일자를 입력해주세요.");
		return $("#EQUIPMENT_RECEIVED_D").focus();
	}


	data = {
		EQUIPMENT_BUSINESS_PLACE: $("#EQUIPMENT_BUSINESS_PLACE option:selected").val(),
		EQUIPMENT_INFO_CODE: document.getElementById("EQUIPMENT_INFO_CODE").value,
		EQUIPMENT_INFO_NAME: document.getElementById("EQUIPMENT_INFO_NAME").value,
		EQUIPMENT_INFO_ABR: document.getElementById("EQUIPMENT_INFO_ABR").value,
		EQUIPMENT_HEIGHT: document.getElementById("EQUIPMENT_HEIGHT").value,
		EQUIPMENT_WIDTH: document.getElementById("EQUIPMENT_WIDTH").value,
		EQUIPMENT_DEPTH: document.getElementById("EQUIPMENT_DEPTH").value,
		EQUIPMENT_SERIAL_NUM: document.getElementById("EQUIPMENT_SERIAL_NUM").value,
		EQUIPMENT_WEIGHT: document.getElementById("EQUIPMENT_WEIGHT").value,
		EQUIPMENT_RECEIVED_D: document.getElementById("EQUIPMENT_RECEIVED_D").value,
		EQUIPMENT_MODEL_YEAR: document.getElementById("EQUIPMENT_MODEL_YEAR").value,
		EQUIPMENT_MANUFACTURER: document.getElementById("EQUIPMENT_MANUFACTURER").value,
		EQUIPMENT_STATUS: $("#EQUIPMENT_STATUS option:selected").val(),
		EQUIPMENT_INFO_RMARK: document.getElementById("EQUIPMENT_INFO_RMARK").value,
		EQUIPMENT_USE_STATUS: document.getElementById("EQUIPMENT_USE_STATUS").checked
	}
	console.log("data : " + data);
	var code = encodeURI(JSON.stringify(data)).length;
	console.log(code);

	$.ajax({
		method: "get",
		url: "machineManageRest/machineManageInsert?data=" + encodeURI(JSON.stringify(data)) + "",
		data: null,
		success: function(data, testStatus) {
			//console.log("data : " + data);
			if (data == "Overlap")
				alert("중복 코드를 입력하셨습니다. 다시 입력해주세요.");
			else {
				alert("입력 성공 하였습니다.");
				resetBtn();
			}
		}
	});
}
// 추가, 삭제, 수정 처리하는 로직
function modBtn() {
	// 사용유무 true / false
	var check_count1 = document.getElementsByName("EQUIPMENT_USE_STATUS").length;
	var EQUIPMENT_USE_STATUS = "";

	for (var i = 0; i < check_count1; i++) {
		if (document.getElementsByName("EQUIPMENT_USE_STATUS")[i].checked == true) {
			EQUIPMENT_USE_STATUS = document.getElementsByName("EQUIPMENT_USE_STATUS")[i].value;
		}
	}
	data = {
		EQUIPMENT_BUSINESS_PLACE: $("#EQUIPMENT_BUSINESS_PLACE option:selected").val(),
		EQUIPMENT_INFO_CODE: document.getElementById("EQUIPMENT_INFO_CODE").value,
		EQUIPMENT_INFO_NAME: document.getElementById("EQUIPMENT_INFO_NAME").value,
		EQUIPMENT_INFO_ABR: document.getElementById("EQUIPMENT_INFO_ABR").value,
		EQUIPMENT_HEIGHT: document.getElementById("EQUIPMENT_HEIGHT").value,
		EQUIPMENT_WIDTH: document.getElementById("EQUIPMENT_WIDTH").value,
		EQUIPMENT_DEPTH: document.getElementById("EQUIPMENT_DEPTH").value,
		EQUIPMENT_SERIAL_NUM: document.getElementById("EQUIPMENT_SERIAL_NUM").value,
		EQUIPMENT_WEIGHT: document.getElementById("EQUIPMENT_WEIGHT").value,
		EQUIPMENT_RECEIVED_D: document.getElementById("EQUIPMENT_RECEIVED_D").value,
		EQUIPMENT_MODEL_YEAR: document.getElementById("EQUIPMENT_MODEL_YEAR").value,
		EQUIPMENT_MANUFACTURER: document.getElementById("EQUIPMENT_MANUFACTURER").value,
		EQUIPMENT_STATUS: $("#EQUIPMENT_STATUS option:selected").val(),
		EQUIPMENT_INFO_RMARK: document.getElementById("EQUIPMENT_INFO_RMARK").value,
		EQUIPMENT_USE_STATUS: document.getElementById("EQUIPMENT_USE_STATUS").checked
	}

	if (document.getElementById("EQUIPMENT_RECEIVED_D").value == "") {
		alert("구입일자를 입력해주세요.");
		return $("#EQUIPMENT_RECEIVED_D").focus();
	}

	$.ajax({
		method: "get",
		data: null,
		url: "machineManageRest/machineManageUpdate?data=" + encodeURI(JSON.stringify(data)) + "",
		success: function(data) {
			console.log(data);
			alert("수정 성공 하였습니다.");
			resetBtn();
		}
	}
	);
}
element = null;

var machineManageTable = new Tabulator("#machineManageTable", {
	//페이징
	layout: "fitColumns",
	pagination: "local",
	paginationSize: 20,
	height: "calc(100% - 175px)",
	virtualDomHoz: true,
	headerFilterPlaceholder: null,
	layout: "fitColumns",
	selectable: 1,
	rowClick: function(e, row) {
		// 클릭 이벤트 -> 열의 값을 가져옴
		// 사업장
		$('#EQUIPMENT_BUSINESS_PLACE').val(row.getData().equipment_BUSINESS_PLACE);
		// 설비코드
		$('#EQUIPMENT_INFO_CODE').val(row.getData().equipment_INFO_CODE);
		/*$('#EQUIPMENT_INFO_CODE').setAttribute("disabled","");*/
		// 설비명
		$('#EQUIPMENT_INFO_NAME').val(row.getData().equipment_INFO_NAME);
		// 설비호기
		$('#EQUIPMENT_INFO_ABR').val(row.getData().equipment_INFO_ABR);
		// 설비(높이)
		$('#EQUIPMENT_HEIGHT').val(row.getData().equipment_HEIGHT);
		// 설비(폭)
		$('#EQUIPMENT_WIDTH').val(row.getData().equipment_WIDTH);
		// 설비(깊이)
		$('#EQUIPMENT_DEPTH').val(row.getData().equipment_DEPTH);
		// 설비(S/N)
		$('#EQUIPMENT_SERIAL_NUM').val(row.getData().equipment_SERIAL_NUM);
		// 설비(무게)
		$('#EQUIPMENT_WEIGHT').val(row.getData().equipment_WEIGHT);
		// 구입일자
		$('#EQUIPMENT_RECEIVED_D').val(row.getData().equipment_RECEIVED_D);
		// 설비연식
		$('#EQUIPMENT_MODEL_YEAR').val(row.getData().equipment_MODEL_YEAR);
		// 제작업체
		$('#EQUIPMENT_MANUFACTURER').val(row.getData().equipment_MANUFACTURER);
		// 설비상태
		$('#EQUIPMENT_STATUS').val(row.getData().equipment_STATUS);
		// 사용유무
		/*      if(row.getData().EQUIPMENT_USE_STATUS ==  "1" || row.getData().equipment_USE_STATUS == "true"){
					document.getElementsByName("EQUIPMENT_USE_STATUS")[0].checked = true;
				}else{
					document.getElementsByName("EQUIPMENT_USE_STATUS")[1].checked = true;
				}*/
		if (row.getData().equipment_USE_STATUS == "true")
			document.getElementById("EQUIPMENT_USE_STATUS").checked = true;
		else
			document.getElementById("EQUIPMENT_USE_STATUS").checked = false;
		// 비고
		$('#EQUIPMENT_INFO_RMARK').val(row.getData().equipment_INFO_RMARK);
	},
	rowDblClick: function(e, row) {
		//행에 클릭유지
		row.select();

		modify()
	},
	columns: [
		{
			title: "번호",
			field: "id",
			hozAlign: "center",
			headerHozAlign: "center",
			width: 60
		}, {
			title: "사업장",
			field: "equipment_BUSINESS_PLACE_NAME",
			headerHozAlign: "center",
			headerFilter: "input",
			width: 75
		}, {
			title: "설비코드",
			field: "equipment_INFO_CODE",
			headerHozAlign: "center",
			headerFilter: "input",
			width: 85
		}, {
			title: "설비명",
			field: "equipment_INFO_NAME",
			headerHozAlign: "center",
			headerFilter: "input",
			width: 85
		}, {
			title: "설비호기",
			field: "equipment_INFO_ABR",
			headerHozAlign: "center",
			headerFilter: "input",
			width: 85
		}, {
			title: "설비(높이)",
			field: "equipment_HEIGHT",
			headerHozAlign: "center",
			headerFilter: "input",
			width: 100
		}, {
			title: "설비(폭)",
			field: "equipment_WIDTH",
			headerHozAlign: "center",
			headerFilter: "input",
			width: 85
		}, {
			title: "설비(깊이)",
			field: "equipment_DEPTH",
			headerHozAlign: "center",
			headerFilter: "input",
			width: 100
		}, {
			title: "설비(S/N)",
			field: "equipment_SERIAL_NUM",
			headerHozAlign: "center",
			headerFilter: "input",
			width: 100
		}, {
			title: "설비(무게)",
			field: "equipment_WEIGHT",
			headerHozAlign: "center",
			headerFilter: "input",
			width: 100
		}, {
			title: "구입일자",
			field: "equipment_RECEIVED_D",
			headerHozAlign: "center",
			headerFilter: "input",
			width: 85
		}, {
			title: "설비연식",
			field: "equipment_MODEL_YEAR",
			headerHozAlign: "center",
			headerFilter: "input",
			width: 85
		}, {
			title: "제작업체",
			field: "equipment_MANUFACTURER",
			headerHozAlign: "center",
			headerFilter: "input",
			width: 150
		}, {
			title: "설비상태",
			field: "equipment_STATUS_NAME",
			headerHozAlign: "center",
			headerFilter: "input",
			width: 85
		}, {
			title: "비고",
			field: "equipment_INFO_RMARK",
			headerHozAlign: "center",
			headerFilter: "input",
			width: 150
		}, {
			title: "사용유무",
			field: "equipment_USE_STATUS",
			headerHozAlign: "center",
			width: 85,
			headerHozAlign: "center",
			hozAlign: "center",
			formatter: "tickCross",
			headerFilter: true,
			headerFilterParams: { values: { "true": "사용", "false": "미사용" } },
			editor: "select",
			editorParams: { values: {} }
		}, {
			title: "수정일자",
			field: "equipment_MODIFY_D",
			headerHozAlign: "center",
			hozAlign: "right",
			formatter: "datetime", formatterParams: { outputFormat: "YYYY-MM-DD HH:mm:ss" },
			headerFilter: "input",
			width: 126
		}, {
			title: "수정자",
			field: "equipment_MODIFIER",
			headerHozAlign: "center",
			headerFilter: "input",
			width: 75
		}]
});
function insert() {
	$('#inputModal input').val("");
	// 설비(높이)
	$('#EQUIPMENT_HEIGHT').val("0");
	// 설비(폭)
	$('#EQUIPMENT_WIDTH').val("0");
	// 설비(깊이)
	$('#EQUIPMENT_DEPTH').val("0");
	// 설비(S/N)
	$('#EQUIPMENT_SERIAL_NUM').val("0");
	// 설비(무게)
	$('#EQUIPMENT_WEIGHT').val("0");
	//인서트가 논을 가지고있으면 제거하고 모디파이는 추가한다.
	console.log("insert");
	$('#machineModal .modify').addClass('none');
	if ($('#machineModal .insert').hasClass('none')) {
		$('#machineModal .insert').removeClass('none');
	}

	// 포커스부여
	$("#machineModal").on("shown.bs.modal", function() {
		$('#EQUIPMENT_INFO_CODE').focus();
	});
}
function modify() {
	if (machineManageTable.getDataCount("selected") != "1") {
		alert("행을 선택해야 합니다.")
	} else {
		//모디파이가 논을 가지고있으면 제거하고 인서트는 추가한다.
		console.log("modify");
		$("#machineModal").modal("toggle");
		$('#EQUIPMENT_INFO_CODE').attr("readonly", true);
		$('#machineModal .insert').addClass('none');
		if ($('#machineModal .modify').hasClass('none')) {
			$('#machineModal .modify').removeClass('none');
		}

		// 포커스부여
		$("#machineModal").on("shown.bs.modal", function() {
			$('#EQUIPMENT_INFO_NAME').focus();
		});
	}

}
function chebtn() {
	console.log("버튼누름");
}

function nextFocus(next) {
	if (event.keyCode == 13) {
		console.log(next);
		$('#' + next).focus();
	}
}


window.onload = function() {
	$.ajax({
		method: "GET",
		url: "machineManageRest/machineManageSelect",
		success: function(data) {
			console.log(data)
			machineManageTable.setData(data);
		}
	})

	//SubmenuSelector("2", "13231");
}