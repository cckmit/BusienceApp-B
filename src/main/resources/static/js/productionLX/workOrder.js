
var maskEquipTable = new Tabulator("#maskEquipTable", {
	//페이징
	height: "calc(100% - 175px)",
	layoutColumnsOnNewData: true,
	movableRows: true,
	movableColumns: true,
	movableRowsConnectedTables: "#workOrderTable",
	movableRowsSender: customReceiver,
	movableRowsReceiver: customReceiver,
	placeholder: "All Rows Moved",
	rowDblClick: function(e, row) {

		matOutputTable.deselectRow();
		row.select();

	},
	columns: [
		{ title: "설비코드", field: "equipment_INFO_CODE", headerHozAlign: "center", width: 100 },
		{ title: "설비명", field: "equipment_INFO_NAME", headerHozAlign: "center", hozAlign: "left", width: 170 }
	]
});

var rowArr = new Array();
var arrData = new Array();
var equipNameRawArr = new Array();
var equipCodeArr = [];
var equipNameArr = [];

// 전송하는 테이블과 전송받는 테이블의 field명이 일치하고, movable 설정이 동일해야 연동 가능
var customReceiver = function(fromRow, toRow, fromTable) {

    // 보내는 row
	if (fromRow) {
		fromRow.delete();
	}

	//console.log(fromRow);

	rowArr.push(fromRow);

	//console.log(toRow);
	
	// 받은 row
	if (toRow) {
		
		for (i = 0; i < rowArr.length; i++) {
			console.log(rowArr[i]);
			// 받은 row를 update 해줌
			toRow.update({
				"equipment_INFO_CODE":"," + equipCodeArr + "," + rowArr[i].getData().equipment_INFO_CODE,
				"equipment_INFO_NAME":"," + equipNameArr + "," + rowArr[i].getData().equipment_INFO_NAME
			});
			
			arrData.push(rowArr[i].getData().equipment_INFO_CODE);
			equipNameRawArr.push(rowArr[i].getData().equipment_INFO_NAME);
			
			// 중복 데이터 검사 
			arrData.forEach((element) => {
				if(!equipCodeArr.includes(element)) {
					equipCodeArr.push(element);
				}
			})
			
			equipNameRawArr.forEach((ENelement) => {
				if(!equipNameArr.includes(ENelement)) {
					equipNameArr.push(ENelement);
				}
			})
			
			//console.log(equipCodeArr);
			//console.log(equipNameArr);
			
		}

		return true;
	}

	return false;

}

var packEquipTable = new Tabulator("#packEquipTable", {
	height: "calc(100% - 175px)",
	layoutColumnsOnNewData: true,
	rowDblClick: function(e, row) {

		matOutputTable.deselectRow();
		row.select();

		if (!inputDuplCheck(row)) {
			matOutputSubTable.addRow({
				"outMat_Code": row.getData().sm_Code,
				"outMat_Name": row.getData().sm_Name,
				"stock_Qty": row.getData().sm_Qty,
				"outMat_Qty": 0,
				"outMat_Consignee": '1',
				"outMat_Send_Clsfc": '208'
			});
			UseBtn();
		}

	},
	columns: [
		{ title: "포장 설비코드", field: "equipment_INFO_CODE", headerHozAlign: "center", width: 100 },
		{ title: "포장 설비명", field: "equipment_INFO_NAME", headerHozAlign: "center", hozAlign: "left", width: 170 }]
});



//orderMaster 목록검색 matOrder와 동일하지만 테이브이름이 다르고, 미입고 컬럼이 추가됨
function MOS_Search() {

	data = {
		SM_Code: matOutputTable.getData("selected").sm_Code,
		SM_Name: matOutputTable.getData("selected").sm_Name,
		itemCode: $("#outmatLX_itemCode").val()
	}

	$.ajax({
		method: "GET",
		dataType: "json",
		async: false,
		url: "matOutputLXRest/MOS_Search?data=" + encodeURI(JSON.stringify(data)),
		success: function(data) {
			//console.log(data);
			matOutputTable.setData(data);
			matOutputSubTable.clearData();
		}
	});
}

$('#MR_SearchBtn').click(function() {
	MOS_Search();
})

// 출고구분 select를 구성하는 리스트
var output_dtl = dtlSelectList(18);
for (prop in output_dtl) {
	if (output_dtl[prop] == "출고반품") {
		delete output_dtl[prop]
	}
}

// 매니저명 select를 구성하는 리스트
var manager_dtl = dtlSelectList(1);

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
	layoutColumnsOnNewData: true,
	movableRows: true,
	movableRowsConnectedTables: "#maskEquipTable",
	movableRowsSender: customReceiver,
	movableRowsReceiver: customReceiver,
	data: [],
	rowAdded: function(row, cell) {
		row.select();
		console.log(row.getData().workOrder_ItemCode);
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
	cellEditing: function(cell) {
		//셀위치 저장하여 포커싱부여
		cellPos = cell;
	},
	rowClick: function(e, row) {
		matOutputSubTable.deselectRow();
		row.select();
	},
	columns: [
		{ title: "순번", field: "rownum", formatter: "rownum", headerHozAlign: "center", hozAlign: "center" },
		{ title: "품목코드", field: "workOrder_ItemCode", headerHozAlign: "center", hozAlign: "left", editor: WO_InputEditor },
		{ title: "품목명", field: "workOrder_ItemName", headerHozAlign: "center", width: 120 },
		{ title: "규격1", field: "workOrder_INFO_STND_1", headerHozAlign: "center", width: 120 },
		{ title: "품목분류1", field: "workOrder_Item_STND_1", headerHozAlign: "center", width: 120 },
		{ title: "품목분류2", field: "workOrder_Item_STND_2", headerHozAlign: "center", width: 120 },
		{ title: "설비코드", field: "equipment_INFO_CODE", headerHozAlign: "center" },
		{ title: "설비명", field: "equipment_INFO_NAME", headerHozAlign: "center", width: 120 },
		{ title: "포장 설비코드", field: "workOrder_PackEquipCode", headerHozAlign: "center", width: 120 },
		{ title: "포장 설비명", field: "workOrder_PackEquipName", headerHozAlign: "center", hozAlign: "right" }
	]
});

// 행추가
$('#WO_AddBtn').click(function() {
	WO_Add();
})

function WO_Add() {
	workOrderTable.addRow();
}

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

//inMatTable 저장
function MOM_Save() {

	var realData = [];

	var rowData = matOutputSubTable.getData();

	//realData.push(rowData[i]);

	for (var i = 0; i < rowData.length; i++) {
		if (rowData[i].stock_Qty < rowData[i].outMat_Qty) {
			alert("재고 수량보다 출고 수량이 더 많습니다.");
			return false;
		}

		if (rowData[i].outMat_Qty != 0) {
			realData.push(rowData[i]);
		}
	}

	//InputSub 저장부분
	$.ajax({
		method: "post",
		url: "matOutputLXRest/MOM_Save",
		data: JSON.stringify(realData),
		contentType: 'application/json',
		beforeSend: function(xhr) {
			var header = $("meta[name='_csrf_header']").attr("content");
			var token = $("meta[name='_csrf']").attr("content");
			xhr.setRequestHeader(header, token);
		},
		success: function(result) {
			//console.log(result);
			if (result == 0) {
				alert("중복된 값이 있습니다..");
			} else if (result == 1) {
				MOS_Search();
				alert("저장되었습니다.");
			}
		}
	});

}

$('#MOM_SaveBtn').click(function() {
	MOM_Save();
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
})