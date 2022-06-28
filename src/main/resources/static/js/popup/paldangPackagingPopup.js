//주소에서 파라미터값을 가져오기 위한 기반
//(input_value,type_value,tab_value,search_value) 값이 들어옴
const url = new URL(window.location.href);
const urlParams = url.searchParams;

$(document).keyup(function(e) {
	if (e.keyCode == 27) {
		exitfrn()
	}
});

// 팝업 종료
function exitfrn() {
	window.close();
}
var paldangPackagingPopupTable = new Tabulator("#paldangPackagingPopupTable", {
	layoutColumnsOnNewData: true,
	keybindings: {
		"navUp": "38",
		"navDown": "40"
	},
	rowClick: function(e, row) {
		row.getTable().deselectRow();
		row.select();
		//팝업창 검색란으로 값이 들어감
		$('#Item_Word').val(row.getData().packaging_Item);
	},
	rowDblClick: function(e, row) {
		//e - the click event object
		//row - row component
		list_select(row)
	},
	height: "100%",
	columns: [
		{ title: "", field: "rownum", formatter: "rownum", hozAlign: "center" },
		{ title: "No", field: "no", headerHozAlign: "center" },
		{ title: "구분", field: "packaging_Clsfc", headerHozAlign: "center" },
		{ title: "타입", field: "packaging_Type", headerHozAlign: "center" },
		{ title: "규격", field: "packaging_Size", headerHozAlign: "center" },
		{ title: "품목", field: "packaging_Item", headerHozAlign: "center" },
		{ title: "소포장", field: "packaging_Small", headerHozAlign: "center" },
		{ title: "대포장", field: "packaging_Large", headerHozAlign: "center" }
	]

})

function list_select(row) {

	window.opener.paldangPackaging_gridInit(row.getData().no);

	exitfrn()
}

//검색
function search() {
	paldangPackagingPopupTable.setData("paldangPackagingListSelect")
		.then(function(data) {
			console.log(data);
			if (paldangPackagingPopupTable.getDataCount() > 0) {
				console.log(paldangPackagingPopupTable.getRows()[0]);
				paldangPackagingPopupTable.getRows()[0].select();
				$("#paldangPackagingPopupTable").focus();
			}
		});
}

$('#searchBtn').click(function() {
	search()
})

$("#paldangPackagingPopupTable").keypress(function(e) {
	if (e.keyCode == 13) {
		list_select(paldangPackagingPopupTable.getSelectedRows()[0]);
	}
})

$("#Item_Word").keypress(function(e) {
	if (e.keyCode == 13) {
		search();
	}
})

$("#item_Type").change(function() {
	search();
})

$(document).ready(function() {
	search();
})