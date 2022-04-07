var maskEquipTable = new Tabulator("#maskEquipTable", {
	//페이징
	height: "calc(100% - 175px)",
	layoutColumnsOnNewData: true,
	movableRows: true,
	movableColumns: true,
	clipboard: false,
	movableRowsConnectedTables: "#workOrderTable",
	movableRowsSender: customReceiver,
	movableRowsReceiver: customReceiver,
	placeholder: "All Rows Moved",
	rowDblClick: function(e, row) {

		matOutputTable.deselectRow();
		row.select();

	},
	columns: [
		{ title: "설비코드", field: "equipment_INFO_CODE", headerHozAlign: "center", width: 120 },
		{ title: "설비명", field: "equipment_INFO_NAME", headerHozAlign: "center", hozAlign: "left", width: 170 }
	]
});

// 전송하는 테이블과 전송받는 테이블의 field명이 일치하고, movable 설정이 동일해야 연동 가능
var customReceiver = function(fromRow, toRow, fromTable) {

	var equip;

	//console.log(fromRow);
	// 보내는 row
	if (fromRow) {

		//console.log($(fromTable).attr('id'));

		// 작업지시 테이블 검색
		if (fromRow.getData().equipment_INFO_CODE == '') {
			equip = fromRow.getData().equipment_PACK_CODE
		} else if (fromRow.getData().equipment_PACK_CODE == '') {
			equip = fromRow.getData().equipment_INFO_CODE
		}

        // 작업시작되지 않은 데이터만 삭제 가능. 데이터 조회될 때도 작업시작 안 된 데이터만 조회됨
		$.when(deleteChkFunc(equip)
			.then(function(result) {
				//console.log(result);
				if (result.length > 0) {
					if (confirm("작업지시 데이터가 삭제됩니다. 삭제하시겠습니까?")) {
						for (i = 0; i < result.length; i++) {
							//console.log(result[0].workOrder_EquipCode);
							WO_Delete(result[0].workOrder_EquipCode);
						}
					} else {
						return false;
					}

				} else if (result.length == 0) {

					//alert("데이터가 없음");

					if (($(fromTable).attr('id') == 'workOrderTable') && (fromRow.getData().equipment_INFO_CODE == null || fromRow.getData().equipment_INFO_CODE == '')) {
						packEquipTable.addRow();

						packEquipTable.getRows()[packEquipTable.getDataCount("active") - 1].update({
							"equipment_PACK_CODE": fromRow.getData().equipment_PACK_CODE,
							"equipment_PACK_NAME": fromRow.getData().equipment_PACK_NAME
						});


						fromRow.delete();

					}

					if (($(fromTable).attr('id') == 'workOrderTable') && (fromRow.getData().equipment_PACK_CODE == null || fromRow.getData().equipment_PACK_CODE == '')) {
						maskEquipTable.addRow();

						maskEquipTable.getRows()[maskEquipTable.getDataCount("active") - 1].update({
							"equipment_INFO_CODE": fromRow.getData().equipment_INFO_CODE,
							"equipment_INFO_NAME": fromRow.getData().equipment_INFO_NAME
						});

						fromRow.delete();

					}
				}
			}))


		if ($(fromTable).attr('id') != 'workOrderTable') {
			workOrderTable.addRow();

			workOrderTable.getRows()[workOrderTable.getDataCount("active") - 1].update({
				"equipment_INFO_CODE": fromRow.getData().equipment_INFO_CODE,
				"equipment_INFO_NAME": fromRow.getData().equipment_INFO_NAME
			});

			workOrderTable.getRows()[workOrderTable.getDataCount("active") - 1].update({
				"equipment_PACK_CODE": fromRow.getData().equipment_PACK_CODE,
				"equipment_PACK_NAME": fromRow.getData().equipment_PACK_NAME
			});


			fromRow.delete();

		}

		//return true;
	}

	//console.log(fromRow);

	/*
		if (toRow) {
			
		}*/

	return false;

}


var packEquipTable = new Tabulator("#packEquipTable", {
	height: "calc(100% - 175px)",
	layoutColumnsOnNewData: true,
	movableRows: true,
	movableColumns: true,
	movableRowsConnectedTables: "#workOrderTable",
	movableRowsSender: customReceiver,
	movableRowsReceiver: customReceiver,
	clipboard: false,
	placeholder: "All Rows Moved",
	rowDblClick: function(e, row) {

		matOutputTable.deselectRow();
		row.select();

	},
	columns: [
		{ title: "포장 설비코드", field: "equipment_PACK_CODE", headerHozAlign: "center", width: 120 },
		{ title: "포장 설비명", field: "equipment_PACK_NAME", headerHozAlign: "center", hozAlign: "left", width: 170 }]
});

//matInputSub 커스텀 기능설정
var WO_InputEditor = function(cell, onRendered, success, cancel, editorParams) {
	//cell - 편집 가능한 셀의 셀 구성 요소
	//onRendered - 에디터가 렌더링 되었을 때 호출 할 함수
	//success - 성공적을 업데이트 된 값을 tabulator에 전달하기 위해 호출되는 함수
	//cancel - 편집을 중단하고 일반 셀로 돌아 가기 위해 호출하는 함수
	//editorParams - editorParams 열 정의 속성에 전달 된 params 객체

	//create 및 style editor
	var WO_input = document.createElement("input");

	WO_input.setAttribute("type", "text");

	//입력 생성 및 스타일 지정
	WO_input.style.padding = "3px";
	WO_input.style.width = "100%";
	WO_input.style.boxSizing = "border-box";

	//편집기의 값을 셀의 현재 값으로 설정
	if (cell.getValue() == undefined) {
		WO_input.value = "";
	} else {
		WO_input.value = cell.getValue();
	}

	//에디터가 선택되면 선택 상자에 포커스 설정 (타임 아웃은 편집기를 DOM에 추가 할 수 있음)
	onRendered(function() {
		WO_input.focus();
		WO_input.select();
		WO_input.style.css = "100%";
	});

	//값이 설정되면 업데이트 할 셀 트리거
	function onChange() {
		success(WO_input.value);
	}

	//바꼈을때 블러됫을때 함수 발동
	WO_input.addEventListener("change", onChange);
	WO_input.addEventListener("blur", onChange);

	//키버튼 이벤트
	WO_input.addEventListener("keydown", function(e) {
		if (e.keyCode == 13) {
			//수량셀
			if (cell.getField() == "outMat_Qty") {
				cell.nav().down();
			}
		}

		//제품코드 팝업창
		//코드셀체크
		if (cell.getField() == "workOrder_ItemCode") {
			//내용이 있을경우 검색해서 값이 하나일경우 생략, 아닐경우 팝업창
			//쿼리실행
			$.ajax({
				method: "GET",
				url: "product_check?PRODUCT_ITEM_CODE=" + WO_input.value,
				dataType: "json",
				success: function(data) {
					console.log(data)
					if (data.length == 1) {
						//검색어와 일치하는값이 있는경우
						cell.getRow().update({
							"workOrder_ItemCode": data[0].product_ITEM_CODE,
							"workOrder_ItemName": data[0].product_ITEM_NAME,
							"workOrder_INFO_STND_1": data[0].product_INFO_STND_1
						});
					} else {
						//검색어와 일치하는값이 없는경우, 팝업창
						itemPopup(WO_input.value, 'grid', '', 'material');
					}
				}
			})
		}

	});
	//반환
	return WO_input;
};

var workOrderTable = new Tabulator("#workOrderTable", {
	height: "calc(100% - 175px)",
	layout: "fitData",
	layoutColumnsOnNewData: true,
	movableRows: true,
	movableRowsConnectedTables: ["#maskEquipTable", "#packEquipTable"],
	movableRowsSender: customReceiver,
	movableRowsReceiver: customReceiver,
	rowAdded: function(row, cell) {
		row.select();
		//행이 추가되면 첫셀에 포커스
		do {
			setTimeout(function() {
				row.getCell("workOrder_ItemCode").edit();
				workOrderTable.deselectRow();
				row.select();
			}, 100);
		}
		while (row.getData().workOrder_ItemCode === "undefined");

	},
	rowSelected: function(row) {

		//버튼 설정
		/*ResetBtn()
		if(row.getData().order_mCheck != 'I'){
			UseBtn();
		}*/
		UseBtn();
	},
	cellEditing: function(cell) {
		//셀위치 저장하여 포커싱부여
		cellPos = cell;
	},
	rowClick: function(e, row) {
		workOrderTable.deselectRow();
		row.select();
	},
	columns: [
		{ formatter: "rowSelection", titleFormatter: "rowSelection", headerHozAlign: "center", hozAlign: "center", headerSort: false },
		{ title: "순번", field: "rownum", formatter: "rownum", headerHozAlign: "center", hozAlign: "center" },
		{ title: "품목코드", field: "workOrder_ItemCode", headerHozAlign: "center", hozAlign: "left", editor: WO_InputEditor },
		{ title: "품목명", field: "workOrder_ItemName", headerHozAlign: "center", width: 120 },
		{ title: "규격1", field: "workOrder_INFO_STND_1", headerHozAlign: "center", width: 120 },
		{ title: "품목분류1", field: "workOrder_Item_STND_1", headerHozAlign: "center", width: 120 },
		{ title: "품목분류2", field: "workOrder_Item_STND_2", headerHozAlign: "center", width: 120 },
		{ title: "설비코드", field: "equipment_INFO_CODE", headerHozAlign: "center", width: 120 },
		{ title: "설비명", field: "equipment_INFO_NAME", headerHozAlign: "center", width: 160 },
		{ title: "포장 설비코드", field: "equipment_PACK_CODE", headerHozAlign: "center", width: 120 },
		{ title: "포장 설비명", field: "equipment_PACK_NAME", headerHozAlign: "center", width: 160 }
	]
});

//셀위치저장
var cellPos = null;

//팝업창으로부터 특정 파라미터 값으로 데이터를 받는다 
function item_gridInit(PCode, PName, PSTND_1, PItemClsfc_1, PItemClsfc_2) {
	cellPos.getRow().update({
		"workOrder_ItemCode": PCode,
		"workOrder_ItemName": PName,
		"workOrder_INFO_STND_1": PSTND_1,
		"workOrder_Item_STND_1": PItemClsfc_1,
		"workOrder_Item_STND_2": PItemClsfc_2
	});
	//선택 후 포커스 이동
	cellPos.getElement().focus();
}

//workOrderTable 저장
function WO_Save() {

	var equipData;
	var itemData;

	$.when(saveChkFunc())
		.then(function(data) {
			//console.log(data);
			if (data != false) {

				for (i = 0; i < data.length; i++) {
					//console.log(data[i].equipment_INFO_CODE);

					if (data[i].equipment_PACK_CODE == undefined) {
						//console.log("포장설비없음");
						equipData = data[i].equipment_INFO_CODE
						//console.log(equipData);

					} else if (data[i].equipment_INFO_CODE == undefined) {
						equipData = data[i].equipment_PACK_CODE
					}

					itemData = data[i].workOrder_ItemCode;
				}

				datas = {
					WorkOrder_ItemCode: itemData,
					WorkOrder_EquipCode: equipData,
					WorkOrder_WorkStatus: "243"
				}

				//console.log(datas);

				//InputSub 저장부분
				$.ajax({
					method: "post",
					url: "workOrderRest/workOrderRegister",
					data: JSON.stringify(data),
					contentType: 'application/json',
					beforeSend: function(xhr) {
						var header = $("meta[name='_csrf_header']").attr("content");
						var token = $("meta[name='_csrf']").attr("content");
						xhr.setRequestHeader(header, token);
					},
					success: function(result) {
						//console.log(result);
						if (result == 0) {
							alert("중복된 값이 있습니다.");
						} else if (result == 1) {
							alert("저장되었습니다.");
						}
					}
				});
			}

		})
}

function deleteChkFunc(equip) {

	datas = {
		MachineCode: equip
	}

	var resultData = $.ajax({
		method: "get",
		async: false,
		url: "workOrderRest/workOrderChoice",
		data: datas,
		success: function(result) {
			resultData = result;
		}
	});

	return resultData;
}

function WO_Delete(equipData) {

	//console.log(equipData);

	datas = {
		WorkOrder_EquipCode: equipData
	}

	$.ajax({
		method: "post",
		url: "workOrderRest/workOrderDelete",
		data: datas,
		beforeSend: function(xhr) {
			var header = $("meta[name='_csrf_header']").attr("content");
			var token = $("meta[name='_csrf']").attr("content");
			xhr.setRequestHeader(header, token);
		},
		success: function(result) {
			if (result == 1) {
				//console.log(result);
				alert("저장되었습니다.");
				location.reload();
			} else if(result == 0) {
				alert("저장 중 오류가 발생했습니다. 잠시 후 다시 시도해 주세요.");
			}
		}
	});
}

function saveChkFunc() {
	var selectedRow = workOrderTable.getData("selected");
	for (i = 0; i < selectedRow.length; i++) {
		if (selectedRow[i].workOrder_ItemCode == '') {
			alert("품목을 선택하세요.");
			return false;
		}
	}

	return selectedRow;
}

$('#WO_SaveBtn').click(function() {
	WO_Save();
})

// 설비 정보 리스트 조회
function machineListSelect(value) {

	var resultData;

	// 설비정보 불러오는 ajax
	$.ajax({
		method: "get",
		async: false,
		url: "machineManageRest/dtlMachineList",
		data: { "EQUIPMENT_TYPE": value },
		success: function(result) {
			resultData = result;
		}
	});

	return resultData;
}


$(document).ready(function() {

	var maskEquipList = machineListSelect(1);
	var packEquipList = machineListSelect(2);
	//console.log(maskEquipList);
	//console.log(packEquipList);
	maskEquipTable.setData(maskEquipList);
	packEquipTable.setData(packEquipList);
	workOrderTable.setData("workOrderRest/workOrderSubSelect");
	//console.log(workOrderTable);
})
