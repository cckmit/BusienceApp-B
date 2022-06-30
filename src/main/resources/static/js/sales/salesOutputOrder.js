// salesOrderMaster
var salesOrderTable = new Tabulator("#salesOrderTable", {
	headerFilterPlaceholder: null,
    height: "calc(50% - 125px)",
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
		//클릭했을때 salesOutputOrderTable에 기존에 작업중인 데이터는 없엔다
		for(i=0;i<salesOutputOrderTable.getDataCount();i++){
			if(salesOutputOrderTable.getData()[i].sales_Output_Order_mOrder_No == null){
				salesOutputOrderTable.getRows()[i].delete()
			}
		}
		//salesOutputOrderTable에 행추가
		salesOutputOrderTable.addRow({
									sales_Output_Order_mCus_No : row.getData().sales_Order_mCus_No,
									sales_Output_Order_mCode : row.getData().sales_Order_mCode,
									sales_Output_Order_mName : row.getData().sales_Order_mName,
									sales_Output_Order_mDate : moment(new Date()).format('YYYY-MM-DD HH:mm:ss'),
									sales_Output_Order_mDlvry_Date : row.getData().sales_Order_mDlvry_Date,
									sales_Output_Order_mRemarks : row.getData().sales_Order_mRemarks,
									sales_Output_Order_mTotal : row.getData().sales_Order_mTotal,
									sales_Output_Order_mTotal : row.getData().sales_Order_mTotal});
									
		salesOrderTable.deselectRow();
		row.select();

    },
	rowSelected:function(row){
    	//salesOrderSubTable에 목록 출력
		SOS_Search(row.getData().sales_Order_mCus_No);
		SOSM_Search(row.getData().sales_Order_mCus_No);
		salesOrderSubTable.selectRow();
		$("#sales_Order_lCus_No").val(row.getData().sales_Order_mCus_No);
		
		//저장버튼 사용가능
		UseBtn()
    },
	//복사하여 엑셀 붙여넣기 가능
	clipboard: true,
 	columns:[
 		{title:"수주번호", field:"sales_Order_mCus_No", headerHozAlign:"center", hozAlign:"right", headerFilter:true, width : 117},
 		{title:"코드", field:"sales_Order_mCode", headerHozAlign:"center", headerFilter:true, width : 60},
		{title:"거래처명", field:"sales_Order_mName", headerHozAlign:"center", headerFilter:true},
 		{title:"수주일", field:"sales_Order_mDate", headerHozAlign:"center", hozAlign:"right", headerFilter:true, width : 130,
			formatter: "datetime", formatterParams : {outputFormat : "YYYY-MM-DD HH:mm:ss"}},
 		{title:"납기일자", field:"sales_Order_mDlvry_Date", headerHozAlign:"center", hozAlign:"right", headerFilter:true, width : 85},
 		{title:"특이사항", field:"sales_Order_mRemarks", headerHozAlign:"center", headerFilter:true, width : 85},
 		{title:"합계금액", field:"sales_Order_mTotal", headerHozAlign:"center", hozAlign:"right", headerFilter:true, formatter: "money", formatterParams: { precision: false }, width : 85},
 		{title:"목록확인", field:"sales_Order_mCheck", visible:false}]
});

//orderMaster 목록검색 matOrder와 동일하지만 테이브이름이 다르고, 미입고 컬럼이 추가됨
function SO_Search(){
	datas = {
		startDate : $("#startDate").val(),
		endDate : $("#endDate").val(),
		ClientCode : $("#Sales_InMat_Client_Code").val(),
		condition: "Y",
		SalesCusNo: $("#Sales_Order_mCus_No").val()
	}
	$.ajax({
		method : "GET",
		dataType : "json",
		async: false,
		url : "salesOrderRest/SO_Search",
		data : datas,
		success : function(data) {
			salesOrderTable.setData(data);
			salesOrderSubTable.clearData();
			salesOutputOrderSubTable.clearData();
			salesOutputStockTable.clearData();
			ResetBtn()
		}
	});
}

$('#SO_SearchBtn').click(function(){
	SO_Search();
	salesOutputOrderTable.replaceData();
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
var salesOrderSubTable = new Tabulator("#salesOrderSubTable", {
	height: "calc(50% - 125px)",
	selectable: true,
	//복사하여 엑셀 붙여넣기 가능
	clipboard: true,
	rowFormatter:function(row){
		//미출고가 0이면 글자색을 빨간색으로 바꿔준다
        if(row.getData().sales_Order_lNot_Stocked == 0){
            row.getElement().style.color = "red";
        }
    },
	rowSelectionChanged:function(data, rows){
		if(salesOutputOrderSubTable != null){
			
			replaceData = []
			for(j=0;j<data.length;j++){
				replaceData[j] = {sales_Output_Order_lNo : j+1,
							sales_Output_Order_lOrder_No : data[j].sales_Order_lOrder_No,
							sales_Output_Order_lCode : data[j].sales_Order_lCode,
							sales_Output_Order_lName : data[j].sales_Order_lName,
							sales_Order_STND_1 : data[j].sales_Order_STND_1,
							sales_Order_CLSFC_1 : data[j].sales_Order_CLSFC_1,
							sales_Output_Order_lQty : data[j].sales_Order_lNot_Stocked,
							sales_Output_Order_lUnit_Price : data[j].sales_Order_lUnit_Price,
							sales_Output_Order_lPrice : data[j].sales_Order_lPrice,
							sales_Output_Order_lInfo_Remark : data[j].sales_Order_lInfo_Remark,
							sales_Output_Order_Send_Clsfc : data[j].sales_Order_Send_Clsfc}
			}
			console.log(replaceData)
			salesOutputOrderSubTable.setData(replaceData);
		}
		
    },
	//order_lNo를 인덱스로 설정
	index:"sales_Order_lNo",
 	columns:[
	{formatter:"rowSelection", titleFormatter:"rowSelection", headerHozAlign:"center", hozAlign:"center", headerSort:false, width:40},
	{title:"순번", field:"sales_Order_lNo", headerHozAlign:"center", hozAlign:"center", width:60},
	{title:"수주번호", field:"sales_Order_lCus_No", visible:false},
 	{title:"코드", field:"sales_Order_lCode", headerHozAlign:"center", width:60},
 	{title:"품목명", field:"sales_Order_lName", headerHozAlign:"center"},
 	{title:"규격1", field:"sales_Order_STND_1", headerHozAlign:"center", hozAlign:"right", width:85},
	{title:"분류1", field:"sales_Order_CLSFC_1", headerHozAlign:"center", hozAlign:"right", width:85},
 	{title:"수량", field:"sales_Order_lQty", headerHozAlign:"center", hozAlign:"right", width:60},
	{title:"지시수량", field:"sales_Order_lSum", headerHozAlign:"center", hozAlign:"right", width:85},
 	{title:"단가", field:"sales_Order_lUnit_Price", headerHozAlign:"center", hozAlign:"right", formatter: "money", formatterParams: { precision: false }, topCalc:function(){return "합계금액"}, width:80},
 	{title:"금액", field:"sales_Order_lPrice", headerHozAlign:"center", hozAlign:"right", formatter: "money", formatterParams: { precision: false },  width:80,
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
	{title: "비고", field: "sales_Order_lInfo_Remark", headerHozAlign: "center", width: 70 },
	{title: "구분", field: "sales_Order_Send_Clsfc", headerHozAlign: "center", width: 70,
		formatter: function(cell, formatterParams) {
			var value = cell.getValue();
			if(dtl_arr[value] != null){
					value = dtl_arr[value];	
				}else{
					value = "";
				}
		    return value;
		},
		editorParams:{values:dtl_arr}
	}]
});

//salesOrderSub 목록검색
function SOS_Search(sales_Order_lCus_No){
	
	datas = {
		SalesOrderNo : sales_Order_lCus_No
	}
	//발주넘버
	$.ajax({
		method : "GET",
		url : "salesOrderRest/SOL_Search",
		data : datas,
		success : function(result) {
			console.log(result);
			salesOrderSubTable.setData(result);
			salesOrderSubTable.selectRow();
		}
	});
}

var salesOutputOrderTable = new Tabulator("#salesOutputOrderTable", {
	ajaxConfig : "get",
	ajaxContentType:"json",
	ajaxURL : "salesOutputOrderRest/SOO_Search",
	height:"calc(47% - 129px)",
	headerFilterPlaceholder: null,
	//행클릭 이벤트
	rowClick:function(e, row){
		 
		for(i=0;i<salesOutputOrderTable.getDataCount();i++){
			//salesOutputOrderTable에 지시번호가 null이 있는 행이 있다면 선택안되게함ㄴ
			if(salesOutputOrderTable.getData()[i].sales_Output_Order_mOrder_No == null){
				return alert("작성중인 행이 있습니다.")
			}
		}
		row.getTable().deselectRow();
		row.select();

    },
	rowAdded:function(row){
		row.getTable().deselectRow();
    	row.select();
    },
	rowSelected:function(row){
		if(row.getData().sales_Output_Order_mOrder_No != null){
			//salesOrderSubTable에 목록 출력
			SOOS_Search(row.getData().sales_Output_Order_mOrder_No);	
		}
    },
	//복사하여 엑셀 붙여넣기 가능
	clipboard: true,
	columns:[
		{title:"지시번호", field:"sales_Output_Order_mOrder_No", headerHozAlign:"center", hozAlign:"right", headerFilter:true, width : 117},
		{title:"수주번호", field:"sales_Output_Order_mCus_No", headerHozAlign:"center", hozAlign:"right", headerFilter:true, width : 117},
		{title:"코드", field:"sales_Output_Order_mCode", headerHozAlign:"center", headerFilter:true, width : 60},
		{title:"거래처명", field:"sales_Output_Order_mName", headerHozAlign:"center", headerFilter:true},
		{title:"지시일", field:"sales_Output_Order_mDate", headerHozAlign:"center", hozAlign:"right", headerFilter:true, width : 130,
			formatter: "datetime", formatterParams : {outputFormat : "YYYY-MM-DD HH:mm:ss"}},
		{title:"납기일자", field:"sales_Output_Order_mDlvry_Date", headerHozAlign:"center", hozAlign:"right", headerFilter:true, width : 85},
		{title:"특이사항", field:"sales_Output_Order_mRemarks", headerHozAlign:"center", headerFilter:true, editor:"input", width : 85},
		{title:"합계금액", field:"sales_Output_Order_mTotal", headerHozAlign:"center", hozAlign:"right", headerFilter:true, formatter: "money", formatterParams: { precision: false }, width : 85},
		{title:"목록확인", field:"sales_Output_Order_mCheck", visible:false}
	]
});

//커스텀 기능설정
var SOOS_InputEditor = function(cell, onRendered, success, cancel, editorParams){
    //cell - 편집 가능한 셀의 셀 구성 요소
    //onRendered - 에디터가 렌더링 되었을 때 호출 할 함수
    //success - 성공적을 업데이트 된 값을 tabulator에 전달하기 위해 호출되는 함수
    //cancel - 편집을 중단하고 일반 셀로 돌아 가기 위해 호출하는 함수
    //editorParams - editorParams 열 정의 속성에 전달 된 params 객체

    //create 및 style editor
    var input = document.createElement("input");

    input.setAttribute("type", "text");

    //입력 생성 및 스타일 지정
    input.style.padding = "3px";
    input.style.width = "100%";
    input.style.boxSizing = "border-box";

    //편집기의 값을 셀의 현재 값으로 설정
	if(cell.getValue() == undefined){
		input.value = "";
	}else{
		input.value = cell.getValue();
	}

    //에디터가 선택되면 선택 상자에 포커스 설정 (타임 아웃은 편집기를 DOM에 추가 할 수 있음)
    onRendered(function(){
        input.focus();
		input.select();
        input.style.css = "100%";
    });

    //값이 설정되면 업데이트 할 셀 트리거
    function onChange(){
		success(input.value);   
    }
    
	//바꼈을때 블러됫을때 함수 발동
    input.addEventListener("change", onChange);
	input.addEventListener("blur", onChange);

    //키버튼 이벤트
    input.addEventListener("keydown", function (e) {
		//엔터쳤을떄
		if(e.keyCode == 13){
			if (cell.getField() == "sales_Output_Order_lQty") {
				if(input.value >= cell.getInitialValue ()){
					input.value = cell.getInitialValue ();
				}
			}
			//다음셀로
			cell.nav().next();
		}
    });
    //반환
    return input;
};

var salesOutputOrderSubTable = new Tabulator("#salesOutputOrderSubTable", {
	height:"calc(50% - 154px)",
	//복사하여 엑셀 붙여넣기 가능
	clipboard: true,
 	columns:[
	{title:"순번", field:"sales_Output_Order_lNo", headerHozAlign:"center", hozAlign:"center", width:60},
	{title:"지시번호", field:"sales_Output_Order_lOrder_No", visible:false},
 	{title:"코드", field:"sales_Output_Order_lCode", headerHozAlign:"center", width:60},
 	{title:"품목명", field:"sales_Output_Order_lName", headerHozAlign:"center"},
 	{title:"규격1", field:"sales_Order_STND_1", headerHozAlign:"center", hozAlign:"right", width:85},
	{title:"분류1", field:"sales_Order_CLSFC_1", headerHozAlign:"center", hozAlign:"right", width:85},
 	{title:"수량", field:"sales_Output_Order_lQty", headerHozAlign:"center", hozAlign:"right", editor:SOOS_InputEditor,
		formatter : "money", formatterParams: {precision: false},
			cellEdited:function(cell){
			//수량이 변경될때 금액값이 계산되어 입력
			temQty = cell.getValue();
			temUP = cell.getRow().getData().sales_Output_Order_lUnit_Price;
			if(temQty*temUP>0){
				iPrice = temQty*temUP	
			}else{
				iPrice = 0;	
			}
			cell.getRow().update({"sales_Output_Order_lPrice": iPrice});

		}, width:60},
 	{title:"단가", field:"sales_Output_Order_lUnit_Price", headerHozAlign:"center", hozAlign:"right", formatter: "money", formatterParams: { precision: false }, topCalc:function(){return "합계금액"}, width:80},
 	{title:"금액", field:"sales_Output_Order_lPrice", headerHozAlign:"center", hozAlign:"right", formatter: "money", formatterParams: { precision: false },  width:80,
		//금액이 변경될때 합계금액을 계산하여 mastertable에 입력
		topCalc:function(values, data, calcParams){
		    //values - array of column values
		    //data - all table data
		    //calcParams - params passed from the column definition object
		
		    var calc = 0;

		    values.forEach(function(value){
			if(isNaN(value)){
				value = 0;
			}
			calc += value
		 });
		if(salesOutputOrderTable.getDataCount("selected") != 0){
			salesOutputOrderTable.getRows('selected')[0].update({"sales_Output_Order_mTotal": calc});
		}
    	return calc;
	}, topCalcFormatter: "money", topCalcFormatterParams: { precision: false }},
	{title: "비고", field: "sales_Order_lInfo_Remark", headerHozAlign: "center", editor:SOOS_InputEditor, width: 70 },
	{title: "구분", field: "sales_Output_Order_Send_Clsfc", headerHozAlign: "center", width: 70,
		formatter: function(cell, formatterParams) {
			var value = cell.getValue();
			if(dtl_arr[value] != null){
					value = dtl_arr[value];	
				}else{
					value = "";
				}
		    return value;
		},
		editorParams:{values:dtl_arr}
	}]
});

//OrderSub 저장
function SOO_Save() {
	if(salesOutputOrderSubTable.getData().length == 0){
		alert("저장할 데이터가 없습니다.")
		return;
	}
	selectedRow = salesOutputOrderTable.getData("selected")[0];
	console.log(selectedRow);

	//OrderSub 저장부분
	$.ajax({
		method: "post",
		async: false,
		url: "salesOutputOrderRest/SOO_Save",
		data : { masterData : JSON.stringify(selectedRow), subData : JSON.stringify(salesOutputOrderSubTable.getData())},
		beforeSend: function(xhr) {
			var header = $("meta[name='_csrf_header']").attr("content");
			var token = $("meta[name='_csrf']").attr("content");
			xhr.setRequestHeader(header, token);
		},
		success: function(result) {
			if (result) {
				alert("저장되었습니다.");
				SO_Search()
				salesOutputOrderTable.replaceData()
				.then(function(){
					Order_No_select(result)
				})
			} else {
				alert("빈칸이 있어서 저장할 수 없습니다.")
			}
		}
	});
}

//SOL_SaveBtn
$('#SOO_SaveBtn').click(function(){
	SOO_Save();
})

//품목코드로 salesOutputTable 선택하는 코드
function Order_No_select(value){
	rowCount = salesOutputOrderTable.getDataCount();
	//컬럼값을 검색해서 입력값을 포함하는 값이 있으면 선택한다. 
	for(i=0;i<rowCount;i++){
		Output_Order_mOrder_No = salesOutputOrderTable.getData()[i].sales_Output_Order_mOrder_No;

		//지시번호가 입력내용을 포함하면 코드 실행
		if(Output_Order_mOrder_No == value){
			//발주번호가 같은 행 선택
			salesOutputOrderTable.deselectRow();
			salesOutputOrderTable.getRows()[i].select();
			break;
		}					
	}
}

//salesOrderSub 목록검색
function SOOS_Search(sales_Output_Order_mOrder_No){
	//발주넘버
	$.ajax({
		method : "GET",
		url : "salesOutputOrderWORest/SOOS_Search?sales_Output_Order_lOrder_No="+ sales_Output_Order_mOrder_No,
		success : function(result) {
			console.log(result);
			salesOutputOrderSubTable.setData(result);
		}
	});
}

var salesOutputStockTable = new Tabulator("#salesOutputStockTable", {
	selectable: 1,
	height: "102.21px",
	//복사하여 엑셀 붙여넣기 가능
	clipboard: true,
	columns: [
		{ title: "제품코드", field: "s_ItemCode", headerHozAlign: "center" },
		{ title: "제품명", field: "s_ItemName", headerHozAlign: "center" },
		{ title: "규격1", field: "s_Item_Standard_1", headerHozAlign: "center" },
		{ title: "분류1", field: "s_Item_Classfy_1_Name", headerHozAlign: "center", hozAlign: "right"},
		{ title: "재고수량", field: "s_Qty", headerHozAlign: "center", hozAlign: "right", formatter: "money", formatterParams: { precision: false }},
		{ title: "현재지시수량", field: "s_Sales_Output_Order_Qty", headerHozAlign: "center", hozAlign: "right", formatter: "money", formatterParams: { precision: false }}
	]
});

//salesOutputStockTable 목록검색
function SOSM_Search(value){
	
	console.log(value);
	
	datas = {
		SalesOrderNo : value
	}
	//발주넘버
	$.ajax({
		method : "GET",
		url : "salesStockRest/salesOutputStockSelect",
		data: datas,
		success : function(result) {
			console.log(result);
			salesOutputStockTable.setData(result);
		}
	});
}