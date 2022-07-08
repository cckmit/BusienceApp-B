var salesOrderListTable = new Tabulator("#salesOrderListTable", {
	headerFilterPlaceholder: null,
	height: "calc(100% - 175px)",
	//복사하여 엑셀 붙여넣기 가능
	clipboard: true,
	rowFormatter:function(row){
		//order_mCheck가 Y면 빨간색 I면 파란색으로 바꿔준다
        if(row.getData().sales_Order_mCheck == "Y"){
            row.getElement().style.color = "red";
        }else if(row.getData().sales_Order_mCheck == "I"){
            row.getElement().style.color = "blue";
		}
    },
	rowClick: function(e, row) {
		row.getTable().deselectRow();
		salesOrderListStockTable.clearData();
		row.select();
	},
	rowSelected:function(row){
    	SOLS_Search(row.getData().sales_Order_mCus_No);
    },
	columns: [
		{ title: "수주번호", field: "sales_Order_mCus_No", headerHozAlign: "center", hozAlign: "right", headerFilter: true, width: 117 },
		{ title: "코드", field: "sales_Order_mCode", headerHozAlign: "center", headerFilter: true, width: 60 },
		{ title: "거래처명", field: "sales_Order_mName", headerHozAlign: "center", headerFilter: true },
		{
			title: "수주일", field: "sales_Order_mDate", headerHozAlign: "center", hozAlign: "right", headerFilter: true, width: 137,
			formatter: "datetime", formatterParams: { outputFormat: "YYYY-MM-DD HH:mm:ss", color: "red" }
		},
		{ title: "납기일자", field: "sales_Order_mDlvry_Date", headerHozAlign: "center", hozAlign: "right", headerFilter: true, width: 85 },
		{ title: "특이사항", field: "sales_Order_mRemarks", headerHozAlign: "center", headerFilter: true, width: 85 },
		{ title: "합계금액", field: "sales_Order_mTotal", headerHozAlign: "center", hozAlign: "right", headerFilter: true, formatter: "money", formatterParams: { precision: false }, width: 85 },
		{ title: "수정자", field: "sales_Order_mModifier", headerHozAlign: "center", headerFilter: true, width: 72 },
		{
			title: "수정일자", field: "sales_Order_mModify_Date", headerHozAlign: "center", headerFilter: true, width: 137,
			formatter: "datetime", formatterParams: { outputFormat: "YYYY-MM-DD HH:mm:ss" }
		},
		{ title: "목록확인", field: "sales_Order_mCheck", visible: false }
	]
});

// salesMaster 목록 검색
function SOL_Search() {
	
	datas = {
		startDate: $("#startDate").val(),
		endDate: $("#endDate").val(),
		ClientCode: $("#Sales_InMat_Client_Code").val(),
		condition : $("#saleOrderTypeListSelectBox option:selected").val()
	}
	
	salesOrderListTable.setData("salesOrderRest/SO_Search", datas)
	.then(function() {
		// list와 stock 데이터를 없애준다.
		salesOrderListSubTable.clearData();
		salesOrderListStockTable.clearData();
	})
}

$("#SOL_SearchBtn").click(function(){
	SOL_Search();
})

// 출하구분 select를 구성하는 리스트
var output_dtl = dtlSelectList(19);

var salesOrderListSubTable = new Tabulator("#salesOrderListSubTable", {
	headerFilterPlaceholder: null,
	//Sub Total 색상
	rowFormatter: function(row) {
		if (row.getData().sales_Order_lNot_Stocked <= 0) {
			row.getElement().style.backgroundColor = "#1E88E5";
			row.getElement().style.color = "white";
		}
	},
	height: "calc(90% - 175px)",
	//복사하여 엑셀 붙여넣기 가능
	clipboard: true,
	tabEndNewRow: true,
	//행을 클릭하면 salesOrderReportStockTable에 리스트가 나타남
	rowClick: function(e, row) {
		row.getTable().deselectRow();
		row.select();
	},
	rowSelected:function(row){
    	SOSS_Search(row.getData().sales_Order_lCode);
    },
	columns: [
		{ title: "순번", field: "sales_Order_lNo", headerHozAlign: "center", hozAlign: "center", width: 60 },
		{ title: "수주No", field: "sales_Order_lCus_No"},
		{ title: "코드", field: "sales_Order_lCode", headerHozAlign: "center", width: 60 },
		{ title: "제품명", field: "sales_Order_lName", headerHozAlign: "center", width: 200 },
		{ title: "규격1", field: "sales_Order_STND_1", headerHozAlign: "center", width: 70 },
		{
			title: "수량", field: "sales_Order_lQty", headerHozAlign: "center", hozAlign: "right",
			formatter: "money", formatterParams: { precision: false }, width: 60},
		{
			title: "단가", field: "sales_Order_lUnit_Price", headerHozAlign: "center", hozAlign: "right",
			topCalc: function() { return "합계금액" }, formatter: "money", formatterParams: { precision: false }, width: 75},
		{
			title: "금액", field: "sales_Order_lPrice", headerHozAlign: "center", hozAlign: "right", width: 80, formatter: "money", formatterParams: { precision: false, },
			//금액이 변경될때 합계금액을 계산하여 mastertable에 입력
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
		{ title: "비고", field: "sales_Order_lInfo_Remark", headerHozAlign: "center", width: 70 },
		{ title: "구분", field: "sales_Order_Send_Clsfc", headerHozAlign: "center", width: 70,
			formatter: function(cell, formatterParams) {
				var value = cell.getValue();
				if(output_dtl[value] != null){
						value = output_dtl[value];	
					}else{
						value = "";
					}
			    return value;
			}},
		{title: "미입고", field: "sales_Order_lNot_Stocked", visible: false}
	]
});

//SALESorderList 목록검색
function SOLS_Search(sales_Order_lCus_No) {
	$("#sales_Order_lCus_No").val(sales_Order_lCus_No);
	
	var datas = {
		SalesOrderNo : sales_Order_lCus_No
	}
	
	salesOrderListSubTable.setData("salesOrderRest/SOL_Search" , datas);
	
}

var salesOrderListStockTable = new Tabulator("#salesOrderListStockTable", {
	selectable: 1,
	height: "10%",
	//복사하여 엑셀 붙여넣기 가능
	clipboard: true,
	columns: [
		{ title: "제품코드", field: "s_ItemCode", headerHozAlign: "center" },
		{ title: "제품명", field: "s_ItemName", headerHozAlign: "center" },
		{ title: "규격1", field: "s_Item_Standard_1", headerHozAlign: "center" },
		{ title: "규격2", field: "s_Item_Standard_2", headerHozAlign: "center" },
		{ title: "분류1", field: "s_Item_Classfy_1_Name", headerHozAlign: "center" },
		{ title: "분류2", field: "s_Item_Classfy_2_Name", headerHozAlign: "center" },
		{ title: "재질", field: "s_Item_Material", headerHozAlign: "center" },
		{ title: "수량", field: "s_Qty", headerHozAlign: "center", hozAlign: "right", formatter: "money", formatterParams: { precision: false }, }
	]
});

// salesOrderStock 목록검색
function SOSS_Search(sales_Order_lCode) {
	salesOrderListStockTable.setData("salesStockRest/salesStockSelect", {ItemCode : sales_Order_lCode})
}

