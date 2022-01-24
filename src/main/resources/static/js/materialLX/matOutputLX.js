var matOutputTable = new Tabulator("#matOutputTable", {
	//페이징
	pagination: "local",
	paginationSize: 20,
	paginationAddRow: "table",
	height: "calc(100% - 175px)",
	layoutColumnsOnNewData: true,
	headerFilterPlaceholder: null,
	rowDblClick: function(e, row) {

		matOutputTable.deselectRow();
		row.select();
		
		if(!inputDuplCheck(row)){
			matOutputSubTable.addRow({"outMat_Code": row.getData().sm_Code,
									"outMat_Name": row.getData().sm_Name,
									"stock_Qty": row.getData().sm_Qty,
									"outMat_Qty": 0,
									"outMat_Consignee": '1',
									"outMat_Send_Clsfc": '208'
									});
			UseBtn();
		}

	},
	columns: [
		{ title: "순번", field: "rownum", formatter: "rownum", headerHozAlign: "center", hozAlign: "center"},
		{ title: "코드", field: "sm_Code", headerHozAlign: "center", headerFilter: true },
		{ title: "품목명", field: "sm_Name", headerHozAlign: "center", hozAlign: "left", headerFilter: true},
		{ title: "규격", field: "sm_STND_1", headerHozAlign: "center", headerFilter: true },
		{ title: "품목분류1", field: "sm_CLSFC_1_Name", headerHozAlign: "center", headerFilter: true },
		{ title: "재고", field: "sm_Qty", headerHozAlign: "center", headerFilter: true }]
});


function inputDuplCheck(row){
	var selectDataCode = row.getData("selected").sm_Code;
	
	var inputDatas = matOutputSubTable.getData();
	
	var idResult = inputDatas.some(function(currentValue, index, array){
		return (currentValue.outMat_Code == selectDataCode);
	})
	
	if(idResult){
		alert("이미 선택한 코드 입니다.");
		return true
	}else{
		return false
	}
}

//orderMaster 목록검색 matOrder와 동일하지만 테이브이름이 다르고, 미입고 컬럼이 추가됨
function MOS_Search() {

	data = {
		SM_Code: matOutputTable.getData("selected").sm_Code,
		SM_Name: matOutputTable.getData("selected").sm_Name,
		itemCode: $("#outmatLX_itemCode").val()
	}

	$.ajax({
		method: "GET",
		dataType: "json",
		async: false,
		url: "matOutputLXRest/MOS_Search?data=" + encodeURI(JSON.stringify(data)),
		success: function(data) {
			//console.log(data);
			matOutputTable.setData(data);
		}
	});
}

$('#MR_SearchBtn').click(function() {
	MOS_Search();
})

// 출고구분 select를 구성하는 리스트
var output_dtl = dtlSelectList(18);
for(prop in output_dtl){
    if(output_dtl[prop] == "출고반품"){
        delete output_dtl[prop]
    }
}

// 매니저명 select를 구성하는 리스트
var manager_dtl = dtlSelectList(1);

//matInputSub 커스텀 기능설정
var MOM_InputEditor = function(cell, onRendered, success, cancel, editorParams) {
	//cell - 편집 가능한 셀의 셀 구성 요소
	//onRendered - 에디터가 렌더링 되었을 때 호출 할 함수
	//success - 성공적을 업데이트 된 값을 tabulator에 전달하기 위해 호출되는 함수
	//cancel - 편집을 중단하고 일반 셀로 돌아 가기 위해 호출하는 함수
	//editorParams - editorParams 열 정의 속성에 전달 된 params 객체

	//create 및 style editor
	var MOM_input = document.createElement("input");

	MOM_input.setAttribute("type", "text");

	//입력 생성 및 스타일 지정
	MOM_input.style.padding = "3px";
	MOM_input.style.width = "100%";
	MOM_input.style.boxSizing = "border-box";

	//편집기의 값을 셀의 현재 값으로 설정
	if (cell.getValue() == undefined) {
		MOM_input.value = "";
	} else {
		MOM_input.value = cell.getValue();
	}

	//에디터가 선택되면 선택 상자에 포커스 설정 (타임 아웃은 편집기를 DOM에 추가 할 수 있음)
	onRendered(function() {
		MOM_input.focus();
		MOM_input.select();
		MOM_input.style.css = "100%";
	});

	//값이 설정되면 업데이트 할 셀 트리거
	function onChange() {
		success(MOM_input.value);
	}

	//바꼈을때 블러됫을때 함수 발동
	MOM_input.addEventListener("change", onChange);
	MOM_input.addEventListener("blur", onChange);

	//키버튼 이벤트
	MOM_input.addEventListener("keydown", function(e) {
		if (e.keyCode == 13) {
			//수량셀
			if (cell.getField() == "outMat_Qty") {
				cell.nav().down();
			}
		}
	});
	//반환
	return MOM_input;
};

var matOutputSubTable = new Tabulator("#matOutputSubTable", {
	height: "calc(100% - 175px)",
	layoutColumnsOnNewData: true,
	rowAdded: function(row, cell) {
		row.select();

		//행이 추가되면 첫셀에 포커스
		do {
			setTimeout(function() {
				row.getCell("outMat_Qty").edit();
				matOutputSubTable.deselectRow();
				row.select();
			}, 100);
		}
		while (row.getData().outMat_Qty === "0");

	},
	rowClick: function(e, row) {
		matOutputSubTable.deselectRow();
		row.select();
	},
	columns: [
		{ title: "순번", field: "rownum", formatter: "rownum", headerHozAlign: "center", hozAlign: "center" },
		{ title: "코드", field: "outMat_Code", headerHozAlign: "center" },
		{ title: "품목명", field: "outMat_Name", headerHozAlign: "center", width: 120 },
		{ title: "재고수량", field: "stock_Qty", visible:false },
		{ title: "출고수량", field: "outMat_Qty", headerHozAlign: "center", hozAlign: "right", editor: MOM_InputEditor},
		{ title: "수취인", field: "outMat_Consignee", headerHozAlign: "center", hozAlign: "left", editor: "select",
			formatter: function(cell, formatterParams) {
				var value = cell.getValue();
				if (manager_dtl[value] != null) {
					value = manager_dtl[value];
				} else {
					value = "";
				}
				return value;
			}, editorParams: { values: manager_dtl }
		},
		{
			title: "구분", field: "outMat_Send_Clsfc", headerHozAlign: "center", editor: "select",
			formatter: function(cell, formatterParams) {
				var value = cell.getValue();
				if (output_dtl[value] != null) {
					value = output_dtl[value];
				} else {
					value = output_dtl[0];
				}
				return value;
			},
			editorParams: { values: output_dtl }
		}
	]
});

//inMatTable 저장
function MOM_Save() {

	var realData = [];

	var rowData = matOutputSubTable.getData();

	//realData.push(rowData[i]);

	for (var i = 0; i < rowData.length; i++) {
		if (rowData[i].stock_Qty < rowData[i].outMat_Qty) {
			alert("재고 수량보다 출고 수량이 더 많습니다.");
			return false;
		}

		if (rowData[i].outMat_Qty != 0) {
			realData.push(rowData[i]);
		}
	}

	//InputSub 저장부분
	$.ajax({
		method: "post",
		url: "matOutputLXRest/MOM_Save",
		data : JSON.stringify(realData),
		contentType:'application/json',
		beforeSend: function (xhr) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           xhr.setRequestHeader(header, token);
		},
		success: function(result) {
			//console.log(result);
			if (result == 0) {
				alert("중복된 값이 있습니다..");
			} else if (result == 1) {
				MOS_Search();
				alert("저장되었습니다.");
			}
		}
	});
}

$('#MOM_SaveBtn').click(function() {
	MOM_Save();
})