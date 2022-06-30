var lotIssueMasterTable = new Tabulator("#lotIssueMasterTable", { 
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
	{title:"순번", field:"rownum", formatter:"rownum", hozAlign:"center"},
	{title:"LotNo", field:"li_LotNo", headerHozAlign:"center", headerFilter: true},
 	{title:"제품 코드", field:"li_ItemCode", headerHozAlign:"center", headerFilter: true},
 	{title:"제품 명", field:"li_ItemName", headerHozAlign:"center", headerFilter: true},
 	{title:"수량", field:"li_Qty", headerHozAlign:"center",hozAlign:"right"},
 	{title:"규격 1", field:"li_Item_STND_1", headerHozAlign:"center", headerFilter: true},
 	{title:"품목분류 1", field:"li_Item_Clsfc_1", headerHozAlign:"center", headerFilter: true},
 	{title:"품목분류 2", field:"li_Item_Clsfc_2", headerHozAlign:"center", headerFilter: true},
 	{title:"재질", field:"li_Item_Material", headerHozAlign:"center", headerFilter: true},
 	{title:"상태", field:"li_Status", headerHozAlign:"center", headerFilter: true},
 	{title:"발행일", field:"li_Create_Date", headerHozAlign:"center"
 	, formatter:"datetime", formatterParams:{
    		outputFormat:"YYYY-MM-DD HH:mm" }}
 	],
});

//검색
function lotIssue_Search() {
	if ($('#startDate').val().length < 10) {
		alert("시작일은 반드시 입력하여 주십시오.");
		return;
	}
3
	if ($('#endDate').val().length < 10) {
		alert("끝일은 반드시 입력하여 주십시오.");
		return;
	}
	
	console.log(document.querySelector('#lotTypeListBox').value);
	
	let lotType = null;
	
	if(document.querySelector('#lotTypeListBox').value == 'mask') {
		lotType = 'mask';
	} else {
		lotType = 'packing';
	}

	var jsonData = {
		lotType: lotType,
		subType: document.querySelector('#lotTypeListBox').value,
		startDate : $('#startDate').val(),
		endDate : $('#endDate').val(),
		itemCode : $('#PRODUCT_ITEM_CODE').val(),
		LotNo: document.querySelector('#lotIssueLotNo').value
	}

	lotIssueMasterTable.setData('proListRest/LotIssueList', jsonData);
	
	console.log(lotIssueMasterTable);
}


$('#lotIssue_SearchBtn').click(function(){
	lotIssue_Search();
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