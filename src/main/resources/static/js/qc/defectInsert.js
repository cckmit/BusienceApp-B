var WorkOrderTable = new Tabulator("#WorkOrderTable", {
	//페이징
	pagination: "local",
	paginationSize: 20,
	height:"calc(100% - 175px)",
	layoutColumnsOnNewData : true,
	rowClick:function(e, row){
		row.getTable().deselectRow();
		row.select();
		DIS_Search(row.getData().workOrder_ONo);
	},
	columns:[
		{ title: "작업지시No", field: "workOrder_ONo", headerHozAlign: "center"},
		{ title: "제품코드", field: "workOrder_ItemCode", headerHozAlign: "center"},
		{ title: "제품명", field: "workOrder_ItemName", headerHozAlign: "center"},
		{ title: "규격", field: "workOrder_Item_STND_1", headerHozAlign: "center"},
		{ title: "설비코드", field: "workOrder_EquipCode", headerHozAlign: "center"},
		{ title: "설비명", field: "workOrder_EquipName", headerHozAlign: "center"},	
		{ title: "작업상태", field: "workOrder_WorkStatus_Name", headerHozAlign: "center"},	
		{ title: "작업완료일", field: "workOrder_CompleteTime", headerHozAlign: "center"},	
		{ title: "지시수량", field: "workOrder_PQty", headerHozAlign: "center", align:"right"},
		{ title: "생산수량", field: "workOrder_RQty", headerHozAlign: "center", align:"right"},
		{ title: "불량수량", field: "workOrder_DQty", headerHozAlign: "center", align:"right"}
	]
});

$("#DI_SearchBtn").click(function(){
	DI_Search();
})

function DI_Search(selectedRow){
	var datas = {
		startDate : $("#startDate").val(),
		endDate : $("#endDate").val()
	}
	WorkOrderTable.setData("defectInsertRest/DI_Search", datas);
}

//defectTable 커스텀 기능설정
var DIS_InputEditor = function(cell, onRendered, success, cancel, editorParams) {
	//cell - 편집 가능한 셀의 셀 구성 요소
	//onRendered - 에디터가 렌더링 되었을 때 호출 할 함수
	//success - 성공적을 업데이트 된 값을 tabulator에 전달하기 위해 호출되는 함수
	//cancel - 편집을 중단하고 일반 셀로 돌아 가기 위해 호출하는 함수
	//editorParams - editorParams 열 정의 속성에 전달 된 params 객체

	//create 및 style editor
	var DIS_input = document.createElement("input");

	DIS_input.setAttribute("type", "text");

	//입력 생성 및 스타일 지정
	DIS_input.style.padding = "3px";
	DIS_input.style.width = "100%";
	DIS_input.style.boxSizing = "border-box";

	//편집기의 값을 셀의 현재 값으로 설정
	if (cell.getValue() == undefined) {
		DIS_input.value = "";
	} else {
		DIS_input.value = cell.getValue();
	}

	//에디터가 선택되면 선택 상자에 포커스 설정 (타임 아웃은 편집기를 DOM에 추가 할 수 있음)
	onRendered(function() {
		DIS_input.focus();
		DIS_input.select();
		DIS_input.style.css = "100%";
	});

	//값이 설정되면 업데이트 할 셀 트리거
	function onChange() {
		success(DIS_input.value);
	}

	//바꼈을때 블러됫을때 함수 발동
	DIS_input.addEventListener("change", onChange);
	DIS_input.addEventListener("blur", onChange);

	//키버튼 이벤트
	DIS_input.addEventListener("keydown", function(e) {
		if (e.keyCode == 13) {
			//수량셀
			if (cell.getField() == "defect_Qty") {
				cell.nav().down();
			}
		}
	});
	//반환
	return DIS_input;
};

var defectTable = new Tabulator("#defectTable", {
	height:"calc(100% - 175px)",
	columns:[
		{ title: "작업지시 번호", field: "defect_ONo", headerHozAlign: "center", visible: false },
		{ title: "불량 코드", field: "defect_Code", headerHozAlign: "center" },
		{ title: "불량 이름", field: "defect_Name", headerHozAlign: "center" },
		{ title: "불량 수량", field: "defect_Qty", headerHozAlign: "center", align:"right", editor: DIS_InputEditor}
	]
});

function DIS_Search(value){
	defectTable.setData("defectInsertRest/DIS_Search", {"workOrder_ONo" : value})
	.then(function(){
		defectTable.getRows()[0].getCell("defect_Qty").edit();
	})
}

$("#DI_SaveBtn").click(function(){
	DI_Save();
})

function DI_Save(){
	var rows = defectTable.getRows();
	var sum = 0;
	var selectedRow = WorkOrderTable.getRows("selected")[0];
	for(let i=0;i<rows.length;i++){
		sum += Number(rows[i].getCell("defect_Qty").getValue());
	}
	if(selectedRow.getData().workOrder_RQty < sum){
		alert("생산수량보다 불량수량이 더 많을 수 없습니다.");
		return false;
	}
	
    $.ajax({
        method : "post",
        url : "defectInsertRest/DI_Save",
		data : JSON.stringify(defectTable.getData()),
		contentType:'application/json',
		beforeSend: function (xhr) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           xhr.setRequestHeader(header, token);
		},
        success : function(result) {
            if (result == 0) {
				alert("잘못 입력하였습니다.");
			} else if (result == 1) {
				alert("저장되었습니다.");
				WorkOrderTable.updateRow(selectedRow,{"workOrder_DQty" : sum}); 
			}
        }
    })
}

$(document).ready(function(){
	DI_Search();
})