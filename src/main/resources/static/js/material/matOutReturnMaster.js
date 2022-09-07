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
			//반품수량셀 채크
			if (cell.getField() == "inReturn_Qty") {
				//입력값이 양의 정수가 아니거나 입고수량보다 높으면 안내문
				if(!(input.value >= 0) || cell.getRow().getData().InMat_Qty < input.value){
					alert("반품 수량을 잘못입력하였습니다.");
					input.value = 0;
				}
				//입력값이 0이 아닌경우 (반품수량을 입력했을경우) 셀 선택
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
	paginationAddRow: "table",
	height: "calc(100% - 175px)",
	rowDblClick: function(e, row) {
		row.toggleSelect();
	},
	ajaxConfig : "get",
	ajaxContentType:"json",
	ajaxURL : "/matOutReturnRest/outReturnLXSearch",
	ajaxParams : {warehouse : $("#warehouseSelectBox").val(), check: true},
	ajaxResponse:function(url, params, response){
		if(response.length == 0){
			toastr.info("목록이 없습니다.");	
		}
		return response;
    },
	columns: [
		{ formatter: "rowSelection", align: "center"},
		{ title: "순번", field: "rownum", formatter:"rownum", headerHozAlign: "center", align: "center" },
		{ title: "품목코드", field: "s_ItemCode", headerHozAlign: "center" },
		{ title: "품목명", field: "s_ItemName", headerHozAlign: "center" },
		{ title: "출고수량", field: "s_Qty", align: "right", headerHozAlign: "center" },
		{ title: "반품수량", field: "s_ReturnQty", align: "right", editor: MORI_InputEditor, headerHozAlign: "center"},
		{ title: "규격1", field: "s_Item_Standard_1", headerHozAlign: "center" },
		{ title: "규격2", field: "s_Item_Standard_2", headerHozAlign: "center" },
		{ title: "창고", field: "s_Warehouse_Name", headerHozAlign: "center" }
	]
});

$("#warehouseSelectBox").change(function(){
	matOutReturnInsertTable.setData("/matOutReturnRest/outReturnLXSearch",{warehouse : $(this).val()})
})

$("#outReturnSaveBtn").click(function(){
	var selectedData = matOutReturnInsertTable.getData("selected");
	
	if (selectedData.length == 0) {
		alert("저장할 목록이 없습니다. 행을 선택해 주세요.");
		return;
	}

	for (var i = 0; i < selectedData.length; i++) {
		if (selectedData[i].outReturn_Qty == "0") {
			alert("반품수량이 입력되지 않은 행이 존재합니다.");
			return;
		}
	}
	
	outReturnSave(selectedData);
})

function outReturnSave(selectedData){	
	
	$.ajax({
		method: "post",
		url: "/matOutReturnRest/outReturnLXSave",
		data: JSON.stringify(selectedData),
		contentType:'application/json',
		beforeSend: function (xhr) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           xhr.setRequestHeader(header, token);
		},
		success: function(result) {
			if (result) {
				alert("저장되었습니다.");
				matOutReturnInsertTable.replaceData();
			} else {
				alert("오류")
			}
		}
	});
}

var matOutReturnSearchTable = new Tabulator("#matOutReturnSearchTable", {
	height: "calc(100% - 175px)",
	ajaxConfig : "get",
	ajaxContentType:"json",
	ajaxURL : "matOutReturnRest/MORS_Search",
	ajaxParams : {
		startDate: $(".startDate").val(),
		endDate: $(".endDate").val(),
		outMat_Code: $(".itemCode").val()
	},
	columns: [
		{ title: "순번", field: "rownum", formatter:"rownum", headerHozAlign: "center", align: "center" },
		{ title: "품목코드", field: "om_ItemCode", headerHozAlign: "center" },
		{ title: "품목명", field: "om_ItemName", headerHozAlign: "center" },
		{ title: "규격1", field: "om_Item_Stnd_1", headerHozAlign: "center"},
		{ title: "규격2", field: "om_Item_Stnd_2", headerHozAlign: "center"},
		/*{ title: "폼목분류1", field: "om_Item_Stnd_1", headerHozAlign: "center"},
		{ title: "품목분류2", field: "om_Item_Stnd_2", headerHozAlign: "center"},*/
		{ title: "반품수량", field: "om_Qty", align: "right", headerHozAlign: "center" },
		{ title: "출고구분", field: "om_Send_Clsfc_Name", headerHozAlign: "center" },
		{ title: "반품일", field: "om_OutDate", headerHozAlign: "center",
			formatter: "datetime", formatterParams : {outputFormat : "YYYY-MM-DD HH:mm:ss"}},
		{ title: "작업자명", field: "outMat_Modifier", headerHozAlign: "center" }
	]
});
$("#outReturnSearchBtn").click(function(){
	outReturnSearch()
})

function outReturnSearch(){	
	matOutReturnSearchTable.setData("matOutputRest/MOL_Search",
		{
			startDate: $(".startDate").val(),
			endDate: $(".endDate").val(),
			itemCode: $(".itemCode").val(),
			DeptCode: '',
			itemSendClsfc: '210'
		})
}
