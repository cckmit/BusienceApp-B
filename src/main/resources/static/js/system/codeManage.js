element = null;
element2 = null;
var child_TBL_NO = 0;
var new_TBL_CODE = 0;
var child_TBL_NUM = 0;

// 입력버튼을 클릭을 할때 모달창을 여는 이벤트
function insertModal() {
	$("#insertModal").modal("show");
}

function insertModal2() {
	$("#insertYesNo").modal("show");
}

function insBtn() {
	if (document.getElementById("CHILD_TBL_TYPE").value == "") {
		alert("코드명은 반드시 입력하셔야 합니다.");
		return;
	}
	
	var CHILD_TBL_USE_STATUS_VALUE = document.getElementById("CHILD_TBL_USE_STATUS").checked;
	//console.log(CHILD_TBL_USE_STATUS_VALUE);
	
	$.ajax({
		method: "POST",
		data: {
			CHILD_TBL_NO : child_TBL_NO,
			NEW_TBL_CODE: new_TBL_CODE,
			CHILD_TBL_TYPE: document.getElementById("CHILD_TBL_TYPE").value,
			CHILD_TBL_RMARK: document.getElementById("CHILD_TBL_RMARK").value,
			CHILD_TBL_USE_STATUS: CHILD_TBL_USE_STATUS_VALUE
		},
		url: "codeManageRest/insert",
		success: function(data) {
			if (data == "Success") {
				alert("저장 완료 하였습니다.");

				location.reload();
			}
		}
	});
}

function GridSetting1(orgindata) {
	table = new Tabulator("#example-table1", {
		rowSelectionChanged: function(data, rows) {
		},
		rowClick: function(e, row) {
			new_TBL_CODE = row.getData().new_TBL_CODE;

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

			$("#addinitbtn").css(
					{
						"opacity" : "1"
					}
			);
			document.getElementById("addinitbtn").onclick = insertModal;
			

			GridSetting2(row.getData().new_TBL_CODE);
		},
		rowDblClick: function(e, row) {
			row.select();
		},
		rowUpdated: function(row) {
			alert("수정");
		},
		height: "calc(100% - 175px)",
		selectable:1, //make rows selectable
		data: orgindata,
		columns: [
			{ title: "공통코드", field: "new_TBL_CODE", headerHozAlign: "center", hozAlign: "right" },
			{ title: "공통코드 명", field: "new_TBL_NAME", headerHozAlign: "center" },
			{ title: "비고", field: "new_TBL_INDEX", headerHozAlign: "center", width: 100 }
		],
	});
}

function GridSetting2(NEW_TBL_CODE) {
	$.ajax({
		method: "GET",
		url: "codeManageRest/view2?NEW_TBL_CODE=" + NEW_TBL_CODE,
		success: function(data) {
			datas = data;
			console.log(datas);
			table = new Tabulator("#example-table2", {
				rowDblClick: function(e, row) {
					//e - the click event object
					//row - row component
					console.log("ok");
					console.log(row.getData());

					if (element2 == null) {
						element2 = row.getElement();
						element2.style.color = "red";
						element2.style.fontWeight = "bold";
					}
					else {
						element2.style.color = "black";
						element2.style.fontWeight = "normal";
						row.getElement().style.color = "red";
						row.getElement().style.fontWeight = "bold";
						element2 = row.getElement();
					}

					$("#updateModal").modal("show");

					//child_TBL_USE_STATUS
					if (row.getData().child_TBL_USE_STATUS == "true")
						document.getElementById("mCHILD_TBL_USE_STATUS").checked = true;
					else
						document.getElementById("mCHILD_TBL_USE_STATUS").checked = false;
						
					child_TBL_NO = row.getData().child_TBL_NO;
					new_TBL_CODE = row.getData().new_TBL_CODE;
					child_TBL_NUM = row.getData().child_TBL_NUM;
					document.getElementById("mCHILD_TBL_TYPE").value = row.getData().child_TBL_TYPE;
					document.getElementById("mCHILD_TBL_RMARK").value = row.getData().child_TBL_RMARK;
				},
				rowUpdated: function(row) {
					alert("수정");
				},
				//페이징
				height: "calc(100% - 175px)",
				selectable:1, //make rows selectable
				data: datas,
				columns: [
					{ title: "순번", field: "child_TBL_NUM", headerHozAlign: "center", hozAlign: "right" },
					{ title: "No", field: "child_TBL_NO", headerHozAlign: "center" },
					{ title: "타입명", field: "child_TBL_TYPE", headerHozAlign: "center" },
					{ title: "비고", field: "child_TBL_RMARK", headerHozAlign: "center", width: 100 },
					{
						title: "사용유무",
						field: "child_TBL_USE_STATUS",
						headerHozAlign: "center",
						width: 110,
						headerHozAlign: "center",
						hozAlign: "center",
						formatter: "tickCross",
						editor: "select2",
						editorParams: { values: { "true": "사용", "false": "미사용" } }
					}
				],
			});
		}
	});
}

window.onload = function() {
	$.ajax({
		method: "GET",
		url: "codeManageRest/view1",
		success: function(data) {
			GridSetting1(data);
		}
	});
}

// 입력 모달창에서 취소버튼을 누를때 입력 모달창 데이터를 초기화
function insResetBtn() {
	document.getElementById("CHILD_TBL_TYPE").value = "";
	document.getElementById("CHILD_TBL_RMARK").value = "";
	document.getElementById("CHILD_TBL_USE_STATUS").checked = false;
}

function modResetBtn() {
	document.getElementById("mCHILD_TBL_TYPE").value = "";
	document.getElementById("mCHILD_TBL_RMARK").value = "";
	document.getElementById("mCHILD_TBL_USE_STATUS").checked = false;
}

function modBtn() {
	$.ajax({
		method: "POST",
		data: {
			NEW_TBL_CODE: new_TBL_CODE,
			CHILD_TBL_NUM: child_TBL_NUM,
			CHILD_TBL_TYPE: document.getElementById("mCHILD_TBL_TYPE").value,
			CHILD_TBL_RMARK: document.getElementById("mCHILD_TBL_RMARK").value,
			CHILD_TBL_USE_STATUS: document.getElementById("mCHILD_TBL_USE_STATUS").checked
		},
		url: "codeManageRest/update",
		success: function(data) {
			if (data == "Success") {
				alert("수정 완료 하였습니다.");

				location.reload();
			}
		}
	});
}

$('#insertModal').on('show.bs.modal', function () {
	$('#CHILD_TBL_TYPE').focus();
});

$('#updateModal').on('show.bs.modal', function () {
	$('#mCHILD_TBL_TYPE').focus();
});