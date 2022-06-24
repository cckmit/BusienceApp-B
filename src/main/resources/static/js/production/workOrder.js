//셀위치저장
var cellPos = new Array();

var editCheck = function(cell) {
	//cell - the cell component for the editable cell

	//get row data
	var data = cell.getRow().getData();

	return data.c_Create_Date == undefined; // only allow the name cell to be edited if the age is over 18
}

let maskEquipTable = new Tabulator("#maskEquipTable", {
	//페이징
	height: "calc(100% - 175px)",
	layoutColumnsOnNewData: true,
	selectable: true,
	rowFormatter: function(row) {
		if (row.getData().status == '1')
			row.getElement().style.color = "blue";
	},
	rowSelected: function(row, cell) {
		cellPos.push(row.getCell("workOrder_EquipCode"));
	},
	ajaxURL:"machineManageRest/dtlMachineList",
	ajaxParams: {"EQUIPMENT_TYPE": 1},
    ajaxConfig:"get",
    ajaxContentType:"json",
	columns:[
		{title: "마스크", field: "machineType", headerHozAlign:"center",
			columns: [
				{ formatter: "rowSelection", titleFormatter: "rowSelection", headerHozAlign: "center", hozAlign: "center", headerSort: false },
				{ title: "설비코드", field: "workOrder_EquipCode", headerHozAlign: "center" },
				{ title: "설비명", field: "workOrder_EquipName", headerHozAlign: "center", hozAlign: "left" },
				{ title: "제품코드", field: "workOrder_ItemCode", headerHozAlign: "center" },
				{ title: "제품명", field: "workOrder_ItemName", headerHozAlign: "center", width: 150 },
				{ title: "상태", field: "status", headerHozAlign: "center", visible: false }
			]
		}
	]
});


let packEquipTable = new Tabulator("#packEquipTable", {
	height: "calc(100% - 175px)",
	layoutColumnsOnNewData: true,
	selectable: true,
	rowFormatter: function(row) {
		if (row.getData().status == '1')
			row.getElement().style.color = "blue";
	},
	rowSelected: function(row, cell) {
		cellPos.push(row.getCell("workOrder_EquipCode"));
	},
	ajaxURL:"machineManageRest/dtlMachineList",
	ajaxParams: {"EQUIPMENT_TYPE": 2},
    ajaxConfig:"get",
    ajaxContentType:"json",
	columns:[
		{title: "포장", field: "machineType", headerHozAlign:"center",
			columns: [
				{ formatter: "rowSelection", titleFormatter: "rowSelection", headerHozAlign: "center", hozAlign: "center", headerSort: false },
				{ title: "설비코드", field: "workOrder_EquipCode", headerHozAlign: "center" },
				{ title: "설비명", field: "workOrder_EquipName", headerHozAlign: "center", hozAlign: "left" },
				{ title: "제품코드", field: "workOrder_ItemCode", headerHozAlign: "center" },
				{ title: "제품명", field: "workOrder_ItemName", headerHozAlign: "center", width: 150  },
				{ title: "상태", field: "status", headerHozAlign: "center", visible: false }
			]
		}
	]
});

let labelEquipTable = new Tabulator("#labelEquipTable", {
	height: "calc(100% - 175px)",
	layoutColumnsOnNewData: true,
	selectable: true,
	rowFormatter: function(row) {
		if (row.getData().status == '1')
			row.getElement().style.color = "blue";
	},
	rowSelected: function(row, cell) {
		cellPos.push(row.getCell("workOrder_EquipCode"));
	},
	ajaxURL:"machineManageRest/dtlMachineList",
	ajaxParams: {"EQUIPMENT_TYPE": 3},
    ajaxConfig:"get",
    ajaxContentType:"json",
	columns:[
		{title: "라벨", field: "machineType", headerHozAlign:"center",
			columns: [
				{ formatter: "rowSelection", titleFormatter: "rowSelection", headerHozAlign: "center", hozAlign: "center", headerSort: false },
				{ title: "프린터코드", field: "workOrder_EquipCode", headerHozAlign: "center" },
				{ title: "프린터명", field: "workOrder_EquipName", headerHozAlign: "center", hozAlign: "left" },
				{ title: "제품코드", field: "workOrder_ItemCode", headerHozAlign: "center" },
				{ title: "제품명", field: "workOrder_ItemName", headerHozAlign: "center", width: 150  },
				{ title: "상태", field: "status", headerHozAlign: "center", visible: false }
			]
		}
	]
});

$('#PRODUCT_ITEM_NAME').keypress(function(e) {
	if (e.keyCode == 13) {
		let value = $(this).val()

		let maskSelectedData = maskEquipTable.getData("selected");
		let packSelectedData = packEquipTable.getData("selected");
		let labelSelectedData = labelEquipTable.getData("selected");

		if (maskSelectedData.length < 1 && packSelectedData.length < 1 && labelSelectedData.length < 1) {
			alert("설비를 선택하세요.");
			return
		} else {
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
						itemPopup(value, 'grid', '', 'material');
					}
				}
			})
		}
	}
})

//품목선택시 자동 입력
function item_gridInit(code, name) {
		
	let maskSelectedRows = maskEquipTable.getRows("selected");
	for(let i=0;i<maskSelectedRows.length;i++){
		maskSelectedRows[i].update({
			"workOrder_ItemCode": code,
			"workOrder_ItemName": name,
			"status": 1
		})
	}
	maskEquipTable.deselectRow();
	
	let packSelectedRows = packEquipTable.getRows("selected");
	for(let i=0;i<packSelectedRows.length;i++){
		packSelectedRows[i].update({
			"workOrder_ItemCode": code,
			"workOrder_ItemName": name,
			"status": 1
		})
	}
	packEquipTable.deselectRow();
	
	let labelSelectedRows = labelEquipTable.getRows("selected");
	for(let i=0;i<labelSelectedRows.length;i++){
		labelSelectedRows[i].update({
			"workOrder_ItemCode": code,
			"workOrder_ItemName": name,
			"status": 1
		})
	}
	labelEquipTable.deselectRow();
}

function ppCheck(){
	let packSelectedRows = packEquipTable.getRows("selected");
	let labelSelectedRows = labelEquipTable.getRows("selected");
	
	var threeItem = new Object();
	console.log(packSelectedRows);
	threeItem[packSelectedRows.getData().workOrder_ItemCode] = threeItem[packSelectedRows.getData().workOrder_ItemCode]++;
	console.log(threeItem);
}

//workOrderTable 저장
function WO_Save() {
	// 보낼 데이터를 전부 담을 배열(조건에 부합하는 데이터 전부)
	let selectedData = new Array();

	// 검증
	let packData = packEquipTable.getDataCount();
	let labelData = labelEquipTable.getDataCount("active");
	let maskData = maskEquipTable.getDataCount();
	let labelArray = new Array();
	let packArray = new Array();
	let elementArray = new Array();
	let itemArray = new Array();
	
	for (let j = 0; j < labelData; j++) {
		labelArray.push(labelEquipTable.getData()[j].workOrder_ItemCode);
	}

	for (let i = 0; i < packData; i++) {
		packArray.push(packEquipTable.getData()[i].workOrder_ItemCode);
	}
	
	// 1. 포장 설비는 최대 제품 3개까지만 등록 가능
	packArray.forEach((element) => {
		if(element != null) {
			if(!itemArray.includes(element)) {
				itemArray.push(element);
			}
		}
	});
	
	if(itemArray.length > 3) {
		alert("포장 설비 오류: 제품 종류 3개 이상 등록할 수 없습니다.");
		return;
	}
	
	// 2. 라벨 프린터 코드 목록에 제품과 포장 설비의 제품이 일치하는지 확인
	labelArray.forEach((element) => {
		if (!packArray.includes(element)) {
			elementArray.push(element);
		}
	})

	if (elementArray.length > 0) {
		alert("라벨 프린터 오류: 포장설비와 같은 제품을 등록하세요.");
		return;
	}
	
	// 배열에 데이터 담기
	for (let i = 0; i < maskData; i++) {
		if(maskEquipTable.getData()[i].status == 1) {
			selectedData.push(maskEquipTable.getData()[i]);
		}
	}

	for (let k = 0; k < packData; k++) {
		if(packEquipTable.getData()[k].status == 1) {
			selectedData.push(packEquipTable.getData()[k]);
		}
	}
	
	for (let j = 0; j < labelData; j++) {
		if(labelEquipTable.getData()[j].status == 1) {
			selectedData.push(labelEquipTable.getData()[j]);
		}
	}
	
	if(selectedData.length == 0) {
		alert("제품이 등록된 행이 없습니다.");
		return;
	}

	//작업지시 등록 저장
	$.ajax({
		method: "post",
		url: "workOrderRest/workOrderRegister",
		data: JSON.stringify(selectedData),
		contentType: 'application/json',
		beforeSend: function(xhr) {
			var header = $("meta[name='_csrf_header']").attr("content");
			var token = $("meta[name='_csrf']").attr("content");
			xhr.setRequestHeader(header, token);
		},
		success: function(result) {
			//console.log(result);
			if (result == 0) {
				alert("중복된 값이 있습니다.");
			} else if (result == 1) {
				alert("저장되었습니다.");
				maskEquipTable.replaceData();
				packEquipTable.replaceData();
				labelEquipTable.replaceData();
			}
		}
	});
}

$('#WO_SaveBtn').click(function() {
	WO_Save();
})

window.onload = function(){
	
	setInterval(function(){
		//ppCheck()
	},2000);
}
