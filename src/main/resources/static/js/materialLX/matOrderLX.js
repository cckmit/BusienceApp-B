//matOrder 커스텀 기능설정
var MO_inputEditor = function(cell, onRendered, success, cancel, editorParams){
    //cell - 편집 가능한 셀의 셀 구성 요소
    //onRendered - 에디터가 렌더링 되었을 때 호출 할 함수
    //success - 성공적을 업데이트 된 값을 tabulator에 전달하기 위해 호출되는 함수
    //cancel - 편집을 중단하고 일반 셀로 돌아 가기 위해 호출하는 함수
    //editorParams - editorParams 열 정의 속성에 전달 된 params 객체

    //create 및 style editor
    var MO_input = document.createElement("input");

    MO_input.setAttribute("type", "text");

    //입력 생성 및 스타일 지정
    MO_input.style.padding = "3px";
    MO_input.style.width = "100%";
    MO_input.style.boxSizing = "border-box";

    //편집기의 값을 셀의 현재 값으로 설정
	if(cell.getValue() == undefined){
		MO_input.value = "";
	}else{
		MO_input.value = cell.getValue();
	}

    //에디터가 선택되면 선택 상자에 포커스 설정 (타임 아웃은 편집기를 DOM에 추가 할 수 있음)
    onRendered(function(){
        MO_input.focus();
		MO_input.select();
        MO_input.style.css = "100%";
    });

    //값이 설정되면 업데이트 할 셀 트리거
    function onChange(){
        success(MO_input.value);
    }
    
	//바꼈을때 블러됫을때 함수 발동
    MO_input.addEventListener("change", onChange);
    MO_input.addEventListener("blur", onChange);

    //키버튼 이벤트
    MO_input.addEventListener("keydown", function (e) {
		//거래처코드 셀에서 백스페이스를 눌렀을경우 거래처명이 사라지게함
		if (e.keyCode == 8) {
			if (cell.getField() == "order_mCode") {
				cell.getRow().update({"order_mName":''})
			}
		}
		if (e.keyCode == 13) {
			//거래처코드셀 체크
			if (cell.getField() == "order_mCode") {
				//내용이 있을경우 검색해서 값이 하나일경우 생략, 아닐경우 팝업창
				$.ajax({
					method: "GET",
					url: "customer_check?Cus_Code=" + MO_input.value,
					dataType: "json",
					success: function(data) {
						console.log("쿼리실행");
						if (data.length == 1) {
							//검색어와 일치하는값이 있는경우
							cell.getRow().update({
								"order_mCode": data[0].cus_Code,
								"order_mName": data[0].cus_Name
							});
						} else {
							//검색어와 일치하는값이 없는경우, 팝업창
							customerPopup(MO_input.value,'grid','','in');
						}
					}
				})
			}
			//특이사항셀 체크
			if (cell.getField() == "order_mRemarks") {
				//납기일자를 체크해서 잘못되었으면 반응 안함
				if(cell.getRow().getData().order_mDlvry_Date.length != "10"){
					alert("납기일자를 잘못 입력하였습니다. \n형식 : '2020-01-01'");
				}else{
					if(cell.getRow().getData().order_mCheck != "I"){
			        	//특이사항에서 엔터를 하면 행추가
						matOrderSubTable.addRow();
					}			
				}
			}
			cell.nav().next();
		}
    });
    //반환
    return MO_input;
};

//팝업창으로부터 특정 파라미터 값으로 데이터를 받는다 
function customer_gridInit(CCode, CName) {
	cellPos.getRow().update({
		"order_mCode": CCode,
		"order_mName": CName
	});
	//선택 후 포커스 이동
	cellPos.getElement().focus();
}

//셀위치저장
var cellPos = null;

var matOrderTable = new Tabulator("#matOrderTable", {
	//페이징
	pagination:"local",
	paginationSize:20,
	paginationAddRow : "table",
	layoutColumnsOnNewData : true,
	headerFilterPlaceholder: null,
    height:"calc(100% - 175px)",
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
	//커스텀 키 설정
	/*keybindings:{
        "navNext" : "13"
    },*/
	//행클릭 이벤트
	rowClick:function(e, row){
		row.getTable().deselectRow();
		row.select();
		
    },
	rowSelected:function(row){
    	MOL_Search(row.getData().order_mCus_No);
		
		//버튼 설정
		ResetBtn()
		if(row.getData().order_mCheck != 'I'){
			UseBtn();
		}
    },
	//행추가시 기능
	rowAdded : function ( row ) {
	row.getTable().deselectRow();
	row.select();

	row.update({"order_mCus_No": '',
				"order_mDlvry_Date": '',
				"order_mCode": $("#InMat_Client_Code").val(),
				"order_mName": $("#InMat_Client_Name").val(),
				"order_mDate": moment(new Date()).format('YYYY-MM-DD HH:mm:ss')});
		
	//페이지 이동(전체 행 수/페이지당 행 수)
	matOrderTable.setPage(Math.ceil(matOrderTable.getDataCount("active") / matOrderTable.getPageSize()));

	//MO_Add버튼 비활성화 (작성중인 행이 있다면 추가못하게함)
	if (!$('#MO_AddBtn').hasClass('unUseBtn'))  {
		$('#MO_AddBtn').addClass('unUseBtn');
	}

	UseBtn();
	//list와 stock의 데이터를 없에준다
	matOrderSubTable.clearData();
	matOrderStockTable.clearData();
		
	//행이 추가되면 첫셀에 포커스
	do{
	setTimeout(function(){
		row.getCell("order_mCode").edit();
		},100);
	}
	while(row.getData().order_mDlvry_Date === "undefined");
	},
	cellEditing:function(cell){
        //셀위치 저장하여 포커싱부여
		cellPos = cell;
    },
 	columns:[ 
 		{title:"발주번호", field:"order_mCus_No", headerHozAlign:"center", hozAlign:"right", headerFilter:true},
 		{title:"코드", field:"order_mCode", headerHozAlign:"center", headerFilter:true, editor: MO_inputEditor},
		{title:"거래처명", field:"order_mName", headerHozAlign:"center", headerFilter:true},
 		{title:"발주일", field:"order_mDate", headerHozAlign:"center", hozAlign:"right", headerFilter:true,
			formatter: "datetime", formatterParams : {outputFormat : "YYYY-MM-DD HH:mm:ss"}},
 		{title:"납기일자", field:"order_mDlvry_Date", headerHozAlign:"center", hozAlign:"right", editor: MO_inputEditor, headerFilter:true},
 		{title:"특이사항", field:"order_mRemarks", headerHozAlign:"center", editor: MO_inputEditor, headerFilter:true},
 		{title:"합계금액", field:"order_mTotal", headerHozAlign:"center", hozAlign:"right", headerFilter:true, 
			formatter : "money", formatterParams: {precision: false}},
 		{title:"수정자", field:"order_mModifier", headerHozAlign:"center", headerFilter:true},
 		{title:"수정일자", field:"order_mModify_Date", headerHozAlign:"center",  headerFilter:true,
			formatter: "datetime", formatterParams : {outputFormat : "YYYY-MM-DD HH:mm:ss"}},
		{title:"목록확인", field:"order_mCheck", visible:false}
 	]
});

//MO_AddBtn
$('#MO_AddBtn').click(function(){
	//행추가
	matOrderTable.addRow();
})

//orderMaster 목록검색
function MO_Search(){
	data = {
		startDate : $("#startDate").val(),
		endDate : $("#endDate").val(),
		order_mCode : $("#InMat_Client_Code").val()
	}
	
	$.ajax({
		method : "GET",
		dataType : "json",
		async: false,
		url : "matOrderLXRest/MO_Search?data="+ encodeURI(JSON.stringify(data)),
		success : function(datas) {
			console.log(datas);
			matOrderTable.setData(datas);
			
			//list와 stock의 데이터를 없에준다
			matOrderSubTable.clearData();
			matOrderStockTable.clearData();
			
			ResetBtn();
			//MO_Add버튼 활성화
			if($('#MO_AddBtn').hasClass('unUseBtn')){
				$('#MO_AddBtn').removeClass('unUseBtn');				
			}
		}
	});
}

//SO_SearchBtn
$('#MO_SearchBtn').click(function(){
	MO_Search();
})

//삭제버튼
function MO_Delete(){
	selectedData = matOrderTable.getData("selected");
	
	if(selectedData.length == 0){
		alert("선택된 행이 없습니다.");
		return false;
	}
	if(selectedData[0].order_mCheck != "N"){
		alert("입고내역이 있는 발주내역은 삭제할 수 없습니다.")
		return false;
	}
	
	// 기존 조회한 데이터는 수주번호가 있고, 새로 추가된데이터는 수주번호가 없다
	if(confirm("선택한 행이 삭제됩니다. 삭제하시겠습니까?")){
		$.ajax({
			method: "post",
			url: "matOrderLXRest/MO_Delete?data=" + encodeURI(JSON.stringify(selectedData[0])),
			success: function(result) {
				console.log(result);
				if (result == "error") {
					alert("삭제 오류")
				}else{
					// 행삭제
					matOrderTable.deleteRow(matOrderTable.getRows("selected"));
					//list와 stock의 데이터를 없에준다
					matOrderSubTable.clearData();
					matOrderStockTable.clearData();
					
					ResetBtn();
				}
			}
		})
	}
}

//MO_DeleteBtn
$('#MO_DeleteBtn').click(function(){
	MO_Delete();
})

//orderprint
function MO_printBtn(){
	selectedData = matOrderTable.getData("selected");
	console.log(selectedData)
	//선택한 행이 있을경우 프린트가능
	if(selectedData.length == 1){
		//팝업창으로 파라미터를 전달하기위해 form태그 input 안에 저장
		$('#mCus_No').val(selectedData[0].order_mCus_No);
		$('#mCode').val(selectedData[0].order_mCode);
		$('#mName').val(selectedData[0].order_mName);
		$('#mDate').val(selectedData[0].order_mDate);
		$('#mDlvry_Date').val(selectedData[0].order_mDlvry_Date);
		
		//창의 주소
		var url = "";
		//창의 이름
		var name = "orderprint";
		//창의 css
		var option = "width = 800, height = 800, top = 50, left = 300, location = no";
		
		openWin = window.open(url, name, option);
		$("#cus_frm").submit();
	}else{
		alert("행을 선택해주세요. 선택한 행의 데이터를 인쇄 할 수 있습니다.")
	}	
}

//matOrderSub커스텀 기능설정
var MOL_InputEditor = function(cell, onRendered, success, cancel, editorParams){
    //cell - 편집 가능한 셀의 셀 구성 요소
    //onRendered - 에디터가 렌더링 되었을 때 호출 할 함수
    //success - 성공적을 업데이트 된 값을 tabulator에 전달하기 위해 호출되는 함수
    //cancel - 편집을 중단하고 일반 셀로 돌아 가기 위해 호출하는 함수
    //editorParams - editorParams 열 정의 속성에 전달 된 params 객체

    //create 및 style editor
    var MOL_input = document.createElement("input");

    MOL_input.setAttribute("type", "text");

    //입력 생성 및 스타일 지정
    MOL_input.style.padding = "3px";
    MOL_input.style.width = "100%";
    MOL_input.style.boxSizing = "border-box";

    //편집기의 값을 셀의 현재 값으로 설정
	if(cell.getValue() == undefined){
		MOL_input.value = "";
	}else{
		MOL_input.value = cell.getValue();
	}

    //에디터가 선택되면 선택 상자에 포커스 설정 (타임 아웃은 편집기를 DOM에 추가 할 수 있음)
    onRendered(function(){
        MOL_input.focus();
		MOL_input.select();
        MOL_input.style.css = "100%";
    });

    //값이 설정되면 업데이트 할 셀 트리거
    function onChange(){
        success(MOL_input.value);
    }
    
	//바꼈을때 블러됫을때 함수 발동
    MOL_input.addEventListener("change", onChange);
    MOL_input.addEventListener("blur", onChange);

    //키버튼 이벤트
    MOL_input.addEventListener("keydown", function (e) {
		//제품코드 셀에서 백스페이스를 눌렀을경우 제품명이 사라지게함
		if (e.keyCode == 8) {
			if (cell.getField() == "order_lCode") {
				cell.getRow().update({"order_lName":'',
									"order_STND_1": '',
									"order_lUnit_Price": ''})
			}
		}
		//제품코드 팝업창
		if(e.keyCode == 13){
			//코드셀체크
			if (cell.getField() == "order_lCode") {
				//내용이 있을경우 검색해서 값이 하나일경우 생략, 아닐경우 팝업창
				//쿼리실행
				$.ajax({
					method : "GET",
					url : "product_check?PRODUCT_ITEM_CODE=" + MOL_input.value,
					dataType : "json",
					success : function(data) {
						console.log(data);			
						if(data.length == 1){
							//검색어와 일치하는값이 있는경우
							cell.getRow().update({ 
								"order_lCode" : data[0].product_ITEM_CODE, 
								"order_lName" : data[0].product_ITEM_NAME,
								"order_STND_1" : data[0].product_INFO_STND_1,
								"order_lUnit_Price" : data[0].product_UNIT_PRICE
								});
						}else{
							//검색어와 일치하는값이 없는경우, 팝업창
							itemPopup(MOL_input.value,'grid','','material');
						}
					}
				})
	        }
			//비고셀 체크
			if (cell.getField() == "order_lInfo_Remark") {
				//구분넘기기
				//cell.nav().next();
				//만약 마지막행의 합계금액이 비어있을경우 추가 안됨
				lastRow = matOrderSubTable.getData()[matOrderSubTable.getDataCount("active")-1];
				if(lastRow.order_lPrice == 0){
					alert("수량과 단가를 입력해주세요.");
					cell.nav().prev();					
				}else if(lastRow.order_lCode.length != "6"){
					alert("제품코드를 잘못 입력하였습니다.")
					cell.nav().prev();	
				}
			}
		}
    });
    //반환
    return MOL_input;
};

// 입고구분 select를 구성하는 리스트
var input_dtl = dtlSelectList(17);
console.log(input_dtl);

//matOrderSubTable 이미 저장되있는 데이터는 편집 불가능 하게 하는 확인 기능
var editCheck = function(cell){
    //cell - the cell component for the editable cell
    //get row data
    var data = cell.getRow().getData();
    return data.Order_lCus_No == null;
}

var matOrderSubTable = new Tabulator("#matOrderSubTable", {
	//페이징
	pagination:"local",
	paginationSize:20,
	paginationAddRow : "table",
	layoutColumnsOnNewData : true,
	height:"calc(90% - 175px)",
	selectable: 1,
	//복사하여 엑셀 붙여넣기 가능
	clipboard: true,
	tabEndNewRow: true,
	//커스텀 키 설정
	keybindings:{
        "navNext" : ["13",  "9"],
    },
	rowFormatter:function(row){
		//order_lNot_Stocked가 0이면 빨간색으로, 입고수량보다 작으면 파란색으로 나타냄
        if(row.getData().order_lNot_Stocked == 0){
            row.getElement().style.color = "red";
        }else if(row.getData().order_lNot_Stocked < row.getData().order_lQty){
			row.getElement().style.color = "blue";
		}
    },
	//행이 추가될때마다 인덱스 부여
	rowAdded : function ( row ) {
	//순번을 정하는 코드 마지막행의 순번+1
	if(matOrderSubTable.getDataCount("active") >1){
		//행이 생성된후의 마지막행의 -1행의 순번값을 찾아서 
		//matOrderSubTable.getDataCount("active")는 행갯수 즉 1부터 시작, [] 안의 값은 0부터 시작하기때문에 2를 빼줘야 마지막행의 바로 전행이 된다.
		// 마지막행에다가 순번값+1을 넣는다
		next_order_lNo = matOrderSubTable.getData()[matOrderSubTable.getDataCount("active")-2].order_lNo+1;
		console.log(next_order_lNo);
	}else{
		next_order_lNo = 1;
	}
	
	row.update({"order_lNo": next_order_lNo,
				"order_lPrice": 0,
				"Order_lInfo_Remark": '',
				"order_Rcv_Clsfc": "203"});
	//행이 추가되면 첫셀에 포커스
	do{
	setTimeout(function(){
		row.getCell("order_lCode").edit();
		},100);
	}
	while(row.getData().order_lCode === "undefined");
	},
	cellEditing:function(cell){
		//셀위치 저장하여 포커싱부여
		cellPos = cell;
		//편집하는 행의 품목에 대한 재고가 테이블에 나타남
		var cell_lCode = null;
		if(cell.getRow().getData().order_lCode != cell_lCode){
			if(cell.getRow().getData().order_lCode.length == 6){
				MOS_Search(cell.getRow().getData().order_lCode);
				
				cell_lCode = cell.getRow().getData().order_lCode	
			}
		}
    },
 	columns:[
	{formatter:"rowSelection", titleFormatter:"rowSelection", headerHozAlign:"center", hozAlign:"center", headerSort:false},
 	{title:"순번", field:"order_lNo", headerHozAlign:"center", hozAlign:"center"},
	{title:"발주No", field:"order_lCus_No", visible:false},
 	{title:"코드", field:"order_lCode", headerHozAlign:"center", editor: MOL_InputEditor, editable:editCheck},
 	{title:"제품명", field:"order_lName", headerHozAlign:"center",},
	{title:"규격1", field:"order_STND_1", headerHozAlign:"center"},
 	{title:"수량", field:"order_lQty", headerHozAlign:"center", hozAlign:"right", editor: MOL_InputEditor, 
		formatter : "money", formatterParams: {precision: false},
		cellEdited:function(cell){
		//수량이 변경될때 금액값이 계산되어 입력
		temQty = cell.getValue();
		temUP = cell.getRow().getData().order_lUnit_Price;
		if(temQty*temUP>0){
			iPrice = temQty*temUP	
		}else{
			iPrice = 0;	
		}
		cell.getRow().update({"order_lPrice": iPrice});
		}
	},
 	{title:"단가", field:"order_lUnit_Price", headerHozAlign:"center", hozAlign:"right", editor: MOL_InputEditor, 
		topCalc:function(){return "합계금액"}, formatter : "money", formatterParams: {precision: false},
		cellEdited:function(cell){
		//단가가 변경될때 금액값이 계산되어 입력
		temQty = cell.getRow().getData().order_lQty;
		temUP = cell.getValue();			

		if(temQty*temUP>0){
			iPrice = temQty*temUP	
		}else{
			iPrice = 0;	
		}
		cell.getRow().update({"order_lPrice": iPrice});
		}
	},
 	{title:"금액", field:"order_lPrice", headerHozAlign:"center", hozAlign:"right", formatter : "money", formatterParams: {precision: false}, width: 80,
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
		if(matOrderTable.getDataCount("selected") != 0){
			matOrderTable.getRows('selected')[0].update({"order_mTotal": calc});
		}
    	return calc;
	}, topCalcFormatter : "money", topCalcFormatterParams: {precision: false}
	
	},
	{title:"미입고재고", field:"order_lNot_Stocked", visible:false},
 	{title:"비고", field:"order_lInfo_Remark", headerHozAlign:"center", editor: MOL_InputEditor},
	{title:"구분", field:"order_Rcv_Clsfc", headerHozAlign:"center",/* editor:"select",*/
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
	}
 	]
});

//팝업창으로부터 특정 파라미터 값으로 데이터를 받는다 
function item_gridInit(PCode,PName,PSTND_1,PPrice) {
	cellPos.getRow().update({
		"order_lCode" : PCode, 
		"order_lName" : PName, 
		"order_STND_1" : PSTND_1, 
		"order_lUnit_Price" : PPrice
		});
	//선택 후 포커스 이동
	cellPos.getElement().focus();
}

//OrderSub 목록검색
function MOL_Search(order_lCus_No){
	$("#order_lCus_No").val(order_lCus_No)
	//발주넘버
	$.ajax({
		method : "GET",
		url : "matOrderLXRest/MOL_Search?order_lCus_No="+ order_lCus_No,
		success : function(datas) {
			console.log(datas)
			matOrderSubTable.setData(datas);
		}
	});
}

//행추가버튼
function MOL_Add(){
	//master 납기일자 입력했는지 확인
	if(matOrderTable.getData("selected")[0].order_mDlvry_Date.length != "10"){
		alert("납기일자를 잘못 입력하였습니다.")
		return false;
	}

	//목록의 제품명과 합계금액을 검사하여 입력하지 않았을 경우 추가 안됨
	for(i=0;i<matOrderSubTable.getDataCount("active");i++){
		rowData = matOrderSubTable.getData()[i];
		
		if(rowData.order_lPrice == 0 || rowData.order_lName == ''){
			alert("작성중인 행이 있습니다.");
			return false;
		}
	}

	matOrderSubTable.addRow();
	matOrderSubTable.setPage(Math.ceil(matOrderSubTable.getDataCount("active")/matOrderSubTable.getPageSize()));
}

//MOL_addBtn
$('#MOL_AddBtn').click(function(){
	MOL_Add();
})

//삭제버튼
function MOL_Delete(){
	selectedData = matOrderSubTable.getSelectedData();
	realData = []
	// 기존 조회한 데이터는 수주번호가 있고, 새로 추가된데이터는 수주번호가 없다
	if(confirm("선택한 행이 삭제됩니다. 삭제하시겠습니까?")){
		//list데이터에서 수주번호가 있는것만 따로 배열에 담아서 쿼리로 실행한다.
		for(i=0;i<selectedData.length;i++){
			if(selectedData[i].order_lCus_No != null){
				realData.push(selectedData[i]);
			}
		}
		//배열에 담은 데이터가 있을경우 쿼리 실행
		if(realData.length != 0){
			$.ajax({
				method: "post",
				url: "matOrderLXRest/MOL_Delete?data=" + encodeURI(JSON.stringify(realData)),
				success: function(result) {
					console.log(result);
					if (result == "error") {
						alert("삭제 오류")
					}
				}
			})
		}
		// 행삭제
		matOrderSubTable.deleteRow(matOrderSubTable.getSelectedRows())
		.then(function(){
			// 삭제후 순번 정리
			rowCount = matOrderSubTable.getDataCount("active");
			for(i=0;i<rowCount;i++){
				matOrderSubTable.getRows()[i].update({order_lNo:i+1})
			}
		});
	}
}

//MOL_DeleteBtn
$('#MOL_DeleteBtn').click(function(){
	MOL_Delete();
})

//OrderSub 저장
function MOL_Save(){
	selectedRow = matOrderTable.getData("selected")[0];
	rowCount = matOrderSubTable.getDataCount("active");
	
	//목록의 마지막 데이터를 확인하고 금액이 0이면 행을 삭제하고 저장한다. 
	if(matOrderSubTable.getData()[rowCount-1].order_lPrice == 0){
		matOrderSubTable.deleteRow(matOrderSubTable.getRows()[rowCount-1]);
	}
	
	//만약 선택한행의 합계금액이 비어있을경우 저장 안됨
	if(selectedRow.order_mTotal == 0){
		alert("작성중인 목록이 있습니다.");
		return false;
	}
	
	if(item_Code_Check()){
		alert("중복된 품목이 있습니다.");
		return false;
	}
	
	//OrderSub 저장부분
	$.ajax({
		method: "get",
		url: "matOrderLXRest/MOL_Save?masterData=" + encodeURI(JSON.stringify(selectedRow))
										+"&listData=" + encodeURI(JSON.stringify(matOrderSubTable.getData())),
		success: function(result) {
			if (result == "error") {
				alert("빈칸이 있어서 저장할 수 없습니다.")
			} else {
				alert("저장되었습니다.");
				$("#order_lCus_No").val(result);
				MO_Search();
				Cus_No_select();
			}
		}
	});
}

$('#MOL_SaveBtn').click(function(){
	MOL_Save();
	}
)

//발주번호로 OrderMaster 선택하는 코드
function Cus_No_select(){
	rowCount = matOrderTable.getDataCount("active");
	//컬럼값을 검색해서 입력값을 포함하는 값이 있으면 선택한다. 
	if(rowCount > 0){
		for(i=0;i<rowCount;i++){
			Cus_No = matOrderTable.getColumn("order_mCus_No").getCells();
			//발주번호가 입력내용을 포함하면 코드 실행
			if(Cus_No[i].getValue() == $('#order_lCus_No').val()){
				//발주번호가 같은 행 선택
				Cus_No[i].getRow().select();
				break;
			}					
		}
	}
}

//list에서 같은 품목을 추가할때 경고 알리고 추가안됨
function item_Code_Check() {
	rowCount = matOrderSubTable.getDataCount("active");
	
	itemCode = matOrderSubTable.getColumn("order_lCode").getCells();
	//컬럼값을 검색해서 입력값을 포함하는 값이 있으면 선택한다.
	for (i=0;i<rowCount-1;i++) {
		count = 0;
		for(j=i+1;j<rowCount;j++){
			if(itemCode[i].getValue() == itemCode[j].getValue()){
				count++
				//중복일경우
				if(count>0){
					itemCode[i].getRow().select();
					itemCode[j].getRow().select();
					return true;
				}	
			}
		}
	}
	return false;
}

var matOrderStockTable = new Tabulator("#matOrderStockTable", {
	selectable:1,
	height:"10%",
	layoutColumnsOnNewData : true,
	//복사하여 엑셀 붙여넣기 가능
	clipboard: true,
 	columns:[
 	{title:"제품코드", field:"sm_Code", headerHozAlign:"center"},
 	{title:"제품명", field:"sm_Name", headerHozAlign:"center"},
	{title:"규격1", field:"sm_STND_1", headerHozAlign:"center"},
 	{title:"수량", field:"sm_Qty", headerHozAlign:"center", hozAlign:"right", formatter:"money", formatterParams: {precision: false}}
 	]
});

//orderStock 목록검색
function MOS_Search(order_lCode){
	//발주넘버
	$.ajax({
		method : "GET",
		url : "matOrderLXRest/MOS_Search?order_lCode="+ order_lCode,
		success : function(datas) {
			matOrderStockTable.setData(datas);
		}
	});
}