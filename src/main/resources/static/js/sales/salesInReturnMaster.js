//커스텀 기능설정
var SIRI_InputEditor = function(cell, onRendered, success, cancel, editorParams){
    //cell - 편집 가능한 셀의 셀 구성 요소
    //onRendered - 에디터가 렌더링 되었을 때 호출 할 함수
    //success - 성공적을 업데이트 된 값을 tabulator에 전달하기 위해 호출되는 함수
    //cancel - 편집을 중단하고 일반 셀로 돌아 가기 위해 호출하는 함수
    //editorParams - editorParams 열 정의 속성에 전달 된 params 객체

    //create 및 style editor
    var SIRI_input = document.createElement("input");

    SIRI_input.setAttribute("type", "text");

    //입력 생성 및 스타일 지정
    SIRI_input.style.padding = "3px";
    SIRI_input.style.width = "100%";
    SIRI_input.style.boxSizing = "border-box";

    //편집기의 값을 셀의 현재 값으로 설정
	if(cell.getValue() == undefined){
		SIRI_input.value = "";
	}else{
		SIRI_input.value = cell.getValue();
	}

    //에디터가 선택되면 선택 상자에 포커스 설정 (타임 아웃은 편집기를 DOM에 추가 할 수 있음)
    onRendered(function(){
        SIRI_input.focus();
		SIRI_input.select();
        SIRI_input.style.css = "100%";
    });

    //값이 설정되면 업데이트 할 셀 트리거
    function onChange(){
		success(SIRI_input.value);   
    }
    
	//바꼈을때 블러됫을때 함수 발동
    SIRI_input.addEventListener("change", onChange);
	SIRI_input.addEventListener("blur", onChange);

    //키버튼 이벤트
    SIRI_input.addEventListener("keydown", function (e) {
		//엔터쳤을떄
		if(e.keyCode == 13){
			//반품수량셀 채크
			if (cell.getField() == "sales_InReturn_Qty") {
				console.log(cell.getValue());
				//입력값이 양의 정수가 아니거나 입고수량보다 높으면 안내문
				if(!(SIRI_input.value >= 0) || cell.getRow().getData().sales_InMat_Qty < SIRI_input.value){
					alert("반품 수량을 잘못입력하였습니다.");
					SIRI_input.value = 0;
				}
				//입력값이 0이 아닌경우 (반품수량을 입력했을경우) 셀 선택
				if(SIRI_input.value != 0){
					cell.getRow().select();
				}
			//다음셀로
			cell.nav().next();
			}
		}
    });
    //반환
    return SIRI_input;
};

var salesInReturnInsertTable = new Tabulator("#salesInReturnInsertTable", {
	//페이징
	pagination: "local",
	paginationSize: 20,
	height: "calc(100% - 175px)",
	//복사하여 엑셀 붙여넣기 가능
	clipboard: true,
	rowDblClick: function(e, row) {
		row.toggleSelect();
	},
	columns: [
		{formatter: "rowSelection", titleFormatter: "rowSelection", headerHozAlign: "center", hozAlign: "center", headerSort: false},
		{title:"순번", field:"rownum", formatter:"rownum", headerHozAlign:"center", align:"center" },
		{title:"Lot번호", field:"sales_InMat_Lot_No", headerHozAlign:"center", hozAlign:"left"},
		{title:"품목코드", field:"sales_InMat_Code", headerHozAlign:"center" },
		{title:"품목명", field:"sales_InMat_Name", headerHozAlign:"center" },
		{title:"규격", field:"sales_InMat_STND_1", headerHozAlign:"center", hozAlign:"left"},
		{title:"분류1", field:"sales_InMat_STND_1", headerHozAlign:"center", hozAlign:"left"},
		{title:"단위", field:"sales_InMat_UNIT", headerHozAlign:"center", hozAlign:"left" },
		{title:"입고수량", field:"sales_InMat_Qty", align:"right", headerHozAlign:"center" },
		{title:"반품수량", field:"sales_InReturn_Qty", align:"right", editor:SIRI_InputEditor, headerHozAlign:"center"},
		{title:"입고구분", field:"sales_InMat_Rcv_Clsfc_Name", headerHozAlign:"center" },
		{title:"입고일", field:"sales_InMat_Date", headerHozAlign:"center" },
	]
});

// fgoodsInReturn 목록 검색
function SIRI_Search() {
	data = {
		sales_InMat_Code: $('#PRODUCT_ITEM_CODE1').val()
	}

	$.ajax({
		method: "GET",
		dataType: "json",
		async: false,
		url: "salesInReturnLXRest/SIRI_Search?data=" + encodeURI(JSON.stringify(data)),
		success: function(datas) {
			console.log(datas);
			salesInReturnInsertTable.setData(datas);
		}
	});
}

//SIRI_Save
function SIRI_Save() {
	
	selectedData = salesInReturnInsertTable.getData("selected");
	realData = [];
	
	// 선택한 행이 있을경우에 저장이 가능하다.
	//만약 선택된행에서 반품수량이 0 이면 리스트에서 제외
	for(let i=0;i<selectedData.length;i++){
		if(selectedData[i].sales_InReturn_Qty != 0){
			realData.push(selectedData[i]);
		}
	}
	if(realData.length == 0){
		alert("저장할 목록이 없습니다.");
		return false;
	}
	
	$.ajax({
		method : "get",
		url : "salesInReturnLXRest/SIRI_Save?data="+ encodeURI(JSON.stringify(realData)),
		success : function(datas) {
			if(datas == "error"){
				alert("중복된 값이 있습니다.");
			}else if(datas == "success"){
				SIRI_Search();
				alert("저장되었습니다.");
			}
		}				
	});
}

var salesInReturnSearchTable = new Tabulator("#salesInReturnSearchTable", {
	//페이징
	pagination: "local",
	paginationSize: 20,
	height: "calc(100% - 175px)",
	//복사하여 엑셀 붙여넣기 가능
	clipboard: true,
	columns: [
		{title:"순번", field:"rownum", formatter:"rownum", headerHozAlign:"center", align:"center" },
		{title:"Lot번호", field:"sales_InMat_Lot_No", headerHozAlign:"center", hozAlign:"left"},
		{title:"품목코드", field:"sales_InMat_Code", headerHozAlign:"center" },
		{title:"품목명", field:"sales_InMat_Name", headerHozAlign:"center" },
		{title:"규격", field:"sales_InMat_STND_1", headerHozAlign:"center", hozAlign:"left"},
		{title:"분류1", field:"sales_InMat_STND_1", headerHozAlign:"center", hozAlign:"left"},
		{title:"단위", field:"sales_InMat_UNIT", headerHozAlign:"center", hozAlign:"left" },
		{title:"반품수량", field:"sales_InReturn_Qty", align:"right", headerHozAlign:"center" },
		{title:"단가", field:"sales_InMat_Unit_Price", align:"right", headerHozAlign:"center", formatter :"money", formatterParams: { precision: false } },
		{title:"금액", field:"sales_InMat_Price", align:"right", headerHozAlign:"center", formatter :"money", formatterParams: { precision: false } },
		{title:"입고구분", field:"sales_InMat_Rcv_Clsfc_Name", headerHozAlign:"center" },
		{title:"입고일", field:"sales_InMat_Date", headerHozAlign:"center"},
		{title:"반품일", field:"sales_InMat_dInsert_Time", headerHozAlign:"center"},
		{title:"작업자명", field:"sales_InMat_Modifier", headerHozAlign:"center" }
	]
});

// fgoodsInReturn 목록 검색
function SIRS_Search() {
	data = {
		startDate: $("#SIRS_startDate").val(),
		endDate: $("#SIRS_endDate").val(),
		sales_InMat_Code: $('#PRODUCT_ITEM_CODE2').val()
	}

	$.ajax({
		method: "GET",
		dataType: "json",
		async: false,
		url: "salesInReturnLXRest/SIRS_Search?data=" + encodeURI(JSON.stringify(data)),
		success: function(datas) {
			console.log(datas);
			salesInReturnSearchTable.setData(datas);
		}
	});
}