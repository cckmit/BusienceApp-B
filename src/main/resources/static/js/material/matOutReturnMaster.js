//커스텀 기능설정
var MORI_InputEditor = function(cell, onRendered, success, cancel, editorParams){
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
			//이동수량셀 채크
			if (cell.getField() == "lm_ReturnQty") {
				//입력값이 음수이거나 입고수량보다 높으면 안내문
				if(!(input.value >= 0) || cell.getRow().getData().lm_Qty < input.value){
					alert("이동 수량을 잘못입력하였습니다.");
					input.value = 0;
				}
				//입력값이 0이 아닌경우 (이동수량을 입력했을경우) 셀 선택
				if(input.value != 0){
					cell.getRow().select();
				}
			//다음셀로
			cell.nav().next();
			}
		}
    });
    //반환
    return input;
};

var matOutReturnInsertTable = new Tabulator("#matOutReturnInsertTable", {
	height: "calc(100% - 175px)",
	placeholder:"이동 가능한 자재가 없습니다.",
	rowDblClick: function(e, row) {
		row.toggleSelect();
	},
	ajaxConfig : "get",
	ajaxContentType:"json",
	ajaxURL : "/matOutReturnRest/stockTransSelect",
	ajaxParams : {lotNo: $(".lotNo").val()},
	columns: [
		{ formatter: "rowSelection", align: "center"},
		{ title: "순번", field: "rownum", headerHozAlign: "center", align: "center", formatter: "rownum"},
		{ title: "Lot번호", field: "lm_LotNo", headerHozAlign: "center" },
		{ title: "품목코드", field: "lm_ItemCode", headerHozAlign: "center" },
		{ title: "품목명", field: "lm_ItemName", headerHozAlign: "center" },
		{ title: "출고수량", field: "lm_Qty", align: "right", headerHozAlign: "center" },
		{ title: "보관창고", field: "lm_Warehouse_Name", headerHozAlign: "center"},
		{ title: "이동수량", field: "lm_TransQty", align: "right", editor: MORI_InputEditor, headerHozAlign: "center"},
		{ title: "이동장소", field: "lm_After", headerHozAlign: "center", formatter: function(cell){return "자재창고"}}
	]
});

$("#MORI_Search").keypress(function(e){
	if(e.keyCode == '13'){
		MORI_Search()	
	}
})

// matOutReturn 목록 검색
function MORI_Search() {
	matOutReturnInsertTable.setData("matOutReturnRest/stockTransSelect", {lotNo: $(".lotNo").val()});
}

function MORI_Save() {
	//선택된 행만 저장
	//만약 선택된행에서 이동수량이 0 이면 저장 안함
	var selectedData = matOutReturnInsertTable.getData("selected");

	if (selectedData.length == 0) {
		alert("저장할 목록이 없습니다. 행을 선택해 주세요.");
		return;
	}

	for (var i = 0; i < selectedData.length; i++) {
		if (selectedData[i].outReturn_Qty == "0") {
			alert("이동수량이 입력되지 않은 행이 존재합니다.");
			return;
		}
	}
	$.ajax({
		method: "post",
		url: "matOutReturnRest/MORI_Save",
		data: JSON.stringify(selectedData),
		contentType:'application/json',
		beforeSend: function (xhr) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           xhr.setRequestHeader(header, token);
		},
		success: function(result) {
			if (result) {
				MORI_Search();
				alert("이동 처리 되었습니다.");
			} else {
				alert("중복된 값이 있습니다.");
			}
		}
	});
}

var matOutReturnSearchTable = new Tabulator("#matOutReturnSearchTable", {
	height: "calc(100% - 175px)",
	//복사하여 엑셀 붙여넣기 가능
	clipboard: true,
	rowDblClick: function(e, row) {
		row.toggleSelect();
	},
	columns: [
		{ title: "순번", field: "rownum", headerHozAlign: "center", align: "center", formatter: "rownum"},
		{ title: "Lot번호", field: "t_LotNo", headerHozAlign: "center" },
		{ title: "품목코드", field: "t_ItemCode", headerHozAlign: "center" },
		{ title: "품목명", field: "t_ItemName", headerHozAlign: "center" },
		{ title: "규격1", field: "t_Item_Stnd_1", headerHozAlign: "center" },
		{ title: "규격2", field: "t_Item_Stnd_2", headerHozAlign: "center" },
		{ title: "분류1", field: "t_ITEM_CLSFC_1", headerHozAlign: "center" },
		{ title: "분류2", field: "t_ITEM_CLSFC_2", headerHozAlign: "center" },
		{ title: "이동수량", field: "t_Qty", align: "right", headerHozAlign: "center" },
		{ title: "이동전", field: "t_Before_Name", align: "right", headerHozAlign: "center" },
		{ title: "이동후", field: "t_After_Name", align: "right", headerHozAlign: "center" },
		{ title: "이동일", field: "t_Create_Date", headerHozAlign: "center",
			formatter: "datetime", formatterParams : {outputFormat : "YYYY-MM-DD HH:mm:ss"}}
	]
});

// 출고이동조회 검색
function MORS_Search() {
	var datas = {
		startDate: $("#MORS_startDate").val(),
		endDate: $("#MORS_endDate").val(),
		itemCode: $("#PRODUCT_ITEM_CODE2").val()
	}

	$.ajax({
		method: "GET",
		dataType: "json",
		url: "matOutReturnRest/MORS_Search",
		data: datas,
		success: function(data) {
			matOutReturnSearchTable.setData(data);
		}
	});
}