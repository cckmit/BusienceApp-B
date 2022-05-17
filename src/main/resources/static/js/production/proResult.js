//검색기능
function Search() {
	if ($('#startDate').val().length < 10) {
		alert("시작일은 반드시 입력하여 주십시오.");
		return;
	}
	3
	if ($('#endDate').val().length < 10) {
		alert("끝일은 반드시 입력하여 주십시오.");
		return;
	}
	var jsonData = {
		startDate: $('#startDate').val(),
		endDate: $('#endDate').val(),
		itemCode: $('#itemCode').val(),
		itemName: $('#itemName').val(),
		machineCode: $('#machineCode').val(),
		machineName: $('#machineName').val()
	}
	proResultTable.setData('proResultRest/proResultSelect', jsonData)
}

$('#SearchBtn').click(function() {
	Search();
})

$('#itemName').keypress(function(e) {
	if (e.keyCode == 13) {
		var value = $(this).val()
		//내용이 있을경우 검색해서 값이 하나일경우 생략, 아닐경우 팝업창
		$.ajax({
			method: "GET",
			url: "product_check?PRODUCT_ITEM_CODE=" + value,
			dataType: "json",
			success: function(data) {
				if (data.length == 1) {
					//검색어와 일치하는값이 있는경우
					$('#itemCode').val(data[0].product_ITEM_CODE);
					$('#itemName').val(data[0].production_Product_Name);
				} else {
					//검색어와 일치하는값이 없는경우, 팝업창
					itemPopup(value, 'input', '', 'material');
				}
			}
		})
	}
})

$('#machineName').keypress(function(e) {
	if (e.keyCode == 13) {
		var value = $(this).val()
		//내용이 있을경우 검색해서 값이 하나일경우 생략, 아닐경우 팝업창
		$.ajax({
			method: "GET",
			url: "equipment_check?EQUIPMENT_INFO_CODE=" + value,
			dataType: "json",
			success: function(data) {
				if (data.length == 1) {
					//검색어와 일치하는값이 있는경우
					$('#machineCode').val(data[0].equipment_INFO_CODE)
					$('#machineName').val(data[0].equipment_INFO_NAME)
				} else {
					//검색어와 일치하는값이 없는경우, 팝업창
					machinePopup(value, 'input', '');
				}
			}
		})
	}
})

//salesInputSub 커스텀 기능설정
var PR_InputEditor = function(cell, onRendered, success, cancel, editorParams) {
	//cell - 편집 가능한 셀의 셀 구성 요소
	//onRendered - 에디터가 렌더링 되었을 때 호출 할 함수
	//success - 성공적을 업데이트 된 값을 tabulator에 전달하기 위해 호출되는 함수
	//cancel - 편집을 중단하고 일반 셀로 돌아 가기 위해 호출하는 함수
	//editorParams - editorParams 열 정의 속성에 전달 된 params 객체

	//create 및 style editor
	var PR_input = document.createElement("input");

	PR_input.setAttribute("type", "text");

	//입력 생성 및 스타일 지정
	PR_input.style.padding = "3px";
	PR_input.style.width = "100%";
	PR_input.style.boxSizing = "border-box";

	//편집기의 값을 셀의 현재 값으로 설정
	if (cell.getValue() == undefined) {
		PR_input.value = "";
	} else {
		PR_input.value = cell.getValue();
	}

	//에디터가 선택되면 선택 상자에 포커스 설정 (타임 아웃은 편집기를 DOM에 추가 할 수 있음)
	onRendered(function() {
		PR_input.focus();
		PR_input.select();
		PR_input.style.css = "100%";
	});

	//값이 설정되면 업데이트 할 셀 트리거
	function onChange() {
		success(PR_input.value);
	}

	//바꼈을때 블러됫을때 함수 발동
	PR_input.addEventListener("change", onChange);
	PR_input.addEventListener("blur", onChange);

	//키버튼 이벤트
	PR_input.addEventListener("keydown", function(e) {
		if (e.keyCode == 13) {
			//수량셀
			if (cell.getField() == "production_Qty") {
				cell.nav().down();
			}
		}
	});
	//반환
	return PR_input;
};

//셀위치저장
var cellPos = null;

var proResultTable = new Tabulator("#proResultTable", {
	//페이징
	pagination: "local",
	paginationSize: 20,
	layoutColumnsOnNewData: true,
	clipboard: true,
	selectable: true,
	height: "calc(100% - 175px)",
	cellEdited: function(cell) {
		//셀위치 저장하여 포커싱부여
		cellPos = cell;

/*		$.ajax({
			method: "GET",
			url: "proResultRest/workOrderDetail",
			data: { WorkOrder_ONo: cell.getValue() },
			dataType: "json",
			success: function(data) {
				cell.getRow().update({
					production_Product_Code: data.workOrder_ItemCode,
					production_Product_Name: data.workOrder_ItemName,
					production_Equipment_Code: data.workOrder_EquipCode,
					production_Equipment_Name: data.workOrder_EquipName
				})
				cell.getRow().select();
			}
		})*/
	},
	columns: [ //Define Table Columns
		{ title: "순번", field: "rownum", formatter: "rownum", hozAlign: "center" },
		{ title: "작업지시번호", field: "production_WorkOrder_ONo", headerHozAlign: "center" },
		{ title: "제품 코드", field: "production_Product_Code", headerHozAlign: "center"},
		{ title: "제품명", field: "production_Product_Name", headerHozAlign: "center" },
		{ title: "생산 수량", field: "production_Qty", headerHozAlign: "center", hozAlign: "right", editor: PR_InputEditor },
		{ title: "설비 코드", field: "production_Equipment_Code", headerHozAlign: "center"},
		{ title: "설비 명", field: "production_Equipment_Name", headerHozAlign: "center" },
		{ title: "시간", field: "production_Date", headerHozAlign: "center" }
	]
});

//팝업창으로부터 특정 파라미터 값으로 데이터를 받는다 
function item_gridInit(PCode, PName) {
	cellPos.getRow().update({
		"production_Product_Code": PCode,
		"production_Product_Name": PName
	})
	cellPos.getElement().focus();
}

$('#SaveBtn').click(function() {
	if (confirm("선택한 행을 수정하시겠습니까?")) {
		save();
	}
})

function save() {
	selectedData = proResultTable.getData("selected");

	$.ajax({
		method: "put",
		url: "proResultRest/proResultUpdate",
		data: JSON.stringify(selectedData),
		contentType: 'application/json',
		beforeSend: function(xhr) {
			var header = $("meta[name='_csrf_header']").attr("content");
			var token = $("meta[name='_csrf']").attr("content");
			xhr.setRequestHeader(header, token);
		},
		success: function(data) {
			if (data) {
				Search()
				alert("수정되었습니다.")
			} else {
				alert("작업지시번호를 잘못 입력하셨습니다.")
			}
		}
	})
}
