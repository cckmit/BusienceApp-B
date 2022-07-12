let cellPos;

var editCheck = function(cell) {
	//cell - the cell component for the editable cell

	//get row data
	var data = cell.getRow().getData();

	return data.s_LotNo == undefined; // only allow the name cell to be edited if the age is over 18
}

var MSC_inputEditor = function(cell, onRendered, success, cancel, editorParams) {
	//cell - 편집 가능한 셀의 셀 구성 요소
	//onRendered - 에디터가 렌더링 되었을 때 호출 할 함수
	//success - 성공적을 업데이트 된 값을 tabulator에 전달하기 위해 호출되는 함수
	//cancel - 편집을 중단하고 일반 셀로 돌아 가기 위해 호출하는 함수
	//editorParams - editorParams 열 정의 속성에 전달 된 params 객체

	//create 및 style editor
	var MSC_input = document.createElement("input");

	MSC_input.setAttribute("type", "text");

	//입력 생성 및 스타일 지정
	MSC_input.style.padding = "3px";
	MSC_input.style.width = "100%";
	MSC_input.style.boxSizing = "border-box";

	//편집기의 값을 셀의 현재 값으로 설정
	if (cell.getValue() == undefined) {
		MSC_input.value = "";
	} else {
		MSC_input.value = cell.getValue();
	}

	//에디터가 선택되면 선택 상자에 포커스 설정 (타임 아웃은 편집기를 DOM에 추가 할 수 있음)
	onRendered(function() {
		MSC_input.focus();
		MSC_input.select();
		MSC_input.style.css = "100%";
	});

	//값이 설정되면 업데이트 할 셀 트리거
	function onChange() {
		success(MSC_input.value);
	}

	//바꼈을때 블러됫을때 함수 발동
	MSC_input.addEventListener("change", onChange);
	MSC_input.addEventListener("blur", onChange);

	//키버튼 이벤트
	MSC_input.addEventListener("keydown", function(e) {
		if (e.keyCode == 13) {
			//거래처코드셀 체크
			if (cell.getField() == "s_ItemCode") {
				//내용이 있을경우 검색해서 값이 하나일경우 생략, 아닐경우 팝업창
				$.ajax({
					method: "GET",
					url: "product_check?PRODUCT_ITEM_CODE=" + MSC_input.value,
					dataType: "json",
					success: function(data) {
						if (data.length == 1) {
							//검색어와 일치하는값이 있는경우
							cell.getRow().update({
								"s_ItemCode": data[0].product_ITEM_CODE,
								"s_ItemName": data[0].product_ITEM_NAME,
								"s_Item_Standard_1": data[0].product_INFO_STND_1
							});
						} else {
							//검색어와 일치하는값이 없는경우, 팝업창
							itemPopup(MSC_input.value, 'grid', '', 'material');
						}
					}
				})
			}

			if (cell.getField() == "s_ChangeQty") {
				//내용이 있을경우 검색해서 값이 하나일경우 생략, 아
				cell.getRow().select();
			}

			cell.nav().next();
		}
	});
	//반환
	return MSC_input;
}

var matStockChangeTable = new Tabulator("#matStockChangeTable", {
	headerFilterPlaceholder: null,
	height: "calc(100% - 175px)",
	//행추가시 기능
	rowAdded: function(row) {
		row.getTable().deselectRow();
		row.select();

		//행이 추가되면 첫셀에 포커스
		do {
			setTimeout(function() {
				row.getCell("s_ItemCode").edit();
			}, 100);
		}
		while (row.getData().s_ItemCode === "undefined");
	},
	cellEditing: function(cell) {
		//셀위치 저장하여 포커싱부여
		cellPos = cell;
	},
	columns: [ //Define Table Columns
		{ formatter: "rowSelection", titleFormatter: "rowSelection", headerHozAlign: "center", hozAlign: "center", headerSort: false },
		{ title: "순번", field: "rownum", headerHozAlign: "center", hozAlign: "center", formatter: "rownum" },
		{ title: "LotNo", field: "s_LotNo", headerHozAlign: "center", headerFilter: true, hozAlign: "left" },
		{ title: "품목코드", field: "s_ItemCode", headerHozAlign: "center", headerFilter: true, hozAlign: "left", editor: MSC_inputEditor, editable: editCheck },
		{ title: "품명", field: "s_ItemName", headerHozAlign: "center", headerFilter: true, hozAlign: "left" },
		{ title: "규격1", field: "s_Item_Standard_1", headerHozAlign: "center", headerFilter: true, hozAlign: "left" },
		{ title: "재질", field: "s_Item_Material", headerHozAlign: "center", headerFilter: true, hozAlign: "center" },
		{ title: "단위", field: "s_Item_Unit", headerHozAlign: "center", headerFilter: true, hozAlign: "left" },
		{ title: "재고수량", field: "s_Qty", headerHozAlign: "center", hozAlign: "right" },
		{ title: "변경수량", field: "s_ChangeQty", headerHozAlign: "center", hozAlign: "right", editor: MSC_inputEditor,
		cellEdited:function(cell){
			//수량이 변경될때 금액값이 계산되어 입력
			temReturnQty = cell.getValue();
			temCode = cell.getRow().getData().s_ItemCode;	
			temQty = cell.getRow().getData().s_Qty;			
	
			if(temCode.charAt(0) == 'A'){
				if(temQty == 0) {
					if(temReturnQty != 1) {
						alert("1만 입력 가능합니다.");
						cell.getRow().update({"s_ChangeQty": 1});
					}
				} else if(temQty == 1) {
					if(temReturnQty != 0) {
						alert("0만 입력 가능합니다.");
						cell.getRow().update({"s_ChangeQty": 0});
					}
				}
			}
		}}
	]
});

function changeFunc() {

  let chv = document.querySelector('#inMatTypeListSelectBox').value;	
	
  MSC_SearchBtn(chv);
}

function MSC_SearchBtn(chv) {
	
	s_WareHouse = chv;
	
	datas = {
		s_WareHouse: s_WareHouse
	}
	
	console.log(datas)

	matStockChangeTable.setData("matStockRest/matStockChangeSelect", datas);
	console.log(matStockChangeTable);
}

// ADD버튼을 클릭할때 모달창을 여는 이벤트
$("#stockChangeAddBtn").click(function() {
	matStockChangeTable.addRow();

});

//팝업창으로부터 특정 파라미터 값으로 데이터를 받는다 
function item_gridInit(PCode, PName, PSTND_1, UNIT_Price, UNIT_Name, Material_Name) {
	console.log("dd");
	cellPos.getRow().update({
		"s_ItemCode": PCode,
		"s_ItemName": PName,
		"s_Item_Standard_1": PSTND_1,
		"s_Item_Unit": UNIT_Name,
		"s_Item_Material": Material_Name
	});
	//선택 후 포커스 이동
	cellPos.getElement().focus();
}

// ADD버튼을 클릭할때 모달창을 여는 이벤트
$("#stockChangeSaveBtn").click(function() {
	stockChangeSave();

});

function stockChangeSave() {

	let selectedRow = matStockChangeTable.getData("selected");
	let subData = matStockChangeTable.getData("selected");
	console.log(selectedRow);

	if (selectedRow.length == 0) {
		alert("저장할 행을 선택하세요.");
		return;
	}

	for (let i = 0; i < selectedRow.length; i++) {
		if (selectedRow[i].s_ChangeQty == undefined) {
			console.log(selectedRow[i].s_ChangeQty);
			alert("변경수량을 입력하세요.");
			return;
		}
	}
	
	for (let j = 0; j < selectedRow.length; j++) {
		if (selectedRow[j].s_Qty == selectedRow[j].s_ChangeQty) {
			alert("재고수량과 변경수량은 같을 수 없습니다.");
			matStockChangeTable.updateRow(matStockChangeTable.getRows("selected")[i], {"s_ChangeQty":null});
			matStockChangeTable.deselectRow();
		}
	}

	let subTable = new Array();

	for (let i = 0; i < selectedRow.length; i++) {
		subTable.push({
			rs_ItemCode: selectedRow[i].s_ItemCode,
			rs_Qty: selectedRow[i].s_ChangeQty
		})
	}

	


	console.log(subTable);

	//OrderSub 저장부분
	$.ajax({
		method: "post",
		url: "matStockRest/matStockChangeSave",
		data: { masterData: JSON.stringify(selectedRow), requestlistData: JSON.stringify(subTable) },
		beforeSend: function(xhr) {
			var header = $("meta[name='_csrf_header']").attr("content");
			var token = $("meta[name='_csrf']").attr("content");
			xhr.setRequestHeader(header, token);
		},
		success: function(result) {
			if (result) {
				alert("저장되었습니다.");
				MSC_SearchBtn();
			} else {
				alert("빈칸이 있어서 저장할 수 없습니다.");
			}
		}
	});


}

$(document).ready(function() {
	document.querySelector('#inMatTypeListSelectBox').value = '50';	
	let s_WareHouse = document.querySelector('#inMatTypeListSelectBox').value;
	MSC_SearchBtn(s_WareHouse);
});
