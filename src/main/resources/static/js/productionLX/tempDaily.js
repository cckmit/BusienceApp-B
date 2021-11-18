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
		EQUIPMENT_INFO_CODE : $('#EQUIPMENT_INFO_CODE').val(),
		EQUIPMENT_INFO_NAME : $('#EQUIPMENT_INFO_NAME').val()
	}

	proMachineTable.setData('proSumLXRest/proMachineSumSelect2', jsonData);
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
			url: "product_check?PRODUCT_ITEM_CODE=" + value,
			dataType: "json",
			success: function(data) {
				if (data.length == 1) {
					//검색어와 일치하는값이 있는경우
					$('#EQUIPMENT_INFO_CODE').val(data[0].equipment_INFO_CODE)
					$('#EQUIPMENT_INFO_NAME').val(data[0].equipment_INFO_NAME)
				} else {
					//검색어와 일치하는값이 없는경우, 팝업창
					machinePopup(value,'input','','sales')
				}
			}
		})
	}
})

var proMachineTable = new Tabulator("#proMachineTable", {
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
	height:"calc(100% - 175px)",
 	columns:[ //Define Table Columns
	{title:"순번", field:"rownum", formatter:"rownum", hozAlign:"center"},
	{title:"작업지시번호", field:"production_WorkOrder_ONo", headerHozAlign:"center"},
 	{title:"설비 코드", field:"production_EQUIPMENT_CODE", headerHozAlign:"center"},
 	{title:"설비 명", field:"production_EQUIPMENT_INFO_NAME", headerHozAlign:"center"},
 	{title:"생산 수량", field:"production_P_Qty", headerHozAlign:"center",hozAlign:"right"},
 	{title:"품목 코드", field:"production_PRODUCT_CODE", headerHozAlign:"center"},
 	{title:"품목명", field:"product_ITEM_NAME", headerHozAlign:"center"},
 	{title:"시간", field:"production_Date", headerHozAlign:"center"}
 	],
});
