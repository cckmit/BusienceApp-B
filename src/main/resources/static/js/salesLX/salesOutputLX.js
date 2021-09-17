// salesOrderMaster
var salesOutputTable = new Tabulator("#salesOutputTable", {
	//페이징
	pagination:"local",
	paginationSize:20,
    height:"calc(100% - 175px)",
	layoutColumnsOnNewData : true,
	headerFilterPlaceholder: null,
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
	//행클릭 이벤트
	rowClick:function(e, row){
		salesOutputTable.deselectRow();
		row.select();
    },
	rowSelected:function(row){		
    	SOS_Search(row.getData().sales_Order_mCus_No);
    },
	//복사하여 엑셀 붙여넣기 가능
	clipboard: true,	
 	columns:[ 
 		{title:"수주번호", field:"sales_Order_mCus_No", headerHozAlign:"center", hozAlign:"right", headerFilter:true},
 		{title:"코드", field:"sales_Order_mCode", headerHozAlign:"center", headerFilter:true},
		{title:"거래처명", field:"sales_Order_mName", headerHozAlign:"center", headerFilter:true},
 		{title:"수주일", field:"sales_Order_mDate", headerHozAlign:"center", hozAlign:"right", headerFilter:true},
 		{title:"납기일자", field:"sales_Order_mDlvry_Date", headerHozAlign:"center", hozAlign:"right", headerFilter:true},
 		{title:"특이사항", field:"sales_Order_mRemarks", headerHozAlign:"center", headerFilter:true},
 		{title:"합계금액", field:"sales_Order_mTotal", headerHozAlign:"center", hozAlign:"right", headerFilter:true,
			formatter: "money", formatterParams: { precision: false }},
 		{title:"수정자", field:"sales_Order_mModifier", headerHozAlign:"center", headerFilter:true},
 		{title:"수정일자", field:"sales_Order_mModify_Date", headerHozAlign:"center",  headerFilter:true},
 		{title:"목록확인", field:"sales_Order_mCheck", visible:false}
 	],
});

//orderMaster 목록검색 matOrder와 동일하지만 테이브이름이 다르고, 미입고 컬럼이 추가됨
function SO_Search(){
	data = {
		startDate : $("#startDate").val(),
		endDate : $("#endDate").val(),
		sales_Order_mCode : $("#Sales_InMat_Client_Code").val(),
		sales_Order_mCus_No : $("#sales_Order_mCus_No").val()
	}
	$.ajax({
		method : "GET",
		dataType : "json",
		async: false,
		url : "salesOutputLXRest/SO_Search?data="+ encodeURI(JSON.stringify(data)),
		success : function(data) {
			console.log(data);
			salesOutputTable.setData(data);
			salesOutputSubTable.clearData();
			salesOutMatTable.clearData();
			
			ResetBtn();
		}
	});
}

$("#SO_SearchBtn").click(function(){
	SO_Search();
})

// 판매구분 select를 구성하기위한 ajax
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

// salesOrderList
var salesOutputSubTable = new Tabulator("#salesOutputSubTable", {
	height:"calc(50% - 124.1px)",
	layoutColumnsOnNewData : true,
	//복사하여 엑셀 붙여넣기 가능
	clipboard: true,
	rowFormatter:function(row){
		//재고가 없다면 파란색
		if(row.getData().sales_SM_Last_Qty == 0){
            row.getElement().style.color = "blue";
        }
		//미출고가 0이면 글자색을 빨간색으로 바꿔준다
        if(row.getData().sales_Order_lNot_Stocked == 0){
            row.getElement().style.color = "red";
        }		
    },
	//행클릭 이벤트
	rowClick:function(e, row){
		//미출고가 없다면
		if(row.getData().sales_Order_lNot_Stocked == 0){
			return false;
		}
		row.toggleSelect();
    },
	rowSelected:function(row){
		//재고가 없다면
		if(row.getData().sales_SM_Last_Qty == 0){
			return false;
		}
		var SOS_Qty = row.getData().sales_Order_lQty
		if(SOS_Qty > row.getData().sales_SM_Last_Qty){
			SOS_Qty = row.getData().sales_SM_Last_Qty
		}
    	//salesOutMatTable 반영
		salesOutMatTable.addRow({
				"Number" : row.getData().sales_Order_lNo,
				"sales_OutMat_Code" : row.getData().sales_Order_lCode,
				"sales_OutMat_Name" : row.getData().sales_Order_lName,
				"sales_OutMat_Qty" : SOS_Qty,
				"sales_OutMat_Unit_Price" : row.getData().sales_Order_lUnit_Price,
				"sales_OutMat_Price" : SOS_Qty * row.getData().sales_Order_lUnit_Price,
				"sales_OutMat_Send_Clsfc" : row.getData().sales_Order_Send_Clsfc,
				"sales_OutMat_Date" : moment(new Date()).format('YYYY-MM-DD HH:mm:ss')});
		
		UseBtn();
    },
	rowDeselected:function(row){
		//클릭한 행과 같은 순번을 찾아서 삭제해줌
		for(i=0;i<salesOutMatTable.getDataCount();i++){
			if(salesOutMatTable.getData()[i].Number == row.getData().sales_Order_lNo){
				salesOutMatTable.getRows()[i].delete()
				break;
			}
		}
    },
 	columns:[
	{title:"순번", field:"sales_Order_lNo", headerHozAlign:"center", hozAlign:"center"},
	{title:"수주번호", field:"sales_Order_lCus_No", visible:false},
 	{title:"코드", field:"sales_Order_lCode", headerHozAlign:"center"},
 	{title:"품목명", field:"sales_Order_lName", headerHozAlign:"center"},
 	{title:"수량", field:"sales_Order_lQty", headerHozAlign:"center", hozAlign:"right"},
	{title:"출고수량", field:"sales_Order_lSum", headerHozAlign:"center", hozAlign:"right"},
 	{title:"단가", field:"sales_Order_lUnit_Price", headerHozAlign:"center", hozAlign:"right",
		formatter: "money", formatterParams: { precision: false }, topCalc:function(){return "합계금액"}},
 	{title:"금액", field:"sales_Order_lPrice", headerHozAlign:"center", hozAlign:"right", width: 75,
		formatter: "money", formatterParams: { precision: false },
		//맨윗줄 합계금액 나타내기
		topCalc:function(values, data, calcParams){
		    //values - array of column values
		    //data - all table data
		    //calcParams - params passed from the column definition object
		
		    var calc = 0;

		    values.forEach(function(value){
			calc += value
		 });
    	return calc;
	}, topCalcFormatter: "money", topCalcFormatterParams: { precision: false }},
	{title:"미출고", field:"sales_Order_lNot_Stocked", headerHozAlign:"center", hozAlign:"right"},
 	{title:"재고", field:"sales_SM_Last_Qty", headerHozAlign:"center", hozAlign:"right"},
	{title:"구분", field:"sales_Order_Send_Clsfc", headerHozAlign:"center",
		formatter: function(cell, formatterParams) {
				var value = cell.getValue();
				if(dtl_arr[value] != null){
						value = dtl_arr[value];	
					}else{
						value = "";
					}
			    return value;
			}, width:65}]
});

//salesOutputSub 목록검색
function SOS_Search(sales_Order_lCus_No){
	//발주넘버
	$.ajax({
		method : "GET",
		url : "salesOutputLXRest/SOS_Search?Sales_Order_lCus_No="+ sales_Order_lCus_No,
		success : function(datas) {
			console.log(datas);
			salesOutputSubTable.setData(datas);
			salesOutMatTable.clearData();
		}
	});
}

var salesOutMatTable = new Tabulator("#salesOutMatTable", {
	height: "calc(50% - 124.1px)",
	layoutColumnsOnNewData : true,
	//복사하여 엑셀 붙여넣기 가능
	clipboard: true,
	selectable: true,
	//커스텀 키 설정
	rowAdded:function(row){
		row.getTable().deselectRow();
		row.select();
	},
 	columns:[
	{title:"순번", field:"Number", headerHozAlign:"center", hozAlign:"center", formatter:"rownum"},
 	{title:"코드", field:"sales_OutMat_Code", headerHozAlign:"center"},
 	{title:"품목명", field:"sales_OutMat_Name", headerHozAlign:"center", width: 140},
 	{title:"수량", field:"sales_OutMat_Qty", headerHozAlign:"center", hozAlign:"right", 
		formatter:"money", formatterParams: {precision: false}},
	{title:"단가", field:"sales_OutMat_Unit_Price", headerHozAlign:"center", hozAlign:"right",
		formatter:"money", formatterParams: {precision: false}},
	{title:"금액", field:"sales_OutMat_Price", headerHozAlign:"center", hozAlign:"right",
		formatter:"money", formatterParams: {precision: false}},
	{title:"구분", field:"sales_OutMat_Send_Clsfc", headerHozAlign:"center",editor: "select", width: 65,
			formatter: function(cell, formatterParams) {
				var value = cell.getValue();
				if(dtl_arr[value] != null){
						value = dtl_arr[value];	
					}else{
						value = "";
					}
			    return value;
			}
	},
	{title:"출고일자", field:"sales_OutMat_Date", headerHozAlign:"center", hozAlign:"right", width: 124}
 	]
});

//Sales_OutMat 목록검색
function SOM_Search(CusNo,Code){
	
	data = {
		sales_OutMat_Order_No : CusNo,
		sales_OutMat_Code : Code
	}
	$.ajax({
		method : "GET",
		url : "salesOutputLXRest/SOM_Search?data="+ encodeURI(JSON.stringify(data)),
		success : function(SOM_datas) {
			console.log(SOM_datas);
			salesOutMatTable.setData(SOM_datas);
		}
	});
}

//SOM_Save
function SOM_Save() {
	if(salesOutMatTable.getData().length == 0){
		alert("저장할 데이터가 없습니다.")
		return;
	}
	selectedRow = salesOutputTable.getData("selected")[0];
	
	//OrderSub 저장부분
	$.ajax({
		method: "post",
		async: false,
		url: "salesOutputLXRest/SOM_Save?masterData=" + encodeURI(JSON.stringify(selectedRow))
										+"&listData=" + encodeURI(JSON.stringify(salesOutMatTable.getData())),
		success: function(result) {
			if (result == "error") {
				alert("오류")
			} else {
				alert("저장되었습니다.");
				SO_Search()
				Order_mCode_select(selectedRow.sales_Order_mCus_No);
			}
		}
	});
}

//SOM_SaveBtn
$('#SOM_SaveBtn').click(function(){
	SOM_Save();
})

//품목코드로 salesOutputTable 선택하는 코드
function Order_mCode_select(value){
	rowCount = salesOutputTable.getDataCount();
	//컬럼값을 검색해서 입력값을 포함하는 값이 있으면 선택한다. 
	for(i=0;i<rowCount;i++){
		Output_Order_mCus_No = salesOutputTable.getData()[i].sales_Order_mCus_No;
		//수주번호가 입력내용을 포함하면 코드 실행
		if(Output_Order_mCus_No == value){
			//발주번호가 같은 행 선택
			salesOutputTable.deselectRow();
			salesOutputTable.getRows()[i].select();
			break;
		}					
	}
}