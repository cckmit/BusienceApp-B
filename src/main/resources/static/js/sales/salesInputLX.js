var salesInputMasterTable = new Tabulator("#salesInputMasterTable", {
    height:"calc(100% - 175px)",
	layoutColumnsOnNewData : true,
	headerFilterPlaceholder: null,
	ajaxConfig : "get",
	ajaxContentType:"json",
	ajaxURL : "salesInputRest/salesInputSelect",
	rowDblClick: function(e, row) {
		row.getTable().deselectRow();
		row.select();
		
		if(!inputDuplCheck(row)){
			salesInputSubTable.addRow({
				"sales_InMat_Code" : row.getData().product_ITEM_CODE,
				"sales_InMat_Name" : row.getData().product_ITEM_NAME,
				"sales_InMat_STND_1" : row.getData().product_INFO_STND_1,
				"sales_InMat_STND_2" : row.getData().product_INFO_STND_2,
				"sales_InMat_Item_Clsfc_1_Name" : row.getData().product_ITEM_CLSFC_1_NAME,
				"sales_InMat_Item_Clsfc_2_Name" : row.getData().product_ITEM_CLSFC_2_NAME,
				"sales_InMat_Qty" : 0,
				"sales_InMat_Rcv_Clsfc" : "203"
			});
			UseBtn();
		}
	},
 	columns:[
 		{title:"순번", field:"rownum", formatter:"rownum", headerHozAlign:"center", hozAlign:"center"},
		{title:"제품코드", field:"product_ITEM_CODE", headerHozAlign:"center", headerFilter: true},
 		{title:"제품명", field:"product_ITEM_NAME", headerHozAlign:"center", headerFilter: true},
 		{title:"규격1", field:"product_INFO_STND_1", headerHozAlign:"center", hozAlign:"right", headerFilter: true},
 		{title:"규격2", field:"product_INFO_STND_2", headerHozAlign:"center", hozAlign:"right", headerFilter: true},
		{title:"분류1", field:"product_ITEM_CLSFC_1_NAME", headerHozAlign:"center", hozAlign:"right", headerFilter: true},
		{title:"분류2", field:"product_ITEM_CLSFC_2_NAME", headerHozAlign:"center", hozAlign:"right", headerFilter: true}
		]
});

function inputDuplCheck(row){
	var selectDataCode = row.getData("selected").product_ITEM_CODE;
	
	var inputDatas = salesInputSubTable.getData();
	
	var idResult = inputDatas.some(function(currentValue, index, array){
		return (currentValue.sales_InMat_Code == selectDataCode);
	})
	
	if(idResult){
		alert("이미 선택한 코드 입니다.");
		return true
	}else{
		return false
	}
}

$("#SI_SearchBtn").click(function(){
	SI_Search();
})

function SI_Search(){
	salesInputMasterTable.setData("salesInputRest/salesInputSelect");
}

// 입고구분 select를 구성하는 리스트
var input_dtl = dtlSelectList(17);

//salesInputSub 커스텀 기능설정
var SIS_InputEditor = function(cell, onRendered, success, cancel, editorParams) {
	//cell - 편집 가능한 셀의 셀 구성 요소
	//onRendered - 에디터가 렌더링 되었을 때 호출 할 함수
	//success - 성공적을 업데이트 된 값을 tabulator에 전달하기 위해 호출되는 함수
	//cancel - 편집을 중단하고 일반 셀로 돌아 가기 위해 호출하는 함수
	//editorParams - editorParams 열 정의 속성에 전달 된 params 객체

	//create 및 style editor
	var SIS_input = document.createElement("input");

	SIS_input.setAttribute("type", "text");

	//입력 생성 및 스타일 지정
	SIS_input.style.padding = "3px";
	SIS_input.style.width = "100%";
	SIS_input.style.boxSizing = "border-box";

	//편집기의 값을 셀의 현재 값으로 설정
	if (cell.getValue() == undefined) {
		SIS_input.value = "";
	} else {
		SIS_input.value = cell.getValue();
	}

	//에디터가 선택되면 선택 상자에 포커스 설정 (타임 아웃은 편집기를 DOM에 추가 할 수 있음)
	onRendered(function() {
		SIS_input.focus();
		SIS_input.select();
		SIS_input.style.css = "100%";
	});

	//값이 설정되면 업데이트 할 셀 트리거
	function onChange() {
		success(SIS_input.value);
	}

	//바꼈을때 블러됫을때 함수 발동
	SIS_input.addEventListener("change", onChange);
	SIS_input.addEventListener("blur", onChange);

	//키버튼 이벤트
	SIS_input.addEventListener("keydown", function(e) {
		if (e.keyCode == 13) {
			//수량셀
			if (cell.getField() == "sales_InMat_Qty") {
				cell.nav().down();
			}
		}
	});
	//반환
	return SIS_input;
};

var salesInputSubTable = new Tabulator("#salesInputSubTable", {
	height: "calc(100% - 175px)",
	layoutColumnsOnNewData: true,
	rowAdded: function(row, cell) {
		row.getTable().deselectRow();
		row.select();
		
		//행이 추가되면 첫셀에 포커스
		do {
			setTimeout(function() {
				row.getCell("sales_InMat_Qty").edit();
				salesInputSubTable.deselectRow();
				row.select();
			}, 100);
		}
		while (row.getData().sales_InMat_Code === "0");
	},
	rowClick: function(e, row) {
		row.getTable().deselectRow();
		row.select();
	},
	columns: [
		{ title: "순번", field: "rownum", formatter:"rownum", headerHozAlign: "center", hozAlign: "center" },
		{ title: "코드", field: "sales_InMat_Code", headerHozAlign: "center" },
		{ title: "품목명", field: "sales_InMat_Name", headerHozAlign: "center", width: 120},
		{ title: "규격1", field: "sales_InMat_STND_1", headerHozAlign: "center", hozAlign: "right"},
 		{ title: "규격2", field: "sales_InMat_STND_2", headerHozAlign: "center", hozAlign: "right"},
		{ title: "분류1", field: "sales_InMat_Item_Clsfc_1_Name", headerHozAlign: "center", hozAlign: "right"},
		{ title: "분류2", field: "sales_InMat_Item_Clsfc_2_Name", headerHozAlign: "center", hozAlign: "right"},
		{ title: "입고수량", field: "sales_InMat_Qty", headerHozAlign: "center", hozAlign: "right", editor: SIS_InputEditor},
		{ title: "구분", field: "sales_InMat_Rcv_Clsfc", headerHozAlign: "center", editor: "select",
			formatter: function(cell, formatterParams) {
				var value = cell.getValue();
				if (input_dtl[value] != null) {
					value = input_dtl[value];
				} else {
					value = input_dtl[0];
				}
				return value;
			},
			editorParams: { values: input_dtl }
		}
	]
});

$("#SI_SaveBtn").click(function(){
	if(confirm("저장하시겠습니까?")){
		SI_Save();
	}
})

function SI_Save(){
	var realData = [];

	var rowData = salesInputSubTable.getData();

	for (var i = 0; i < rowData.length; i++) {
		if (rowData[i].sales_InMat_Qty != 0) {
			realData.push(rowData[i]);
		}
	}
	
    $.ajax({
        method : "post",
        url : "salesInputRest/salesInputSave",
		data : JSON.stringify(realData),
		contentType:'application/json',
		beforeSend: function (xhr) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           xhr.setRequestHeader(header, token);
		},
        success : function(result) {
            if (result == 0) {
				alert("중복된 값이 있습니다.");
			} else if (result == 1) {
				alert("저장되었습니다.");
				salesInputSubTable.clearData();
			}
        }
    })
}
