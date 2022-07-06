//셀위치저장
var cellPos = new Array();

//공통
function dtlSelectList() {
	var label_arr = new Object();

	$.ajax({
		method: "get",
		async: false,
		url: "machineManageRest/labelMachineList",
		success: function(datas) {
			console.log(datas);
			for (i = 0; i < datas.length; i++) {
				label_arr[datas[i].equipment_INFO_CODE] = datas[i].equipment_INFO_CODE;

			}
		}
	});

	return label_arr;
}

let dtl_arr_material = dtlSelectList();

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
		row.select();
	},
	rowSelected: function(row, cell) {
		cellPos.push(row.getCell("workOrder_EquipCode"));
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
				{ title: "재질", field: "workOrder_Material", headerHozAlign: "center" },
				{ title: "단위", field: "workOrder_Unit", headerHozAlign: "center" },
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
		row.select();
	},
	rowSelected: function(row, cell) {
		cellPos.push(row.getCell("workOrder_EquipCode"));
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
				{
					title: "라벨코드", field: "workOrder_SubCode", headerHozAlign: "center", editor: "select", headerFilterParams: dtl_arr_material,
					formatter: function(cell, formatterParams) {
						var value = cell.getValue();
						if (dtl_arr_material[value] != null) {
							value = dtl_arr_material[value];

							// 라벨 코드가 같은 것이 있는 지 검색
							for (let i = 0; i < packEquipTable.getDataCount("active"); i++) {
								if (packEquipTable.getData()[i].workOrder_SubCode == cell.getRow().getData().workOrder_SubCode) {
									if (packEquipTable.getData()[i].workOrder_ItemCode != cell.getRow().getData().workOrder_ItemCode) {
										alert("동일한 라벨 프린터는 제품이 같아야 합니다.");
										packEquipTable.getRows("selected")[0].update({
											"workOrder_SubName": null
										});
										return;
									}
								}
							}

							if (cell.getRow().getData().status != 1 && cell.getRow().getData().workOrder_ItemCode == undefined) {
								alert("제품을 먼저 선택하세요.");
								return;
							} else if (cell.getRow().getData().workOrder_ItemCode != undefined) {
								machineSelect(value);
							}
						} else {
							value = value;
						}

						return value;
					},
					editorParams: { values: dtl_arr_material }
				},
				{ title: "설비명", field: "workOrder_SubName", headerHozAlign: "center", hozAlign: "left", width: 100 },
				{ title: "코드", field: "workOrder_ItemCode", headerHozAlign: "center" },
				{ title: "규격2", field: "workOrder_STND_2", headerHozAlign: "center" },
				{ title: "제품명", field: "workOrder_ItemName", headerHozAlign: "center", width: 170 },
				{ title: "분류1", field: "workOrder_Item_CLSFC_1", headerHozAlign: "center" },
				{ title: "분류2", field: "workOrder_Item_CLSFC_2", headerHozAlign: "center", hozAlign: "left" },
				{ title: "규격1", field: "workOrder_STND_1", headerHozAlign: "center" },
				{ title: "재질", field: "workOrder_Material", headerHozAlign: "center" },
				{ title: "단위", field: "workOrder_Unit", headerHozAlign: "center" },
				{ title: "상태", field: "status", headerHozAlign: "center", visible: false }
			]
		}
	]
});


function machineSelect(val) {

	datas = {
		machineCode: val
	}

	$.ajax({
		method: "get",
		url: "machineManageRest/selectMachineInfo",
		data: datas,
		contentType: 'application/json',
		success: function(result) {
			console.log(result);
			packEquipTable.getRows("selected")[0].update({
				"workOrder_SubName": result.equipment_INFO_NAME
			});
			packEquipTable.deselectRow();

		}
	});
}

$('#PRODUCT_ITEM_NAME').keypress(function(e) {
	if (e.keyCode == 13) {
		let value = $(this).val()

		let maskSelectedData = maskEquipTable.getData("selected");

		if (maskSelectedData.length < 1) {
			alert("마스크 설비를 선택하세요.");
			return
		} else {

			//내용이 있을경우 검색해서 값이 하나일경우 생략, 아닐경우 팝업창
			$.ajax({
				method: "GET",
				url: "product_check?PRODUCT_ITEM_CODE=" + value,
				dataType: "json",
				success: function(data) {
					//검색어와 일치하는값이 없는경우, 팝업창
					if (maskSelectedData.length > 0) {
						itemPopup(value, 'workOrder', '', 'workMask');
					}
				}
			})
		}
	}
})

$('#PRODUCT_ITEM_NAME2').keypress(function(e) {
	if (e.keyCode == 13) {
		let value = $(this).val()

		let packSelectedData = packEquipTable.getData("selected");

		if (packSelectedData.length < 1) {
			alert("포장 설비를 선택하세요.");
			return
		} else {
			//내용이 있을경우 검색해서 값이 하나일경우 생략, 아닐경우 팝업창
			$.ajax({
				method: "GET",
				url: "product_stnd2_check?Product_Stnd2=" + value,
				dataType: "json",
				success: function(data) {
					//검색어와 일치하는값이 없는경우, 팝업창
					if (packSelectedData.length > 0) {
						itemPopup(value, 'workOrder', '', 'workNonMask');
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
	let maskData = maskEquipTable.getDataCount();

	// 라벨 코드가 같은 것이 있는 지 검색
	for (let i = 0; i < packEquipTable.getDataCount("active"); i++) {
		for (let j = 0; j < packEquipTable.getDataCount("active"); j++) {
			if (packEquipTable.getData()[i].workOrder_SubCode == packEquipTable.getData()[j].workOrder_SubCode) {
				if (packEquipTable.getData()[i].workOrder_SubCode != undefined) {
					if (packEquipTable.getData()[i].workOrder_ItemCode != packEquipTable.getData()[j].workOrder_ItemCode) {
						alert("동일한 라벨 프린터는 제품이 같아야 합니다.");
						return;
					}
				}
			}
		}
	}

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
			}
		}
	});
}

$('#WO_SaveBtn').click(function() {
	WO_Save();
})

function itemDelBtn() {
	// 보낼 데이터를 전부 담을 배열(조건에 부합하는 데이터 전부)
	let selectedData = new Array();

	// 검증
	let packData = packEquipTable.getRows("selected");
	let maskData = maskEquipTable.getRows("selected");

	// 배열에 데이터 담기
	for (let i = 0; i < maskData.length; i++) {
		maskEquipTable.updateRow(maskEquipTable.getRows("selected")[i], { "workOrder_ItemCode": null });
		selectedData.push(maskEquipTable.getData("selected")[i]);
	}

	for (let k = 0; k < packData.length; k++) {
		packEquipTable.updateRow(packEquipTable.getRows("selected")[k], { "workOrder_ItemCode": null });
		selectedData.push(packEquipTable.getData("selected")[k]);
	}

	if (selectedData.length == 0) {
		alert("삭제할 행이 없습니다.");
		return;
	}

	//작업지시 등록된 제품 삭제
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
				alert("오류가 발생했습니다. 다시 선택해주세요.");
			} else if (result == 1) {
				alert("저장되었습니다.");
				maskEquipTable.replaceData();
				packEquipTable.replaceData();
			}
		}
	});
}

