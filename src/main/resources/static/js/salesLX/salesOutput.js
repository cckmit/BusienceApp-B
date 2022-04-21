var returnData = new Array();

// salesOrderMaster
var salesOutputTable = new Tabulator("#salesOutputTable", {
	//페이징
	pagination: "local",
	paginationSize: 20,
	headerFilterPlaceholder: null,
	height: "calc(50% - 85px)",
	//복사하여 엑셀 붙여넣기 가능
	clipboard: true,
	rowFormatter: function(row) {
		//order_mCheck가 Y면 빨간색 I면 파란색으로 바꿔준다
		if (row.getData().sales_Output_Order_mCheck == "Y") {
			row.getElement().style.color = "red";
		} else if (row.getData().sales_Output_Order_mCheck == "I") {
			row.getElement().style.color = "blue";
		}
	},
	//행클릭 이벤트
	rowClick: function(e, row) {
		salesOutputTable.deselectRow();
		row.select();
	},
	rowSelected: function(row) {
		SOS_Search(row.getData().sales_Output_Order_mOrder_No);
	},
	//복사하여 엑셀 붙여넣기 가능
	clipboard: true,
	columns: [
		{ title: "지시번호", field: "sales_Output_Order_mOrder_No", headerHozAlign: "center", hozAlign: "right", headerFilter: true, width: 117 },
		{ title: "코드", field: "sales_Output_Order_mCode", headerHozAlign: "center", headerFilter: true, width: 60 },
		{ title: "거래처명", field: "sales_Output_Order_mName", headerHozAlign: "center", headerFilter: true },
		{
			title: "수주일", field: "sales_Output_Order_mDate", headerHozAlign: "center", hozAlign: "right", headerFilter: true, width: 130,
			formatter: "datetime", formatterParams: { outputFormat: "YYYY-MM-DD HH:mm:ss" }
		},
		{ title: "납기일자", field: "sales_Output_Order_mDlvry_Date", headerHozAlign: "center", hozAlign: "right", headerFilter: true, width: 85 },
		{ title: "특이사항", field: "sales_Output_Order_mRemarks", headerHozAlign: "center", headerFilter: true, width: 85 },
		{
			title: "합계금액", field: "sales_Output_Order_mTotal", headerHozAlign: "center", hozAlign: "right", headerFilter: true,
			formatter: "money", formatterParams: { precision: false }, width: 85
		},
		{ title: "수정자", field: "sales_Output_Order_mModifier", headerHozAlign: "center", headerFilter: true, width: 72 },
		{
			title: "수정일자", field: "sales_Output_Order_mModify_Date", headerHozAlign: "center", headerFilter: true, width: 130,
			formatter: "datetime", formatterParams: { outputFormat: "YYYY-MM-DD HH:mm:ss" }
		},
		{ title: "목록확인", field: "sales_Output_Order_mCheck", visible: false }
	],
});

//orderMaster 목록검색 matOrder와 동일하지만 테이브이름이 다르고, 미입고 컬럼이 추가됨
function SO_Search() {
	datas = {
		startDate: $("#startDate").val(),
		endDate: $("#endDate").val(),
		ClientCode: $("#Sales_InMat_Client_Code").val(),
		SalesOrderNo: $("#sales_Output_Order_mOrder_No").val()
	}
	$.ajax({
		method: "GET",
		dataType: "json",
		async: false,
		url: "salesOutputOrderRest/SOR_Search",
		data: datas,
		success: function(data) {
			console.log(data);
			salesOutputTable.setData(data);
			salesOutputSubTable.clearData();
			salesLotMasterTable.clearData();
			salesOutMatTable.clearData();

			SSM_total = 0;
			salesLotMasterTable.redraw();
			ResetBtn();
		}
	});
}

$("#SO_SearchBtn").click(function() {
	SO_Search();
})

// 판매구분 select를 구성하기위한 ajax
var dtl_arr = new Object();

$.ajax({
	method: "GET",
	async: false,
	url: "dtl_tbl_select?NEW_TBL_CODE=19",
	success: function(datas) {
		for (i = 0; i < datas.length; i++) {
			dtl_arr[datas[i].child_TBL_NO] = datas[i].child_TBL_TYPE;
		}
	}
});

// salesOrderList
var salesOutputSubTable = new Tabulator("#salesOutputSubTable", {
	height: "calc(50% - 90px)",
	//복사하여 엑셀 붙여넣기 가능
	clipboard: true,
	rowFormatter: function(row) {
		//미출고가 0이면 글자색을 빨간색으로 바꿔준다
		if (row.getData().sales_Output_Order_lNot_Stocked == 0) {
			row.getElement().style.color = "red";
		}
	},
	//행을클릭하면 salesOutMatTable에 행추가
	rowClick: function(e, row) {
		//행을 추가하면서 해당행의 데이터값을 넘겨줌
		//미출고재고가 있다면
		if (row.getData().sales_Output_Order_lNot_Stocked != 0) {
			salesOutputSubTable.deselectRow();
			row.select();
			add_mode = false;
		}
	},
	rowSelected: function(row) {

		$("#sales_Output_Order_lCode").val(row.getData().sales_Output_Order_lCode);
		$("#sales_Output_Order_lName").val(row.getData().sales_Output_Order_lName);

		// LotMaster 품목코드로 검색
		SLM_Search(row.getData().sales_Output_Order_lCode);

		// OutMat 지시번호와 품목코드로 검색
		//SSM_Search(row.getData().sales_Output_Order_lOrder_No, row.getData().sales_Output_Order_lCode)

		ResetBtn();
	},
	columns: [
		{ title: "순번", field: "sales_Output_Order_lNo", headerHozAlign: "center", hozAlign: "center", width: 60 },
		{ title: "지시번호", field: "sales_Output_Order_lOrder_No", visible: false },
		{ title: "코드", field: "sales_Output_Order_lCode", headerHozAlign: "center", width: 60 },
		{ title: "품목명", field: "sales_Output_Order_lName", headerHozAlign: "center" },
		{ title: "수량", field: "sales_Output_Order_lQty", headerHozAlign: "center", hozAlign: "right", width: 60 },
		{ title: "출고수량", field: "sales_Output_Order_lSum", headerHozAlign: "center", hozAlign: "right", width: 85 },
		{
			title: "단가", field: "sales_Output_Order_lUnit_Price", headerHozAlign: "center", hozAlign: "right",
			formatter: "money", formatterParams: { precision: false }, topCalc: function() { return "합계금액" }, width: 75
		},
		{
			title: "금액", field: "sales_Output_Order_lPrice", headerHozAlign: "center", hozAlign: "right",
			formatter: "money", formatterParams: { precision: false }, width: 75,
			//맨윗줄 합계금액 나타내기
			topCalc: function(values, data, calcParams) {
				//values - array of column values
				//data - all table data
				//calcParams - params passed from the column definition object

				var calc = 0;

				values.forEach(function(value) {
					calc += value
				});
				return calc;
			}, topCalcFormatter: "money", topCalcFormatterParams: { precision: false }
		},
		{ title: "미출고", field: "sales_Output_Order_lNot_Stocked", headerHozAlign: "center", hozAlign: "right", width: 75 },
		{ title: "재고", field: "sales_Output_Order_SQty", headerHozAlign: "center", hozAlign: "right", width: 60 },
		{
			title: "구분", field: "sales_Output_Order_Send_Clsfc", headerHozAlign: "center",
			formatter: function(cell, formatterParams) {
				var value = cell.getValue();
				if (dtl_arr[value] != null) {
					value = dtl_arr[value];
				} else {
					value = "";
				}
				return value;
			}, width: 65
		}]
});

//salesOutputSub 목록검색
function SOS_Search(sales_Output_Order_lOrder_No) {

	datas = {
		SalesOrderNo: sales_Output_Order_lOrder_No
	}

	//발주넘버
	$.ajax({
		method: "GET",
		url: "salesOutputOrderRest/SORS_Search",
		data: datas,
		success: function(datas) {
			console.log(datas);
			salesOutputSubTable.setData(datas);
			salesLotMasterTable.clearData();
			salesOutMatTable.clearData();
		}
	});
}

// 현재 출고합계수량
var SSM_total = 0;
//4번째 테이블에 행 추가모드 확인
var add_mode = false;

var salesLotMasterTable = new Tabulator("#salesLotMasterTable", {
	height: "calc(50% - 124.1px)",
	//복사하여 엑셀 붙여넣기 가능
	clipboard: true,
	rowClick: function(e, row) {
		//salesOutputSubTable에서 선택된 행
		FOS_selectedRow = salesOutputSubTable.getData("selected")[0]

		//미출고재고 - 현재 출고 합계수량이 0보다 크면 선택함
		if (FOS_selectedRow.sales_Output_Order_lNot_Stocked - SSM_total > 0) {
			row.toggleSelect();
		} else {
			row.deselect();
		}
	},
	rowSelected: function(row) {
		if (!add_mode) {
			salesOutMatTable.clearData();
			add_mode = true;
			UseBtn();
		}
		//salesOutputSubTable에서 선택된 행
		FOS_selectedRow = salesOutputSubTable.getData("selected")[0]
		//출고수량 정하기

		//미출고재고 - 현재 출고합계수량 < LotMaster 수량
		if (FOS_selectedRow.sales_Output_Order_lNot_Stocked - SSM_total < row.getData().lm_Qty) {
			FO_QTY = FOS_selectedRow.sales_Output_Order_lNot_Stocked - SSM_total
		} else {
			FO_QTY = row.getData().lm_Qty
		}
		//salesOutMatTable 반영
		salesOutMatTable.addRow(
			{
				"sales_OutMat_Lot_No": row.getData().lm_LotNo,
				"sales_OutMat_Code": row.getData().lm_ItemCode,
				"sales_OutMat_Name": row.getData().lm_ItemName,
				"sales_OutMat_Qty": row.getData().lm_Qty,
				"sales_OutMat_Unit_Price": FOS_selectedRow.sales_Output_Order_lUnit_Price,
				"sales_OutMat_Price": FO_QTY * FOS_selectedRow.sales_Output_Order_lUnit_Price,
				"sales_OutMat_Send_Clsfc": FOS_selectedRow.sales_Output_Order_Send_Clsfc,
				"sales_OutMat_Date": moment(new Date()).format('YYYY-MM-DD HH:mm:ss')
			});
		SSM_total = SSM_total + FO_QTY;
		salesLotMasterTable.redraw();
	},
	rowDeselected: function(row) {
		//클릭한 행과 같은 랏번호를 찾아서 삭제해줌
		for (i = 0; i < salesOutMatTable.getDataCount(); i++) {
			if (salesOutMatTable.getData()[i].sales_OutMat_Lot_No == row.getData().sales_LMaster_LotNo) {
				SSM_total = SSM_total - salesOutMatTable.getData()[i].sales_OutMat_Qty;
				salesOutMatTable.getRows()[i].delete();
				salesLotMasterTable.redraw();
			}
		}

	},
	columns: [
		{ title: "LotNo", field: "lm_LotNo", headerHozAlign: "center", width: 145 },
		{ title: "품목코드", field: "lm_ItemCode", headerHozAlign: "center", bottomCalc: function() { return "출고합계수량" }, width: 100 },
		{ title: "품목명", field: "lm_ItemName", headerHozAlign: "center", width: 145 },
		{ title: "규격1", field: "lm_STND_1", headerHozAlign: "center", width: 100 },
		{ title: "분류1", field: "lm_Item_CLSFC_1", headerHozAlign: "center", width: 100 },
		{
			title: "수량", field: "lm_Qty", headerHozAlign: "center", hozAlign: "right",
			formatter: "money", formatterParams: { precision: false }, bottomCalc: function() { return SSM_total; }
		},
	]
});

//LotMaster 목록검색
function SLM_Search(ItemCode) {


	datas = {
		ItemCode: ItemCode
	}

	$.ajax({
		method: "GET",
		url: "salesOutputRest/SLM_Search",
		data: datas,
		async: false,
		success: function(FLM_datas) {
			if (FLM_datas.length == 0) {
				alert("해당 품목은 재고량이 없습니다.")
			}
				
			// 전체 출고일 경우 => lot 데이터 순차적 추가
			// 선택 출고일 경우 => lot 그리드 초기화 후 추가	
			var rows = salesOutputSubTable.getRows("selected");
			
			for (i = 0; i < FLM_datas.length; i++) {

				if (rows.length > 1) {

					salesLotMasterTable.addRow(
						{
							"lm_LotNo": FLM_datas[i].lm_LotNo,
							"lm_ItemCode": FLM_datas[i].lm_ItemCode,
							"lm_ItemName": FLM_datas[i].lm_ItemName,
							"lm_STND_1": FLM_datas[i].lm_Qty,
							"lm_Item_CLSFC_1": FLM_datas[i].lm_Item_CLSFC_1,
							"lm_Qty": FLM_datas[i].lm_Qty
						});
				} else {
					salesLotMasterTable.redraw();
					salesLotMasterTable.setData(FLM_datas);
				}
			}
		}
	});

}

var salesOutMatTable = new Tabulator("#salesOutMatTable", {
	height: "calc(50% - 124.1px)",
	//복사하여 엑셀 붙여넣기 가능
	clipboard: true,
	selectable: true,
	//커스텀 키 설정
	rowAdded: function(row) {
		row.getTable().deselectRow();
		row.select();
	},
	columns: [
		{ title: "순번", field: "Number", headerHozAlign: "center", hozAlign: "center", formatter: "rownum", width: 60 },
		{ title: "LotNo", field: "sales_OutMat_Lot_No", headerHozAlign: "center", width: 145 },
		{ title: "코드", field: "sales_OutMat_Code", headerHozAlign: "center", width: 60 },
		{ title: "품목명", field: "sales_OutMat_Name", headerHozAlign: "center", width: 150 },
		{
			title: "수량", field: "sales_OutMat_Qty", headerHozAlign: "center", hozAlign: "right", width: 60,
			formatter: "money", formatterParams: { precision: false }
		},
		{
			title: "단가", field: "sales_OutMat_Unit_Price", headerHozAlign: "center", hozAlign: "right", width: 60,
			formatter: "money", formatterParams: { precision: false }
		},
		{
			title: "금액", field: "sales_OutMat_Price", headerHozAlign: "center", hozAlign: "right", width: 60,
			formatter: "money", formatterParams: { precision: false }
		},
		{
			title: "구분", field: "sales_OutMat_Send_Clsfc", headerHozAlign: "center", width: 70, editor: "select",
			formatter: function(cell, formatterParams) {
				var value = cell.getValue();
				if (dtl_arr[value] != null) {
					value = dtl_arr[value];
				} else {
					value = "";
				}
				return value;
			}
		},
		{
			title: "출고일자", field: "sales_OutMat_Date", headerHozAlign: "center", hozAlign: "right", width: 137,
			formatter: "datetime", formatterParams: { outputFormat: "YYYY-MM-DD HH:mm:ss" }
		}
	]
});

//Sales_OutMat 목록검색
function SSM_Search(CusNo, Code) {

	data = {
		sales_OutMat_Order_No: CusNo,
		sales_OutMat_Code: Code
	}
	$.ajax({
		method: "GET",
		url: "salesOutputWORest/SSM_Search?data=" + encodeURI(JSON.stringify(data)),
		success: function(SSM_datas) {
			console.log(SSM_datas);
			salesOutMatTable.setData(SSM_datas);
		}
	});
}

//SOM_Save
function SOM_Save() {
	if (salesOutMatTable.getData().length == 0) {
		alert("저장할 데이터가 없습니다.")
		return;
	}
	selectedRow = salesOutputTable.getData("selected")[0];

	//OrderSub 저장부분
	$.ajax({
		method: "post",
		async: false,
		url: "salesOutputWORest/SOM_Save?masterData=" + encodeURI(JSON.stringify(selectedRow))
			+ "&listData=" + encodeURI(JSON.stringify(salesOutMatTable.getData())),
		success: function(result) {
			if (result == "error") {
				alert("오류")
			} else {
				alert("저장되었습니다.");
				SO_Search()
				Order_mCode_select(selectedRow.sales_Output_Order_mOrder_No);
			}
		}
	});
}

//SOM_SaveBtn
$('#SOM_SaveBtn').click(function() {
	SOM_Save();
})

//품목코드로 salesOutputTable 선택하는 코드
function Order_mCode_select(value) {
	rowCount = salesOutputTable.getDataCount();
	//컬럼값을 검색해서 입력값을 포함하는 값이 있으면 선택한다. 
	for (i = 0; i < rowCount; i++) {
		Output_Order_mOrder_No = salesOutputTable.getData()[i].sales_Output_Order_mOrder_No;
		//지시번호가 입력내용을 포함하면 코드 실행
		if (Output_Order_mOrder_No == value) {
			//발주번호가 같은 행 선택
			salesOutputTable.deselectRow();
			salesOutputTable.getRows()[i].select();
			break;
		}
	}
}

// 전체 출고
$("#allOutput").click(function() {
	var rows = salesOutputSubTable.getRows();
	for (var i = 0; i < salesOutputSubTable.getDataCount(); i++) {
		if (rows[i].getData().sales_Output_Order_lSum == 0) {
			salesOutputSubTable.selectRow(rows[i]);
		}
	}
})