//커스텀 기능설정
var SO_inputEditor = function(cell, onRendered, success, cancel, editorParams) {
	//cell - 편집 가능한 셀의 셀 구성 요소
	//onRendered - 에디터가 렌더링 되었을 때 호출 할 함수
	//success - 성공적을 업데이트 된 값을 tabulator에 전달하기 위해 호출되는 함수
	//cancel - 편집을 중단하고 일반 셀로 돌아 가기 위해 호출하는 함수
	//editorParams - editorParams 열 정의 속성에 전달 된 params 객체

	//create 및 style editor
	var SO_input = document.createElement("input");

	SO_input.setAttribute("type", "text");

	//입력 생성 및 스타일 지정
	SO_input.style.padding = "3px";
	SO_input.style.width = "100%";
	SO_input.style.boxSizing = "border-box";

	//편집기의 값을 셀의 현재 값으로 설정
	if (cell.getValue() == undefined) {
		SO_input.value = "";
	} else {
		SO_input.value = cell.getValue();
	}

	//에디터가 선택되면 선택 상자에 포커스 설정 (타임 아웃은 편집기를 DOM에 추가 할 수 있음)
	onRendered(function() {
		SO_input.focus();
		SO_input.select();
		SO_input.style.css = "100%";
	});

	//값이 설정되면 업데이트 할 셀 트리거
	function onChange() {
		success(SO_input.value);
	}

	//바꼈을때 블러됫을때 함수 발동
	SO_input.addEventListener("change", onChange);
	SO_input.addEventListener("blur", onChange);

	//키버튼 이벤트
	SO_input.addEventListener("keydown", function(e) {
		//거래처코드 셀에서 백스페이스를 눌렀을경우 거래처명이 사라지게함
		if (e.keyCode == 8) {
			if (cell.getField() == "sales_Order_mCode") {
				cell.getRow().update({ "sales_Order_mName": '' })
			}
		}
		if (e.keyCode == 13) {
			//거래처코드셀 체크
			if (cell.getField() == "sales_Order_mCode") {
				//내용이 있을경우 검색해서 값이 하나일경우 생략, 아닐경우 팝업창
				$.ajax({
					method: "GET",
					url: "customer_check?Cus_Code=" + SO_input.value,
					dataType: "json",
					success: function(data) {
						if (data.length == 1) {
							//검색어와 일치하는값이 있는경우
							cell.getRow().update({
								"sales_Order_mCode": data[0].cus_Code,
								"sales_Order_mName": data[0].cus_Name
							});
						} else {
							//검색어와 일치하는값이 없는경우, 팝업창
							customerPopup(SO_input.value, 'grid', '', 'out');
						}

					}
				})
			}

			//특이사항셀 체크
			if (cell.getField() == "sales_Order_mRemarks") {
				//납기일자를 체크해서 잘못되었으면 반응 안함
				if (cell.getRow().getData().sales_Order_mDlvry_Date.length != "10") {

					alert("납기일자를 잘못 입력하였습니다.")

				} else {
					//특이사항에서 엔터를 하면 행추가
					if (cell.getRow().getData().sales_Order_mCheck != "I") {
						salesOrderSubTable.addRow();
					}
				}
			}
		}
	});
	//반환
	return SO_input;
};

//팝업창으로부터 특정 파라미터 값으로 데이터를 받는다 
function customer_gridInit(CCode, CName) {
	cellPos.getRow().update({
		"sales_Order_mCode": CCode,
		"sales_Order_mName": CName
	});
	//선택 후 포커스 이동
	cellPos.getElement().focus();
}

//셀위치저장
var cellPos = null;

var salesOrderTable = new Tabulator("#salesOrderTable", {
	layoutColumnsOnNewData: true,
	paginationAddRow: "table",
	headerFilterPlaceholder: null,
	height: "calc(100% - 175px)",
	rowFormatter: function(row) {
		//order_mCheck가 Y면 빨간색 I면 파란색으로 바꿔준다
		if (row.getData().sales_Order_mCheck == "Y") {
			row.getElement().style.color = "red";
		} else if (row.getData().sales_Order_mCheck == "I") {
			row.getElement().style.color = "blue";
		}
	},
	//커스텀 키 설정
	keybindings: {
		"navRight": "13"
	},
	rowClick: function(e, row) {
		row.getTable().deselectRow();
		row.select();
	},
	rowSelected: function(row) {
		//stock의 데이터를 없에준다
		salesOrderStockTable.clearData();
		
		SOL_Search(row.getData().sales_Order_mCus_No);

		//버튼 설정
		ResetBtn()
		if (row.getData().sales_Order_mCheck != 'I') {
			UseBtn();
		}
	},
	//행추가시 기능
	rowAdded: function(row) {
		row.getTable().deselectRow();
		row.select();

		row.update({
			"sales_Order_mCus_No": '',
			"sales_Order_mCode": $(".clientCode").val(),
			"sales_Order_mName": $(".clientName").val(),
			"sales_Order_mDate": moment(new Date()).format('YYYY-MM-DD HH:mm:ss'),
			"sales_Order_mDlvry_Date": moment(new Date()).format('YYYY-MM-DD')			
		});

		//행이 추가되면 첫셀에 포커스
		do {
			setTimeout(function() {
				row.getCell("sales_Order_mCode").edit();
			}, 100);
		}
		while (row.getData().sales_Order_mCode === "undefined");

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
		{ title: "수주번호", field: "sales_Order_mCus_No", headerHozAlign: "center", hozAlign: "right", headerFilter: true },
		{ title: "코드", field: "sales_Order_mCode", headerHozAlign: "center", headerFilter: true, editor: SO_inputEditor },
		{ title: "거래처명", field: "sales_Order_mName", headerHozAlign: "center", headerFilter: true },
		{ title: "수주일", field: "sales_Order_mDate", headerHozAlign: "center", hozAlign: "right", editor: SO_inputEditor, headerFilter: true, width: 135,
			formatter: "datetime", formatterParams: { outputFormat: "YYYY-MM-DD HH:mm:ss"}},
		{ title: "납기일자", field: "sales_Order_mDlvry_Date", headerHozAlign: "center", hozAlign: "right", editor: SO_inputEditor, headerFilter: true },
		{ title: "특이사항", field: "sales_Order_mRemarks", headerHozAlign: "center", editor: SO_inputEditor, headerFilter: true },
		{ title: "합계금액", field: "sales_Order_mTotal", headerHozAlign: "center", hozAlign: "right", headerFilter: true,
			formatter: "money", formatterParams: { precision: false }},
		{ title: "목록확인", field: "sales_Order_mCheck", visible: false }
	]
});

//SO_AddBtn
$('#SO_AddBtn').click(function() {
	//행추가
	salesOrderTable.addRow();
})

// salesMaster 검색
function SO_Search() {
	salesOrderTable.setData("salesOrderRest/SO_Search", {
		startDate: $("#startDate").val(),
		endDate: $("#endDate").val(),
		clientCode: $(".clientCode").val(),
		condition: "Y"
	})
	.then(function(){
		// list와 Stock의 데이터를 없애준다
		salesOrderSubTable.clearData();
		salesOrderStockTable.clearData();
		ResetBtn()
		//SO_ADD버튼 활성화
		if ($('#SO_AddBtn').hasClass('unUseBtn')) {
			$('#SO_AddBtn').removeClass('unUseBtn');
		}
	})
}

//SO_SearchBtn
$('#SO_SearchBtn').click(function() {
	SO_Search();
})

//커스텀 기능설정
var SOL_InputEditor = function(cell, onRendered, success, cancel, editorParams) {
	//cell - 편집 가능한 셀의 셀 구성 요소
	//onRendered - 에디터가 렌더링 되었을 때 호출 할 함수
	//success - 성공적을 업데이트 된 값을 tabulator에 전달하기 위해 호출되는 함수
	//cancel - 편집을 중단하고 일반 셀로 돌아 가기 위해 호출하는 함수
	//editorParams - editorParams 열 정의 속성에 전달 된 params 객체

	//create 및 style editor
	var SOL_input = document.createElement("input");

	SOL_input.setAttribute("type", "text");

	//입력 생성 및 스타일 지정
	SOL_input.style.padding = "3px";
	SOL_input.style.width = "100%";
	SOL_input.style.boxSizing = "border-box";

	//편집기의 값을 셀의 현재 값으로 설정
	if (cell.getValue() == undefined) {
		SOL_input.value = "";
	} else {
		SOL_input.value = cell.getValue();
	}

	//에디터가 선택되면 선택 상자에 포커스 설정 (타임 아웃은 편집기를 DOM에 추가 할 수 있음)
	onRendered(function() {
		SOL_input.focus();
		SOL_input.select();
		SOL_input.style.css = "100%";
	});

	//값이 설정되면 업데이트 할 셀 트리거
	function onChange() {
		success(SOL_input.value);
	}

	//바꼈을때 블러됫을때 함수 발동
	SOL_input.addEventListener("change", onChange);
	SOL_input.addEventListener("blur", onChange);

	//키버튼 이벤트
	SOL_input.addEventListener("keydown", function(e) {
		//제품코드 셀에서 백스페이스를 눌렀을경우 제품명이 사라지게함
		if (e.keyCode == 8) {
			if (cell.getField() == "sales_Order_lCode") {
				cell.getRow().update({
					"sales_Order_lName": '',
					"sales_Order_STND_1": '',
					"sales_Order_lUnit_Price": ''
				})
			}
		}
		//제품코드 팝업창
		if (e.keyCode == 13) {
			//코드셀체크
			if (cell.getField() == "sales_Order_lCode") {
				//내용이 있을경우 검색해서 값이 하나일경우 생략, 아닐경우 팝업창
				//쿼리실행
				$.ajax({
					method: "GET",
					url: "product_check?PRODUCT_ITEM_CODE=" + SOL_input.value,
					dataType: "json",
					success: function(data) {
						if (data.length == 1) {
							//검색어와 일치하는값이 있는경우
							cell.getRow().update({
								"sales_Order_lCode": data[0].product_ITEM_CODE,
								"sales_Order_lName": data[0].product_ITEM_NAME,
								"sales_Order_STND_1": data[0].product_INFO_STND_1,
								"sales_Order_lUnit_Price": data[0].product_UNIT_PRICE
							})
						} else {
							//검색어와 일치하는값이 없는경우, 팝업창
							itemPopup(SOL_input.value, 'grid', '', 'sales');
						}
					}
				})
			}
			//비고셀 체크
			if (cell.getField() == "sales_Order_lInfo_Remark") {
				//구분넘기기
				cell.nav().next();
				//만약 마지막행의 합계금액이 비어있을경우 추가 안됨
				lastRow = salesOrderSubTable.getData()[salesOrderSubTable.getDataCount("active") - 1];
				if (lastRow.sales_Order_lQty == 0) {
					alert("수량을 입력해주세요.");
					cell.nav().prev();
				} else if (lastRow.sales_Order_lCode.length != "6") {
					alert("제품코드를 잘못 입력하였습니다.")
					cell.nav().prev();
				}
			}
		}
	});
	//반환
	return SOL_input;
};

// 출하구분 select를 구성하는 리스트
var output_dtl = dtlSelectList(19);

//salesOrderSubTable 이미 저장되있는 데이터는 편집 불가능 하게 하는 확인 기능
var editCheck = function(cell) {
	//cell - the cell component for the editable cell
	//get row data
	var data = cell.getRow().getData();
	return data.sales_Order_lCus_No == null;
}

var salesOrderSubTable = new Tabulator("#salesOrderSubTable", {
	layoutColumnsOnNewData: true,
	height: "calc(85% - 175px)",
	selectable: true,
	tabEndNewRow: true,
	//커스텀 키 설정
	keybindings: {
		"navNext": "13"
	},
	rowFormatter: function(row) {
		//order_lNot_Stocked가 0이면 빨간색으로, 입고수량보다 작으면 파란색으로 나타냄
		if (row.getData().sales_Order_lNot_Stocked == 0) {
			row.getElement().style.color = "red";
		} else if (row.getData().sales_Order_lNot_Stocked < row.getData().sales_Order_lQty) {
			row.getElement().style.color = "blue";
		}
	},
	//행을 클릭하면 salesOrderStockTable에 리스트가 나타남
	rowClick: function(e, row) {
		SOS_Search(row.getData().sales_Order_lCode);
	},
	//행이 추가될때마다 인덱스 부여
	rowAdded: function(row) {
		row.update({
			"sales_Order_lPrice": 0,
			"sales_Order_lInfo_Remark": '',
			"sales_Order_Send_Clsfc": "211"
		});

		//행이 추가되면 첫셀에 포커스
		do {
			setTimeout(function() {
				row.getCell("sales_Order_lCode").edit();
			}, 100);
		}
		while (row.getData().sales_Order_lCode === "undefined");

	},
	cellEditing: function(cell) {
		//셀위치 저장하여 포커싱부여
		cellPos = cell;
		//편집하는 행의 품목에 대한 재고가 테이블에 나타남
		var cell_lCode = null;
		if (cell.getRow().getData().sales_Order_lCode != cell_lCode) {
			if (cell.getRow().getData().sales_Order_lCode.length == 6) {
				SOS_Search(cell.getRow().getData().sales_Order_lCode);

				cell_lCode = cell.getRow().getData().sales_Order_lCode
			}
		}
	},
	ajaxResponse:function(url, params, response){
		if(response.length == 0){
			toastr.info("목록이 없습니다.");	
		}
		return response;
    },
	columns: [
		{ formatter: "rowSelection", titleFormatter: "rowSelection", headerHozAlign: "center", hozAlign: "center", headerSort: false },
		{ title: "순번", field: "rownum", headerHozAlign: "center", hozAlign: "center", formatter: "rownum" },
		{ title: "수주No", field: "sales_Order_lCus_No", visible: false },
		{ title: "코드", field: "sales_Order_lCode", headerHozAlign: "center", editor: SOL_InputEditor, editable: editCheck },
		{ title: "제품명", field: "sales_Order_lName", headerHozAlign: "center" },
		{ title: "규격1", field: "sales_Order_STND_1", headerHozAlign: "center" },
		{ title: "수량", field: "sales_Order_lQty", headerHozAlign: "center", hozAlign: "right", editor: SOL_InputEditor,
			formatter: "money", formatterParams: { precision: false },
			cellEdited: function(cell) {
				//수량이 변경될때 금액값이 계산되어 입력
				temQty = cell.getValue();
				temUP = cell.getRow().getData().sales_Order_lUnit_Price;
				if (temQty * temUP > 0) {
					iPrice = temQty * temUP
				} else {
					iPrice = 0;
				}
				cell.getRow().update({ "sales_Order_lPrice": iPrice });
			}
		},
		{ title: "단가", field: "sales_Order_lUnit_Price", headerHozAlign: "center", hozAlign: "right", editor: SOL_InputEditor,
			topCalc: function() { return "합계금액" }, formatter: "money", formatterParams: { precision: false },
			cellEdited: function(cell) {
				//단가가 변경될때 금액값이 계산되어 입력
				temQty = cell.getRow().getData().sales_Order_lQty;
				temUP = cell.getValue();

				if (temQty * temUP > 0) {
					iPrice = temQty * temUP
				} else {
					iPrice = 0;
				}
				cell.getRow().update({ "sales_Order_lPrice": iPrice });
			}
		},
		{ title: "금액", field: "sales_Order_lPrice", headerHozAlign: "center", hozAlign: "right", formatter: "money", width: 130, formatterParams: { precision: false },
			//금액이 변경될때 합계금액을 계산하여 mastertable에 입력
			topCalc: function(values, data, calcParams) {
				//values - array of column values
				//data - all table data
				//calcParams - params passed from the column definition object

				var calc = 0;

				values.forEach(function(value) {
					if (isNaN(value)) {
						value = 0;
					}
					calc += value
				});
				if (salesOrderTable.getDataCount("selected") == 1) {
					salesOrderTable.getRows('selected')[0].update({ "sales_Order_mTotal": calc });
				}
				return calc;
			}, topCalcFormatter: "money", topCalcFormatterParams: { precision: false }
		},
		{ title: "미입고재고", field: "sales_Order_lNot_Stocked", visible: false },
		{ title: "비고", field: "sales_Order_lInfo_Remark", headerHozAlign: "center", editor: SOL_InputEditor },
		{ title: "구분", field: "sales_Order_Send_Clsfc", headerHozAlign: "center", editor: "select",
			formatter: function(cell, formatterParams) {
				var value = cell.getValue();
				if (output_dtl[value] != null) {
					value = output_dtl[value];
				} else {
					value = "";
				}
				return value;
			},
			editorParams: { values: output_dtl }
		}
	]
});

//팝업창으로부터 특정 파라미터 값으로 데이터를 받는다 
function item_gridInit(PCode, PName, PSTND_1, PPrice) {
	cellPos.getRow().update({
		"sales_Order_lCode": PCode,
		"sales_Order_lName": PName,
		"sales_Order_STND_1": PSTND_1,
		"sales_Order_lQty": 0,
		"sales_Order_lUnit_Price": PPrice
	})
	cellPos.getElement().focus();
}

//salesorderList 목록검색
function SOL_Search(sales_Order_lCus_No) {
	$("#sales_Order_lCus_No").val(sales_Order_lCus_No);

	salesOrderSubTable.setData("salesOrderRest/SOL_Search",{SalesOrderNo: sales_Order_lCus_No})
}

//행추가
function SOL_Add() {
	//master 납기일자 입력했는지 확인
	if (salesOrderTable.getData("selected")[0].sales_Order_mDlvry_Date.length != "10") {
		alert("납기일자를 잘못 입력하였습니다.")
		return false;
	}
	//목록의 제품명과 합계금액을 검사하여 입력하지 않았을 경우 추가 안됨
	for (i = 0; i < salesOrderSubTable.getDataCount("active"); i++) {
		rowData = salesOrderSubTable.getData()[i];
		if (rowData.sales_Order_lQty == 0 || rowData.sales_Order_lName == '') {
			alert("작성중인 행이 있습니다.");
			return false;
		}
	}
	salesOrderSubTable.addRow();
}

//SOL_AddBtn
$('#SOL_AddBtn').click(function() {
	SOL_Add();
})

// 삭제
function SOL_Delete() {
	//list테이블에서 선택한 데이터
	selectedData = salesOrderSubTable.getSelectedData();
	realData = []

	// 기존 조회한 데이터는 수주번호가 있고, 새로 추가된데이터는 수주번호가 없다
	if (confirm("선택한 행이 삭제됩니다. 삭제하시겠습니까?")) {
		//list데이터에서 수주번호가 있는것만 따로 배열에 담아서 쿼리로 실행한다.
		for (i = 0; i < selectedData.length; i++) {
			if (selectedData[i].sales_Order_lCus_No != null) {
				realData.push(selectedData[i]);
			}
		}

		//배열에 담은 데이터가 있을경우 쿼리 실행
		if (realData.length != 0) {
			$.ajax({
				method: "delete",
				url: "salesOrderRest/SOL_Delete",
				data: JSON.stringify(realData),
				contentType: 'application/json',
				beforeSend: function(xhr) {
					var header = $("meta[name='_csrf_header']").attr("content");
					var token = $("meta[name='_csrf']").attr("content");
					xhr.setRequestHeader(header, token);
				},
				success: function(result) {
					if (result) {
						SO_Search();
						alert("삭제되었습니다.");
					} else {
						alert("출고 처리된 수주목록은 삭제할 수 없습니다.");
					}
				}
			})
		}
	}
}

//SOL_DeleteBtn
$('#SOL_DeleteBtn').click(function() {
	SOL_Delete();
})

//OrderSub 저장
function SOL_Save() {
	selectedRow = salesOrderTable.getData("selected")[0];
	subTable = salesOrderSubTable.getData();
	saveData = new Array();

	for(let i=0;i<subTable.length;i++){
		if(subTable[i].sales_Order_lQty > 0){
			saveData.push(subTable[i]);	
		}
	}
	
	if (item_Code_Check(subTable.length)) {
		alert("중복된 품목이 있습니다.");
		return false;
	}
	if (saveData.length == 0) {
		alert("저장할 데이터가 없습니다.");
		return false;
	}
	//OrderSub 저장부분
	$.ajax({
		method: "post",
		url: "salesOrderRest/SOL_Save",
		data: { masterData: JSON.stringify(selectedRow), subData: JSON.stringify(saveData) },
		beforeSend: function(xhr) {
			var header = $("meta[name='_csrf_header']").attr("content");
			var token = $("meta[name='_csrf']").attr("content");
			xhr.setRequestHeader(header, token);
		},
		success: function(result) {
			if (result == "error") {
				alert("빈칸이 있어서 저장할 수 없습니다.")
			} else {
				alert("저장되었습니다.");
				$("#sales_Order_lCus_No").val(result);
				SO_Search();
				Cus_No_select();
			}
		}
	});

	if ($('#SO_AddBtn').hasClass('unUseBtn')) {
		$('#SO_AddBtn').removeClass('unUseBtn');
	}
}

//SOL_SaveBtn
$('#SOL_SaveBtn').click(function() {
	SOL_Save();
})

//수주번호로 OrderMaster 선택하는 코드
function Cus_No_select() {
	rowCount = salesOrderTable.getDataCount("active");
	//input값에 있는 수주번호와 같은 값을 선택한다. 
	for (i = 0; i < rowCount; i++) {
		Cus_No = salesOrderTable.getColumn("sales_Order_mCus_No").getCells();
		//테이블의 수주번호가 선택되었던 수주번호와 같으면 코드 실행
		if (Cus_No[i].getValue() == $('#sales_Order_lCus_No').val()) {
			//수주번호가 같은 행 선택
			Cus_No[i].getRow().select();
			break;
		}
	}
}

//list에서 같은 품목을 추가할때 경고 알리고 추가안됨
function item_Code_Check(rowCount) {
	itemCode = salesOrderSubTable.getColumn("sales_Order_lCode").getCells();
	//컬럼값을 검색해서 입력값을 포함하는 값이 있으면 선택한다.
	for (i = 0; i < rowCount - 1; i++) {
		count = 0;
		for (j = i + 1; j < rowCount; j++) {
			if (itemCode[i].getValue() == itemCode[j].getValue()) {
				count++
				//중복일경우
				if (count > 0) {
					salesOrderSubTable.selectRow(itemCode[i].getRow())
					salesOrderSubTable.selectRow(itemCode[j].getRow())
					return true;
				}
			}
		}
	}
	return false;
}

var salesOrderStockTable = new Tabulator("#salesOrderStockTable", {
	height: "15%",
	columns: [
		{ title: "제품코드", field: "s_ItemCode", headerHozAlign: "center" },
		{ title: "제품명", field: "s_ItemName", headerHozAlign: "center" },
		{ title: "규격1", field: "s_Item_Standard_1", headerHozAlign: "center" },
		{ title: "규격2", field: "s_Item_Standard_2", headerHozAlign: "center" },
		{ title: "분류1", field: "s_Item_Classfy_1_Name", headerHozAlign: "center" },
		{ title: "분류2", field: "s_Item_Classfy_2_Name", headerHozAlign: "center" },
		{ title: "재질", field: "s_Item_Material", headerHozAlign: "center" },
		{ title: "수량", field: "s_Qty", headerHozAlign: "center", hozAlign: "right", formatter: "money", formatterParams: { precision: false }}
	]
});

// salesOrderStock 목록검색
function SOS_Search(itemCode) {
	salesOrderStockTable.setData("salesStockRest/salesStockSelect", {itemCode: itemCode});
}