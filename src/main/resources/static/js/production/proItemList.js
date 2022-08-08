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
		itemCode : $('#PRODUCT_ITEM_CODE').val(),
		itemName : $('#PRODUCT_ITEM_NAME').val()
	}
	
	proItemListTable.setData('/proSumRest/proItemSumSelect2', jsonData);
}

$('#SearchBtn').click(function(){
	Search();
})

$('#PRODUCT_ITEM_NAME').keypress(function(e){
	if(e.keyCode==13) {
		var value = $(this).val()
		//내용이 있을경우 검색해서 값이 하나일경우 생략, 아닐경우 팝업창
		$.ajax({
			method: "GET",
			url: "product_check?PRODUCT_ITEM_CODE=" + value,
			dataType: "json",
			success: function(data) {
				if (data.length == 1) {
					//검색어와 일치하는값이 있는경우
					$('#PRODUCT_ITEM_CODE').val(data[0].product_ITEM_CODE)
					$('#PRODUCT_ITEM_NAME').val(data[0].product_ITEM_NAME)
				} else {
					//검색어와 일치하는값이 없는경우, 팝업창
					itemPopup(value,'input','','sales');
				}
			}
		})
	}
})

var proItemListTable = new Tabulator("#proItemListTable", { 
	//페이징
	pagination:"local",
	paginationSize:20,
	layoutColumnsOnNewData : true,
    //Sub Total 색상
	rowFormatter: function(row){
		if(row.getData().production_WorkOrder_ONo == "Sub Total"){
            row.getElement().style.backgroundColor = "#D8D8D8";
            }
    },
	headerFilterPlaceholder: null,
	height:"calc(100% - 175px)",
 	columns:[
		{ title: "순번", field: "rownum", formatter: "rownum", hozAlign: "center" },
		{ title: "LotNo", field: "cl_LotNo", headerHozAlign: "center", headerFilter: true},
		{ title: "제품 코드", field: "cl_ItemCode", headerHozAlign: "center", headerFilter: true},
		{ title: "제품명", field: "cl_ItemName", headerHozAlign: "center", headerFilter: true },
		{ title: "규격1", field: "cl_STND_1", headerHozAlign: "center", headerFilter: true},
		{ title: "규격2", field: "cl_STND_2", headerHozAlign: "center", headerFilter: true},
		{ title: "품목 분류1", field: "cl_Item_Clsfc_Name_1", headerHozAlign: "center", headerFilter: true },
		{ title: "품목 분류2", field: "cl_Item_Clsfc_Name_2", headerHozAlign: "center", headerFilter: true },
		{ title: "재질", field: "cl_Item_Material", headerHozAlign: "center", headerFilter: true },
		{ title: "생산 수량", field: "cl_Qty", headerHozAlign: "center", hozAlign: "right" },
		{ title: "설비 코드", field: "cl_MachineCode", headerHozAlign: "center", headerFilter: true},
		{ title: "설비 명", field: "cl_EquipName", headerHozAlign: "center", headerFilter: true },
		{ title: "시간", field: "cl_Create_Date", headerHozAlign: "center", formatter:"datetime", formatterParams:{
    		outputFormat:"YYYY-MM-DD HH:mm" } }
 	]
});
