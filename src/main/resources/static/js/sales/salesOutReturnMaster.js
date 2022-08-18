//커스텀 기능설정
var SORI_InputEditor = function(cell, onRendered, success, cancel, editorParams){
    //cell - 편집 가능한 셀의 셀 구성 요소
    //onRendered - 에디터가 렌더링 되었을 때 호출 할 함수
    //success - 성공적을 업데이트 된 값을 tabulator에 전달하기 위해 호출되는 함수
    //cancel - 편집을 중단하고 일반 셀로 돌아 가기 위해 호출하는 함수
    //editorParams - editorParams 열 정의 속성에 전달 된 params 객체

    //create 및 style editor
    var SORI_input = document.createElement("input");

    SORI_input.setAttribute("type", "text");

    //입력 생성 및 스타일 지정
    SORI_input.style.padding = "3px";
    SORI_input.style.width = "100%";
    SORI_input.style.boxSizing = "border-box";

    //편집기의 값을 셀의 현재 값으로 설정
	if(cell.getValue() == undefined){
		SORI_input.value = "";
	}else{
		SORI_input.value = cell.getValue();
	}

    //에디터가 선택되면 선택 상자에 포커스 설정 (타임 아웃은 편집기를 DOM에 추가 할 수 있음)
    onRendered(function(){
        SORI_input.focus();
		SORI_input.select();
        SORI_input.style.css = "100%";
    });

    //값이 설정되면 업데이트 할 셀 트리거
    function onChange(){
		success(SORI_input.value);   
    }
    
	//바꼈을때 블러됫을때 함수 발동
    SORI_input.addEventListener("change", onChange);
	SORI_input.addEventListener("blur", onChange);

    //키버튼 이벤트
    SORI_input.addEventListener("keydown", function (e) {
		//엔터쳤을떄
		if(e.keyCode == 13){
			//반품수량셀 채크
			if (cell.getField() == "sales_OutReturn_Qty") {
				console.log(cell.getValue());
				//입력값이 양의 정수가 아니거나 입고수량보다 높으면 안내문
				if(!(SORI_input.value >= 0) || cell.getRow().getData().sales_OutMat_Qty < SORI_input.value){
					alert("반품 수량을 잘못입력하였습니다.");
					SORI_input.value = 0;
				}
				//입력값이 0이 아닌경우 (반품수량을 입력했을경우) 셀 선택
				if(SORI_input.value != 0){
					cell.getRow().select();
				}
			//다음셀로
			cell.nav().next();
			}
		}
    });
    //반환
    return SORI_input;
};

var salesOutReturnInsertTable = new Tabulator("#salesOutReturnInsertTable", {
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
		{ title: "수주No", field: "sales_OutMat_Cus_No", headerHozAlign: "center" },
		{ title: "LotNo", field: "sales_OutMat_Lot_No", headerHozAlign: "center" },
		{ title: "품목코드", field: "sales_OutMat_Code", headerHozAlign: "center" },
		{ title: "품목명", field: "sales_OutMat_Name", headerHozAlign: "center" },
		{ title: "규격", field: "sales_OutMat_STND_1", headerHozAlign: "center", hozAlign: "left" },
		{ title: "규격2", field: "sales_OutMat_STND_2", headerHozAlign: "center", hozAlign: "left" },
		{ title: "분류1", field: "sales_OutMat_Item_Clsfc_Name_1", headerHozAlign: "center", hozAlign: "left" },
		{ title: "분류2", field: "sales_OutMat_Item_Clsfc_Name_2", headerHozAlign: "center", hozAlign: "left" },
		{ title: "재질", field: "sales_OutMat_Material", headerHozAlign: "center", hozAlign: "left" },
		{ title: "출고수량", field: "sales_OutMat_Qty", align: "right", headerHozAlign: "center" },
		{ title: "반품수량", field: "sales_OutMat_Qty", align: "right", headerHozAlign: "center" },
		{ title: "단가", field: "sales_OutMat_Unit_Price", align: "right", headerHozAlign: "center" },
		{ title: "금액", field: "sales_OutMat_Price", align: "right", headerHozAlign: "center", formatter: "money", formatterParams: { precision: false }, width: 100 },
		{ title: "거래처코드", field: "sales_OutMat_Client_Code", headerHozAlign: "center" },
		{ title: "거래처명", field: "sales_OutMat_Client_Name", headerHozAlign: "center" },
		{ title: "출고구분", field: "sales_OutMat_Send_Clsfc_Name", headerHozAlign: "center" },
		{ title: "출고일", field: "sales_OutMat_Date", headerHozAlign: "center" },
		{ title: "데이터삽입시간", field: "sales_OutMat_dInsert_Time", headerHozAlign: "center" },
		{ title: "작업자명", field: "sales_OutMat_Modifier", headerHozAlign: "center" }
	]
});

// fgoodsInReturn 목록 검색
function SORI_Search() {
	datas = {
		startDate: $("#SORI_startDate").val(),
		endDate: $("#SORI_endDate").val(),
		ItemCode: $('#PRODUCT_ITEM_CODE1').val(),
		ClientCode: $('#Sales_OutMat_Client_Code1').val()
	}

	salesOutReturnInsertTable.setData("salesOutputRest/SORI_Search", datas);

}

//SORI_Save
function SORI_Save() {
	//선택된 행만 저장
	//만약 선택된행에서 반품수량이 0 이면 저장 안함
	selectedData = salesOutReturnInsertTable.getData("selected");
	dataList = [];

	// 선택한 행이 있을경우에 저장이 가능하다.
	//만약 선택된행에서 반품수량이 0 이면 리스트에서 제외
	for (let i = 0; i < selectedData.length; i++) {
		dataList.push(selectedData[i]);
	}
	if (dataList.length == 0) {
		alert("저장할 목록이 없습니다.");
		return false;
	}

	$.ajax({
		method: "post",
		url: "salesOutReturnRest/SORI_Save",
		data: { dataList: JSON.stringify(dataList) },
		beforeSend: function(xhr) {
			var header = $("meta[name='_csrf_header']").attr("content");
			var token = $("meta[name='_csrf']").attr("content");
			xhr.setRequestHeader(header, token);
		},
		success: function(datas) {
			if (datas) {
				alert("저장되었습니다.");
				//SORI_Search();
			} else if (datas) {
				alert("중복된 값이 있습니다.");
			}
		}
	});
}

var salesOutReturnSearchTable = new Tabulator("#salesOutReturnSearchTable", {
	height: "calc(100% - 175px)",
	ajaxResponse:function(url, params, response){
		if(response.length == 0){
			toastr.info("목록이 없습니다.");	
		}
		return response;
    },
	columns: [
		{ title: "순번", field: "rownum", formatter: "rownum", headerHozAlign: "center", align: "center" },
		{ title: "수주No", field: "sales_OutMat_Cus_No", headerHozAlign: "center" },
		{ title: "LotNo", field: "sales_OutMat_Lot_No", headerHozAlign: "center" },
		{ title: "품목코드", field: "sales_OutMat_Code", headerHozAlign: "center" },
		{ title: "품목명", field: "sales_OutMat_Name", headerHozAlign: "center" },
		{ title: "규격", field: "sales_OutMat_STND_1", headerHozAlign: "center", hozAlign: "left" },
		{ title: "규격2", field: "sales_OutMat_STND_2", headerHozAlign: "center", hozAlign: "left" },
		{ title: "분류1", field: "sales_OutMat_Item_Clsfc_Name_1", headerHozAlign: "center", hozAlign: "left" },
		{ title: "분류2", field: "sales_OutMat_Item_Clsfc_Name_2", headerHozAlign: "center", hozAlign: "left" },
		{ title: "재질", field: "sales_OutMat_Material", headerHozAlign: "center", hozAlign: "left" },
		{ title: "반품수량", field: "sales_OutReturn_Qty", align: "right", headerHozAlign: "center" },
		{ title: "단가", field: "sales_OutMat_Unit_Price", align: "right", headerHozAlign: "center" },
		{ title: "금액", field: "sales_OutMat_Price", align: "right", headerHozAlign: "center", formatter: "money", formatterParams: { precision: false }, width: 100  },
		{ title: "거래처코드", field: "sales_OutMat_Client_Code", headerHozAlign: "center" },
		{ title: "거래처명", field: "sales_OutMat_Client_Name", headerHozAlign: "center" },
		{ title: "출고구분", field: "sales_OutMat_Send_Clsfc_Name", headerHozAlign: "center" },
		{ title: "출고일", field: "sales_OutMat_Date", headerHozAlign: "center" },
		{ title: "반품일", field: "sales_OutMat_dInsert_Time", headerHozAlign: "center" },
		{ title: "작업자명", field: "sales_OutMat_Modifier", headerHozAlign: "center" }
	]
});

// fgoodsInReturn 목록 검색
function SORS_Search() {
	datas = {
		startDate: $("#SORS_startDate").val(),
		endDate: $("#SORS_endDate").val(),
		ItemCode: $('#PRODUCT_ITEM_CODE2').val(),
		ClientCode: $('#Sales_OutMat_Client_Code2').val()
	}
	
	salesOutReturnSearchTable.setData("salesOutputRest/SORS_Search", datas)

}