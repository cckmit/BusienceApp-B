var crateLotTable = new Tabulator("#crateLotTable", {
	height:"calc(100% - 175px)",
	layoutColumnsOnNewData : true,
	rowClick:function(e, row){
		row.getTable().deselectRow();
		row.select();
		DIS_Search(row.getData().cl_LotNo);
	},
	columns:[
		{ title:"순번", field:"rownum", formatter:"rownum", hozAlign:"center"},
		{ title: "LotNo", field: "cl_LotNo", headerHozAlign: "center"},
		{ title: "제품코드", field: "cl_ItemCode", headerHozAlign: "center"},
		{ title: "제품명", field: "cl_ItemName", headerHozAlign: "center"},
		{ title: "규격1", field: "cl_STND_1", headerHozAlign: "center"},
		{ title: "규격2", field: "cl_STND_2", headerHozAlign: "center"},
		{ title: "품목 분류1", field: "cl_Item_Clsfc_Name_1", headerHozAlign: "center"},
		{ title: "품목 분류2", field: "cl_Item_Clsfc_Name_2", headerHozAlign: "center"},
		{ title: "재질", field: "cl_Item_Material", headerHozAlign: "center"},
		{ title: "설비코드", field: "cl_MachineCode", headerHozAlign: "center"},
		{ title: "설비명", field: "cl_EquipName", headerHozAlign: "center"},	
		{ title: "생산수량", field: "cl_Qty", headerHozAlign: "center", align:"right"},
		{ title: "불량수량", field: "cl_Defect_Qty", headerHozAlign: "center", align:"right"},
		{ title: "작업종료일", field: "cl_Create_Date", headerHozAlign: "center"
		, formatter:"datetime", formatterParams:{
    		outputFormat:"YYYY-MM-DD HH:mm" }}
	]
});

$("#DI_SearchBtn").click(function(){
	DI_Search();
})

function DI_Search(selectedRow){
	
	let status = document.querySelector('#defectStatusList').value;
	console.log(status);
	
	if(status == '251') {
		status = 2
	} else if(status == '252') {
		status = 3
	}
	
	var datas = {
		startDate : $("#startDate").val(),
		endDate : $("#endDate").val(),
		condition : status
	}
	crateLotTable.setData("defectInsertRest/DefectList", datas);
	console.log(crateLotTable);
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
		{ title: "불량 명", field: "defect_Name", headerHozAlign: "center" },
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
	var selectedRow = crateLotTable.getRows("selected")[0];
	for(let i=0;i<rows.length;i++){
		sum += Number(rows[i].getCell("defect_Qty").getValue());
	}
	if(selectedRow.getData().cl_Qty < sum){
		alert("생산수량보다 불량수량이 더 많을 수 없습니다.");
		return false;
	}
	
	console.log(defectTable.getData());
	
 /*   $.ajax({
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
				crateLotTable.updateRow(selectedRow,{"cl_Qty" : sum}); 
			}
        }
    })*/
}

$(document).ready(function(){
	DI_Search();
})