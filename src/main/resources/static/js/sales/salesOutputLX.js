// salesOrderMaster
var salesOutputTable = new Tabulator("#salesOutputTable", {
    height:"calc(100% - 175px)",
	layoutColumnsOnNewData : true,
	headerFilterPlaceholder: null,
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
	salesOutputTable.setData("salesOrderRest/SO_Search",{
		startDate: $("#startDate").val(),
		endDate: $("#endDate").val(),
		clientCode: $("#Sales_InMat_Client_Code").val(),
		condition : ""
	})
	.then(function(){
		salesOutputSubTable.clearData();
		salesOutMatTable.clearData();
		ResetBtn();
	})
}

$("#SO_SearchBtn").click(function(){
	SO_Search();
})

// 출하구분 select를 구성하는 리스트
var output_dtl = dtlSelectList(19);

// salesOrderList
var salesOutputSubTable = new Tabulator("#salesOutputSubTable", {
	height:"calc(50% - 124.1px)",
	layoutColumnsOnNewData : true,
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
		var SOS_Qty = row.getData().sales_Order_lNot_Stocked
		if(SOS_Qty > row.getData().sales_SM_Last_Qty){
			SOS_Qty = row.getData().sales_SM_Last_Qty
		}
    	//salesOutMatTable 반영
		salesOutMatTable.addRow({
				"rownum" : row.getData().sales_Order_lNo,
				"sales_OutMat_Cus_No" : row.getData().sales_Order_lCus_No,
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
			if(salesOutMatTable.getData()[i].rownum == row.getData().sales_Order_lNo){
				salesOutMatTable.getRows()[i].delete()
				break;
			}
		}
    },
 	columns:[
	{title:"순번", field:"sales_Order_lNo", headerHozAlign:"center", hozAlign:"center"},
	{title:"수주번호", field:"sales_Order_lCus_No"},
 	{title:"코드", field:"sales_Order_lCode", headerHozAlign:"center"},
 	{title:"품목명", field:"sales_Order_lName", headerHozAlign:"center"},
 	{title:"수량", field:"sales_Order_lQty", headerHozAlign:"center", hozAlign:"right"},
	{title:"출고수량", field:"sales_Order_lSum", headerHozAlign:"center", hozAlign:"right"},
 	{title:"단가", field:"sales_Order_lUnit_Price", headerHozAlign:"center", hozAlign:"right",
		formatter: "money", formatterParams: { precision: false }, topCalc:function(){return "합계금액"}},
 	{title:"금액", field:"sales_Order_lPrice", headerHozAlign:"center", hozAlign:"right", width: 75, formatter: "money", formatterParams: { precision: false }, 
		topCalc: "sum", topCalcFormatter: "money", topCalcFormatterParams: { precision: false }},
	{title:"미출고", field:"sales_Order_lNot_Stocked", headerHozAlign:"center", hozAlign:"right"},
 	{title:"재고", field:"sales_SM_Last_Qty", headerHozAlign:"center", hozAlign:"right"},
	{title:"구분", field:"sales_Order_Send_Clsfc", headerHozAlign:"center",
		formatter: function(cell, formatterParams) {
				var value = cell.getValue();
				if(output_dtl[value] != null){
						value = output_dtl[value];	
					}else{
						value = "";
					}
			    return value;
			}, width:65}]
});

//salesOutputSub 목록검색
function SOS_Search(sales_Order_lCus_No){
	
	salesOutputSubTable.setData("salesOrderRest/SOL_Search",{salesOrderNo : sales_Order_lCus_No})
	.then(function(){
			salesOutMatTable.clearData();		
	})
}

//matInputSub 커스텀 기능설정
var SOM_InputEditor = function(cell, onRendered, success, cancel, editorParams){
    //cell - 편집 가능한 셀의 셀 구성 요소
    //onRendered - 에디터가 렌더링 되었을 때 호출 할 함수
    //success - 성공적을 업데이트 된 값을 tabulator에 전달하기 위해 호출되는 함수
    //cancel - 편집을 중단하고 일반 셀로 돌아 가기 위해 호출하는 함수
    //editorParams - editorParams 열 정의 속성에 전달 된 params 객체

    //create 및 style editor
    var SOM_input = document.createElement("input");

    SOM_input.setAttribute("type", "text");

    //입력 생성 및 스타일 지정
    SOM_input.style.padding = "3px";
    SOM_input.style.width = "100%";
    SOM_input.style.boxSizing = "border-box";

    //편집기의 값을 셀의 현재 값으로 설정
	if(cell.getValue() == undefined){
		SOM_input.value = "";
	}else{
		SOM_input.value = cell.getValue();
	}

    //에디터가 선택되면 선택 상자에 포커스 설정 (타임 아웃은 편집기를 DOM에 추가 할 수 있음)
    onRendered(function(){
        SOM_input.focus();
		SOM_input.select();
        SOM_input.style.css = "100%";
    });

    //값이 설정되면 업데이트 할 셀 트리거
    function onChange(){
        success(SOM_input.value);
    }
    
	//바꼈을때 블러됫을때 함수 발동
    SOM_input.addEventListener("change", onChange);
    SOM_input.addEventListener("blur", onChange);

    //키버튼 이벤트
    SOM_input.addEventListener("keydown", function (e) {
		
    });
    //반환
    return SOM_input;
};

var salesOutMatTable = new Tabulator("#salesOutMatTable", {
	height: "calc(50% - 124.1px)",
	layoutColumnsOnNewData : true,
	rowAdded : function ( row ) {
		//행이 추가되면 첫셀에 포커스
		do{
		setTimeout(function(){
			row.getCell("sales_OutMat_Qty").edit();
			salesOutMatTable.deselectRow();
			row.select();
			},100);
		}
		while(row.getData().sales_OutMat_Qty === "undefined");

	},
 	columns:[
	{title:"순번", field:"rownum", headerHozAlign:"center", hozAlign:"center", formatter:"rownum"},
	{title:"수주번호", field:"sales_OutMat_Cus_No", visible:false},
 	{title:"코드", field:"sales_OutMat_Code", headerHozAlign:"center"},
 	{title:"품목명", field:"sales_OutMat_Name", headerHozAlign:"center", width: 140},
 	{title:"수량", field:"sales_OutMat_Qty", headerHozAlign:"center", hozAlign:"right", editor: SOM_InputEditor,
		formatter:"money", formatterParams: {precision: false},
		cellEdited:function(cell){
			//수량이 변경될때 금액값이 계산되어 입력
			temQty = qtyCheck(cell.getRow().getData().sales_OutMat_Code,cell.getValue())
			temUP = cell.getRow().getData().sales_OutMat_Unit_Price;			
			
			if(temQty*temUP>0){
				iPrice = temQty*temUP
			}else{
				iPrice = 0;	
			}
			cell.getRow().update({
							"sales_OutMat_Qty":temQty,
							"sales_OutMat_Price": iPrice
						});
		}},
	{title:"단가", field:"sales_OutMat_Unit_Price", headerHozAlign:"center", hozAlign:"right",
		formatter:"money", formatterParams: {precision: false}},
	{title:"금액", field:"sales_OutMat_Price", headerHozAlign:"center", hozAlign:"right", width: 75,
		formatter:"money", formatterParams: {precision: false}},
	{title:"구분", field:"sales_OutMat_Send_Clsfc", headerHozAlign:"center",editor: "select", width: 65,
			formatter: function(cell, formatterParams) {
				var value = cell.getValue();
				if(output_dtl[value] != null){
						value = output_dtl[value];	
					}else{
						value = "";
					}
			    return value;
			}
	},
	{title:"출고일자", field:"sales_OutMat_Date", headerHozAlign:"center", hozAlign:"right", width: 124}
 	]
});

function qtyCheck(itemCode,itemQty){
	var subTableData = salesOutputSubTable.getData();
	var forResult = itemQty;
	for(let i=0;i<subTableData.length;i++){
		if(subTableData[i].sales_Order_lCode == itemCode){
			if(subTableData[i].sales_Order_lNot_Stocked < itemQty){
				alert("미출고 수량을 초과할 수 없습니다.")
				forResult = subTableData[i].sales_Order_lNot_Stocked
			}
		}
	}
	return forResult;
}

//SOM_Save
function SOM_Save() {
	if(salesOutMatTable.getData().length == 0){
		alert("저장할 데이터가 없습니다.")
		return;
	}
	var selectedRow = salesOutMatTable.getData("selected");
	
	//OrderSub 저장부분
	$.ajax({
		method: "post",
		url: "salesOutputRest/salesOutputSave",
		data: JSON.stringify(selectedRow),
		contentType: 'application/json',
		beforeSend: function(xhr) {
			var header = $("meta[name='_csrf_header']").attr("content");
			var token = $("meta[name='_csrf']").attr("content");
			xhr.setRequestHeader(header, token);
		},
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

$("#allOutput").click(function(){
	var rows = salesOutputSubTable.getRows();
	for(var i=0;i<salesOutputSubTable.getDataCount();i++){
		if(rows[i].getData().sales_Order_lSum == 0){
			salesOutputSubTable.selectRow(rows[i]);
		}
	}
})