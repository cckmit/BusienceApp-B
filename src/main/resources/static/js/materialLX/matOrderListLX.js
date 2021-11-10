//셀위치저장
var cellPos = null;

var matOrderListTable = new Tabulator("#matOrderListTable", {
	//페이징
	pagination: "local",
	paginationSize: 20,
	paginationAddRow: "table",
	layoutColumnsOnNewData : true,
	headerFilterPlaceholder: null,
	//입고 된 데이터 색상
	rowFormatter: function(row) {
		// 현재 날짜
		var today = new Date();
		// 발주일
		var orderDate = new Date(row.getData().order_mDate);
		
		var orderYear = orderDate.getFullYear();
		var orderMonth = orderDate.getMonth() + 1;
		var orderDay = orderDate.getDate();
		
		var toOrder = orderYear +  ', ' + orderMonth +  ', ' + orderDay;
		
		// 일 계산
		var cDay = 24 * 60 * 60 * 1000;  
		
		var dd = today.getDate();
		var mm = today.getMonth() + 1; //January is 0!
		var yyyy = today.getFullYear();
		today = yyyy + ', ' + mm + ', ' + dd;
		
		var fromDay = new Date(today);
		var toDay = new Date(toOrder);

		var elapsed = fromDay.getTime() - toDay.getTime();
		var elapsedDay = elapsed / cDay;

		var year;
		var month;
		var day;
		
		if(elapsedDay < 30) {
			day = elapsedDay;
		
		} else if (elapsedDay < 365) {
			month = Math.floor(elapsedDay/30);
			day = elapsedDay - (month * 30);

		} else {
			year = Math.floor(elapsedDay/365);
			month = Math.floor((elapsedDay-(year*365))/30);
			day = elapsedDay- (year*365) - (month*30);

		}

		var monthValue = month;
		var yearValue = year;
		// 윤년 평년 계산
	/*	if (yyyy % 4 == 0 && yyyy % 100 != 0 || yyyy % 400 == 0) {
			console.log("윤년");
		} else {
			console.log("평년");
		}
*/

		if (row.getData().order_mCheck == "Y") {
			row.getElement().style.color = "blue";
		}
		else if (row.getData().order_mCheck == "N" && (month >= 3)) {
			row.getElement().style.color = "orange";
		}
		else if (row.getData().order_mCheck == "N" && (month >= 6)) {
			row.getElement().style.color = "green";
		}
		else if (row.getData().order_mCheck == "N" && (year >= 1)) {
			row.getElement().style.color = "red";
		} 
	},
	height: "calc(100% - 175px)",
		//Order_lNo를 인덱스로 설정
		index: "id",
		//행클릭 이벤트
		rowClick: function(e, row) {
			matOrderListTable.deselectRow();
			row.select();
			MOLS_Search(row.getData().order_mCus_No);
		},
	 	columns:[ 
	 	{formatter:"rowSelection", titleFormatter:"rowSelection", headerHozAlign:"center", hozAlign:"center", headerSort:false},
 		{title:"발주번호", field:"order_mCus_No", headerHozAlign:"center", hozAlign:"right", headerFilter:true},
 		{title:"코드", field:"order_mCode", headerHozAlign:"center", headerFilter:true},
		{title:"거래처명", field:"order_mName", headerHozAlign:"center", headerFilter:true},
 		{title:"발주일", field:"order_mDate", headerHozAlign:"center", hozAlign:"right", headerFilter:true},
 		{title:"납기일자", field:"order_mDlvry_Date", headerHozAlign:"center", hozAlign:"right", headerFilter:true},
 		{title:"특이사항", field:"order_mRemarks", headerHozAlign:"center", headerFilter:true},
 		{title:"합계금액", field:"order_mTotal", headerHozAlign:"center", hozAlign:"right", headerFilter:true, 
			formatter:"money", formatterParams: {precision: false }},
 		{title:"수정자", field:"order_mModifier", headerHozAlign:"center", headerFilter:true},
 		{title:"수정일자", field:"order_mModify_Date", headerHozAlign:"center",  headerFilter:true},
		{title:"id", field:"id", visible:false}
 	],
});

//orderMaster 목록검색
function MOL_Search() {
	data = {
		startDate: $("#startDate").val(),
		endDate: $("#endDate").val(),
		order_mCode: $("#InMat_Client_Code").val()
	}
	$.ajax({
		method: "GET",
		dataType: "json",
		url: "matOrderLXReportRest/MOL_Search?data=" + encodeURI(JSON.stringify(data)),
		success: function(datas) {
			TableSetData(matOrderListTable,datas);
			matOrderListSubTable.clearData();
			matOrderListStockTable.clearData();
		}
	});
}

$('#MOL_SearchBtn').click(function(){
	MOL_Search();
})

var matOrderListSubTable = new Tabulator("#matOrderListSubTable", {
	//페이징
	pagination: "local",
	paginationSize: 20,
	paginationAddRow: "table",
	layoutColumnsOnNewData : true,
	//Sub Total 색상
	rowFormatter: function(row) {
		if (row.getData().order_lNot_Stocked <= 0) {
			row.getElement().style.backgroundColor = "#1E88E5";
			row.getElement().style.color = "white";
		}
	},
	height: "calc(90% - 175px)",
	selectable: 1,
	//복사하여 엑셀 붙여넣기 가능
	clipboard: true,
	//행을 클릭하면 matOrderStockTable에 리스트가 나타남
	rowClick: function(e, row) {
		console.log("행클릭")
		MOLSS_Search(row.getData().order_lCode);
	},
	//Order_lNo를 인덱스로 설정
	index: "order_lNo",
	cellEditing: function(cell) {
		//셀위치 저장하여 포커싱부여
		cellPos = cell;
	},
	columns: [
		{ title: "No", field: "order_lNo", headerHozAlign: "left", hozAlign: "center"},
		{ title: "발주No", field: "order_lCus_No", visible: false },
		{ title: "코드", field: "order_lCode", headerHozAlign: "center"},
		{ title: "제품명", field: "order_lName", headerHozAlign: "center"},
		{ title: "규격1", field: "order_lSTND_1", headerHozAlign: "center"},
		{ title: "수량", field: "order_lQty", headerHozAlign: "center", hozAlign: "right"},
		{ title: "미입고", field: "order_lNot_Stocked", headerHozAlign: "center", hozAlign: "right"},
		{ title: "단가", field: "order_lUnit_Price", headerHozAlign: "center", formatter: "money",formatterParams:{ precision:false}, hozAlign: "right",
			topCalc: function() { return "합계금액" }, width: 75},
		
		{title:"금액", field:"order_lPrice", headerHozAlign:"center", hozAlign:"right", formatter: "money", width: 90, formatterParams:{ precision:false},
			topCalc :function(values, data, calcParams){
			    var calc = 0;
	
			    values.forEach(function(value){
				calc += value
			 });
    		return calc;
		},topCalcFormatter : "money", topCalcFormatterParams: {precision: false}},
		{ title: "비고", field: "order_lInfo_Remark", headerHozAlign: "center"},
	]
});

//팝업창으로부터 특정 파라미터 값으로 데이터를 받는다 
function product_test(PCode, PName, PSTND_1, PPrice) {
	cellPos.getRow().update({
		"order_lCode": PCode,
		"order_lName": PName,
		"order_lSTND_1": PSTND_1,
		"order_lUnit_Price": PPrice
	});
	//선택 후 포커스 이동
	cellPos.getElement().focus();
}

//orderList 목록검색
function MOLS_Search(order_lCus_No) {
	$("#Order_lCus_No").val(order_lCus_No);
	//발주넘버
	$.ajax({
		method: "GET",
		url: "matOrderLXReportRest/MOLS_Search?order_lCus_No=" + order_lCus_No,
		success: function(datas) {
			console.log(datas);
			TableSetData(matOrderListSubTable,datas);
		}
	});
}


var matOrderListStockTable = new Tabulator("#matOrderListStockTable", {
	selectable: 1,
	height: "10%",
	layoutColumnsOnNewData : true,
	//복사하여 엑셀 붙여넣기 가능
	clipboard: true,
	columns: [
		{ title: "제품코드", field: "sm_Code", headerHozAlign: "center" },
		{ title: "제품명", field: "sm_Name", headerHozAlign: "center"},
		{ title: "규격1", field: "sm_STND_1", headerHozAlign: "center"},
		{ title: "수량", field: "sm_Qty", headerHozAlign: "center", hozAlign: "right" }
	]
});

//orderStock 목록검색
function MOLSS_Search(order_lCode) {
	//발주넘버
	$.ajax({
		method: "GET",
		url: "matOrderLXReportRest/MOLSS_Search?order_lCode=" + order_lCode,
		success: function(datas) {
			TableSetData(matOrderListStockTable,datas);
		}
	});
}

$('#MOL_PrintBtn').click(function(){
	print_fun();
});
	
function print_fun()
{
		selectedData = matOrderListTable.getData("selected");
		
		if(selectedData.length == 0)
		{
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
        
        while ( form.hasChildNodes() ) 
        { 
        	form.removeChild( form.firstChild ); 
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
}
