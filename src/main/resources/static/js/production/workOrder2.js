//셀위치저장
var cellPos = null;

//matOrder 커스텀 기능설정
var WO_inputEditor = function(cell, onRendered, success, cancel, editorParams) {
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

			//거래처코드셀 체크
			if (cell.getField() == "workOrder_ItemName") {
				//내용이 있을경우 검색해서 값이 하나일경우 생략, 아닐경우 팝업창
				itemPopupMaster(WO_input, cell)

			} else if (cell.getField() == "workOrder_EquipName") {
				//내용이 있을경우 검색해서 값이 하나일경우 생략, 아닐경우 팝업창
				machinePopupMaster(WO_input, cell)

			} else if (cell.getField() == "workOrder_CompleteOrderTime") {
				salesOrderSelect(cell.getRow().getData().workOrder_ItemCode,
					cell.getRow().getData().workOrder_CompleteOrderTime)
			}

			cell.nav().next();
		}
	});
	//반환
	return WO_input;
};

var WorkOrderTable = new Tabulator("#WorkOrderTable", {
	height: "calc(62% - 100px)",
	layoutColumnsOnNewData: true,
	selectable: true,
	rowFormatter: function(row) {
		if (row.getData().workOrder_ONo != null)
			row.getElement().style.color = "blue";
	},
	rowSelected: function(row) {
		var registerTime = moment(row.getData().workOrder_RegisterTime).format('YYYY-MM-DD');
		salesOrderSelect(row.getData().workOrder_ItemCode, registerTime)
	},
	//행추가시 기능
	rowAdded: function(row) {
		row.select();

		//행이 추가되면 첫셀에 포커스
		do {
			setTimeout(function() {
				row.getCell("workOrder_ItemName").edit();
			}, 100);
		}
		while (row.getData().workOrder_ItemName === "undefined");

	},
	cellEditing: function(cell) {
		//셀위치 저장하여 포커싱부여
		cellPos = cell;
	},
	columns: [
		{ formatter: "rowSelection", titleFormatter: "rowSelection", headerHozAlign: "center", hozAlign: "center", headerSort: false },
		{ title: "작업지시No", field: "workOrder_ONo", headerHozAlign: "center" },
		{ title: "제품코드", field: "workOrder_ItemCode", headerHozAlign: "center" },
		{ title: "제품이름", field: "workOrder_ItemName", headerHozAlign: "center", editor: WO_inputEditor },
		{ title: "규격1", field: "workOrder_INFO_STND_1", headerHozAlign: "center" },
		{ title: "품목분류1", field: "workOrder_ITEM_CLSFC_NAME_1", headerHozAlign: "center", visible: false },
		{ title: "품목분류2", field: "workOrder_ITEM_CLSFC_NAME_2", headerHozAlign: "center", visible: false },
		{ title: "설비코드", field: "workOrder_EquipCode", headerHozAlign: "center"},
		{ title: "설비이름", field: "workOrder_EquipName", headerHozAlign: "center", editor: WO_inputEditor},
		{ title: "등록일", field: "workOrder_RegisterTime", align: "right", headerHozAlign: "center" },
		{ title: "특이사항", field: "workOrder_Remark", headerHozAlign: "center", editor: "input"	},
		{ title: "사용유무", field: "workOrder_Use_Status", headerHozAlign: "center", align: "center", formatter: "tickCross", editor: true },
		{ title: "수정자", field: "workOrder_Worker", align: "right", headerHozAlign: "center", visible: false }
	]
});

function itemPopupMaster(WO_input, cell) {
	var ajaxResult = $.ajax({
		method: "GET",
		url: "product_check?PRODUCT_ITEM_CODE=" + WO_input.value,
		dataType: "json",
		success: function(data) {
			if (data.length == 1) {
				cell.getRow().update({
					"workOrder_ItemCode": data[0].product_ITEM_CODE,
					"workOrder_ItemName": data[0].product_ITEM_NAME
				});
			} else {
				//검색어와 일치하는값이 없는경우, 팝업창
				itemPopup(WO_input.value, 'grid', '', 'sales');
			}
		}
	})
	return ajaxResult;
}

function machinePopupMaster(WO_input, cell) {
	var ajaxResult = $.ajax({
		method: "GET",
		url: "equipment_check?EQUIPMENT_INFO_CODE=" + WO_input.value,
		dataType: "json",
		success: function(data) {
			if (data.length == 1) {
				//검색어와 일치하는값이 있는경우
				cell.getRow().update({
					"workOrder_EquipCode": data[0].equipment_INFO_CODE,
					"workOrder_EquipName": data[0].equipment_INFO_NAME
				});
			} else {
				//검색어와 일치하는값이 없는경우, 팝업창
				machinePopup(WO_input.value, 'grid', '');
			}
		}
	})
	return ajaxResult;
}

var SalesOrderMasterTable = new Tabulator("#SalesOrderMasterTable", {
	layoutColumnsOnNewData: true,
	height: "calc(38% - 75px)",
	columns: [
		{ title: "수주No", field: "sales_Order_mCus_No", headerHozAlign: "center" },
		{ title: "거래처", field: "sales_Order_mCode", headerHozAlign: "center" },
		{ title: "거래처명", field: "sales_Order_mName", headerHozAlign: "center" },
		{ title: "납기일자", field: "sales_Order_mDlvry_Date", headerHozAlign: "center" },
		{ title: "제품코드", field: "sales_Order_ItemCode", headerHozAlign: "center" },
		{ title: "제품명", field: "sales_Order_ItemName", headerHozAlign: "center" },
		{ title: "수주수량", field: "sales_Order_Qty", headerHozAlign: "center", formatter: "money", formatterParams: { precision: false }, align: "right" }
	]
});

$('#FI_NewBtn').click(function() {
	newRow_Add();
})

$('#FI_SearchBtn').click(function() {
	Search();
})

function Search() {
	jsonData = {
		startDate: $("#startDate").val(),
		endDate: $("#endDate").val()
	}

	WorkOrderTable.setData("workOrderRest/workOrderSelect", jsonData)

}

function newRow_Add() {
	LRow = WorkOrderTable.getRows()[WorkOrderTable.getRows().length - 1];
	index = 0;

	if (WorkOrderTable.getRows().length > 0) {
		newRow_flag = false;
		lastRowData = LRow.getData();
		index += lastRowData.index1 + 1;

		if (typeof lastRowData.workOrder_ItemCode == "undefined") {
			alert("제품이 입력되지 않았습니다.");
			return;
		}

		if (typeof lastRowData.workOrder_EquipCode == "undefined") {
			alert("설비가 입력되지 않았습니다.");
			return;
		}
	}

	WorkOrderTable.addRow({
		index1: index,
		workOrder_PQty: "0",
		workOrder_OrderTime: today.toISOString().substring(0, 10),
		workOrder_CompleteOrderTime: tomorrow.toISOString().substring(0, 10),
		workOrder_Use_Status: true
	});
}

var cellElement = null;

//완료예정일을 입력했을경우 완료예정일까지 수주데이터를 검색하여 나타냄
/*
function Date_Check(cell) {

	cellElement = cell.getElement();

	cellElement.addEventListener('keydown', function(e) {
		if (e.keyCode == 13) {
			var cellValue = cell.getValue();

			if (cellValue.length != 10) {
				alert("날짜 입력형식이 잘못 되었습니다. ex)2021-05-15");
				cell.restoreOldValue();
				return;
			}
			salesOrderSelect(WorkOrderTable_workOrder_ItemName_cell.getRow().getData().workOrder_ItemCode);

			cell.nav().right();
		}
	});

	cellElement = null;
}*/

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

$('#FI_SaveBtn').click(function() {
	var selectedData = WorkOrderTable.getSelectedData();
	
	if (selectedData.length == 0) {
		alert("선택된 행이 없습니다.");
		return;
	}

	for (var i = 0; i < selectedData.length; i++) {
		var workOrder_ItemCode = selectedData[i].workOrder_ItemCode;
		if (workOrder_ItemCode == undefined) {
			alert("제품코드가 입력되지 않은 행이 존재합니다.");
			return;
		}
	}

	$.ajax({
		method: "post",
		url: "workOrderRest/workOrderRegister",
		data: JSON.stringify(selectedData),
		contentType: 'application/json',
		beforeSend: function(xhr) {
			var header = $("meta[name='_csrf_header']").attr("content");
			var token = $("meta[name='_csrf']").attr("content");
			xhr.setRequestHeader(header, token);
		},
		success: function(data) {
			if (data) {
				alert("작업지시가 등록 되었습니다.");
				Search();
			}
		}
	});
})

function salesOrderSelect(itemCode, dateTime) {
	var data = {
		itemCode: itemCode,
		endDate: dateTime
	}

	SalesOrderMasterTable.setData("workOrderRest/workOrderSalesOrderSelect", data)
}

function machine_gridInit(code, name) {
	cellPos.getRow().update({
		"workOrder_EquipCode": code,
		"workOrder_EquipName": name
	});
	//선택 후 포커스 이동
	cellPos.getElement().focus();
}

//품목선택시 재고수량 같이 나오게
function item_gridInit(code, name, stnd_1) {
	cellPos.getRow().update({
		"workOrder_ItemCode": code,
		"workOrder_ItemName": name,
		"workOrder_INFO_STND_1": stnd_1
	});
	
	//선택 후 포커스 이동
	cellPos.getElement().focus();	
}

$(document).ready(function() {
	Search();
})