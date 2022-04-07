var matOrderListTable = new Tabulator("#matOrderListTable", {
	//페이징
	pagination: "local",
	paginationSize: 20,
	paginationAddRow: "table",
	layoutColumnsOnNewData: true,
	headerFilterPlaceholder: null,
	//입고 된 데이터 색상
	rowFormatter: function(row) {
		
		var gapTime = today.getTime() - new Date(row.getData().order_mDate).getTime();
		
		const gapMonth = Math.abs(gapTime / (1000 * 3600 * 24 * 30));
		
		if (row.getData().order_mCheck == "Y") {
			row.getElement().style.color = "red";
		}else if (row.getData().order_mCheck == "I") {
			row.getElement().style.color = "blue";
		}else if(gapMonth >= 3){
			row.getElement().style.color = "green";
		}else if(gapMonth >= 6){			
			row.getElement().style.color = "orange";
		}
	},
	height: "calc(100% - 175px)",
	//행클릭 이벤트
	rowClick: function(e, row) {
		matOrderListTable.deselectRow();
		row.select();
		MOLS_Search(row.getData().order_mCus_No);
	},
	columns: [
		{ formatter: "rowSelection", titleFormatter: "rowSelection", headerHozAlign: "center", hozAlign: "center", headerSort: false },
		{ title: "발주번호", field: "order_mCus_No", headerHozAlign: "center", hozAlign: "right", headerFilter: true },
		{ title: "코드", field: "order_mCode", headerHozAlign: "center", headerFilter: true },
		{ title: "거래처명", field: "order_mName", headerHozAlign: "center", headerFilter: true },
		{ title: "발주일", field: "order_mDate", headerHozAlign: "center", hozAlign: "right", headerFilter: true },
		{ title: "납기일자", field: "order_mDlvry_Date", headerHozAlign: "center", hozAlign: "right", headerFilter: true },
		{ title: "특이사항", field: "order_mRemarks", headerHozAlign: "center", headerFilter: true },
		{ title: "합계금액", field: "order_mTotal", headerHozAlign: "center", hozAlign: "right", headerFilter: true,
			formatter: "money", formatterParams: { precision: false }}
	]
});

//orderMaster 목록검색
function MOL_Search() {
	var datas = {
		startDate : $("#startDate").val(),
		endDate : $("#endDate").val(),
		ClientCode : $("#InMat_Client_Code").val()
	}
	
	matOrderListTable.setData("matOrderLXRest/MOM_Search",datas)
	.then(function(){		
		//list와 stock의 데이터를 없에준다
		matOrderListSubTable.clearData();
		matOrderListStockTable.clearData();
	})
}

$('#MOL_SearchBtn').click(function() {
	MOL_Search();
});

var matOrderListSubTable = new Tabulator("#matOrderListSubTable", {
	layoutColumnsOnNewData: true,
	height: "calc(90% - 175px)",
	selectable: 1,
	//행을 클릭하면 matOrderStockTable에 리스트가 나타남
	rowClick: function(e, row) {
		MOLSS_Search(row.getData().order_lCode);
	},
	rowFormatter:function(row){
		//order_lNot_Stocked가 0이면 빨간색으로, 입고수량보다 작으면 파란색으로 나타냄
        if(row.getData().order_lNot_Stocked == 0){
            row.getElement().style.color = "red";
        }else if(row.getData().order_lNot_Stocked < row.getData().order_lQty){
			row.getElement().style.color = "blue";
		}
    },
	columns: [
		{ title: "순번", field: "order_lNo", headerHozAlign: "left", hozAlign: "center" },
		{ title: "발주No", field: "order_lCus_No", visible: false },
		{ title: "코드", field: "order_lCode", headerHozAlign: "center" },
		{ title: "제품명", field: "order_lName", headerHozAlign: "center" },
		{ title: "규격1", field: "order_STND_1", headerHozAlign: "center" },
		{ title: "수량", field: "order_lQty", headerHozAlign: "center", hozAlign: "right", formatter: "money", formatterParams: { precision: false }},
		{ title: "미입고", field: "order_lNot_Stocked", headerHozAlign: "center", hozAlign: "right" },
		{ title: "단가", field: "order_lUnit_Price", headerHozAlign: "center", formatter: "money", formatterParams: { precision: false }, hozAlign: "right",
			topCalc: function() { return "합계금액" }, width: 75},
		{ title: "금액", field: "order_lPrice", headerHozAlign: "center", hozAlign: "right", formatter: "money", width: 90, formatterParams: { precision: false },
			topCalc: function(values, data, calcParams) {
				var calc = 0;

				values.forEach(function(value) {
					calc += value
				});
				return calc;
			}, topCalcFormatter: "money", topCalcFormatterParams: { precision: false }},
		{ title: "비고", field: "order_lInfo_Remark", headerHozAlign: "center" },
	]
});

//orderList 목록검색
function MOLS_Search(order_lCus_No) {
	$("#order_lCus_No").val(order_lCus_No)
	
	var datas = {
		OrderNo : order_lCus_No
	}
	
	matOrderListSubTable.setData("matOrderLXRest/MOL_Search",datas)
	.then(function(){
		
	})
}


var matOrderListStockTable = new Tabulator("#matOrderListStockTable", {
	selectable: 1,
	height: "10%",
	layoutColumnsOnNewData: true,
	columns: [
		{ title: "제품코드", field: "sm_Code", headerHozAlign: "center" },
		{ title: "제품명", field: "sm_Name", headerHozAlign: "center" },
		{ title: "규격1", field: "sm_STND_1", headerHozAlign: "center" },
		{ title: "수량", field: "sm_Qty", headerHozAlign: "center", hozAlign: "right" }
	]
});

//orderStock 목록검색
function MOLSS_Search(itemCode) {
	matOrderListStockTable.setData("matOrderLXRest/MOS_Search", {ItemCode : itemCode});
}
/*
$('#MOL_PrintBtn').click(function() {
	print_fun();
});

function print_fun() {
	selectedData = matOrderListTable.getData("selected");

	if (selectedData.length == 0) {
		alert("행을 선택해주세요. 선택한 행의 데이터를 인쇄 할 수 있습니다.");
		return;
	}

	//창의 주소
	var url = "";
	//창의 이름
	var name = "orderprint";
	//창의 css
	var option = "width = 800, height = 800, top = 50, left = 300, location = no";

	openWin = window.open(url, name, option);

	var form = document.getElementById("nform");

	while (form.hasChildNodes()) {
		moveChild(form.frstChild);
	}
	var dataList = {
		"data": []
	}

	dataList.data = selectedData;

	console.log(JSON.stringify(dataList));

	var hiddenField = document.createElement("input");
	hiddenField.setAttribute("type", "hidden");
	hiddenField.setAttribute("name", "dataList");
	hiddenField.setAttribute("value", JSON.stringify(dataList));
	form.appendChild(hiddenField);

	form.submit();
}*/
