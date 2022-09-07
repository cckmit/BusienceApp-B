var matOutputTable = new Tabulator("#matOutputTable", {
	paginationAddRow: "table",
	height: "calc(100% - 175px)",
	layoutColumnsOnNewData: true,
	headerFilterPlaceholder: null,
	rowDblClick: function(e, row) {

		matOutputTable.deselectRow();
		row.select();
		
		if(!inputDuplCheck(row)){
			matOutputSubTable.addRow({
				"om_ItemCode": row.getData().s_ItemCode,
				"om_ItemName": row.getData().s_ItemName,
				"om_Item_Stnd_1":row.getData().s_Item_Standard_1,
				"om_Item_Stnd_2":row.getData().s_Item_Standard_2,
				"om_Qty": 0,
				"om_Send_Clsfc": '208'
			});
			UseBtn();
		}
	},
	ajaxURL:"matStockRest/matStockSelect",
	ajaxParams: {check : true},
    ajaxConfig:"get",
    ajaxContentType:"json",
	columns: [
		{ title: "순번", field: "rownum", formatter: "rownum", headerHozAlign: "center", hozAlign: "center"},
		{ title: "코드", field: "s_ItemCode", headerHozAlign: "center", headerFilter: true },
		{ title: "품목명", field: "s_ItemName", headerHozAlign: "center", hozAlign: "left", headerFilter: true},
		{ title: "규격1", field: "s_Item_Standard_1", headerHozAlign: "center", headerFilter: true },
		{ title: "규격2", field: "s_Item_Standard_2", headerHozAlign: "center", headerFilter: true },
		//{ title: "단위", field: "s_item_Unit_Name", headerHozAlign: "center", headerFilter: true },
		{ title: "재고", field: "s_Qty", headerHozAlign: "center", headerFilter: true }]
});

function inputDuplCheck(row){
	var selectDataCode = row.getData("selected").s_ItemCode;
	
	var inputDatas = matOutputSubTable.getData();
	
	var idResult = inputDatas.some(function(currentValue, index, array){
		return (currentValue.om_ItemCode == selectDataCode);
	})
	
	if(idResult){
		alert("이미 선택한 코드 입니다.");
		return true
	}else{
		return false
	}
}

function MOS_Search() {
	matOutputTable.setData("matStockRest/matStockSelect")
	.then(function(){
		matOutputSubTable.clearData();
	})
}

$('#MOS_SearchBtn').click(function() {
	MOS_Search();
})

// 출고구분 select를 구성하는 리스트
var output_dtl = dtlSelectList(18);
for(prop in output_dtl){
    if(output_dtl[prop] == "출고반품"){
        delete output_dtl[prop]
    }
}

var matOutputSubTable = new Tabulator("#matOutputSubTable", {
	height: "calc(100% - 175px)",
	layoutColumnsOnNewData: true,
	rowAdded: function(row, cell) {
		row.select();

		//행이 추가되면 첫셀에 포커스
		do {
			setTimeout(function() {
				row.getCell("om_Qty").edit();
				matOutputSubTable.deselectRow();
				row.select();
			}, 100);
		}
		while (row.getData().om_Qty === "0");
	},
	rowClick: function(e, row) {
		matOutputSubTable.deselectRow();
		row.select();
	},
	columns: [
		{ title: "순번", field: "rownum", formatter: "rownum", headerHozAlign: "center", hozAlign: "center" },
		{ title: "코드", field: "om_ItemCode", headerHozAlign: "center" },
		{ title: "품목명", field: "om_ItemName", headerHozAlign: "center", width: 120 },
		{ title: "규격1", field: "om_Item_Stnd_1", headerHozAlign: "center", width: 100 },
		{ title: "규격2", field: "om_Item_Stnd_2", headerHozAlign: "center", width: 100 },
		{ title: "출고수량", field: "om_Qty", headerHozAlign: "center", hozAlign: "right", editor: "input"},
		{ title: "구분", field: "om_Send_Clsfc", headerHozAlign: "center", editor: "select",
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

	for (var i = 0; i < rowData.length; i++) {
		if (rowData[i].s_Qty < rowData[i].om_Qty) {
			alert("재고 수량보다 출고 수량이 더 많습니다.");
			return false;
		}

		if (rowData[i].om_Qty != 0) {
			realData.push(rowData[i]);
		}
	}

	//InputSub 저장부분
	$.ajax({
		method: "post",
		url: "matOutputRest/matOutputLXSave",
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
				alert("중복된 값이 있습니다.");
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