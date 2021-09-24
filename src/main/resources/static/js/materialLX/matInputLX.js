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
	//복사하여 엑셀 붙여넣기 가능
	clipboard: true,	
 	columns:[ 
 		{title:"발주번호", field:"order_mCus_No", headerHozAlign:"center", hozAlign:"right", headerFilter:true},
 		{title:"코드", field:"order_mCode", headerHozAlign:"center", headerFilter:true},
		{title:"거래처명", field:"order_mName", headerHozAlign:"center", headerFilter:true},
 		{title:"발주일", field:"order_mDate", headerHozAlign:"center", hozAlign:"right", headerFilter:true},
 		{title:"납기일자", field:"order_mDlvry_Date", headerHozAlign:"center", hozAlign:"right", headerFilter:true},
 		{title:"특이사항", field:"order_mRemarks", headerHozAlign:"center", headerFilter:true},
 		{title:"합계금액", field:"order_mTotal", headerHozAlign:"center", hozAlign:"right", headerFilter:true},
 		{title:"수정자", field:"order_mModifier", headerHozAlign:"center", headerFilter:true},
 		{title:"수정일자", field:"order_mModify_Date", headerHozAlign:"center",  headerFilter:true},
 		{title:"목록확인", field:"order_mCheck", visible:false}
 	],
});

//orderMaster 목록검색 matOrder와 동일하지만 테이브이름이 다르고, 미입고 컬럼이 추가됨
function MI_Search(){
	data = {
		startDate : $("#startDate").val(),
		endDate : $("#endDate").val(),
		order_mCode : $("#InMat_Client_Code").val(),
		order_mCus_No : $("#InMat_Order_No").val()
	}
	$.ajax({
		method : "GET",
		dataType : "json",
		async: false,
		url : "matInputLXRest/MI_Search?data="+ encodeURI(JSON.stringify(data)),
		success : function(data) {
			console.log(data);
			matInputTable.setData(data);
			matInputSubTable.clearData();
			inMatTable.clearData();
			ResetBtn();
		}
	});
}

$('#MI_SearchBtn').click(function(){
	MI_Search();
})

var matInputSubTable = new Tabulator("#matInputSubTable", {
	height:"calc(50% - 124.1px)",
	layoutColumnsOnNewData : true,
	selectable: true,
	//복사하여 엑셀 붙여넣기 가능
	clipboard: true,
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
 	{title:"단가", field:"order_lUnit_Price", headerHozAlign:"center", hozAlign:"right", topCalc:function(){return "합계금액"}, width:75},
 	{title:"금액", field:"order_lPrice", headerHozAlign:"center", hozAlign:"right", width:75,
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
	}},
	{title:"미입고", field:"order_lNot_Stocked", headerHozAlign:"center", hozAlign:"right"},
 	{title:"비고", field:"order_lInfo_Remark", headerHozAlign:"center"},
	{title:"구분", field:"order_Rcv_Clsfc", headerHozAlign:"center",
		formatter:function(cell, formatterParams){
		    var value = cell.getValue();
			if(dtl_arr[value] != null){
					value = dtl_arr[value];	
				}else{
					value = "";
				}
		    return value;
		}}]
});

//InputSub 목록검색
function MIS_Search(order_lCus_No){
	$("#Order_lCus_No").val(order_lCus_No);
	//발주넘버
	$.ajax({
		method : "GET",
		url : "matInputLXRest/MIS_Search?order_lCus_No="+ order_lCus_No,
		success : function(datas) {
			matInputSubTable.setData(datas);
			MIM_Search(order_lCus_No);
		}
	});
}

// 입고구분 select를 구성하기위한 ajax
var dtl_arr = new Object();

$.ajax({
	method : "GET",
	async: false,
	url : "dtl_tbl_select?NEW_TBL_CODE=17",
	success : function(datas) {
		for(i=0;i<datas.length;i++){
			dtl_arr[datas[i].child_TBL_NO] = datas[i].child_TBL_TYPE;
		}
	}
});

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


//inMatTable 이미 저장되있는 데이터는 편집 불가능 하게 하는 확인 기능
var editCheck = function(cell){
    //cell - the cell component for the editable cell
    //get row data
    var data = cell.getRow().getData();
    return data.inMat_Order_No == null;
}

var inMatTable = new Tabulator("#inMatTable", {
	height:"calc(50% - 124.1px)",
	layoutColumnsOnNewData : true,
	//복사하여 엑셀 붙여넣기 가능
	clipboard: true,
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
	//order_lNo를 인덱스로 설정
	index:"inMat_No",
 	columns:[
	{title:"순번", field:"inMat_No", headerHozAlign:"center", hozAlign:"center"},
	{title:"발주번호", field:"inMat_Order_No", visible:false},
 	{title:"코드", field:"inMat_Code", headerHozAlign:"center"},
 	{title:"품목명", field:"inMat_Name", headerHozAlign:"center", width : 120},
 	{title:"수량", field:"inMat_Qty", headerHozAlign:"center", hozAlign:"right", editor:MIM_InputEditor, editable:editCheck,
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
	{title:"단가", field:"inMat_Unit_Price", headerHozAlign:"center", hozAlign:"right", editor:MIM_InputEditor, editable:editCheck,
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
	{title:"금액", field:"inMat_Price", headerHozAlign:"center", hozAlign:"right"},
	{title:"구분", field:"inMat_Rcv_Clsfc", headerHozAlign:"center", editor:"select", editable:editCheck, width : 65,
		formatter:function(cell, formatterParams){
		    var value = cell.getValue();
			if(dtl_arr[value] != null){
					value = dtl_arr[value];
				}else{
					value = "";
				}
		    return value;
		},
		editorParams:{values:dtl_arr}
	},
	{title:"거래처코드", field:"inMat_Client_Code", visible:false},
	{title:"입고일자", field:"inMat_Date", headerHozAlign:"center", hozAlign:"right", width : 125}
 		
 	]
});

//inMatTable에 있던 데이터의 갯수
var MIM_preData = 0;

//inMat 목록검색
function MIM_Search(inMat_Order_No){
	$.ajax({
		method : "GET",
		url : "matInputLXRest/MIM_Search?inMat_Order_No="+ inMat_Order_No,
		success : function(MIM_datas) {
			inMatTable.setData(MIM_datas);
		}
	});
}

//inMatTable 저장
function MIM_Save(){
	rowData = inMatTable.getData();
	console.log("rowData =" + rowData);
	realData = []
	//금액이 0 이거나 입고날짜 컬럼을 확인하여 빈칸일경우 저장안됨
	for(i=0;i<rowData.length;i++){
		if(rowData[i].inMat_Price == 0){
			alert("작성중인 행이 있습니다.")
			return false;
		}
		//발주번호가 있는 행만 저장하게끔 한다.
		if(rowData[i].inMat_Order_No != null){
			realData.push(rowData[i]);
		}
	}
	
	
	if(realData.length == 0){
		alert("저장할 목록이 없습니다.");
		return false;
	}

	//InputSub 저장부분
	$.ajax({
		method : "get",
		contentType:'application/json',
		url : "matInputLXRest/MIM_Save?data="+ encodeURI(JSON.stringify(realData)),
		success : function(result) {
			console.log(result);
			if(result == "error"){
				alert("중복된 값이 있습니다..");
			}else if(result == "success"){
				MI_Search();
				Cus_No_select();
				alert("저장되었습니다.");
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