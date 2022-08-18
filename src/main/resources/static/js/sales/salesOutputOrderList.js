var salesOutputOrderReportTable = new Tabulator("#salesOutputOrderReportTable", {
	headerFilterPlaceholder: null,
	height: "calc(100% - 175px)",
	rowFormatter:function(row){
		//order_mCheck가 Y면 빨간색 I면 파란색으로 바꿔준다
        if(row.getData().sales_Output_Order_mCheck == "Y"){
            row.getElement().style.color = "red";
        }else if(row.getData().sales_Output_Order_mCheck == "I"){
            row.getElement().style.color = "blue";
		}
    },
	rowClick: function(e, row) {
		row.getTable().deselectRow();
		row.select();
	},
	rowSelected:function(row){
    	SORS_Search(row.getData().sales_Output_Order_mOrder_No);
    },
	ajaxResponse:function(url, params, response){
		if(response.length == 0){
			toastr.info("목록이 없습니다.");	
		}
		return response;
    },
	columns: [
		{ title: "지시번호", field: "sales_Output_Order_mOrder_No", headerHozAlign: "center", hozAlign: "right", headerFilter: true, width: 117 },
		{ title: "수주번호", field: "sales_Output_Order_mCus_No", headerHozAlign: "center", hozAlign: "right", headerFilter: true, width: 117 },
		{ title: "코드", field: "sales_Output_Order_mCode", headerHozAlign: "center", headerFilter: true, width: 60 },
		{ title: "거래처명", field: "sales_Output_Order_mName", headerHozAlign: "center", headerFilter: true },
		{ title: "수주일", field: "sales_Output_Order_mDate", headerHozAlign: "center", hozAlign: "right", headerFilter: true, width: 137,
			formatter: "datetime", formatterParams: { outputFormat: "YYYY-MM-DD HH:mm:ss", color: "red" }},
		{ title: "납기일자", field: "sales_Output_Order_mDlvry_Date", headerHozAlign: "center", hozAlign: "right", headerFilter: true, width: 85 },
		{ title: "특이사항", field: "sales_Output_Order_mRemarks", headerHozAlign: "center", headerFilter: true, width: 85 },
		{ title: "합계금액", field: "sales_Output_Order_mTotal", headerHozAlign: "center", hozAlign: "right", headerFilter: true, formatter: "money", formatterParams: { precision: false }, width: 85 },
		{ title: "수정자", field: "sales_Output_Order_mModifier", headerHozAlign: "center", headerFilter: true, width: 72 },
		{ title: "수정일자", field: "sales_Output_Order_mModify_Date", headerHozAlign: "center", headerFilter: true, width: 137,
			formatter: "datetime", formatterParams: { outputFormat: "YYYY-MM-DD HH:mm:ss" }},
		{ title: "목록확인", field: "sales_Output_Order_mCheck", visible: false }
	]
});

// salesMaster 목록 검색
function SOR_Search() {
	datas = {
		startDate: $("#startDate").val(),
		endDate: $("#endDate").val(),
		ClientCode: $("#Sales_InMat_Client_Code").val(),
		condition: $("#saleOrderTypeListSelectBox option:selected").val()
	}
	$.ajax({
		method: "GET",
		dataType: "json",
		async: false,
		url: "salesOutputOrderRest/SOR_Search",
		data: datas,
		success: function(datas) {
			console.log(datas);
			salesOutputOrderReportTable.setData(datas);
			salesOutputOrderReportSubTable.clearData();
			salesOutputOrderReportStockTable.clearData();
		}
	});
}

$("#SOR_SearchBtn").click(function(){
	SOR_Search();
})

// 출하구분 select를 구성하기위한 ajax
var dtl_arr = new Object();

$.ajax({
	method : "GET",
	async: false,
	url : "dtl_tbl_select?NEW_TBL_CODE=19",
	success : function(datas) {
		for(i=0;i<datas.length;i++){
			dtl_arr[datas[i].child_TBL_NO] = datas[i].child_TBL_TYPE;
		}
	}
});

var salesOutputOrderReportSubTable = new Tabulator("#salesOutputOrderReportSubTable", {
	//Sub Total 색상
	rowFormatter: function(row) {
		if (row.getData().sales_Output_Order_lNot_Stocked <= 0) {
			row.getElement().style.backgroundColor = "#1E88E5";
			row.getElement().style.color = "white";
		}
	},
	height: "calc(90% - 175px)",
	tabEndNewRow: true,
	//행을 클릭하면 salesOutputOrderReportStockTable에 리스트가 나타남
	rowClick: function(e, row) {
		row.getTable().deselectRow();
		row.select();
	},
	rowSelected:function(row){
    	SOSS_Search(row.getData().sales_Output_Order_lCode);
    },
	ajaxResponse:function(url, params, response){
		if(response.length == 0){
			toastr.info("목록이 없습니다.");	
		}
		return response;
    },
	columns: [
		{ title: "순번", field: "sales_Output_Order_lNo", headerHozAlign: "center", hozAlign: "center", width: 60 },
		{ title: "지시번호", field: "sales_Output_Order_lOrder_No", visible: false },
		{ title: "코드", field: "sales_Output_Order_lCode", headerHozAlign: "center", width: 60 },
		{ title: "제품명", field: "sales_Output_Order_lName", headerHozAlign: "center", width: 200 },
		{ title: "규격1", field: "sales_Order_STND_1", headerHozAlign: "center", width: 70 },
		{ title: "수량", field: "sales_Output_Order_lQty", headerHozAlign: "center", hozAlign: "right",
			formatter: "money", formatterParams: { precision: false }, width: 60},
		{ title: "단가", field: "sales_Output_Order_lUnit_Price", headerHozAlign: "center", hozAlign: "right",
			topCalc: function() { return "합계금액" }, formatter: "money", formatterParams: { precision: false }, width: 75},
		{ title: "금액", field: "sales_Output_Order_lPrice", headerHozAlign: "center", hozAlign: "right", width: 80, formatter: "money", formatterParams: { precision: false},
			topCalc: function(values, data, calcParams) {
				//values - array of column values
				//data - all table data
				//calcParams - params passed from the column definition object

				var calc = 0;

				values.forEach(function(value) {
					calc += value
				});
				
				return calc;
			}, topCalcFormatter: "money", topCalcFormatterParams: { precision: false }},
		{ title: "비고", field: "sales_Output_Order_lInfo_Remark", headerHozAlign: "center", width: 70 },
		{ title: "구분", field: "sales_Output_Order_Send_Clsfc", headerHozAlign: "center", width: 70,
			formatter: function(cell, formatterParams) {
				var value = cell.getValue();
				if(dtl_arr[value] != null){
						value = dtl_arr[value];	
					}else{
						value = "";
					}
			    return value;
			}},
		{ title: "미입고", field: "sales_Output_Order_lNot_Stocked", visible: false}]
});

//salesOutputOrderReport 목록검색
function SORS_Search(sales_Output_Order_lOrder_No) {
	
	$("#sales_Output_Order_lOrder_No").val(sales_Output_Order_lOrder_No);
	
	datas = {
		SalesOrderNo : sales_Output_Order_lOrder_No
	}
	
	salesOutputOrderReportSubTable.setData("salesOutputOrderRest/SORS_Search", datas)
	
}

var salesOutputOrderReportStockTable = new Tabulator("#salesOutputOrderReportStockTable", {
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

// salesOutputOrderStock 목록검색
function SOSS_Search(sales_Output_Order_lCode) {
	
	datas = {
		ItemCode: sales_Output_Order_lCode
	}
	
	salesOutputOrderReportStockTable.setData("salesStockRest/SOSS_Search" , datas);
	
	console.log(salesOutputOrderReportStockTable);

}