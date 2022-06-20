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

	proMaskTable.setData('proSumRest/proMaskSumSelect', jsonData);
	
	console.log(proMaskTable);
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
					machinePopup(value,'input','')
				}
			}
		})
	}
})

var proMaskTable = new Tabulator("#proMaskTable", {
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
 	{title:"설비 코드", field:"production_Equipment_Code", headerHozAlign:"center"},
 	{title:"설비 명", field:"production_Equipment_Name", headerHozAlign:"center"},
 	{title:"생산 수량", field:"production_Qty", headerHozAlign:"center",hozAlign:"right"},
 	{title:"품목 코드", field:"production_Product_Code", headerHozAlign:"center"},
 	{title:"품목명", field:"production_Product_Name", headerHozAlign:"center"},
	{title:"규격 1", field:"production_Info_STND_1", headerHozAlign:"center"},
 	{title:"품목분류 1", field:"production_Item_CLSFC_NAME_1", headerHozAlign:"center"},
 	{title:"품목분류 2", field:"production_Item_CLSFC_NAME_2", headerHozAlign:"center"},
 	{title:"재질", field:"production_Material", headerHozAlign:"center"},
 	{title:"작업시작시간", field:"production_Start_Date", headerHozAlign:"center"},
 	{title:"작업완료시간", field:"production_Date", headerHozAlign:"center"},
 	{title:"작업시간", field:"production_Total_Work_Time", headerHozAlign:"center"}
 	],
});
