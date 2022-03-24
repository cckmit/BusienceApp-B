var matInputTable = new Tabulator("#matInputTable", {
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
        if(row.getData().order_mCheck == "Y"){
            row.getElement().style.color = "red";
        }else if(row.getData().order_mCheck == "I"){
            row.getElement().style.color = "blue";
		}
    },
	//행클릭 이벤트
	rowClick:function(e, row){
		matInputTable.deselectRow();
		row.select();
    },
	rowSelected:function(row){
    	MIS_Search(row.getData().order_mCus_No);
		ResetBtn();
    },
 	columns:[ 
 		{title:"발주번호", field:"order_mCus_No", headerHozAlign:"center", hozAlign:"right", headerFilter:true},
 		{title:"코드", field:"order_mCode", headerHozAlign:"center", headerFilter:true},
		{title:"거래처명", field:"order_mName", headerHozAlign:"center", headerFilter:true},
 		{title:"발주일", field:"order_mDate", headerHozAlign:"center", hozAlign:"right", headerFilter:true},
 		{title:"납기일자", field:"order_mDlvry_Date", headerHozAlign:"center", hozAlign:"right", headerFilter:true},
 		{title:"특이사항", field:"order_mRemarks", headerHozAlign:"center", headerFilter:true},
 		{title:"합계금액", field:"order_mTotal", headerHozAlign:"center", hozAlign:"right", headerFilter:true,
			formatter : "money", formatterParams:{ precision:false}},
 		{title:"수정자", field:"order_mModifier", headerHozAlign:"center", headerFilter:true},
 		{title:"수정일자", field:"order_mModify_Date", headerHozAlign:"center",  headerFilter:true},
 		{title:"목록확인", field:"order_mCheck", visible:false}
 	],
});

//orderMaster 목록검색 matOrder와 동일하지만 테이브이름이 다르고, 미입고 컬럼이 추가됨
function MI_Search(){
	var data = {
		startDate : $("#startDate").val(),
		endDate : $("#endDate").val(),
		itemCode : $("#InMat_Client_Code").val(),
		OrderNo : $("#InMat_Order_No").val()
	}
	matInputTable.setData("matOrderLXRest/MOM_Search", data)
	.then(function(){
		matInputSubTable.clearData();
		inMatTable.clearData();
		ResetBtn();
	})
}

$('#MI_SearchBtn').click(function(){
	MI_Search();
})

var matInputSubTable = new Tabulator("#matInputSubTable", {
	height:"calc(50% - 124.1px)",
	layoutColumnsOnNewData : true,
	selectable: true,
	rowFormatter:function(row){
		//입고수량이 수량이상이면 글자색을 빨간색으로 바꿔준다
        if(row.getData().order_lQty <= row.getData().order_lSum){
            row.getElement().style.color = "red";
        }
    },
	rowSelected:function(row){
		//미입고 재고가 0 이면 반응 안함
		if(row.getData().order_lNot_Stocked == 0 || row.getData().order_lNot_Stocked == null){
			return false;
		}
		//행이 선택 됬을때 inmat에 행 추가, 이미 추가되어있는 행이라면, 선택
		inMatTable.addRow({
			inMat_No : inMatTable.getDataCount()+1,
			inMat_Order_No : matInputTable.getData('selected')[0].order_mCus_No,
			inMat_Code : row.getData().order_lCode,
			inMat_Name : row.getData().order_lName,
			inMat_Qty : row.getData().order_lNot_Stocked,
			inMat_Unit_Price : row.getData().order_lUnit_Price,
			inMat_Price : row.getData().order_lPrice,
			inMat_Rcv_Clsfc : row.getData().order_Rcv_Clsfc,
			inMat_Client_Code : matInputTable.getData('selected')[0].order_mCode,
			inMat_Date : moment(new Date()).format('YYYY-MM-DD HH:mm:ss')
		});
		UseBtn();
    },
	rowDeselected:function(row){
		//미입고 재고가 0 이면 반응 안함
		if(row.getData().order_lNot_Stocked == 0){
			return false;
		}
		//클릭한 행과 같은 랏번호를 찾아서 삭제해줌
		for(i=0;i<inMatTable.getDataCount();i++){
			if(inMatTable.getData()[i].inMat_Code == row.getData().order_lCode){
				inMatTable.getRows()[i].delete()
				.then(function(){
					// 삭제후 순번 정리
					rowCount = inMatTable.getDataCount("active");
					for(j=0;j<rowCount;j++){
						inMatTable.getRows()[j].update({inMat_No:j+1})
					}
				});
				break;
			}
		}
    },
 	columns:[
	{title:"순번", field:"order_lNo", headerHozAlign:"center", hozAlign:"center"},
	{title:"발주번호", field:"order_lCus_No", visible:false},
 	{title:"코드", field:"order_lCode", headerHozAlign:"center"},
 	{title:"품목명", field:"order_lName", headerHozAlign:"center"},
 	{title:"수량", field:"order_lQty", headerHozAlign:"center", hozAlign:"right"},
	{title:"입고수량", field:"order_lSum", headerHozAlign:"center", hozAlign:"right"},
 	{title:"단가", field:"order_lUnit_Price", headerHozAlign:"center", hozAlign:"right",
		formatter : "money", formatterParams:{ precision:false},
		topCalc:function(){return "합계금액"}},
 	{title:"금액", field:"order_lPrice", headerHozAlign:"center", hozAlign:"right",
		formatter : "money", formatterParams:{ precision:false},
		topCalc:"sum", topCalcFormatter : "money", topCalcFormatterParams: {precision: false}},
	{title:"미입고", field:"order_lNot_Stocked", headerHozAlign:"center", hozAlign:"right"},
 	{title:"비고", field:"order_lInfo_Remark", headerHozAlign:"center"},
	{title:"구분", field:"order_Rcv_Clsfc", headerHozAlign:"center",
		formatter:function(cell, formatterParams){
		    var value = cell.getValue();
			if(input_dtl[value] != null){
					value = input_dtl[value];	
				}else{
					value = "";
				}
		    return value;
		}}]
});

//InputSub 목록검색
function MIS_Search(order_lCus_No){
	$("#Order_lCus_No").val(order_lCus_No);
	
	var datas = {
		OrderNo : order_lCus_No
	}
	
	matInputSubTable.setData("matOrderLXRest/MOL_Search", datas)
}

//matInputSub 커스텀 기능설정
var MIM_InputEditor = function(cell, onRendered, success, cancel, editorParams){
    //cell - 편집 가능한 셀의 셀 구성 요소
    //onRendered - 에디터가 렌더링 되었을 때 호출 할 함수
    //success - 성공적을 업데이트 된 값을 tabulator에 전달하기 위해 호출되는 함수
    //cancel - 편집을 중단하고 일반 셀로 돌아 가기 위해 호출하는 함수
    //editorParams - editorParams 열 정의 속성에 전달 된 params 객체

    //create 및 style editor
    var MIM_input = document.createElement("input");

    MIM_input.setAttribute("type", "text");

    //입력 생성 및 스타일 지정
    MIM_input.style.padding = "3px";
    MIM_input.style.width = "100%";
    MIM_input.style.boxSizing = "border-box";

    //편집기의 값을 셀의 현재 값으로 설정
	if(cell.getValue() == undefined){
		MIM_input.value = "";
	}else{
		MIM_input.value = cell.getValue();
	}

    //에디터가 선택되면 선택 상자에 포커스 설정 (타임 아웃은 편집기를 DOM에 추가 할 수 있음)
    onRendered(function(){
        MIM_input.focus();
		MIM_input.select();
        MIM_input.style.css = "100%";
    });

    //값이 설정되면 업데이트 할 셀 트리거
    function onChange(){
        success(MIM_input.value);
    }
    
	//바꼈을때 블러됫을때 함수 발동
    MIM_input.addEventListener("change", onChange);
    MIM_input.addEventListener("blur", onChange);

    //키버튼 이벤트
    MIM_input.addEventListener("keydown", function (e) {
		//품목코드 팝업창
		if(e.keyCode == 13){
			//단가셀 체크
			if (cell.getField() == "inMat_Unit_Price") {
				//선택되있는 행중 마지막행의 순번 == 선택할 다음행의 인덱스값
				nextRow_No = matInputSubTable.getData("selected")[matInputSubTable.getDataCount("selected")-1].order_lNo;
				nextRow = matInputSubTable.getRows()[nextRow_No];
				if(nextRow){
					matInputSubTable.selectRow(nextRow);	
				}
			}
		}
    });
    //반환
    return MIM_input;
};

// 입고구분 select를 구성하는 리스트
var input_dtl = dtlSelectList(17);

var inMatTable = new Tabulator("#inMatTable", {
	height:"calc(50% - 124.1px)",
	layoutColumnsOnNewData : true,
	//커스텀 키 설정
	keybindings:{
        "navNext" : "13"
    },
	rowAdded : function ( row ) {
		//행이 추가되면 첫셀에 포커스
		do{
		setTimeout(function(){
			row.getCell("inMat_Qty").edit();
			inMatTable.deselectRow();
			row.select();
			},100);
		}
		while(row.getData().inMat_Qty === "undefined");

	},
	rowClick:function(e, row){
		inMatTable.deselectRow();
		row.select();
    },
 	columns:[
	{title:"순번", field:"inMat_No", headerHozAlign:"center", hozAlign:"center"},
	{title:"발주번호", field:"inMat_Order_No", visible:false},
 	{title:"코드", field:"inMat_Code", headerHozAlign:"center"},
 	{title:"품목명", field:"inMat_Name", headerHozAlign:"center", width : 120},
 	{title:"수량", field:"inMat_Qty", headerHozAlign:"center", hozAlign:"right", editor:MIM_InputEditor,
		cellEdited:function(cell){
			//수량이 변경될때 금액값이 계산되어 입력
			temQty = cell.getValue();
			temUP = cell.getRow().getData().inMat_Unit_Price;			
	
			if(temQty*temUP>0){
				iPrice = temQty*temUP
			}else{
				iPrice = 0;	
			}
			cell.getRow().update({"inMat_Price": iPrice});
		}
	},
	{title:"단가", field:"inMat_Unit_Price", headerHozAlign:"center", hozAlign:"right",
		formatter : "money", formatterParams:{ precision:false}, editor:MIM_InputEditor,
		cellEdited:function(cell){
			//단가가 변경될때 금액값이 계산되어 입력
			temQty = cell.getRow().getData().inMat_Qty;
			temUP = cell.getValue();			
	
			if(temQty*temUP>0){
				iPrice = temQty*temUP	
				alert("금액 변경");
			}else{
				iPrice = 0;
			}
			cell.getRow().update({"inMat_Price": iPrice});
		}
	},
	{title:"금액", field:"inMat_Price", headerHozAlign:"center", hozAlign:"right",
		formatter : "money", formatterParams:{ precision:false}},
	{title:"구분", field:"inMat_Rcv_Clsfc", headerHozAlign:"center", editor:"select",
		formatter:function(cell, formatterParams){
		    var value = cell.getValue();
			if(input_dtl[value] != null){
					value = input_dtl[value];
				}else{
					value = "";
				}
		    return value;
		},
		editorParams:{values:input_dtl}
	},
	{title:"거래처코드", field:"inMat_Client_Code", visible:false},
	{title:"입고일자", field:"inMat_Date", headerHozAlign:"center", hozAlign:"right", visible:false}
 		
 	]
});

//inMatTable 저장
function MIM_Save(){
	var rowData = inMatTable.getData();
	//금액이 0 이거나 입고날짜 컬럼을 확인하여 빈칸일경우 저장안됨
	for(i=0;i<rowData.length;i++){
		if(rowData[i].inMat_Qty == 0){
			alert("작성중인 행이 있습니다.")
			return false;
		}
	}
	
	
	if(rowData.length == 0){
		alert("저장할 목록이 없습니다.");
		return false;
	}
	console.log(rowData);
	//InputSub 저장부분
	$.ajax({
		method : "Post",
		url: "matInputLXRest/MIM_Save",
		data: JSON.stringify(rowData),
		contentType:'application/json',
		beforeSend: function (xhr) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           xhr.setRequestHeader(header, token);
		},
		success : function(result) {
			console.log(result)
			if(result){
				MI_Search();
				Cus_No_select();
				alert("저장되었습니다.");
			}else{
				alert("오류가 발생하였습니다.")
			}
		}				
	});	
}


$('#MIM_SaveBtn').click(function(){
	MIM_Save();
})

//발주번호로 OrderMaster 선택하는 코드
function Cus_No_select(){
	rowCount = matInputTable.getDataCount("active");
	//컬럼값을 검색해서 입력값을 포함하는 값이 있으면 선택한다. 
	for(var i=0;i<rowCount;i++){
		Cus_No = matInputTable.getColumn("order_mCus_No").getCells();
		//발주번호가 입력내용을 포함하면 코드 실행
		if(Cus_No[i].getValue() == $('#Order_lCus_No').val()){
			//발주번호가 같은 행 선택
			Cus_No[i].getRow().select();
			break;
		}					
	}
}

$("#allInput").click(function(){
	var rows = matInputSubTable.getRows();
	for(var i=0;i<matInputSubTable.getDataCount();i++){
		if(rows[i].getData().order_lSum == 0){
			matInputSubTable.selectRow(rows[i]);			
		}
	}
})