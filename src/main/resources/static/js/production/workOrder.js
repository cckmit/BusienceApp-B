//셀위치저장
var cellPos = new Array();

let maskEquipTable = new Tabulator("#maskEquipTable", {
	//페이징
	height: "calc(100% - 175px)",
	layoutColumnsOnNewData: true,
	selectable: true,
	rowFormatter: function(row) {
		if (row.getData().status == '1')
			row.getElement().style.color = "blue";
	},
	rowClick: function(e, row) {
		packEquipTable.deselectRow();
		labelEquipTable.deselectRow();
		row.select();
	},
	rowSelected: function(row, cell) {
		cellPos.push(row.getCell("equip_WorkOrder_Code"));
	},
	ajaxURL: "machineManageRest/dtlMachineList",
	ajaxParams: { "EQUIPMENT_TYPE": 1 },
	ajaxConfig: "get",
	ajaxContentType: "json",
	columns: [
		{
			title: "마스크", field: "machineType", headerHozAlign: "center", 
			columns: [
				{ formatter: "rowSelection", titleFormatter: "rowSelection", headerHozAlign: "center", hozAlign: "center", headerSort: false },
				{ title: "코드", field: "workOrder_EquipCode", headerHozAlign: "center" },
				{ title: "설비명", field: "workOrder_EquipName", headerHozAlign: "center", hozAlign: "left" },
				{ title: "코드", field: "workOrder_ItemCode", headerHozAlign: "center" },
				{ title: "제품명", field: "workOrder_ItemName", headerHozAlign: "center", width: 170 },
				{ title: "분류1", field: "workOrder_Item_CLSFC_1", headerHozAlign: "center" },
				{ title: "분류2", field: "workOrder_Item_CLSFC_2", headerHozAlign: "center", hozAlign: "left" },
				{ title: "규격1", field: "workOrder_STND_1", headerHozAlign: "center" },
				{ title: "규격2", field: "workOrder_STND_2", headerHozAlign: "center"},
				{ title: "재질", field: "workOrder_Material", headerHozAlign: "center"},
				{ title: "단위", field: "workOrder_Unit", headerHozAlign: "center"},
				{ title: "상태", field: "status", headerHozAlign: "center", visible: false }
			]
		},

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
	rowClick: function(e, row) {
		maskEquipTable.deselectRow();
		labelEquipTable.deselectRow();
		row.select();
	},
	rowSelected: function(row, cell) {
		cellPos.push(row.getCell("equip_WorkOrder_Code"));
	},
	ajaxURL: "machineManageRest/dtlMachineList",
	ajaxParams: { "EQUIPMENT_TYPE": 2 },
	ajaxConfig: "get",
	ajaxContentType: "json",
	columns: [
		{
			title: "포장", field: "machineType", headerHozAlign: "center",
			columns: [
				{ formatter: "rowSelection", titleFormatter: "rowSelection", headerHozAlign: "center", hozAlign: "center", headerSort: false },
				{ title: "코드", field: "workOrder_EquipCode", headerHozAlign: "center" },
				{ title: "설비명", field: "workOrder_EquipName", headerHozAlign: "center", hozAlign: "left" },
				{ title: "코드", field: "workOrder_ItemCode", headerHozAlign: "center" },
				{ title: "제품명", field: "workOrder_ItemName", headerHozAlign: "center", width: 170 },
				{ title: "분류1", field: "workOrder_Item_CLSFC_1", headerHozAlign: "center" },
				{ title: "분류2", field: "workOrder_Item_CLSFC_2", headerHozAlign: "center", hozAlign: "left" },
				{ title: "규격1", field: "workOrder_STND_1", headerHozAlign: "center" },
				{ title: "규격2", field: "workOrder_STND_2", headerHozAlign: "center"},
				{ title: "재질", field: "workOrder_Material", headerHozAlign: "center"},
				{ title: "단위", field: "workOrder_Unit", headerHozAlign: "center"},
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
	rowClick: function(e, row) {
		packEquipTable.deselectRow();
		maskEquipTable.deselectRow();
		row.select();
	},
	rowSelected: function(row, cell) {
		cellPos.push(row.getCell("equip_WorkOrder_Code"));
	},
	ajaxURL: "machineManageRest/dtlMachineList",
	ajaxParams: { "EQUIPMENT_TYPE": 3 },
	ajaxConfig: "get",
	ajaxContentType: "json",
	columns: [
		{
			title: "라벨", field: "machineType", headerHozAlign: "center",
			columns: [
				{ formatter: "rowSelection", titleFormatter: "rowSelection", headerHozAlign: "center", hozAlign: "center", headerSort: false },
				{ title: "코드", field: "workOrder_EquipCode", headerHozAlign: "center" },
				{ title: "설비명", field: "workOrder_EquipName", headerHozAlign: "center", hozAlign: "left" },
				{ title: "코드", field: "workOrder_ItemCode", headerHozAlign: "center" },
				{ title: "제품명", field: "workOrder_ItemName", headerHozAlign: "center", width: 170 },
				{ title: "분류1", field: "workOrder_Item_CLSFC_1", headerHozAlign: "center" },
				{ title: "분류2", field: "workOrder_Item_CLSFC_2", headerHozAlign: "center", hozAlign: "left" },
				{ title: "규격1", field: "workOrder_STND_1", headerHozAlign: "center" },
				{ title: "규격2", field: "workOrder_STND_2", headerHozAlign: "center"},
				{ title: "재질", field: "workOrder_Material", headerHozAlign: "center"},
				{ title: "단위", field: "workOrder_Unit", headerHozAlign: "center"},
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
						if (maskSelectedData.length > 0) {
							itemPopup(value, 'workOrder', '', 'workMask');
						} else if (packSelectedData.length > 0) {
							itemPopup(value, 'workOrder', '', 'workNonMask');
						} else if (labelSelectedData.length > 0) {
							itemPopup(value, 'workOrder', '', 'workNonMask');
						}
					}
				}
			})
		}
	}
})

$('#PRODUCT_ITEM_NAME2').keypress(function(e) {
	if (e.keyCode == 13) {
		let value = $(this).val()

		let labelSelectedData = labelEquipTable.getData("selected");

		if (labelSelectedData.length < 1) {
			alert("라벨 프린터를 선택하세요.");
			return
		} else {

			//내용이 있을경우 검색해서 값이 하나일경우 생략, 아닐경우 팝업창
			$.ajax({
				method: "GET",
				url: "product_stnd2_check?Product_Stnd2=" + value,
				dataType: "json",
				success: function(data) {
					if (data.length == 1) {
						//검색어와 일치하는값이 있는경우
						$('#itemCode').val(data[0].product_ITEM_CODE);
						$('#itemName').val(data[0].production_Product_Name);
					} else {
						//검색어와 일치하는값이 없는경우, 팝업창
						if (labelSelectedData.length > 0) {
							itemPopup(value, 'workOrder', '', 'workLabel');
						}
					}
				}
			})
		}
	}
})

//품목선택시 자동 입력
function item_gridInit(code, name, clsfc1, clsfc2, stnd1, stnd2, unit, material) {

	let maskSelectedRows = maskEquipTable.getRows("selected");
	for (let i = 0; i < maskSelectedRows.length; i++) {
		maskSelectedRows[i].update({
			"workOrder_ItemCode": code,
			"workOrder_ItemName": name,
			"workOrder_Item_CLSFC_1": clsfc1,
			"workOrder_Item_CLSFC_2": clsfc2,
			"workOrder_STND_1": stnd1,
			"workOrder_STND_2": stnd2,
			"workOrder_Unit": unit,
			"workOrder_Material": material,
			"status": 1
		})
	}
	maskEquipTable.deselectRow();

	let packSelectedRows = packEquipTable.getRows("selected");
	for (let i = 0; i < packSelectedRows.length; i++) {
		packSelectedRows[i].update({
			"workOrder_ItemCode": code,
			"workOrder_ItemName": name,
			"workOrder_Item_CLSFC_1": clsfc1,
			"workOrder_Item_CLSFC_2": clsfc2,
			"workOrder_STND_1": stnd1,
			"workOrder_STND_2": stnd2,
			"workOrder_Unit": unit,
			"workOrder_Material": material,
			"status": 1
		})
	}
	packEquipTable.deselectRow();

	let labelSelectedRows = labelEquipTable.getRows("selected");
	for (let i = 0; i < labelSelectedRows.length; i++) {
		labelSelectedRows[i].update({
			"workOrder_ItemCode": code,
			"workOrder_ItemName": name,
			"workOrder_Item_CLSFC_1": clsfc1,
			"workOrder_Item_CLSFC_2": clsfc2,
			"workOrder_STND_1": stnd1,
			"workOrder_STND_2": stnd2,
			"workOrder_Unit": unit,
			"workOrder_Material": material,
			"status": 1
		})
	}
	labelEquipTable.deselectRow();
}

/*function ppCheck(){
	let packSelectedRows = packEquipTable.getRows("selected");
	let labelSelectedRows = labelEquipTable.getRows("selected");
	
	var threeItem = new Object();
	console.log(packSelectedRows);
	threeItem[packSelectedRows.getData().workOrder_ItemCode] = threeItem[packSelectedRows.getData().workOrder_ItemCode]++;
	console.log(threeItem);
}*/

function product_check(value) {
	//쿼리실행
	$.ajax({
		method: "GET",
		async: false,
		url: "product_check?PRODUCT_ITEM_CODE=" + value,
		dataType: "json",
		success: function(pc_data) {
			console.log(pc_data);
			if (pc_data.length == 1) {
				//검색어와 일치하는값이 있는경우
				$('#fgoodsCode').val(pc_data[0].product_ITEM_CODE);
				$('#fgoodsName').val(pc_data[0].product_ITEM_NAME);
			} else {
				//검색어와 일치하는값이 없는경우, 팝업창
				itemFinishedPopup(value, 'input', '');
			}
		}
	})
}

//workOrderTable 저장
function WO_Save() {
	// 보낼 데이터를 전부 담을 배열(조건에 부합하는 데이터 전부)
	let selectedData = new Array();

	// 검증
	let packData = packEquipTable.getDataCount();
	let labelData = labelEquipTable.getDataCount("active");
	let maskData = maskEquipTable.getDataCount();

	// 배열에 데이터 담기
	for (let i = 0; i < maskData; i++) {
		if (maskEquipTable.getData()[i].status == 1) {
			selectedData.push(maskEquipTable.getData()[i]);
		}
	}

	for (let k = 0; k < packData; k++) {
		if (packEquipTable.getData()[k].status == 1) {
			selectedData.push(packEquipTable.getData()[k]);
		}
	}

	for (let j = 0; j < labelData; j++) {
		if (labelEquipTable.getData()[j].status == 1) {
			selectedData.push(labelEquipTable.getData()[j]);
		}
	}

	if (selectedData.length == 0) {
		alert("제품이 등록된 행이 없습니다.");
		return;
	}

	//작업지시 등록 저장
	$.ajax({
		method: "post",
		url: "equipWorkOrderRest/equipOrderUpdate",
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

