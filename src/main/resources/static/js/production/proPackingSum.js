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
		startDate : $('#startDate').val(),
		endDate : $('#endDate').val(),
		MachineCode : $('#EQUIPMENT_INFO_CODE').val(),
		MachineName : $('#EQUIPMENT_INFO_NAME').val()
	}

	proPackingTable.setData('smallPackagingRest/smallPackagingList', jsonData);
	
}

$('#SearchBtn').click(function(){
	Search();
})

$('#EQUIPMENT_INFO_NAME').keypress(function(e){
	if(e.keyCode==13) {
		var value = $(this).val()
		//내용이 있을경우 검색해서 값이 하나일경우 생략, 아닐경우 팝업창
		$.ajax({
			method: "GET",
			url: "equipment_check?EQUIPMENT_INFO_CODE=" + value,
			dataType: "json",
			success: function(data) {
				if (data.length == 1) {
					//검색어와 일치하는값이 있는경우
					$('#EQUIPMENT_INFO_CODE').val(data[0].equipment_INFO_CODE)
					$('#EQUIPMENT_INFO_NAME').val(data[0].equipment_INFO_NAME)
				} else {
					//검색어와 일치하는값이 없는경우, 팝업창
					machinePopup(value,'input','','label')
				}
			}
		})
	}
})

var proPackingTable = new Tabulator("#proPackingTable", {
	layoutColumnsOnNewData : true,
    //Sub Total 색상
	rowFormatter: function(row){
		if(row.getData().production_WorkOrder_ONo == "Sub Total"){
            row.getElement().style.backgroundColor = "#D8D8D8";
            }
    },
    headerFilterPlaceholder: null,
	height:"calc(100% - 175px)",
 	columns:[ //Define Table Columns
		{ title: "순번", field: "rownum", formatter: "rownum", hozAlign: "center" },
		{ title: "LotNo", field: "small_Packaging_LotNo", headerHozAlign: "center", headerFilter: true,
			bottomCalc:function(){return "Grand Total"}},
		{ title: "설비 코드", field: "machineCode", headerHozAlign: "center", headerFilter: true},
		{ title: "설비 명", field: "machineName", headerHozAlign: "center", headerFilter: true },
		{ title: "제품 코드", field: "itemCode", headerHozAlign: "center", headerFilter: true},
		{ title: "제품명", field: "itemName", headerHozAlign: "center", headerFilter: true },
		{ title: "규격1", field: "itemSTND1", headerHozAlign: "center", headerFilter: true},
		{ title: "규격2", field: "itemSTND2", headerHozAlign: "center", headerFilter: true},
		{ title: "품목 분류1", field: "itemClsfc1", headerHozAlign: "center", headerFilter: true },
		{ title: "품목 분류2", field: "itemClsfc2", headerHozAlign: "center", headerFilter: true },
		{ title: "재질", field: "itemMaterial", headerHozAlign: "center", headerFilter: true },
		{ title: "생산 수량", field: "qty", headerHozAlign: "center", hozAlign: "right",
			bottomCalc:"sum", bottomCalcFormatter : "money", bottomCalcFormatterParams: {precision: false}},
		{ title: "시간", field: "create_Date", headerHozAlign: "center", formatter:"datetime", formatterParams:{
    		outputFormat:"YYYY-MM-DD HH:mm" } }
 	],
});
