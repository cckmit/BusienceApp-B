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
			if (cell.getField() == "workOrder_ItemCode") {
				//내용이 있을경우 검색해서 값이 하나일경우 생략, 아닐경우 팝업창
				itemPopupMaster(WO_input, cell)

			} else if (cell.getField() == "workOrder_EquipCode") {
				//내용이 있을경우 검색해서 값이 하나일경우 생략, 아닐경우 팝업창
				machinePopupMaster(WO_input, cell)

			} else if (cell.getField() == "workOrder_CompleteOrderTime") {
				salesOrderSelect(cell.getRow().getData().workOrder_ItemCode,
					cell.getRow().getData().workOrder_CompleteOrderTime)
			} else if (cell.getField() == "workOrder_Remark") {
				console.log("엥")
				WorkOrderTable.addRow();
			}
		}
	});
	//반환
	return WO_input;
};

var WorkOrderTable = new Tabulator("#WorkOrderTable", {
	height: "calc(62% - 100px)",
	layoutColumnsOnNewData: true,
	//커스텀 키 설정
	keybindings: {
		"navRight": "13"
	},
	rowSelected: function(row) {
		var registerTime = moment(row.getData().workOrder_RegisterTime).format('YYYY-MM-DD');
		salesOrderSelect(row.getData().workOrder_ItemCode, registerTime)
	},
	//행추가시 기능
	rowAdded: function(row) {
		row.select();
		
		row.update({
			workOrder_AllottedQty: "0",
			workOrder_OrderTime: today.toISOString().substring(0, 10),
			workOrder_CompleteOrderTime: tomorrow.toISOString().substring(0, 10),
			workOrder_Use_Status: true
		})

		//행이 추가되면 첫셀에 포커스
		do {
			setTimeout(function() {
				row.getCell("workOrder_ItemCode").edit();
			}, 100);
		}
		while (row.getData().workOrder_ItemCode === "undefined");

	},
	cellEditing: function(cell) {
		//셀위치 저장하여 포커싱부여
		cellPos = cell;
	},
	ajaxResponse:function(url, params, response){
		if(response.length == 0){
			toastr.info("목록이 없습니다.");	
		}
		return response;
    },
	columns: [
		{ formatter: "rowSelection", titleFormatter: "rowSelection", headerHozAlign: "center", hozAlign: "center", headerSort: false },
		{ title: "작업지시No", field: "workOrder_ONo", headerHozAlign: "center" },
		{ title: "제품코드", field: "workOrder_ItemCode", headerHozAlign: "center", editor: WO_inputEditor },
		{ title: "제품이름", field: "workOrder_ItemName", headerHozAlign: "center" },
		{ title: "규격1", field: "workOrder_INFO_STND_1", headerHozAlign: "center" },
		{ title: "규격2", field: "workOrder_INFO_STND_2", headerHozAlign: "center" },
		{ title: "품목분류1", field: "workOrder_Item_CLSFC_1_Name", headerHozAlign: "center"},
		{ title: "품목분류2", field: "workOrder_Item_CLSFC_2_Name", headerHozAlign: "center"},
		{ title: "설비코드", field: "workOrder_EquipCode", headerHozAlign: "center", editor: WO_inputEditor},
		{ title: "설비이름", field: "workOrder_EquipName", headerHozAlign: "center"},
		{ title: "지시수량", field: "workOrder_AllottedQty", headerHozAlign: "center", editor: WO_inputEditor},
		{ title: "등록일", field: "workOrder_RegisterTime", hozAlign: "right", headerHozAlign: "center" },
		{ title: "완료예정일", field: "workOrder_CompleteOrderTime", hozAlign: "right", headerHozAlign: "center", editor: WO_inputEditor },
		{ title: "특이사항", field: "workOrder_Remark", headerHozAlign: "center", editor: WO_inputEditor},
		{ title: "수정자", field: "workOrder_Worker", headerHozAlign: "center", visible: false }
	]
});

function Search() {
	var jsonData = {
		startDate: $("#startDate").val(),
		endDate: $("#endDate").val()
	}

	WorkOrderTable.setData("workOrderRest/workOrderSelect", jsonData)
}

$('#searchBtn').click(function() {
	Search();
})

function salesOrderSelect(itemCode, datetime) {
	var data = {
		itemCode: itemCode,
		endDate: datetime
	}

	SalesOrderMasterTable.setData("workOrderRest/workOrderSalesOrderSelect", data)
}

$("#addBtn").click(function(){
	WorkOrderTable.addRow();
})

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

//품목선택시 재고수량 같이 나오게
function item_gridInit(code, name, stnd_1, stnd_2) {
	cellPos.getRow().update({
		"workOrder_ItemCode": code,
		"workOrder_ItemName": name,
		"workOrder_INFO_STND_1": stnd_1,
		"workOrder_INFO_STND_2": stnd_2
	});
	
	//선택 후 포커스 이동
	cellPos.getElement().focus();	
}

function machine_gridInit(code, name) {
	cellPos.getRow().update({
		"workOrder_EquipCode": code,
		"workOrder_EquipName": name
	});
	//선택 후 포커스 이동
	cellPos.getElement().focus();
}

$('#saveBtn').click(function() {
	var selectedData = WorkOrderTable.getSelectedData();
	
	if (selectedData.length == 0) {
		alert("선택된 행이 없습니다.");
		return;
	}

	for (var i = 0; i < selectedData.length; i++) {
		if (selectedData[i].workOrder_ItemCode == undefined) {
			alert("제품코드가 입력되지 않은 행이 존재합니다.");
			return;
		}
		
		if (selectedData[i].workOrder_AllottedQty == 0) {
			alert("지시수량을 입력해 주세요.");
			return;
		}
	}
	workOrderSave(selectedData)
})

function workOrderSave(selectedData){
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
}

$('#deleteBtn').click(function(){
	var selectedData = WorkOrderTable.getSelectedData();
	
	if (selectedData.length == 0) {
		alert("선택된 행이 없습니다.");
		return;
	}else if(confirm(selectedData.length+"개의 행을 삭제하시겠습니까?")){
		workOrderDelete(selectedData);
	}
})

function workOrderDelete(selectedData){
	$.ajax({
		method: "delete",
		url: "workOrderRest/workOrderDelete",
		data: JSON.stringify(selectedData),
		contentType: 'application/json',
		beforeSend: function(xhr) {
			var header = $("meta[name='_csrf_header']").attr("content");
			var token = $("meta[name='_csrf']").attr("content");
			xhr.setRequestHeader(header, token);
		},
		success: function(data) {
			if (data) {
				alert("삭제 되었습니다.");
				Search();
			}
		}
	});
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
		{ title: "수주수량", field: "sales_Order_Qty", headerHozAlign: "center", hozAlign: "right",
			formatter: "money", formatterParams: { precision: false }}
	]
});