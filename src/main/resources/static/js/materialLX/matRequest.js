//matRequest 커스텀 기능설정
var MR_inputEditor = function(cell, onRendered, success, cancel, editorParams){
    //cell - 편집 가능한 셀의 셀 구성 요소
    //onRendered - 에디터가 렌더링 되었을 때 호출 할 함수
    //success - 성공적을 업데이트 된 값을 tabulator에 전달하기 위해 호출되는 함수
    //cancel - 편집을 중단하고 일반 셀로 돌아 가기 위해 호출하는 함수
    //editorParams - editorParams 열 정의 속성에 전달 된 params 객체

    //create 및 style editor
    var MR_input = document.createElement("input");

    MR_input.setAttribute("type", "text");

    //입력 생성 및 스타일 지정
    MR_input.style.padding = "3px";
    MR_input.style.width = "100%";
    MR_input.style.boxSizing = "border-box";

    //편집기의 값을 셀의 현재 값으로 설정
	if(cell.getValue() == undefined){
		MR_input.value = "";
	}else{
		MR_input.value = cell.getValue();
	}

    //에디터가 선택되면 선택 상자에 포커스 설정 (타임 아웃은 편집기를 DOM에 추가 할 수 있음)
    onRendered(function(){
        MR_input.focus();
		MR_input.select();
        MR_input.style.css = "100%";
    });

    //값이 설정되면 업데이트 할 셀 트리거
    function onChange(){
        success(MR_input.value);
    }
    
	//바꼈을때 블러됫을때 함수 발동
    MR_input.addEventListener("change", onChange);
    MR_input.addEventListener("blur", onChange);

    //키버튼 이벤트
    MR_input.addEventListener("keydown", function (e) {
		if(e.keyCode == 13){
			//특이사항셀 체크
			if (cell.getField() == "request_mRemarks") {
				//특이사항에서 엔터를 하면 행추가
				matRequestSubTable.addRow();			
			}
		}
    });
    //반환
    return MR_input;
};
//셀위치저장
var cellPos = null;

var matRequestTable = new Tabulator("#matRequestTable", {
	//페이징
	pagination:"local",
	paginationSize:20,
	paginationAddRow : "table",
    height:"calc(100% - 175px)",
	headerFilterPlaceholder: null,
	layoutColumnsOnNewData : true,
	//복사하여 엑셀 붙여넣기 가능
	clipboard: true,
	//커스텀 키 설정
	keybindings:{
        "navNext" : "13"
    },
	rowFormatter:function(row){
		//request_mCheck가 Y면 빨간색 I면 파란색으로 바꿔준다
        if(row.getData().request_mCheck == "Y"){
            row.getElement().style.color = "red";
        }else if(row.getData().request_mCheck == "I"){
            row.getElement().style.color = "blue";
		}
    },
	//행클릭 이벤트
	rowClick:function(e, row){
		row.getTable().deselectRow();
		row.select();
    },
	rowSelected:function(row){
    	MRL_Search(row.getData().request_mReqNo);
		
		//버튼 설정
		ResetBtn()
		if(row.getData().request_mCheck != 'I'){
			UseBtn();
		}
    },
	//행추가시 기능
	rowAdded : function ( row ) {
	row.getTable().deselectRow();
	row.select();

	row.update({"request_mReqNo" : '',
				"request_mUser" : $("#User_Code").val(),
				"user_Name" : $("#User_Name").val(),
				"dept_CODE" : $("#Dept_Code").val(),
				"dept_NAME" : $("#Dept_Name").val(),
				"request_mDate" : moment(new Date()).format('YYYY-MM-DD HH:mm:ss')
			});

	//페이지 이동(전체 행 수/페이지당 행 수)
	matRequestTable.setPage(Math.ceil(matRequestTable.getDataCount("active") / matRequestTable.getPageSize()));

	//MO_Add버튼 비활성화 (작성중인 행이 있다면 추가못하게함)
	if (!$('#MR_AddBtn').hasClass('unUseBtn')){
		$('#MR_AddBtn').addClass('unUseBtn');
	}

	UseBtn();
	//list와 stock의 데이터를 없에준다
	matRequestSubTable.clearData();
	matRequestStockTable.clearData();
	
	//행이 추가되면 첫셀에 포커스
	do{
	setTimeout(function(){
		row.getCell("request_mRemarks").edit();
		},100);
	}
	while(row.getData().request_mRemarks === "undefined");
	},
	cellEditing:function(cell){
        //셀위치 저장하여 포커싱부여
		cellPos = cell;
    },
 	columns:[ 
 		{title:"요청No", field:"request_mReqNo", headerHozAlign:"center", hozAlign:"right", headerFilter:true},
		{title:"요청자코드", field:"request_mUser", visible:false},
 		{title:"요청자", field:"user_Name", headerHozAlign:"center", headerFilter:true},
		{title:"부서코드", field:"dept_CODE", visible:false},
		{title:"부서", field:"dept_NAME", headerHozAlign:"center", headerFilter:true},
 		{title:"요청일", field:"request_mDate", headerHozAlign:"center", hozAlign:"right", headerFilter:true},
 		{title:"특이사항", field:"request_mRemarks", headerHozAlign:"center", editor: MR_inputEditor, headerFilter:true},
 		{title:"수정자", field:"request_mModifier", headerHozAlign:"center", headerFilter:true},
 		{title:"수정일자", field:"request_mModify_Date", headerHozAlign:"center",  headerFilter:true},
 		{title:"목록확인", field:"request_mCheck", visible:false},
 	],
});

//MR_AddBtn
$('#MR_AddBtn').click(function(){
	//행추가
	matRequestTable.addRow();
})

//orderMaster 목록검색
function MR_Search(){
	data = {
		startDate : $("#startDate").val(),
		endDate : $("#endDate").val(),
		request_mCode : $("#inMat_Client_Code").val()
	}
	$.ajax({
		method : "GET",
		async: false,
		url : "matRequestRest/MR_Search?data="+ encodeURI(JSON.stringify(data)),
		success : function(result) {
			console.log(result);
			matRequestTable.setData(result);
			
			//list와 stock의 데이터를 없에준다
			matRequestSubTable.clearData();
			matRequestStockTable.clearData();
			ResetBtn()
			//MR_ADD버튼 활성화
			if($('#MR_AddBtn').hasClass('unusebtn')){
				$('#MR_AddBtn').removeClass('unusebtn');				
			}
		}
	});
}

//SO_SearchBtn
$('#MR_SearchBtn').click(function(){
	MR_Search();
})

//matRequestSub 커스텀 기능설정
var MRL_InputEditor = function(cell, onRendered, success, cancel, editorParams){
    //cell - 편집 가능한 셀의 셀 구성 요소
    //onRendered - 에디터가 렌더링 되었을 때 호출 할 함수
    //success - 성공적을 업데이트 된 값을 tabulator에 전달하기 위해 호출되는 함수
    //cancel - 편집을 중단하고 일반 셀로 돌아 가기 위해 호출하는 함수
    //editorParams - editorParams 열 정의 속성에 전달 된 params 객체

    //create 및 style editor
    var MRL_input = document.createElement("input");

    MRL_input.setAttribute("type", "text");

    //입력 생성 및 스타일 지정
    MRL_input.style.padding = "3px";
    MRL_input.style.width = "100%";
    MRL_input.style.boxSizing = "border-box";

    //편집기의 값을 셀의 현재 값으로 설정
	if(cell.getValue() == undefined){
		MRL_input.value = "";
	}else{
		MRL_input.value = cell.getValue();
	}

    //에디터가 선택되면 선택 상자에 포커스 설정 (타임 아웃은 편집기를 DOM에 추가 할 수 있음)
    onRendered(function(){
        MRL_input.focus();
		MRL_input.select();
        MRL_input.style.css = "100%";
    });

    //값이 설정되면 업데이트 할 셀 트리거
    function onChange(){
        success(MRL_input.value);
    }
    
	//바꼈을때 블러됫을때 함수 발동
    MRL_input.addEventListener("change", onChange);
    MRL_input.addEventListener("blur", onChange);

    //키버튼 이벤트
    MRL_input.addEventListener("keydown", function (e) {
		//품목코드 팝업창
		if(e.keyCode == 13){
			//코드셀체크
			if (cell.getField() == "request_lCode") {
				//내용이 있을경우 검색해서 값이 하나일경우 생략, 아닐경우 팝업창
				//쿼리실행
				$.ajax({
				method : "GET",
				url : "product_check?PRODUCT_ITEM_CODE=" + MRL_input.value,
				dataType : "json",
				success : function(data) {
					console.log(data);
					if(data.length == 1){
						//검색어와 일치하는값이 있는경우
						cell.getRow().update({
							"request_lCode" : data[0].product_ITEM_CODE,
							"request_lName" : data[0].product_ITEM_NAME,
							});
					}else{
						//검색어와 일치하는값이 없는경우, 팝업창
						itemPopup(MRL_input.value,'grid','','material');
					}
				}
				})
	        }
			//비고셀 체크
			if (cell.getField() == "request_lInfo_Remark") {
				//구분넘기기
				cell.nav().next();
				//만약 마지막행의 수량이 비어있을경우 추가 안됨
				lastRow = matRequestSubTable.getData()[matRequestSubTable.getDataCount("active")-1];
				if(!(lastRow.request_lQty > 0)){
					alert("수량을 입력해주세요.");
					cell.nav().prev();		
				}else if(lastRow.request_lCode.length != "6"){
					alert("제품코드를 잘못 입력하였습니다.")
					cell.nav().prev();
				}
			}
		}
    });
    //반환
    return MRL_input;
};

// 출고구분 select를 구성하기위한 ajax
var dtl_arr = new Object();

$.ajax({
	method : "GET",
	async: false,
	url : "dtl_tbl_select?NEW_TBL_CODE=18",
	success : function(datas) {
		for(i=0;i<datas.length;i++){
			dtl_arr[datas[i].child_TBL_NO] = datas[i].child_TBL_TYPE;
		}
	}
});

//salesOrderSubTable 이미 저장되있는 데이터는 편집 불가능 하게 하는 확인 기능
var editCheck = function(cell){
    //cell - the cell component for the editable cell
    //get row data
    var data = cell.getRow().getData();
    return data.request_lReqNo == '';
}

var matRequestSubTable = new Tabulator("#matRequestSubTable", {
	//페이징
	pagination:"local",
	paginationSize:20,
	paginationAddRow : "table",
	layoutColumnsOnNewData : true,
	selectable: true,
	height:"calc(90% - 175px)",
	//복사하여 엑셀 붙여넣기 가능
	clipboard: true,
	tabEndNewRow: true,
	//커스텀 키 설정
	keybindings:{
        "navNext" : "13"
    },
	//행을 클릭하면 matRequestStockTable에 리스트가 나타남
	rowClick:function(e, row){
		MRS_Search(row.getData().request_lCode);
    },
	//행이 추가될때마다 인덱스 부여
	rowAdded : function ( row ) {
		
	row.update({"request_lNo": matRequestSubTable.getDataCount("active"),
				"request_lReqNo": '',
				"request_lQty": 0,
				"request_Send_Clsfc": "181"});
				
	//행이 추가되면 첫셀에 포커스
	do{
	setTimeout(function(){
		row.getCell("request_lCode").edit();
		},100);
	}
	while(row.getData().request_lCode === "undefined");

	},
	 cellEditing:function(cell){
        //셀위치 저장하여 포커싱부여
		cellPos = cell;
		//편집하는 행의 품목에 대한 재고가 테이블에 나타남
		var cell_lCode = null;
		if(cell.getRow().getData().request_lCode != cell_lCode){
			if(cell.getRow().getData().request_lCode.length == 6){
				MRS_Search(cell.getRow().getData().request_lCode);
				
				cell_lCode = cell.getRow().getData().request_lCode	
			}
		}
    },
 	columns:[
	{formatter:"rowSelection", titleFormatter:"rowSelection", headerHozAlign:"center", hozAlign:"center", headerSort:false},
	{title:"순번", field:"request_lNo", headerHozAlign:"center", hozAlign:"center"},
	{title:"요청No", field:"request_lReqNo", visible:false},
 	{title:"코드", field:"request_lCode", headerHozAlign:"center", editor: MRL_InputEditor, editable:editCheck},
 	{title:"품목명", field:"request_lName", headerHozAlign:"center"},
 	{title:"수량", field:"request_lQty", headerHozAlign:"center", hozAlign:"right", editor: MRL_InputEditor},
	{title:"비고", field:"request_lInfo_Remark", headerHozAlign:"center", editor: MRL_InputEditor},
	{title:"구분", field:"request_Send_Clsfc", headerHozAlign:"center", editor:"select", width:64,
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
	}]
});

//팝업창으로부터 특정 파라미터 값으로 데이터를 받는다 
function item_gridInit(PCode,PName,PSTND_1,PPrice) {
	cellPos.getRow().update({
		"request_lCode" : PCode, 
		"request_lName" : PName,
		});
	//선택 후 포커스 이동
	cellPos.getElement().focus();
}

//OrderSub 목록검색
function MRL_Search(request_mReqNo){
	$("#request_lReqNo").val(request_mReqNo)
	//발주넘버
	$.ajax({
		method : "GET",
		url : "matRequestRest/MRL_Search?request_mReqNo="+ request_mReqNo,
		success : function(result) {
			matRequestSubTable.setData(result);
		}
	});
}

//행추가버튼
function MRL_Add(){
	//목록의 제품명과 합계금액을 검사하여 입력하지 않았을 경우 추가 안됨
	for(i=0;i<matRequestSubTable.getDataCount("active");i++){
		rowData = matRequestSubTable.getData()[i];
		
		if(rowData.request_lQty == 0 || rowData.request_lName == ''){
			alert("작성중인 행이 있습니다.");
			return false;
		}
	}
	matRequestSubTable.addRow();
	matRequestSubTable.setPage(Math.ceil(matRequestSubTable.getDataCount("active")/matRequestSubTable.getPageSize()));
}

//MRL_AddBtn
$('#MRL_AddBtn').click(function(){
	MRL_Add();
})

//삭제버튼
function MRL_Delete(){
	selectedData = matRequestSubTable.getSelectedData();
	realData = []
	
	// 기존 조회한 데이터는 요청번호가 있고, 새로 추가된데이터는 요청번호가 없다
	if(confirm("선택한 행이 삭제됩니다. 삭제하시겠습니까?")){
		//list데이터에서 수주번호가 있는것만 따로 배열에 담아서 쿼리로 실행한다.
		for(i=0;i<selectedData.length;i++){
			if(selectedData[i].request_lReqNo != null){
				realData.push(selectedData[i]);
			}
		}
		//배열에 담은 데이터가 있을경우 쿼리 실행
		if(realData.length != 0){
			$.ajax({
				method: "post",
				url: "matRequestRest/MRL_Delete?data=" + encodeURI(JSON.stringify(realData)),
				success: function(result) {
					console.log(result);
					if (result == "error") {
						alert("삭제 오류")
					}
				}
			})
		}
		// 행삭제
		matRequestSubTable.deleteRow(matRequestSubTable.getSelectedRows())
		.then(function(){
			// 삭제후 순번 정리
			rowCount = matRequestSubTable.getDataCount("active");
			for(i=0;i<rowCount;i++){
				matRequestSubTable.getRows()[i].update({request_lNo:i+1})
			}
			//sub에 행이 없을경우 master도 삭제함
			if(!rowCount){
				matRequestTable.deleteRow(matRequestTable.getSelectedRows())
				ResetBtn()
			}
		});		
	}
}

//MRL_saveBtn
$('#MRL_DeleteBtn').click(function(){
	MRL_Delete();
})

//OrderSub 저장
function MRL_Save(){
	selectedRow = matRequestTable.getData("selected")[0];
	rowCount = matRequestSubTable.getDataCount("active");
	
	//목록의 마지막 데이터를 확인하고 수량이 0이면 행을 삭제하고 저장한다.
	if(matRequestSubTable.getData()[rowCount-1].request_lQty == 0){
		matRequestSubTable.deleteRow(matRequestSubTable.getRows()[rowCount-1]);
	}
	
	if(item_Code_Check()){
		alert("중복된 품목이 있습니다.");
		return false;
	}
	
	//OrderSub 저장부분
	$.ajax({
		method : "post",
		url : "matRequestRest/MRL_Save?masterData=" + encodeURI(JSON.stringify(selectedRow))
										+"&listData=" + encodeURI(JSON.stringify(matRequestSubTable.getData())),
		success : function(result) {
			if(result == "error"){
				alert("빈칸이 있어서 저장할 수 없습니다.")
			}else{
				alert("저장되었습니다.");
				$("#request_lReqNo").val(result)
				MR_Search();
				ReqNo_select();
			}		
		}
	});
	
	if($('#MR_AddBtn').hasClass('unusebtn')){
		$('#MR_AddBtn').removeClass('unusebtn');				
	}
}

//MRL_saveBtn
$('#MRL_SaveBtn').click(function(){
	MRL_Save();
})

//발주번호로 matRequestTable 선택하는 코드
function ReqNo_select(){
	rowCount = matRequestTable.getDataCount("active");
	//컬럼값을 검색해서 입력값을 포함하는 값이 있으면 선택한다.
	for(i=0;i<rowCount;i++){
		Cus_No = matRequestTable.getColumn("request_mReqNo").getCells();
		//발주번호가 입력내용을 포함하면 코드 실행
		if(Cus_No[i].getValue() == $('#request_lReqNo').val()){
			//발주번호가 같은 행 선택
			Cus_No[i].getRow().select();
			break;
		}					
	}
}

//list에서 같은 품목을 추가할때 경고 알리고 추가안됨
function item_Code_Check() {
	rowCount = matRequestSubTable.getDataCount("active");
	
	itemCode = matRequestSubTable.getColumn("request_lCode").getCells();
	//컬럼값을 검색해서 입력값을 포함하는 값이 있으면 선택한다.
	for (i=0;i<rowCount-1;i++) {
		count = 0;
		for(j=i+1;j<rowCount;j++){
			if(itemCode[i].getValue() == itemCode[j].getValue()){
				count++
				//중복일경우
				if(count>0){
					matRequestSubTable.selectRow(itemCode[i].getRow())
					matRequestSubTable.selectRow(itemCode[j].getRow())
					return true;
				}	
			}
		}
	}
	return false;
}

var matRequestStockTable = new Tabulator("#matRequestStockTable", {
	selectable:1,
	height:"10%",
	layoutColumnsOnNewData : true,
	//복사하여 엑셀 붙여넣기 가능
	clipboard: true,
 	columns:[
 	{title:"품목코드", field:"sm_Code", headerHozAlign:"center"},
 	{title:"품목명", field:"sm_Name", headerHozAlign:"center"},
	{title:"규격1", field:"sm_STND_1", headerHozAlign:"center"},
 	{title:"수량", field:"sm_Qty", headerHozAlign:"center", hozAlign:"right", formatter:"money", formatterParams: {precision: false}}
 	]
});

//orderStock 목록검색
function MRS_Search(request_lCode){
	//발주넘버
	$.ajax({
		method : "GET",
		url : "matRequestRest/MRS_Search?request_lCode="+ request_lCode,
		success : function(datas) {
			matRequestStockTable.setData(datas);
		}
	});
}