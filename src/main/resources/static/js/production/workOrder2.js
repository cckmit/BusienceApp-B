//셀위치저장
var cellPos = new Array();

let maskEquipTable = new Tabulator("#maskEquipTable", {
	//페이징
	height: "calc(100% - 175px)",
	layoutColumnsOnNewData: true,
	selectable: true,
	rowClick: function(e, row) {
		row.select();
	},
	rowSelected: function(row, cell) {
		cellPos.push(row.getCell("workOrder_EquipCode"));
		console.log(cellPos);
	},
	rowAdded: function(row) {
		
	},
	columns: [
		{ formatter: "rowSelection", titleFormatter: "rowSelection", headerHozAlign: "center", hozAlign: "center", headerSort: false },
		{ title: "작업지시No", field: "workOrder_ONo", headerHozAlign: "center" },
		{ title: "마스크 설비코드", field: "workOrder_EquipCode", headerHozAlign: "center" },
		{ title: "마스크 설비명", field: "workOrder_EquipName", headerHozAlign: "center", hozAlign: "left", width: 170 },
		{ title: "제품코드", field: "workOrder_ItemCode", headerHozAlign: "center" },
		{ title: "제품명", field: "workOrder_ItemName", headerHozAlign: "center", width: 150 },
		{ title: "등록일", field: "workOrder_RegisterTime", align: "right", headerHozAlign: "center" },
		{ title: "특이사항", field: "workOrder_Remark", headerHozAlign: "center", editor: "input"	},
		{ title: "작업상태", field: "workOrder_WorkStatus_Name", headerHozAlign: "center", align:"center"},
		{ title: "사용유무", field: "workOrder_Use_Status", headerHozAlign: "center", align: "center", formatter: "tickCross", editor: true },
		{ title: "상태", field: "status", headerHozAlign: "center" }
	]
});


let packEquipTable = new Tabulator("#packEquipTable", {
	height: "calc(100% - 175px)",
	layoutColumnsOnNewData: true,
	selectable: true,
	rowClick: function(e, row) {
		row.select();
	},
	rowSelected: function(row, cell) {
		cellPos.push(row.getCell("workOrder_EquipCode"));
	},
	rowAdded: function(row) {
		if(row.getData().workOrder_ONo == undefined && row.getData().workOrder_ItemCode == undefined) {
			row.update({
				"workOrder_Use_Status": "true"
			})	
		}
	},
	columns: [
		{ formatter: "rowSelection", titleFormatter: "rowSelection", headerHozAlign: "center", hozAlign: "center", headerSort: false },
		{ title: "작업지시No", field: "workOrder_ONo", headerHozAlign: "center" },
		{ title: "포장 설비코드", field: "workOrder_EquipCode", headerHozAlign: "center" },
		{ title: "포장 설비명", field: "workOrder_EquipName", headerHozAlign: "center", hozAlign: "left", width: 170 },
		{ title: "제품코드", field: "workOrder_ItemCode", headerHozAlign: "center" },
		{ title: "제품명", field: "workOrder_ItemName", headerHozAlign: "center", width: 150 },
		{ title: "등록일", field: "workOrder_RegisterTime", align: "right", headerHozAlign: "center" },
		{ title: "특이사항", field: "workOrder_Remark", headerHozAlign: "center", editor: "input"	},
		{ title: "작업상태", field: "workOrder_WorkStatus_Name", headerHozAlign: "center", align:"center"},
		{ title: "사용유무", field: "workOrder_Use_Status", headerHozAlign: "center", align: "center", formatter: "tickCross", editor: true },
		{ title: "상태", field: "status", headerHozAlign: "center" }
	]
});

let labelEquipTable = new Tabulator("#labelEquipTable", {
	height: "calc(100% - 175px)",
	layoutColumnsOnNewData: true,
	selectable: true,
	rowClick: function(e, row) {
		row.select();
	},
	rowSelected: function(row, cell) {
		cellPos.push(row.getCell("workOrder_EquipCode"));
	},
	rowAdded: function(row) {
		if(row.getData().workOrder_ONo == undefined && row.getData().workOrder_ItemCode == undefined) {
			row.update({
				"workOrder_Use_Status": "true"
			})	
		}
	},
	columns: [
		{ formatter: "rowSelection", titleFormatter: "rowSelection", headerHozAlign: "center", hozAlign: "center", headerSort: false },
		{ title: "작업지시No", field: "workOrder_ONo", headerHozAlign: "center" },
		{ title: "라벨 프린터코드", field: "workOrder_EquipCode", headerHozAlign: "center" },
		{ title: "라벨 프린터명", field: "workOrder_EquipName", headerHozAlign: "center", hozAlign: "left", width: 170 },
		{ title: "제품코드", field: "workOrder_ItemCode", headerHozAlign: "center" },
		{ title: "제품명", field: "workOrder_ItemName", headerHozAlign: "center", width: 150 },
		{ title: "등록일", field: "workOrder_RegisterTime", align: "right", headerHozAlign: "center" },
		{ title: "특이사항", field: "workOrder_Remark", headerHozAlign: "center", editor: "input"	},
		{ title: "작업상태", field: "workOrder_WorkStatus_Name", headerHozAlign: "center", align:"center"},
		{ title: "사용유무", field: "workOrder_Use_Status", headerHozAlign: "center", align: "center", formatter: "tickCross", editor: true },
		{ title: "상태", field: "status", headerHozAlign: "center" }
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
	for (let i = 0; i < cellPos.length; i++) {
		cellPos[i].getRow().update({
			"workOrder_ItemCode": code,
			"workOrder_ItemName": name,
			"status": 1
		});

		cellPos[i].getRow().getTable().deselectRow();
	}

	cellPos.length = 0;

}

//workOrderTable 저장
function WO_Save() {
	
	let selectedData = new Array();
	
		
	if(cellPos.length == 0) {
		alert("선택된 행이 없습니다.");
	}
	
	for (let i = 0; i < cellPos.length; i++) {
		if(cellPos[i].getRow().getData().status == 1) {
			selectedData.push(cellPos[i].getRow().getData());
		}
	}
	
	console.log(selectedData);
	
	if(selectedData.length == 0) {
		alert("제품코드가 입력되지 않은 행이 존재합니다.");
		return;
	}

	//console.log(datas);

	//InputSub 저장부분
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
			}
		}
	});
}

function deleteChkFunc(equip) {

	datas = {
		MachineCode: equip
	}

	var resultData = $.ajax({
		method: "get",
		async: false,
		url: "workOrderRest/workOrderChoice",
		data: datas,
		success: function(result) {
			resultData = result;
		}
	});

	return resultData;
}

function WO_Delete(equipData) {

	//console.log(equipData);

	datas = {
		WorkOrder_EquipCode: equipData
	}

	$.ajax({
		method: "post",
		url: "workOrderRest/workOrderDelete",
		data: datas,
		beforeSend: function(xhr) {
			var header = $("meta[name='_csrf_header']").attr("content");
			var token = $("meta[name='_csrf']").attr("content");
			xhr.setRequestHeader(header, token);
		},
		success: function(result) {
			if (result == 1) {
				//console.log(result);
				alert("저장되었습니다.");
				location.reload();
				
			} else if (result == 0) {
				alert("저장 중 오류가 발생했습니다. 잠시 후 다시 시도해 주세요.");
			}
		}
	});
}

$('#WO_SaveBtn').click(function() {
	WO_Save();
})

// 설비 정보 리스트 조회
function machineListSelect(value) {

	var resultData;

	// 설비정보 불러오는 ajax
	$.ajax({
		method: "get",
		async: false,
		url: "machineManageRest/dtlMachineList",
		data: { "EQUIPMENT_TYPE": value },
		success: function(result) {
			resultData = result;
		}
	});

	return resultData;
}

$(document).ready(function() {

	let maskEquipList = machineListSelect(1);
	let packEquipList = machineListSelect(2);
	let labelEquipList = machineListSelect(3);
	//console.log(maskEquipList);
	//console.log(packEquipList);
	maskEquipTable.setData(maskEquipList)
	.then(function(){
		rowCount = maskEquipTable.getDataCount("active");
		for(let i=0; i<rowCount; i++) {
			if((maskEquipTable.getRows()[i].getData().workOrder_ONo == undefined && 
			maskEquipTable.getRows()[i].getData().workOrder_ItemCode == undefined ) {
				maskEquipTable.getRows()[i].update({
					"workOrder_Use_Status": "true"
				})	
			}
		}
		
	})
	
	packEquipTable.setData(packEquipList)
	.then(function(){
		rowCount = packEquipTable.getDataCount("active");
		for(let i=0; i<rowCount; i++) {
			if(packEquipTable.getRows()[i].getData().workOrder_ONo == undefined && 
			packEquipTable.getRows()[i].getData().workOrder_ItemCode == undefined) {
				packEquipTable.getRows()[i].update({
					"workOrder_Use_Status": "true"
				})	
			}
		}
		
	})
	
	labelEquipTable.setData(labelEquipList)
	.then(function(){
		rowCount = labelEquipTable.getDataCount("active");
		for(let i=0; i<rowCount; i++) {
			if(labelEquipTable.getRows()[i].getData().workOrder_ONo == undefined && 
			labelEquipTable.getRows()[i].getData().workOrder_ItemCode == undefined) {
				labelEquipTable.getRows()[i].update({
					"workOrder_Use_Status": "true"
				})	
			}
		}
		
	})
})
