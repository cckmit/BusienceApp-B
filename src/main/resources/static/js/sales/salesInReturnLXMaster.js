//커스텀 기능설정
var SIRI_InputEditor = function(cell, onRendered, success, cancel, editorParams) {
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
	if (cell.getValue() == undefined) {
		input.value = "";
	} else {
		input.value = cell.getValue();
	}

	//에디터가 선택되면 선택 상자에 포커스 설정 (타임 아웃은 편집기를 DOM에 추가 할 수 있음)
	onRendered(function() {
		input.focus();
		input.select();
		input.style.css = "100%";
	});

	//값이 설정되면 업데이트 할 셀 트리거
	function onChange() {
		success(input.value);
	}

	//바꼈을때 블러됫을때 함수 발동
	input.addEventListener("change", onChange);
	input.addEventListener("blur", onChange);

	//키버튼 이벤트
	input.addEventListener("keydown", function(e) {
		//엔터쳤을떄
		if (e.keyCode == 13) {
			//반품수량셀 채크
			if (cell.getField() == "lm_ReturnQty") {
				//입력값이 양의 정수가 아니거나 입고수량보다 높으면 안내문
				if (!(input.value >= 0) || cell.getRow().getData().lm_Qty < input.value) {
					alert("반품 수량을 잘못입력하였습니다.");
					input.value = 0;
				}
				//입력값이 0이 아닌경우 (반품수량을 입력했을경우) 셀 선택
				if (input.value != 0) {
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

var salesInReturnInsertTable = new Tabulator("#salesInReturnInsertTable", {
	height: "calc(100% - 175px)",
	rowClick: function(e, row) {
		row.toggleSelect();
	},
	ajaxResponse:function(url, params, response){
		if(response.length == 0){
			toastr.info("목록이 없습니다.");	
		}
		return response;
    },
	columns: [
		{ formatter: "rowSelection", titleFormatter: "rowSelection", headerHozAlign: "center", hozAlign: "center", headerSort: false },
		{ title: "순번", field: "rownum", formatter: "rownum", headerHozAlign: "center", align: "center" },
		{ title: "품목코드", field: "lm_ItemCode", headerHozAlign: "center" },
		{ title: "품목명", field: "lm_ItemName", headerHozAlign: "center" },
		{ title: "규격1", field: "lm_STND_1", headerHozAlign: "center", hozAlign: "left" },
		{ title: "규격2", field: "lm_STND_2", headerHozAlign: "center", hozAlign: "left" },
		{ title: "재질", field: "lm_Item_Material_Name", headerHozAlign: "center", hozAlign: "left" },
		{ title: "분류1", field: "lm_Item_CLSFC_1_Name", headerHozAlign: "center", hozAlign: "left" },
		{ title: "분류2", field: "lm_Item_CLSFC_2_Name", headerHozAlign: "center", hozAlign: "left" },
		{ title: "입고수량", field: "lm_Qty", align: "right", headerHozAlign: "center" },
		{ title: "반품수량", field: "lm_ReturnQty", align: "right", headerHozAlign: "center", editor: SIRI_InputEditor }
	]
});

// fgoodsInReturn 목록 검색
function SIRI_Search() {
	salesInReturnInsertTable.setData("salesInReturnRest/salesInReturnLXSelect");
}

//SIRI_Save
function SIRI_Save() {

	var selectedData = salesInReturnInsertTable.getData("selected");
	var dataList = [];

	// 선택한 행이 있을경우에 저장이 가능하다.
	//만약 선택된행에서 반품수량이 0 이면 리스트에서 제외
	for (let i = 0; i < selectedData.length; i++) {
		if(selectedData[i].lm_ReturnQty != 0){
			dataList.push(selectedData[i]);
		}
	}
	if (dataList.length == 0) {
		alert("저장할 목록이 없습니다.");
		return false;
	}
	$.ajax({
		method: "post",
		url: "salesInReturnRest/salesInReturnInsert",
		data: JSON.stringify(dataList),
		contentType:'application/json',
		beforeSend: function(xhr) {
			var header = $("meta[name='_csrf_header']").attr("content");
			var token = $("meta[name='_csrf']").attr("content");
			xhr.setRequestHeader(header, token);
		},
		success: function(datas) {
			if (datas) {
				alert("저장되었습니다");
				SIRI_Search();
			} else {
				alert("오류가 발생했습니다. 다시 시도해주십시오.");
			}
		}
	});
}



var salesInReturnSearchTable = new Tabulator("#salesInReturnSearchTable", {
	height: "calc(100% - 175px)",
	ajaxResponse:function(url, params, response){
		if(response.length == 0){
			toastr.info("목록이 없습니다.");	
		}
		return response;
    },
	columns: [
		{ title: "순번", field: "rownum", formatter: "rownum", headerHozAlign: "center", align: "center" },
		{ title: "Lot번호", field: "sales_InMat_Lot_No", headerHozAlign: "center", hozAlign: "left", visible: false},
		{ title: "품목코드", field: "sales_InMat_Code", headerHozAlign: "center" },
		{ title: "품목명", field: "sales_InMat_Name", headerHozAlign: "center" },
		{ title: "규격", field: "sales_InMat_STND_1", headerHozAlign: "center", hozAlign: "left" },
		{ title: "규격2", field: "sales_InMat_STND_2", headerHozAlign: "center", hozAlign: "left" },
		{ title: "분류1", field: "sales_InMat_Item_Clsfc_1_Name", headerHozAlign: "center", hozAlign: "left" },
		{ title: "분류2", field: "sales_InMat_Item_Clsfc_2_Name", headerHozAlign: "center", hozAlign: "left" },
		{ title: "재질", field: "sales_InMat_Item_Material_Name", headerHozAlign: "center", hozAlign: "left" },
		{ title: "단위", field: "sales_InMat_UNIT", headerHozAlign: "center", hozAlign: "left" },
		{ title: "반품수량", field: "sales_InMat_Qty", align: "right", headerHozAlign: "center" },
		{ title: "입고구분", field: "sales_InMat_Rcv_Clsfc", headerHozAlign: "center" },
		{ title: "반품일", field: "sales_InMat_Date", headerHozAlign: "center" },
		{ title: "작업자명", field: "sales_InMat_Modifier", headerHozAlign: "center" }
	]
});

// fgoodsInReturn 목록 검색
function SIRS_Search() {
	salesInReturnSearchTable.setData("salesInReturnRest/salesInReturnListLXSelect", {
		startDate: $(".startDate").val(),
		endDate: $(".endDate").val(),
		itemCode: $('.itemCode').val(),
	});
}