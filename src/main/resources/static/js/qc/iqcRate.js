function Search() {
	var jsonData = {
		startDate : $('#startDate').val(),
		endDate : $('#endDate').val(),
		itemCode : $('#itemCode1').val()
	}
	
	iqcRateTable.setData('/iqcRateRest/iqcRateSelect', jsonData);
}

$('#SearchBtn').click(function(){
	Search();
})

var iqcRateTable = new Tabulator("#iqcRateTable", { 
	layoutColumnsOnNewData : true,
	headerFilterPlaceholder: null,
	height:"calc(100% - 175px)",
 	columns:[
		{ title: "순번", field: "rownum", formatter: "rownum", hozAlign: "center" },
		{ title: "품목코드", field: "itemCode", headerHozAlign: "center", headerFilter: true},
		{ title: "품목명", field: "itemName", headerHozAlign: "center", headerFilter: true},
		{ title: "규격1", field: "stnd_1", headerHozAlign: "center", headerFilter: true},
		{ title: "규격2", field: "stnd_2", headerHozAlign: "center", headerFilter: true},
		{ title: "자재분류", field: "mtrl_Clsfc_Name", headerHozAlign: "center", headerFilter: true},
		{ title: "분류1", field: "clsfc_1_Name", headerHozAlign: "center", headerFilter: true},
		{ title: "분류2", field: "clsfc_2_Name", headerHozAlign: "center", headerFilter: true},
		{ title: "합격수량", field: "pass_Qty", headerHozAlign: "center", hozAlign: "right"},
		{ title: "불량수량", field: "fail_Qty", headerHozAlign: "center", hozAlign: "right"},
		{ title: "검사수량", field: "total_Qty", headerHozAlign: "center", hozAlign: "right"},
		{ title: "합격률", field: "pass_Rate", headerHozAlign: "center", hozAlign: "right",
			formatter: function(cell){return cell.getValue()+"%"}},
 	]
});
