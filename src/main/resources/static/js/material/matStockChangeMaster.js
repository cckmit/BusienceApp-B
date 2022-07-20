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
			if (cell.getField() == "lm_ChangeQty") {
				cell.getRow().select();
			}

			cell.nav().next();
		}
	});
	//반환
	return MSC_input;
}

var stockListTable = new Tabulator("#stockListTable", {
	headerFilterPlaceholder: null,
	ajaxConfig : "get",
	ajaxContentType:"json",
	ajaxURL : "matStockRest/stockSelect",
	ajaxParams : {warehouse: $("#inMatTypeListSelectBox").val(), check : true},
	height: "calc(100% - 175px)",	
	rowSelected:function(row){
    	stockChangeSearch(row.getData().s_ItemCode);
		UseBtn();
    },
	//행클릭 이벤트
	rowClick:function(e, row){
		row.getTable().deselectRow();
		row.select();
    },
	columns: [
		{ title: "순번", field: "rownum", headerHozAlign: "center", hozAlign: "center", formatter: "rownum" },
		{ title: "품목코드", field: "s_ItemCode", headerHozAlign: "center", headerFilter: true, hozAlign: "left"},
		{ title: "품명", field: "s_ItemName", headerHozAlign: "center", headerFilter: true, hozAlign: "left" },
		{ title: "규격1", field: "s_Item_Standard_1", headerHozAlign: "center", headerFilter: true, hozAlign: "left" },
		{ title: "재질", field: "s_Item_Material_Name", headerHozAlign: "center", headerFilter: true, hozAlign: "center" },
		{ title: "단위", field: "s_Item_Unit_Name", headerHozAlign: "center", headerFilter: true, hozAlign: "left" },
		{ title: "재고수량", field: "s_Qty", headerHozAlign: "center", hozAlign: "right"}
	]
});

var stockChangeTable = new Tabulator("#stockChangeTable", {
	headerFilterPlaceholder: null,
	selectable : true,
	selectableRangeMode:"click",
	height: "calc(100% - 175px)",
	//행추가시 기능
	rowAdded: function(row) {
		row.select();
		
		stockChangeTable.scrollToRow(row, "nearest", false)
		.then(function(){
			//행이 추가되면 첫셀에 포커스
			do {
				setTimeout(function() {
					row.getCell("lm_ChangeQty").edit();
				}, 100);
			}
			while (row.getData().lm_ChangeQty === "undefined");
		})
	},
	columns: [ 
		{ formatter: "rowSelection", titleFormatter: "rowSelection", headerHozAlign: "center", hozAlign: "center", headerSort: false },
		{ title: "순번", field: "rownum", headerHozAlign: "center", hozAlign: "center", formatter: "rownum" },
		{ title: "LotNo", field: "lm_LotNo", headerHozAlign: "center", headerFilter: true, hozAlign: "left" },
		{ title: "품목코드", field: "lm_ItemCode", headerHozAlign: "center", headerFilter: true, hozAlign: "left"},
		{ title: "품명", field: "lm_ItemName", headerHozAlign: "center", headerFilter: true, hozAlign: "left" },
		{ title: "규격1", field: "lm_STND_1", headerHozAlign: "center", headerFilter: true, hozAlign: "left" },
		{ title: "재질", field: "lm_Item_Material_Name", headerHozAlign: "center", headerFilter: true, hozAlign: "center" },
		{ title: "단위", field: "lm_Item_Unit_Name", headerHozAlign: "center", headerFilter: true, hozAlign: "left" },
		{ title: "재고수량", field: "lm_Qty", headerHozAlign: "center", hozAlign: "right" },
		{ title: "변경수량", field: "lm_ChangeQty", headerHozAlign: "center", hozAlign: "right", editor: MSC_inputEditor,
			cellEdited:function(cell){
	            //수량이 변경될때 금액값이 계산되어 입력
	            temReturnQty = cell.getValue();
	            temCode = cell.getRow().getData().lm_ItemCode;    
	            temQty = cell.getRow().getData().lm_Qty;            
	    
	            if(temCode.charAt(0) == 'A'){
	                if(temQty == 0) {
	                    if(temReturnQty != 1) {
	                        alert("1만 입력 가능합니다.");
	                        cell.getRow().update({"lm_ChangeQty": 1});
	                    }
	                } else if(temQty == 1) {
	                    if(temReturnQty != 0) {
	                        alert("0만 입력 가능합니다.");
			                cell.getRow().update({"lm_ChangeQty": 0});
		                }
					}
				}
			}
		}
	]
});
$("#inMatTypeListSelectBox").change(function(){
	stockListTable.setData("matStockRest/stockSelect",
		{warehouse: $("#inMatTypeListSelectBox").val(), check : true});
})

function stockChangeSearch(itemCode){
	stockChangeTable.setData("matStockRest/stockChangeSelect",
	{itemCode: itemCode, warehouse: $("#inMatTypeListSelectBox").val()});
}

$("#stockChangeAddBtn").click(function() {
	var selectedRow = stockListTable.getData("selected")
	if(selectedRow.length>0){
		stockChangeTable.addRow({
			lm_ItemCode : selectedRow[0].s_ItemCode,
			lm_ItemName : selectedRow[0].s_ItemName,
			lm_STND_1 : selectedRow[0].s_Item_Standard_1,
			lm_Item_Material_Name : selectedRow[0].s_Item_Material_Name,
			lm_Item_Unit_Name : selectedRow[0].s_Item_Unit_Name,
			lm_Qty : 0});
	}else{
		alert("행을 선택해주세요.")
	}
});

$("#stockChangeSaveBtn").click(function() {
	if(confirm("저장하시겠습니까?")){
		stockChangeSave();	
	}
});

function stockChangeSave() {
	var selectedData = stockChangeTable.getData("selected");

	if (selectedData.length == 0) {
		alert("저장할 행을 선택하세요.");
		return;
	}
	
	for (let j = 0; j < selectedData.length; j++) {
		if (selectedData[j].lm_ChangeQty == undefined) {
			alert("변경수량을 입력하세요.");
			return;
		}
		
		if (selectedData[j].lm_Qty == selectedData[j].lm_ChangeQty) {
			alert("재고수량과 변경수량은 같을 수 없습니다.");
		}
	}
	
	$.ajax({
		method: "post",
		url: "matStockRest/matStockChangeSave",
		data: {selectedData: JSON.stringify(selectedData), warehouse: $("#inMatTypeListSelectBox").val()},
		beforeSend: function(xhr) {
			var header = $("meta[name='_csrf_header']").attr("content");
			var token = $("meta[name='_csrf']").attr("content");
			xhr.setRequestHeader(header, token);
		},
		success: function(result) {
			if (result) {
				RawMaterialPrinter(result);
				stockListTable.replaceData();
				stockChangeTable.clearData();
				alert("저장되었습니다.");
			} else {
				alert("빈칸이 있어서 저장할 수 없습니다.");
			}
		}
	});
}